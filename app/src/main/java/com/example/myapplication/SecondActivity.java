package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.materialDesign.MaterialDesignActivity;
import com.example.myapplication.okhttp.OKHttpActivity;
import com.example.myapplication.retrofit.RetrofitActivity;
import com.example.myapplication.thread.ThreadActivity;


public class SecondActivity extends AppCompatActivity {

    private Button mBtn_web,mBtn_ui,mBtn_Ev,mBtn_DataStorage,mBtn_http,mBtn_MD,mBtn_retrofit,mBtn_activity;
    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");//接收来自mainActivity Intent的数据
        mBtn_web = (Button) findViewById(R.id.button_2);
        mTv =findViewById(R.id.name_first);
        mTv.append(","+data);
        mBtn_ui= findViewById(R.id.button_ui);
        mBtn_Ev=findViewById(R.id.button_ev);
        mBtn_DataStorage=findViewById(R.id.button_data);
        mBtn_http=findViewById(R.id.button_okhttp);
        mBtn_MD=findViewById(R.id.button_MaterialDesign);
        mBtn_retrofit=findViewById(R.id.button_retrofit);
        mBtn_activity=findViewById(R.id.button_activity);
        SetListenner();

    }

    private void SetListenner() {
        OnClick onClick = new OnClick();
        mBtn_web.setOnClickListener(onClick);
        mBtn_ui.setOnClickListener(onClick);
        mBtn_Ev.setOnClickListener(onClick);
        mBtn_DataStorage.setOnClickListener(onClick);
        mBtn_http.setOnClickListener(onClick);
        mBtn_MD.setOnClickListener(onClick);
        mBtn_retrofit.setOnClickListener(onClick);
        mBtn_activity.setOnClickListener(onClick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("dataReturn");
                    Toast.makeText(SecondActivity.this, returnedData,
                             Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private class OnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            Intent intent=null;
            switch (view.getId())
            {
                case R.id.button_2:
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://www.baidu.com"));
                    startActivity(intent);
                    //Toast.makeText(SecondActivity.this, "You clicked Button 1",
                    // Toast.LENGTH_SHORT).show();
                    // finish();//结束活动→关闭这个画面
                    break;
                case R.id.button_ui:
                    intent = new Intent(SecondActivity.this,ThirdActivity.class);
                    startActivityForResult(intent,1);
                    break;
                case R.id.button_ev:
                    intent = new Intent(SecondActivity.this,EventActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_okhttp:
                    intent = new Intent(SecondActivity.this, OKHttpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_MaterialDesign:
                    intent = new Intent(SecondActivity.this, MaterialDesignActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_retrofit:
                    intent = new Intent(SecondActivity.this, RetrofitActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_activity:
                    intent = new Intent(SecondActivity.this, ThreadActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
