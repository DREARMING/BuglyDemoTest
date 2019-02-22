package com.mvcoder.buglydemotest.paging;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mvcoder.buglydemotest.greendao.DaoMaster;
import com.mvcoder.buglydemotest.greendao.DaoSession;


public class DBUtil {

    private DaoSession daoSession;
    private DaoMaster daoMaster;

    private static volatile DBUtil instance;

    public static DBUtil getInstance(){
        if(instance == null){
            synchronized (DBUtil.class){
                if(instance == null){
                    instance = new DBUtil();
                }
            }
        }
        return instance;
    }


    public void init(Context context, String dbname){
        //升级表处理
        DaoMaster.OpenHelper helper = new MySQLiteOpenHelper(context,dbname);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

}