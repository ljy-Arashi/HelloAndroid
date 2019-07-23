package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.util.ToastUtil;

public class CustomerDialogActivity extends AppCompatActivity {

    private Button mBtnCustomDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dialog);
        mBtnCustomDialog=findViewById(R.id.btn_customDialog);
        mBtnCustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCustomerDialog customerDialog=new MyCustomerDialog(CustomerDialogActivity.this,R.style.MyDialog);
                customerDialog.setTitle("提示").setMessage("确定删除此选项吗？").setOk("确定", new MyCustomerDialog.IOnOkListener() {
                    @Override
                    public void onOk(MyCustomerDialog myCustomerDialog) {
                        ToastUtil.showMsg(CustomerDialogActivity.this,"已删除");
                    }
                }).setCancel("取消", new MyCustomerDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(MyCustomerDialog myCustomerDialog) {
                        ToastUtil.showMsg(CustomerDialogActivity.this,"已取消");
                    }
                }).show();

            }
        });
    }
}
