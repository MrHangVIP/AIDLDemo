package com.szh.aidldemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Songzhihang on 2019/4/7.
 */
public class ContentProviderTest extends ContentProvider {

    public static final String AUTHORITY = "com.szh.aidldemo.contentprovidertest.provider";
    private Context context;
    private MyDBHelp dbHelp;

    //添加整形常亮
    public static final int USER_DIR = 0;

    //创建UriMatcher对象
    private static UriMatcher uriMatcher;

    //创建静态代码块
    static {
        //实例化UriMatcher对象
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //可以实现匹配URI的功能
        //参数1：authority 参数2：路径 参数3：自定义代码
        uriMatcher.addURI(AUTHORITY, "", USER_DIR);
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        dbHelp = new MyDBHelp(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Log.i("SZH_AIDL_DB", "ContentProviderTest  query " );
        int code=uriMatcher.match(uri);
        switch (code) {
            case USER_DIR:
                //参数1：表名  参数2：没有赋值的设为空   参数3：插入值
                return dbHelp.query();
            default:
                return dbHelp.query();
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                //参数1：表名  参数2：没有赋值的设为空   参数3：插入值
                dbHelp.insert(contentValues);
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
