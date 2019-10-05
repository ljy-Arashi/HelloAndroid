package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    //环境上下文 ，数据库名称（只有名称，则数据库位置会在私有目录中，如果带SD卡路径，则会在指定路径下）,游标工厂，版本号
    /**
     * 数据库存在则直接打开，若不存在则调用数据库创建方法，再打开
     * 数据库存在，版本号升高，则调用数据库升级方法
     * */
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //数据库不存在的时候，首次创建数据库的时候调用，SQLiteDatabase一般可以执行建库，建表的操作
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table info_tb(_id integer primary key autoincrement," +
                "name varchar(30) not null," +
                "age integer," +
                "gender varchar(2) not null)";
        sqLiteDatabase.execSQL(sql);
    }
    //当数据库的版本发生变化时，会自动执行
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
