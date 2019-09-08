package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Log.i("MainActivity","onCreate end");
        Button button1 = (Button) findViewById(R.id.button_1);
        Button button2=findViewById(R.id.button_rxjava);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent= new Intent(MainActivity.this,SecondActivity.class); 显式intent
                //隐式intent intent action字符串配置在AndroidManifest.xml文件里
                String data = "Hello Leader";
                Intent intent = new Intent("com.example.myapplication.ACTION_START");
                intent.addCategory("com.example.myapplication.MY_CATEGORY");
                intent.putExtra("extra_data", data);//传输数据
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "You clicked Button 1",
                       // Toast.LENGTH_SHORT).show();
               // finish();//结束活动→关闭这个画面
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, .class);
                //startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
