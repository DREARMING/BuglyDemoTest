package com.mvcoder.buglydemotest.paging;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mvcoder.buglydemotest.greendao.DaoMaster;


public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       super.onUpgrade(db, oldVersion, newVersion);
       /* MigrationHelper.migrate(db, ClassBuildingDao.class, FloorDao.class, RoomDao.class,
                CollegeDao.class, DepartmentDao.class, MajorDao.class, GradeDao.class,
                SchoolClassDao.class, CourseDao.class, TimeTableDao.class, LessionTimeInfoDao.class,
                TermTableDao.class, UserDao.class, FriendDao.class, DownloadResourceRecordDao.class, TeacherDao.class);*/
    }



}
