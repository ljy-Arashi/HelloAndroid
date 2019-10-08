package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.util.ArrayList;

public class StudentDao {
    private SQLiteDatabase db;
    public StudentDao(Context context){
        String path= Environment.getExternalStorageDirectory()+ "/stu.db";
        SQLiteOpenHelper helper=new SQLiteOpenHelper(context,path,null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                String sql="create table info_tb(_id integer primary key autoincrement," +
                        "name varchar(30) not null," +
                        "age integer," +
                        "gender varchar(2) not null)";
                sqLiteDatabase.execSQL(sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        db=helper.getReadableDatabase();
    }

    public void addStudent(Student stu){
        String sql="insert into info_tb(name,age,gender)values(?,?,?)";
        db.execSQL(sql,new String[]{stu.getName(),stu.getAge()+"",stu.getGender()});
    }

    //参数是可变str  第一个参数指明条件，第二个参数指明条件值
    public Cursor getStudent(String...strs){
        //1 查询所有
        String sql="select * from info_tb";
        //2 含条件查询（姓名/年龄/编号）
        if(strs.length!=0){
            //int age 加上''不影响查询
            sql+=" where "+strs[0]+"='"+strs[1]+"'";
        }
        Cursor c = db.rawQuery(sql,null);
        return c;
    }

    public ArrayList<Student> getStudentLists(String ...str){
        ArrayList<Student> lists=new ArrayList<>();
        Cursor c=getStudent(str);
        while (c.moveToNext()){
            //columnIndex代表列的索引
            int id=c.getInt(0);
            String name=c.getString(1);
            int age=c.getInt(2);
            String gender=c.getString(3);
            Student s=new Student(id,name,age,gender);
            lists.add(s);
        }
        return lists;
    }

    public void deleteStudent(String...strs){
        String sql="delete from info_tb where "+strs[0]+"='"+strs[1]+"'";
        db.execSQL(sql);
    }
    public void updateStudent(Student stu){
        String sql="update info_tb set name=?,age=?,gender=? where _id=?";
        db.execSQL(sql,new Object[]{stu.getName(),stu.getAge(),stu.getGender(),stu.getId()});
    }
}
