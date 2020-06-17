package com.example.zuoye;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelp extends SQLiteOpenHelper {

    //数据库名称
    private static final String DATA_BASE_NAME = "mydb.db";

    //数据库版本号
    private static final int DATE_BASE_VERSION = 1;
    public static final String TABLE_NAME = "music";
    private final String CREATE_TABLE = "create table " + TABLE_NAME + "(_id integer primary key autoincrement, name text)";

    public DbHelp(Context context) {
        super(context, DATA_BASE_NAME, null, DATE_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
