package com.example.myapplication.jump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class BActivity extends AppCompatActivity {

    private Button mbtnJumpBack;
    private TextView mTvJumpBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        mbtnJumpBack=findViewById(R.id.btn_jumpback);
        mTvJumpBack=findViewById(R.id.tv_jumpback);
        Bundle bundle = getIntent().getExtras();
        String name=bundle.getString("name");
        Integer old=bundle.getInt("old");
        mTvJumpBack.setText("name:"+name+"old:"+old);
        mbtnJumpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent =new Intent();
            Bundle bundle1=new Bundle();
            bundle1.putString("title","我回来了");
            intent.putExtras(bundle1);
            setResult(Activity.RESULT_OK,intent);
            finish();//页面结束
            }
        });
    }
}
