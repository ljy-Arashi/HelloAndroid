package com.example.myapplication.datastorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class DataStorageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnSharedPreference,mBtnFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        mBtnSharedPreference=findViewById(R.id.btn_sharedpreferences);
        mBtnSharedPreference.setOnClickListener(this);
        mBtnFile=findViewById(R.id.btn_file);
        mBtnFile.setOnClickListener(this);

        //申请存储的权限
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()){
            case R.id.btn_sharedpreferences:
                intent=new Intent(DataStorageActivity.this,SharedPreferencesActivity.class);
                break;
            case R.id.btn_file:
                intent=new Intent(DataStorageActivity.this,FileActivity.class);
                break;
        }
        startActivity(intent);
    }
}
