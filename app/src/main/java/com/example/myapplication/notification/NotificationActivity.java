package com.example.myapplication.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

import java.io.File;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn_notice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mBtn_notice=findViewById(R.id.send_notice);
        mBtn_notice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_notice:
                //用意图传入了NotiContenActivity
                Intent intent=new Intent(this,NotiContenActivity.class);
                /**第四个参数flags用于确定PendingIntent的行为，4种值
                 * FLAG_CANCEL_CURRENT:如果当前系统中已经存在一个相同的 PendingIntent 对象，那么就将先将已有的 PendingIntent 取消，然后重新生成一个 PendingIntent 对象。
                 * FLAG_NO_CREATE:如果当前系统中不存在相同的 PendingIntent 对象，系统将不会创建该 PendingIntent 对象而是直接返回 null 。
                 * FLAG_ONE_SHOT:该 PendingIntent 只作用一次。
                 * FLAG_UPDATE_CURRENT:如果系统中已存在该 PendingIntent 对象，那么系统将保留该 PendingIntent 对象，但是会使用新的 Intent 来更新之前 PendingIntent 中的 Intent 对象数据，例如更新 Intent 中的 Extras 。
                 * 通常情况下这个参数传入0就可以了。*/
                PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
               Notification notification =new NotificationCompat.Builder(this,"default")
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        //设置通知时间，默认为系统发出通知的时间，通常不用设置
                        //于指定通知被创建的时间，以毫秒为单位，当下拉系统状态栏时，这里指定的时间会显示在相应的通知上。
                        .setWhen(System.currentTimeMillis())
                        //点击通知后自动清除
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.notice_message)//设置小图标
                       //设置提示音
                       .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
                       //设置震动
                       /**设置手机静止和振动的时长，以毫秒为单位。下标为0的值表示手机静止的时长，下标为1的值表示手机振动的时长，
                        * 下标为2的值又表示手机静止的时长，以此类推。所以，如果想要让手机在通知到来的时候立刻振动1秒，然后静止1秒，再振动1秒，
                        * 代码就可以写成：  不过，想要控制手机振动还需要声明权限<uses-permission android:name="android.permission.VIBRATE" />。*/
                       .setVibrate(new long[] {0, 1000, 1000, 1000 })
                       /**，第一个参数用于指定LED灯的颜色，第二个参数用于指定LED灯亮起的时长，以毫秒为单位，第三个参数用于指定LED灯暗去的时长，
                        * 也是以毫秒为单位。所以，当通知到来时，如果想要实现LED灯以绿色的灯光一闪一闪的效果，就可以写成：*/
                       .setLights(Color.GREEN, 1000, 1000)
                       /**也可以直接使用通知的默认效果，它会根据当前手机的环境来决定播放什么铃声，以及如何振动，写法如下：*/
                       //.setDefaults(NotificationCompat.DEFAULT_ALL)
                       //设置显示超长文字
//                       .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official"
//                               +"Android IDE and developer tools to build apps for Android."))
                       //显示图片
                       .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                               (BitmapFactory.decodeResource(getResources(), R.drawable.vacation)))
                       /**接下来再学习一下setPriority()方法，它可以用于设置通知的重要程度。setPriority()方法接收一个整型参数用于设置这条通知的重要程度，一共有5个常量值可选：
                        * PRIORITY_DEFAULT表示默认的重要程度，和不设置效果是一样的；PRIORITY_MIN表示最低的重要程度，系统可能只会在特定的场景才显示这条通知，
                        * 比如用户下拉状态栏的时候；PRIORITY_LOW表示较低的重要程度，系统可能会将这类通知缩小，或改变其显示的顺序，将其排在更重要的通知之后；
                        * PRIORITY_HIGH表示较高的重要程度，系统可能会将这类通知放大，
                        * 或改变其显示的顺序，将其排在比较靠前的位置；PRIORITY_MAX表示最高的重要程度，这类通知消息必须要让用户立刻看到，甚至需要用户做出响应操作。具体写法如下：*/
                       //.setPriority(NotificationCompat.PRIORITY_MAX) //特别重要的时候显示不了图
                        //大图标 当下拉系统状态栏时，就可以看到设置的大图标了。
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notice_message_big))
                       //通过PendingIntent构建出一个延迟执行的“意图”，当用户点击这条通知时就会执行相应的逻辑。
                       .setContentIntent(pi)
                        .build();
                //第一个参数是id，要保证为每个通知所指定的id都是不同的。第二个参数则是Notification对象
                manager.notify(1,notification);
                //manager.cancel(1);//和setAutoCancel(true)一个效果传入该通知的id就行了。
                break;
             default:
                 break;
        }
    }
}
