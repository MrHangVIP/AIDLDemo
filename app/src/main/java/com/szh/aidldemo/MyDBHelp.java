package com.szh.aidldemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Songzhihang on 2019/4/7.
 */
public class MyDBHelp extends SQLiteOpenHelper {

    private static final String DB_NAME = "demodb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "phone";

    private final static String CREATE_TBL = "create table phone(_id integer primary key autoincrement, name text)";

    public MyDBHelp(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //插入方法
    public void insert(ContentValues values) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        //插入数据库中
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void providerInsert(Context context, ContentValues values) {
        ContentResolver resolver = context.getContentResolver();
        resolver.insert(Uri.parse("content://" + ContentProviderTest.AUTHORITY), values);
    }
    public void providerQuery(Context context) {
        Log.i("SZH_AIDL_DB", " providerQuery " );
        ContentResolver resolver = context.getContentResolver();
        resolver.query(Uri.parse("content://" + ContentProviderTest.AUTHORITY), null,null,null,null);
    }

    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        //获取Cursor
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        if (c != null) {
            Log.i("SZH_AIDL_DB", " db count = " + c.getCount());
        }
        return c;
    }
}
