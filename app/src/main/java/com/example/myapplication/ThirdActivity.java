package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.eventbus.EventBusAActivity;
import com.example.myapplication.eventbus.MainFgActivity;
import com.example.myapplication.fragment.ContainerActivity;
import com.example.myapplication.gridview.GridViewActivity;
import com.example.myapplication.imageload.UniversalImgLoaderActivity;
import com.example.myapplication.jump.AActivity;
import com.example.myapplication.listview.ListViewActivity;
import com.example.myapplication.picasso.PicassoListViewActivity;
import com.example.myapplication.recyclerview.RecyclerViewActivity;

public class ThirdActivity extends AppCompatActivity {
    private Button bt_call;
    private Button bt_up;
    private Button bt_EditText;
    private Button bt_RadioBt;
    private Button bt_CheckBox;
    private Button bt_imageView;
    private Button bt_listView;
    private Button bt_gridView;
    private Button bt_recyclerView;
    private Button bt_webView,bt_toast,bt_alertDialog,bt_progress,bt_customDialog,bt_popUp,bt_jump,bt_fragment;
    private Button bt_imageLoad,bt_Picasso,btn_EventBus,btn_EventBus2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
         bt_call= findViewById(R.id.button_3);
         bt_up= findViewById(R.id.button_5);
         bt_EditText= findViewById(R.id.button_6);
         bt_RadioBt=findViewById(R.id.button_radio);
         bt_CheckBox=findViewById(R.id.button_checkBox);
         bt_imageView=findViewById(R.id.button_imageview);
        bt_listView=findViewById(R.id.button_listview);
        bt_gridView=findViewById(R.id.button_gridview);
        bt_recyclerView=findViewById(R.id.button_recyclerView);
        bt_webView=findViewById(R.id.button_webView);
        bt_toast=findViewById(R.id.button_Toast);
        bt_alertDialog=findViewById(R.id.button_Alert);
        bt_progress=findViewById(R.id.button_ProgressBar);
        bt_customDialog=findViewById(R.id.button_CustomerDialog);
        bt_popUp=findViewById(R.id.button_PopUp);
        bt_jump=findViewById(R.id.button_jump);
        bt_fragment=findViewById(R.id.button_Fragment);
        bt_imageLoad=findViewById(R.id.button_imageLoad);
        bt_Picasso=findViewById(R.id.button_Picasso);
        btn_EventBus=findViewById(R.id.button_EventBus);
        btn_EventBus2=findViewById(R.id.button_EventBus2);
        SetListenner();
   }
   private void SetListenner()
   {
       OnClick onClick=new OnClick();
       bt_call.setOnClickListener(onClick);
       bt_up.setOnClickListener(onClick);
       bt_EditText.setOnClickListener(onClick);
       bt_RadioBt.setOnClickListener(onClick);
       bt_CheckBox.setOnClickListener(onClick);
       bt_imageView.setOnClickListener(onClick);
       bt_listView.setOnClickListener(onClick);
       bt_gridView.setOnClickListener(onClick);
       bt_recyclerView.setOnClickListener(onClick);
       bt_webView.setOnClickListener(onClick);
       bt_toast.setOnClickListener(onClick);
       bt_alertDialog.setOnClickListener(onClick);
       bt_progress.setOnClickListener(onClick);
       bt_customDialog.setOnClickListener(onClick);
       bt_popUp.setOnClickListener(onClick);
       bt_jump.setOnClickListener(onClick);
       bt_fragment.setOnClickListener(onClick);
       bt_imageLoad.setOnClickListener(onClick);
       bt_Picasso.setOnClickListener(onClick);
       btn_EventBus.setOnClickListener(onClick);
       btn_EventBus2.setOnClickListener(onClick);
   }
    private class OnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            Intent intent=null;
            switch (view.getId())
            {
                case R.id.button_3:
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:10086"));
                    startActivity(intent);
                    break;
                case R.id.button_5:
                    intent = new Intent();
                    intent.putExtra("dataReturn","Hello SecondActivity");
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
                case R.id.button_6:
                    intent = new Intent(ThirdActivity.this,EditTextActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_radio:
                    intent = new Intent(ThirdActivity.this,RadioButtonActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_checkBox:
                    intent = new Intent(ThirdActivity.this,CheckBoxActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_imageview:
                    intent = new Intent(ThirdActivity.this,ImageViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_listview:
                    intent = new Intent(ThirdActivity.this, ListViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_gridview:
                    intent = new Intent(ThirdActivity.this, GridViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_recyclerView:
                    intent = new Intent(ThirdActivity.this, RecyclerViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_webView:
                    intent = new Intent(ThirdActivity.this, WebViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_Toast:
                    intent = new Intent(ThirdActivity.this, ToastActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_Alert:
                    intent = new Intent(ThirdActivity.this, AlertDialogActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_ProgressBar:
                    intent = new Intent(ThirdActivity.this, ProgressBarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_CustomerDialog:
                    intent = new Intent(ThirdActivity.this, CustomerDialogActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_PopUp:
                    intent = new Intent(ThirdActivity.this, PopUpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_jump:
                    intent = new Intent(ThirdActivity.this, AActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_Fragment:
                    intent = new Intent(ThirdActivity.this, ContainerActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_imageLoad:
                    intent = new Intent(ThirdActivity.this, UniversalImgLoaderActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_Picasso:
                    intent = new Intent(ThirdActivity.this, PicassoListViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_EventBus:
                    intent = new Intent(ThirdActivity.this, EventBusAActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_EventBus2:
                    intent = new Intent(ThirdActivity.this, MainFgActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
