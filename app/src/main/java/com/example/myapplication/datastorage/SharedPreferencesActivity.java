package com.example.myapplication.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class SharedPreferencesActivity extends AppCompatActivity {

    private Button mBtnSave,mBtnShow;
    private EditText mEtName;
    private TextView mTvContent;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        mBtnSave=findViewById(R.id.btn_save);
        mBtnShow=findViewById(R.id.btn_show);
        mEtName=findViewById(R.id.et_name);
        mTvContent=findViewById(R.id.tv_show);

        //文件名： data  mode模式 ：MODE_PRIVATE  本应用才可以用
        mSharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过Editor进行数据存储，键值对Key-Value形式 要进行提交commit(同步存储，存储完了才干别的) apply(值立马变，但是存储是异步，在后台进行)
                //推荐使用apply 多进程友好
                //查看存的文件 View-tool windows-divceFile Explorer-data-data-包名-shared_prefs下自己存的文件名data.xml
                mEditor.putString("name",mEtName.getText().toString());
                mEditor.apply();
            }
        });
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //读取一下存好的名字
                //key-name  ,缺省值 如果没有取到传个空值""
                mTvContent.setText(mSharedPreferences.getString("name",""));
            }
        });
    }
}
