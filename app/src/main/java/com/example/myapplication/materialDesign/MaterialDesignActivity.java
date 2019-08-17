package com.example.myapplication.materialDesign;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.util.SnackBarUtil;
import com.example.myapplication.util.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MaterialDesignActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    FloatingActionButton mFloatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        Toolbar toolbar=findViewById(R.id.ui_toolbar);
        mDrawerLayout =findViewById(R.id.drawer_layout);
        mNavigationView=findViewById(R.id.nav_view);
        mFloatingActionButton=findViewById(R.id.fab);
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
        mNavigationView.setCheckedItem(R.id.nav_call);//默认选中
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_call:
                        ToastUtil.showMsg(MaterialDesignActivity.this,"call");
                        break;
                    case R.id.nav_friends:
                        ToastUtil.showMsg(MaterialDesignActivity.this,"friends");
                        break;
                    case R.id.nav_location:
                        ToastUtil.showMsg(MaterialDesignActivity.this,"location");
                        break;
                    case R.id.nav_mail:
                        ToastUtil.showMsg(MaterialDesignActivity.this,"mail");
                        break;
                    case R.id.nav_task:
                        ToastUtil.showMsg(MaterialDesignActivity.this,"task");
                        break;
                }
                mDrawerLayout.closeDrawers();//将滑动菜单关闭
                return true;
            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ToastUtil.showMsg(MaterialDesignActivity.this,"喜欢");
                //SnackBar和Toast类似，但是点击显示后会遮挡FloatingActionButton，所以FrameLayout布局要换成CoordinatorLayout
                /*
                 * CoordinatorLayout可以协调其它控件并实现控件之间的联动。
                 * 通过在其直接子View上设置behavior来实现子View的不同交互效果。一般作为一个界面的根布局，来协调AppbarLayout，ToolBarLayout以及ScrollView之间的联动
                 */
                final Snackbar sb=Snackbar.make(view,"关注他吗？",Snackbar.LENGTH_SHORT);
                       View viewSnack = sb.getView();
                       TextView tv = viewSnack.findViewById(R.id.snackbar_text);//获取Snackbar的layout中的控件
                //在其textView里面加图片，也可以通过自定义的SnackBarUtil添加一个ImageView控件
                       Drawable d = ContextCompat.getDrawable(MaterialDesignActivity.this, R.drawable.info_attention);
                       d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
                       tv.setCompoundDrawables(d, null, null, null); // 给TextView左边添加图标
                       tv.setGravity(Gravity.CENTER); // 让文字居中
                        //设置与用户的交互行为 不能设置多个action，否则会被覆盖
                        sb.setAction("关注", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.showMsg(MaterialDesignActivity.this,"已关注");
                            }
                        })
                                //添加出现和消失监听
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        ToastUtil.showMsg(MaterialDesignActivity.this,"消失");
                                    }

                                    @Override
                                    public void onShown(Snackbar transientBottomBar) {
                                        ToastUtil.showMsg(MaterialDesignActivity.this,"显示");
                                    }
                                })
                                .show();//需要show一下
                //自定义SnackBar添加一个action和按钮
                SnackBarUtil.addViewToSnackbar(sb,R.layout.mysnackbar,1);
                SnackBarUtil.setAction(sb, R.id.snackbar_cancel_btn, "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sb.dismiss();//消失
                    }
                });
            }
        });
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
