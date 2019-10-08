package com.example.myapplication.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.util.ToastUtil;

public class SQLiteActivity extends AppCompatActivity {

    private EditText nameEdt,ageEdt,idEdt;
    private RadioGroup mRadioGroup;
    private RadioButton maleRadio;
    private String genderStr="男";
    private ListView mListView;
    private StudentDao mStudentDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        nameEdt=findViewById(R.id.name_edt);
        ageEdt=findViewById(R.id.age_edt);
        idEdt=findViewById(R.id.id_edt);
        mRadioGroup=findViewById(R.id.gender_group);
        mListView=findViewById(R.id.listView);
        maleRadio=findViewById(R.id.male);
        mStudentDao=new StudentDao(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i==R.id.male){
                //男
                genderStr="男";
            }else {
                //女
                genderStr="女";
            }
            }
        });
    }

    public void operate(View view){
        /**
         * 数据库存在则直接打开，若不存在则调用数据库创建方法，再打开
         * 数据库存在，版本号升高，则调用数据库升级方法
         * */
        //db.rawQuery() 查询 select * from table
        //db.execSQL(); 添加 删除 修改 创建表
        String path= Environment.getExternalStorageDirectory()+ "/stu.db";
        DBOpenHelper openHelper=new DBOpenHelper(this,path,null,1);
        SQLiteDatabase db=openHelper.getReadableDatabase();
        String nameStr=nameEdt.getText().toString();
        String ageStr=ageEdt.getText().toString();
        String idStr=idEdt.getText().toString();
        switch (view.getId()){
            case R.id.btn_insert:
                //写法1 拼接写法
                //String sql="insert into info_tb(name,age,gender)values('"+nameStr+"',"+ageStr+",'"+genderStr+"')";
               // db.execSQL(sql);
                //写法2 传入数组
                /*String sql2="insert into info_tb(name,age,gender)values(?,?,?)";
                db.execSQL(sql2, new String[]{nameStr,ageStr,genderStr});*/
                //写法3 用API
                /**
                 * 在SQLiteDatabase类下提供四个方法 insert() delete() update() query
                 * 都不需要写sql语句
                 * */
                /**
                 * 参数1 String table  所要操作的数据库表名称
                 * 参数2 String nullColumnHack 可以为空的列 如果参数3是null或者没有数据，则sql语句
                 * 变为insert into info_tb（），Values() 语法上错误
                 * 此时通过参数3指定一个可以为空的列insert into info_tb（可空列），Values(null)
                 * 参数3 ContentValues values
                 * */
//                //insert into 表名（列1，列2），Values（值1，值2）
//                ContentValues values=new ContentValues();
//                //put 键值对  列--值
//                values.put("name",nameStr);
//                values.put("age",ageStr);
//                values.put("gender",genderStr);
//                ////返回值代表插入新行的id
//                long id=db.insert("info_tb",null,values);
//                if (id>0){
//                    ToastUtil.showMsg(SQLiteActivity.this,"数据添加成功 新学员学号为="+id);
//                }else {
//                    ToastUtil.showMsg(SQLiteActivity.this,"数据添加失败");
//                }
                //写法4  封装StudenDao
                int age=0;
                if (!ageStr.equals("")){
                    age=Integer.parseInt(ageStr);
                }
                Student student=new Student(nameStr,age,genderStr);
                mStudentDao.addStudent(student);
                ToastUtil.showMsg(SQLiteActivity.this,"数据添加成功");
                break;
            case R.id.btn_del:
                //写法1
                //如果不带条件会删除所有
//                String sql3="delete from info_tb where _id=?";
//                db.execSQL(sql3,new String[]{idStr});
                //API写法
                //int count=db.delete("info_tb","_id=?",new String[]{idStr});
//                if (count>0){
//                    ToastUtil.showMsg(SQLiteActivity.this,"数据删除成功 影响行数：="+count);
//                }else {
//                    ToastUtil.showMsg(SQLiteActivity.this,"数据删除失败");
//                }
                //StudentDao封装写法
                String[] param=getParams(nameStr,ageStr,idStr);
                mStudentDao.deleteStudent(param[0],param[1]);
                ToastUtil.showMsg(SQLiteActivity.this,"数据删除成功");
                break;
            case R.id.btn_update:
                //写法1
//                String sql4="update info_tb set name=?,age=?,gender=? where _id=?";
//                db.execSQL(sql4,new String[]{nameStr,ageStr,genderStr,idStr});
                //写法2 API
                /**
                 * update参数说明
                 * 　table：表名
                 * 　value：就是ContentValues中的value
                 * 　whereClause：要修改的是哪一列，根据什么修改
                 * 　whereArgs：这里返回的是一个数组对象，根据修改列的值*/
//                ContentValues value=new ContentValues();
////                value.put("name",nameStr);
////                value.put("age",ageStr);
////                value.put("gender",genderStr);
////                int update=db.update("info_tb",value,"_id=?",new String[]{idStr});
////                if (update>0) {
////                    ToastUtil.showMsg(SQLiteActivity.this, "数据更新成功 更新行数：" + update);
////                }
////                else {
////                    ToastUtil.showMsg(SQLiteActivity.this, "数据更新失败" );
////                }
                //写法3 StudentDao
                int ageInt=0,idInt=0;
                if (!ageStr.equals("")){
                    ageInt=Integer.parseInt(ageStr);
                }
                if (!idStr.equals("")){
                    ageInt=Integer.parseInt(idStr);
                }
                Student stu2=new Student(idInt,nameStr,ageInt,genderStr);
                mStudentDao.updateStudent(stu2);
                ToastUtil.showMsg(SQLiteActivity.this, "数据更新成功" );
                break;
            case R.id.btn_select:
                //写法1
//                String sql5="select * from info_tb";
//                if (!idStr.equals("")){
//                    sql5+="where _id="+idStr;
//                }
//                //查询结果
//                Cursor c=db.rawQuery(sql5,null);
                //写法2 用API
                /**
                 * query参数说明
                 * 　table：表名
                 * 　columns：查询的列{"name","age","gender"} 查询所有传入null或者{"*"}
                 * 　selection：根据什么查询  where 条件 列1= ？（值1） and 列2=？（值2） 或者写null 查所有
                 *   selectionArgs：查询的条件的值{"值1","值2"} 或者写null 查所有
                 * 　groupBy：分组  与having合用
                 * 　having：查询条件，这里要区分having与where的区别！分组后通过having去除不符合条件的组
                 * 　orderBy：排序
                 * 　moveToNext()：遍历数据表中的数据
                 * 　cursor：Google工程师封装好的指针对象，用于遍历集合下标*/
               // Cursor c=db.query("info_tb",null,null,null,null,null,null);
                //写法3 封装StudentDao
                String[] valueStr= getParams(nameStr,ageStr,idStr);
                Cursor c;
                if (valueStr[0]==null){
                   c = mStudentDao.getStudent();
                }else {
                    c = mStudentDao.getStudent(valueStr[0],valueStr[1]);
                }
                //SimpleCursorAdapter 要求须有下划线列
                //参数3：数据源 String[] from 表头
                //参数4：数据传入到哪个控件上，传入控件id
                //参数5：FLAG_REGISTER_CONTENT_OBSERVER 数据库变更与画面同步 设置标志用来添加一个监听器，监听着参数cursor的数据是否有更变。
                SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.cursorlist,c,new String[]{"_id","name","age","gender"}
                        ,new int[]{R.id.id_item,R.id.name_item,R.id.age_item,R.id.gender_item}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                mListView.setAdapter(adapter);
                break;
        }
        db.close();
        idEdt.setText("");
        ageEdt.setText("");
        nameEdt.setText("");
        maleRadio.setChecked(true);
    }

    public String[] getParams(String name,String age,String id){
        String[]params=new String[2];
        if(!name.equals(""))
        {
            params[0]="name";
            params[1]=name;
        }else if (!age.equals("")){
            params[0]="age";
            params[1]=age;
        }
        else if (!id.equals("")){
            params[0]="_id";
            params[1]=id;
        }
        return params;
    }
}
