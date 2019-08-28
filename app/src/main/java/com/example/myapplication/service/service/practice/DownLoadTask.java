package com.example.myapplication.service.service.practice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownLoadListener mListener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownLoadTask(DownLoadListener listener) {
        this.mListener = listener;
    }

    // 方法1：onPreExecute（）
    // 作用：执行 线程任务前的操作
    @Override
    protected void onPreExecute() {
        // UI操作
        //mAsyncText.setText("加载中...");
    }

    // 方法2：doInBackground（）
    // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
    //注：必须复写，从而自定义线程任务
    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
        long downloadedLength = 0; // 记录已下载的文件长度
        String downloadUrl = params[0];
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        file = new File(directory + fileName);
        if (file.exists()){
            downloadedLength=file.length();
        }
            long contentLength = getContentLength(downloadUrl);
            if (contentLength==0){
                return TYPE_FAILED;
            }else if (contentLength==downloadedLength){
                // 已下载字节和文件总字节相等，说明已经下载完成了
            }
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    // 断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Call call=client.newCall(request);
            Response response=call.execute();
            if (response!=null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);// 跳过已下载的字节
                byte[] bytes=new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(bytes))!=-1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total+=len;
                        // 计算已下载的百分比
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }

    // 方法3：onProgressUpdate（）
    // 作用：在主线程 显示线程任务执行的进度
    // 注：根据需求复写
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            mListener.onProgress(progress);
            lastProgress = progress;
        }
    }

    // 方法4：onPostExecute（）
    // 作用：接收线程任务执行结果、将执行结果显示到UI组件
    // 注：必须复写，从而自定义UI操作
    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                mListener.onSuccess();
                break;
            case TYPE_FAILED:
                mListener.onFailed();
                break;
            case TYPE_PAUSED:
                mListener.onPaused();
                break;
            case TYPE_CANCELED:
                mListener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }


    public void cancelDownload() {
        isCanceled = true;
    }
}
