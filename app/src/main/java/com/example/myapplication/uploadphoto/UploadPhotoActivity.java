package com.example.myapplication.uploadphoto;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UploadPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnTakePhoto,mBtnChooseFromAlbum;
    private ImageView mPicture;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PHOTO = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        mBtnTakePhoto=findViewById(R.id.take_photo);
        mBtnChooseFromAlbum=findViewById(R.id.choose_from_album);
        mPicture=findViewById(R.id.picture);
        mBtnTakePhoto.setOnClickListener(this);
        mBtnChooseFromAlbum.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.take_photo:
            File outPutImag=new File(getExternalCacheDir(),"output_image.jpg");
            if(outPutImag.exists()){
                outPutImag.delete();
            }
                try {
                    outPutImag.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /**如果运行设备的系统版本低于Android 7.0，就调用Uri的fromFile()方法将File对象转换成Uri对象，
                 * 这个Uri对象标识着output_image.jpg这张图片的本地真实路径。否则，就调用FileProvider的getUriForFile()方法将File对象转换成一个封装过的Uri对象
                FileProvider.getUriForFile()方法内容提供器需要注册AndroidManifest.xml   android:name名字com.example.cameraalbumtest.fileprovider*/
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(UploadPhotoActivity.this, "com.example.myapplication.uploadphoto", outPutImag);
                } else {
                    imageUri = Uri.fromFile(outPutImag);
                }

                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//指定图片输出地址
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.choose_from_album:
                /**动态申请WRITE_EXTERNAL_STORAGE这个危险权限。为什么需要申请这个权限呢？因为相册中的照片都是存储在SD卡上的，
                 * 我们要从SD卡中读取照片就需要申请这个权限。WRITE_EXTERNAL_STORAGE表示同时授予程序对SD卡读和写的能力。*/
                if (ContextCompat.checkSelfPermission(UploadPhotoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadPhotoActivity.this, new
                            String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        mPicture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片，需要对这个Uri进行解析才行。
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
            default:
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.
                    getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                        imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.
                        Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mPicture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

}
