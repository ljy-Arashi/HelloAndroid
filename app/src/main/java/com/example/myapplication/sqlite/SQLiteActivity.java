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
                String sql2="insert into info_tb(name,age,gender)values(?,?,?)";
                db.execSQL(sql2, new String[]{nameStr,ageStr,genderStr});
                ToastUtil.showMsg(SQLiteActivity.this,"数据添加成功");
                break;
            case R.id.btn_del:
                //如果不带条件会删除所有
                String sql3="delete from info_tb where _id=?";
                db.execSQL(sql3,new String[]{idStr});
                ToastUtil.showMsg(SQLiteActivity.this,"数据删除成功");
                break;
            case R.id.btn_update:
                String sql4="update info_tb set name=?,age=?,gender=? where _id=?";
                db.execSQL(sql4,new String[]{nameStr,ageStr,genderStr,idStr});
                ToastUtil.showMsg(SQLiteActivity.this,"数据更新成功");
                break;
            case R.id.btn_select:
                String sql5="select * from info_tb";
                if (!idStr.equals("")){
                    sql5+="where _id="+idStr;
                }
                //查询结果
                Cursor c=db.rawQuery(sql5,null);
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
}
