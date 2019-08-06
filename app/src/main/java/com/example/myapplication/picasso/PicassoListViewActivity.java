package com.example.myapplication.picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.listview.ListViewActivity;

import java.util.ArrayList;
import java.util.List;

public class PicassoListViewActivity extends AppCompatActivity {

    private ListView mLv_p;
    private List<PicassoBean> mLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_list_view);
        initData();
        mLv_p=findViewById(R.id.lv_Picasso);
        mLv_p.setAdapter(new MyAdapterPicasso(this,mLists));
        mLv_p.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PicassoListViewActivity.this,"点击position:"+i,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData(){
        if(mLists==null)
        {
            mLists=new ArrayList<PicassoBean>();
        }
        PicassoBean pb1=new PicassoBean("Web-肖战","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565109408242&di=51d7cf0663208414febf2b5ef31c7bb0&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201805%2F06%2F20180506235254_PaY8M.jpeg");
        this.mLists.add(pb1);
        PicassoBean pb2=new PicassoBean("Web-排球少年","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565109530036&di=34528f08d1bbf47fa7e7b045a3b96975&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161227%2Fddc403e718c742659cc7cc956e39052f_th.jpg");
        this.mLists.add(pb2);
        PicassoBean pb3=new PicassoBean("assets-鬼灭之刃","file:///android_asset/guimie.jpg");
        this.mLists.add(pb3);
    }
}
