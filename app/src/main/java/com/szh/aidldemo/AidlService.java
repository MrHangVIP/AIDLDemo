package com.szh.aidldemo;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Songzhihang on 2019/4/7.
 */
public class AidlService extends Service {

    private static final String TAG = "SZH_AIDL";

    private final IBinder myBinder = new MyBinder();
    private MyDBHelp dbHelp;
    private Context context;

    private class MyBinder extends AidlDemoFile.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void chat(String msg) throws RemoteException {
            Log.i(TAG, " service chat msg:" + msg);
        }

        @Override
        public Bundle data(Bundle msg) throws RemoteException {
            Log.i(TAG, " service data pid:" + android.os.Process.myPid()
                    + " tid:" + android.os.Process.myTid());
            if (msg != null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dbHelp.providerQuery(context);
                ContentValues values = new ContentValues();
                values.put("name", msg.getString("aidl"));
                dbHelp.providerInsert(context, values);
                dbHelp.query();
                Log.i(TAG, " service data bundle:" + msg.get("aidl"));
                msg.putString("aidl", "service");
//                Intent intent=new Intent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setClass(context,SecondActivity.class);
//                startActivity(intent);
//                Log.i(TAG, " service data bundle:" + msg.get("aidl"));
            } else {
                Log.i(TAG, " service data is null");
            }
            return msg;
        }
    }


    @Override
    public void onCreate() {
        dbHelp = new MyDBHelp(this);
        context = this;
        dbHelp.providerQuery(context);
        Log.i(TAG, " service onCreate pid:" + android.os.Process.myPid()
                + " tid:" + android.os.Process.myTid());
        super.onCreate();
//        Intent intent=new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setClass(context,SecondActivity.class);
//        startActivity(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, " service onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, " service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, " service onDestroy");
        super.onDestroy();
    }
}
