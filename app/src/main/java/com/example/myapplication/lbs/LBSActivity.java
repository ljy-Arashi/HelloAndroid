package com.example.myapplication.lbs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LBSActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private TextView positionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lbs);
        positionText=findViewById(R.id.position_text_view);
        mLocationClient=new LocationClient(getApplicationContext());
        //注册一个定位监听器，当获取到位置信息的时候，就会回调这个定位监听器。
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation bdLocation) {
//                /**通过BDLocation的getLatitude()方法获取当前位置的纬度，通过getLongitude()方法获取当前位置的经度，
//                 * 通过getLocType()方法获取当前的定位方式，最终将结果组装成一个字符串，显示到TextView上面。*/
//                StringBuilder currentPosition = new StringBuilder();
//                currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
//                currentPosition.append("经线：").append(bdLocation.getLongitude()).
//                        append("\n");
//                currentPosition.append("定位方式：");
//                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
//                    currentPosition.append("GPS");
//                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//                    currentPosition.append("网络");
//                }else {
//                    currentPosition.append("其他："+bdLocation.getLocType());//167是无法定位 可能用真机才行
//                }
//                positionText.setText(currentPosition);
                /**调用getCountry()方法可以得到当前所在国家，调用getProvince()方法可以得到当前所在省份，以此类推。另外还有一点需要注意，由于获取地址信息一定需要用到网络，
                 * 因此即使我们将定位模式指定成了Device_Sensors，也会自动开启网络定位功能。*/
                StringBuilder currentPosition = new StringBuilder();
                currentPosition.append("纬度：").append(bdLocation.getLatitude()).
                        append("\n");
                currentPosition.append("经线：").append(bdLocation.getLongitude()).
                        append("\n");
                currentPosition.append("国家：").append(bdLocation.getCountry()).
                        append("\n");
                currentPosition.append("省：").append(bdLocation.getProvince()).
                        append("\n");
                currentPosition.append("市：").append(bdLocation.getCity()).
                        append("\n");
                currentPosition.append("区：").append(bdLocation.getDistrict()).
                        append("\n");
                currentPosition.append("街道：").append(bdLocation.getStreet()).
                        append("\n");
                currentPosition.append("定位方式：");
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                    currentPosition.append("GPS");
                } else if (bdLocation.getLocType() ==
                        BDLocation.TypeNetWorkLocation) {
                    currentPosition.append("网络");
                }
                positionText.setText(currentPosition);
            }
        });
        /**在运行时一次性申请3个权限
         * 首先创建一个空的List集合，然后依次判断这3个权限有没有被授权，如果没被授权就添加到List集合中，
         * 最后将List转换成数组，再调用ActivityCompat.requestPermissions()方法一次性申请。*/
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LBSActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(LBSActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(LBSActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            ActivityCompat.requestPermissions(LBSActivity.this, permissions, 1);
        } else {
            //mLocationClient.start();
            requestLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /**这次我们通过一个循环将申请的每个权限都进行了判断，如果有任何一个权限被拒绝，那么就直接调用finish()方法关闭当前程序，
         * 只有当所有权限都被用户同意了，才会调用requestLocation()方法开始地理位置定位。
         */
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    //调用了一下LocationClient的start()方法就能开始定位了
                    //mLocationClient.start();
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    //正在快速移动中，怎样才能实时更新当前的位置
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    /**可以在initLocation()方法中对百度LBS SDK的定位模式进行指定，一共有3种模式可选：Hight_Accuracy、Battery_Saving和Device_Sensors。
     * Hight_Accuracy表示高精确度模式，会在GPS信号正常的情况下优先使用GPS定位，在无法接收GPS信号的时候使用网络定位。Battery_Saving表示节电模式，
     * 只会使用网络进行定位。Device_Sensors表示传感器模式，只会使用GPS进行定位。其中，Hight_Accuracy是默认的模式，
     * 也就是说，我们即使不修改任何代码，只要拿着手机走到室外去，让手机可以接收到GPS信号，就会自动切换到GPS定位模式了*/
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);//设置更新的间隔
        option.setIsNeedAddress(true);//示我们需要获取当前位置详细的地址信息
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在活动被销毁的时候一定要调用LocationClient的stop()方法来停止定位，不然程序会持续在后台不停地进行定位，从而严重消耗手机的电量。
        mLocationClient.stop();
    }


}
