package com.example.zuoye;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHelper extends SQLiteOpenHelper {
    private MyDBHelper dbHelper;
    private Context mContext;
    public static final String CREATE_USERDATA="create table userData(" +
            "_id integer primary key autoincrement,"
            +"name text,"
            +"password text)";
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory,int version){
        super(context,name,cursorFactory,version);
        mContext=context;
    }

    public static final String CREATE="create table userData(" +
            "id integer primary key autoincrement,name text,password text)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
