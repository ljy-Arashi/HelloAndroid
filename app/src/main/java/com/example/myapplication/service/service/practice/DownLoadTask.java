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

/**一个字符串参数给后台任务；第二个泛型参数指定为Integer，表示使用整型数据来作为进度显示单位；第三个泛型参数指定为Integer，则表示使用整型数据来反馈执行结果。*/
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
    // 作用：接收输入参数DownLoadTask.execute(参数);、执行任务中的耗时操作、返回 线程任务执行的结果
    //注：必须复写，从而自定义线程任务
    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        //RandomAccessFile类可以写入和读取文件，其最大的特点就是可以在任意位置读取文件(random access的意思)，是通过文件指针实现的。
        RandomAccessFile savedFile = null;
        File file = null;
        try {
        long downloadedLength = 0; // 记录已下载的文件长度
        String downloadUrl = params[0];//从参数中获取到了下载的URL地址
        // 并根据URL地址解析出了下载的文件名
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        //SD卡的Download目录
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        file = new File(directory + fileName);
        if (file.exists()){
            downloadedLength=file.length();//如果已经存在的话则读取已下载的字节数，这样就可以在后面启用断点续传的功能。
        }
            long contentLength = getContentLength(downloadUrl);//下载总长度
            if (contentLength==0){
                return TYPE_FAILED;
            }else if (contentLength==downloadedLength){
                // 已下载字节和文件总字节相等，说明已经下载完成了
            }
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    // 断点下载， 请求中添加了一个header，用于告诉服务器我们想要从哪个字节开始下载，因为已下载过的部分就不需要再重新下载了
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Call call=client.newCall(request);
            Response response=call.execute();
            if (response!=null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file,"rw");//“rw”：以读、写方式打开，支持文件的读取或写入。若文件不存在，则创建之。
                savedFile.seek(downloadedLength);// 跳过已下载的字节 将文件记录指针定位到下载的位置，继续下载后面内容
                byte[] bytes=new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(bytes))!=-1){
                    //在这个过程中，我们还要判断用户有没有触发暂停或者取消的操作
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
        //从参数中获取到当前的下载进度，然后和上一次的下载进度进行对比，如果有变化的话则调用DownloadListener的onProgress()方法来通知下载进度更新。
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
