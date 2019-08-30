package com.example.myapplication.service.service.practice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.util.ToastUtil;

import java.io.File;

public class DownloadService extends Service {
    private DownLoadTask downloadTask;

    private String downloadUrl;

    private DownloadBinder mBinder = new DownloadBinder();

    /**要让DownloadService可以和活动进行通信，我们又创建了一个DownloadBinder。DownloadBinder中提供了
     * startDownload()、pauseDownload()和cancelDownload()这3个方法，那么顾名思义，它们分别是用于开始下载、暂停下载和取消下载的。*/
    class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownLoadTask(listener);
                downloadTask.execute(downloadUrl);
                //为了让这个下载服务成为一个前台服务，我们还调用了startForeground()方法，这样就会在系统状态栏中创建一个持续运行的通知了
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.
                        LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            }
            if (downloadUrl != null) {
                // 取消下载时需将文件删除，并将通知关闭
                String fileName = downloadUrl.substring(downloadUrl.
                        lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);
                if (file.exists()) {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

        private NotificationManager getNotificationManager() {
            return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        private Notification getNotification(String title, int progress) {
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            builder.setContentIntent(pi);
            builder.setContentTitle(title);
            if (progress >= 0) {
                // 当progress大于或等于0时才需显示下载进度
                builder.setContentText(progress + "%");
                /**参数依次为：进度条最大数值，当前进度，进度是否不确定 如果为确定的进度条：调用setProgress(max, progress, false)来设置通知，
                 *  在更新进度的时候在此发起通知更新progress，并且在下载完成后要移除进度条 ，通过调用setProgress(0, 0, false)既可。*/
                builder.setProgress(100, progress, false);
            }
            return builder.build();
        }

        private DownLoadListener listener = new DownLoadListener() {
            /**用于显示下载进度的通知，然后调用NotificationManager的notify()方法去触发这个通知，这样就可以在下拉状态栏中实时看到当前下载的进度了。*/
            @Override
            public void onProgress(int progress) {
                getNotificationManager().notify(1, getNotification("Downloading...",
                        progress));
            }

            @Override
            public void onSuccess() {
                downloadTask = null;
                // 下载成功时将前台服务通知关闭，并创建一个下载成功的通知
                stopForeground(true);
                getNotificationManager().notify(1, getNotification("Download Success",
                        -1));
                ToastUtil.showMsg(DownloadService.this, "Download Success");
            }

            @Override
            public void onFailed() {
                downloadTask = null;
                // 下载失败时将前台服务通知关闭，并创建一个下载失败的通知
                stopForeground(true);
                getNotificationManager().notify(1, getNotification("Download Failed",
                        -1));
                ToastUtil.showMsg(DownloadService.this, "Download Failed");
            }

            @Override
            public void onPaused() {
                downloadTask = null;
                Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                downloadTask = null;
                stopForeground(true);
                Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).
                        show();
            }
        };
}
