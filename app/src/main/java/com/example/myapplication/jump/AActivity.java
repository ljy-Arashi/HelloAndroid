package com.example.myapplication.jump;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.util.ToastUtil;

public class AActivity extends AppCompatActivity {

    private Button mbtnJumpGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        mbtnJumpGo=findViewById(R.id.btn_jumpGo);
        mbtnJumpGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //显式1
                Intent intent=new Intent(AActivity.this,BActivity.class);
                //传值
                Bundle bundle=new Bundle();
                bundle.putString("name","大野智");
                bundle.putInt("old",33);
                intent.putExtras(bundle);
                //startActivity(intent);
                startActivityForResult(intent, 0);
                //显式2
//                Intent intent=new Intent();
//                intent.setClass(AActivity.this,BActivity.class);
//                startActivity(intent);
                //显式3
//                Intent intent =new Intent();
//                intent.setClassName(AActivity.this,"com.example.myapplication.jump.BActivity");
//                startActivity(intent);
                //显式4
//                Intent intent =new Intent();
//                intent.setComponent(new ComponentName(AActivity.this,"com.example.myapplication.jump.BActivity"));
//                startActivity(intent);
                //隐式
//                Intent intent=new Intent();
//                intent.setAction("com.ljy.test.BActivity");
//                startActivity(intent);
            }
        });
    }

    //接收返回的值必须重写这个
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtil.showMsg(AActivity.this,data.getExtras().getString("title"));
    }
}
