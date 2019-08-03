package com.example.myapplication.datastorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileActivity extends AppCompatActivity {

    private Button mBtnSave,mBtnShow;
    private EditText mEtName;
    private TextView mTvContent;
    private final String mFileName="test.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        mBtnSave=findViewById(R.id.btn_save);
        mBtnShow=findViewById(R.id.btn_show);
        mEtName=findViewById(R.id.et_name);
        mTvContent=findViewById(R.id.tv_show);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(mEtName.getText().toString().trim());
            }
        });
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvContent.setText(read());
            }
        });
    }
    //存储数据
    private void save(String content){
        FileOutputStream fileOutputStream=null;
        try {
            //文件名： text.txt  mode模式 ：MODE_PRIVATE  本应用才可以用
            //fileOutputStream= openFileOutput(mFileName,MODE_PRIVATE); 内部存储
            //外部存储必须加权限 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
            //在DataStorageActivity动态的申请了权限 版本23以上都要
            //文件夹
            File dir=new File(Environment.getExternalStorageDirectory(),"android_file_save");
            if (!dir.exists()){
                dir.mkdirs();//先建个文件夹
            }
            File file=new File(dir,mFileName);
            if (!file.exists()){
                file.createNewFile();//文件如果没有就新建txt文件
            }
            fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream!=null) {
                try {
                    fileOutputStream.close();//必须关闭一下
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //读取数据
    private String read(){
        FileInputStream fileInputStream=null;
        try {
             //fileInputStream=openFileInput(mFileName); 内部读取
            //外部
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"android_file_save",mFileName);
            fileInputStream=new FileInputStream(file);
            byte[] buff=new byte[1024];//一次次读取 每次1024字节
            //然后拼接
            StringBuilder sb=new StringBuilder("");
            int len=0;
            while ((len=fileInputStream.read(buff))>0){
                sb.append(new String(buff,0,len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        if (fileInputStream!=null){
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
        return null;
    }
}
