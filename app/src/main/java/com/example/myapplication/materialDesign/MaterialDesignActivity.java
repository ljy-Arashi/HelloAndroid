package com.example.myapplication.materialDesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.R;

public class MaterialDesignActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        Toolbar toolbar=findViewById(R.id.ui_toolbar);
        mDrawerLayout =findViewById(R.id.drawer_layout);
        toolbar.setTitle("FaceBook");//设置标题
        toolbar.setLogo(R.drawable.facebook_logo);//设置logo
        //toolbar.setSubtitle("by Arashi");//设置副标题
        //toolbar.setBackgroundResource(int resid);//设置背景
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.toolbar_menu));//设置菜单按钮样式
        //需要在代码中进行申明, 将actionbar替换成toolbar, 然后我们才可以对它进行一些操作
        setSupportActionBar(toolbar);
        //toolbar左边设置home键
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置返回home图标
            actionBar.setHomeAsUpIndicator(R.drawable.toolbar_menu2);
        }
    }

    //添加menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    //menu点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://按toolbar左边home键
                mDrawerLayout.openDrawer(GravityCompat.START);//左边滑动控件显示
                break;
            case R.id.action_backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.action_delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).
                        show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).
                        show();
                break;
            default:
        }
        return true;
    }
}