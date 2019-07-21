package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.util.ToastUtil;

public class AlertDialogActivity extends AppCompatActivity {

    private Button mBtnDialog1,mBtnDialog2,mBtnDialog3,mBtnDialog4,mBtnDialog5;
    private EditText mUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        mBtnDialog1=findViewById(R.id.btn_dialog1);
        mBtnDialog2=findViewById(R.id.btn_dialog2);
        mBtnDialog3=findViewById(R.id.btn_dialog3);
        mBtnDialog4=findViewById(R.id.btn_dialog4);
        mBtnDialog5=findViewById(R.id.btn_dialog5);
        OnClick onClick=new OnClick();
        mBtnDialog1.setOnClickListener(onClick);
        mBtnDialog2.setOnClickListener(onClick);
        mBtnDialog3.setOnClickListener(onClick);
        mBtnDialog4.setOnClickListener(onClick);
        mBtnDialog5.setOnClickListener(onClick);
    }

    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_dialog1:
                    AlertDialog.Builder builder=new AlertDialog.Builder(AlertDialogActivity.this);
                    builder.setTitle("请回答").setMessage("你觉得App如何？")
                            .setIcon(R.drawable.icon_smile)
                            .setPositiveButton("棒", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ToastUtil.showMsg(AlertDialogActivity.this,"你很诚实");
                                }
                            }).setNeutralButton("还行", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this,"你再瞅瞅");
                        }
                    }).setNegativeButton("不好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this,"睁眼说瞎话");
                        }
                    }).show();
                    break;
                case R.id.btn_dialog2:
                    //单选
                    final String[] array=new String[]{"男","女"};
                    AlertDialog.Builder builder1=new AlertDialog.Builder(AlertDialogActivity.this);
                    builder1.setTitle("选择性别").setItems(array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this,array[i]);
                        }
                    }).show();
                    break;
                case R.id.btn_dialog3:
                    //radioButton
                    final String[] array2=new String[]{"男","女"};
                    AlertDialog.Builder builder2=new AlertDialog.Builder(AlertDialogActivity.this);
                    builder2.setTitle("请选择性别").setSingleChoiceItems(array2, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ToastUtil.showMsg(AlertDialogActivity.this,array2[i]);ToastUtil.showMsg(AlertDialogActivity.this,array2[i]);
                            dialogInterface.dismiss();//选择完成后dialog消失
                        }
                    }).setCancelable(false).show();//setCancelable(false)点旁边空白处不会消失
                    break;
                case R.id.btn_dialog4:
                    //多选
                    final String[] array3=new String[]{"唱歌","跳舞","写代码"};
                    boolean[] isSelected=new boolean[]{false,false,true};
                    AlertDialog.Builder builder3=new AlertDialog.Builder(AlertDialogActivity.this);
                    builder3.setTitle("选择兴趣").setMultiChoiceItems(array3, isSelected, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            ToastUtil.showMsg(AlertDialogActivity.this,array3[i]+":"+b);
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //todo
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //todo
                        }
                    }).show();
                    break;
                    case  R.id.btn_dialog5:
                        AlertDialog.Builder builder4=new AlertDialog.Builder(AlertDialogActivity.this);
                        View view1=  LayoutInflater.from(AlertDialogActivity.this).inflate(R.layout.layout_dialog,null);
                        mUserName=view1.findViewById(R.id.ad_username);
                        EditText psw=view1.findViewById(R.id.ad_password);
                        Button btnLogin=view1.findViewById(R.id.ad_login);

                        btnLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.showMsg(AlertDialogActivity.this,"用户"+mUserName.getText().toString()+"欢迎您");
                            }
                        });
                        builder4.setTitle("请先登录").setView(view1).show();
                        break;
            }

        }
    }
}
