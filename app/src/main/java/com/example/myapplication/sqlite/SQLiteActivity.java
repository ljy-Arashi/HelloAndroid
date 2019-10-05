package com.example.myapplication.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.util.ToastUtil;

public class SQLiteActivity extends AppCompatActivity {

    private EditText nameEdt,ageEdt,idEdt;
    private RadioGroup mRadioGroup;
    private String genderStr="男";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        nameEdt=findViewById(R.id.name_edt);
        ageEdt=findViewById(R.id.age_edt);
        idEdt=findViewById(R.id.id_edt);
        mRadioGroup=findViewById(R.id.gender_group);
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
                break;
            case R.id.btn_update:
                break;
            case R.id.btn_select:
                break;
        }
        db.close();
    }
}
