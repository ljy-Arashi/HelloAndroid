package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class CheckBoxActivity extends AppCompatActivity {
    private CheckBox mcb4,mcb5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        mcb4=findViewById(R.id.cb_4);
        mcb5=findViewById(R.id.cb_5);

        mcb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(CheckBoxActivity.this,isChecked?"编程选中":"编程未选中",Toast.LENGTH_SHORT).show();
            }
        });
        mcb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(CheckBoxActivity.this,isChecked?"做饭选中":"做饭未选中",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
