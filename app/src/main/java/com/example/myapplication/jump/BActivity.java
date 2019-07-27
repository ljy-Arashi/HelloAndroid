package com.example.myapplication.jump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class BActivity extends AppCompatActivity {

    private Button mbtnJumpBack,mbtnJumpBack2;
    private TextView mTvJumpBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Log.d("BActivity", "------onCreate----");
        Log.d("BActivity", "taskID:" + getTaskId() + ",hash" + hashCode());//hashcode标识是不是新的
        logtaskName();
        mbtnJumpBack = findViewById(R.id.btn_jumpback);
        mTvJumpBack = findViewById(R.id.tv_jumpback);
        mbtnJumpBack2=findViewById(R.id.btn_jumpbackTo);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        Integer old = bundle.getInt("old");
        mTvJumpBack.setText("name:" + name + "old:" + old);
        mbtnJumpBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BActivity.this,AActivity.class);
                startActivity(intent);
            }
        });
        mbtnJumpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "我回来了");
                intent.putExtras(bundle1);
                setResult(Activity.RESULT_OK, intent);
                finish();//页面结束
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("BActivity", "------onNewIntent-----");
        Log.d("BActivity", "taskID:" + getTaskId() + ",hash" + hashCode());//hashcode标识是不是新的
        logtaskName();
    }

    private void logtaskName() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Log.d("BActivity", info.taskAffinity);//当前activity所在任务栈名称
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
