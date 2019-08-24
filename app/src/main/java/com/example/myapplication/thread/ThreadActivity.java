package com.example.myapplication.thread;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
/**Android异步消息处理的基本用法*/
public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn_handlerMess,mBtnDownLoad,mBtnCancel;
    private TextView mText,mAsyncText;
    private ProgressBar mProgressBar;
    public static final int UPDATE_TEXT = 1;
    private DownLoadTask downLoadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        mBtn_handlerMess=findViewById(R.id.change_handlerMess);
        mText=findViewById(R.id.thread_text);
        mBtnDownLoad=findViewById(R.id.async_button);
        mBtnCancel=findViewById(R.id.async_cancel);
        mAsyncText=findViewById(R.id.async_text);
        mProgressBar=findViewById(R.id.async_progress_bar);
        mBtn_handlerMess.setOnClickListener(this);
        mBtnDownLoad.setOnClickListener(this);
        //AsyncTask子类的实例必须在UI线程中创建
        downLoadTask=new DownLoadTask();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        //主线程创建handleMessage
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case UPDATE_TEXT:
                    //UI操作
                    mText.setText("Nice to meet you");
                    break;
                    default:
                        break;
            }
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_handlerMess:
                /**这里虽然在子线程但是，创建了一个Message（android.os.Message）对象，并将它的what字段的值指定为UPDATE_TEXT，
                然后调用Handler的sendMessage()方法将这条Message发送出去。很快，Handler就会收到这条Message，
                并在handleMessage()方法中对它进行处理。
                 注意此时handleMessage()方法中的代码就是在主线程当中运行的了，所以我们可以放心地在这里进行UI操作
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /*runOnUiThread(new Runnable() { 在子线程中可以直接更改UI
                            @Override
                            public void run() {
                                mText.setText("runOnUiThread变更");
                            }
                        });*/
                        Message message=new Message();
                        message.what=UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.async_button:
                /**
                 * 手动调用execute(Params... params) 从而执行异步线程任务
                 * 注：
                 *    a. 必须在UI线程中调用
                 *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
                 *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
                 *    d. 不能手动调用上述方法
                 */
                if (mProgressBar.getProgress()==0) {
                    downLoadTask.execute();
                }
                break;
            case R.id.async_cancel:
                // 取消一个正在执行的任务,onCancelled方法将会被调用
                downLoadTask.cancel(true);
                break;
                default:
                    break;
        }
    }

    /**3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     *  此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = Boolean类型*/
    private class DownLoadTask extends AsyncTask<String, Integer, Boolean> {

        /**这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示一个进度条对话框等。*/

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {
            // UI操作
            mAsyncText.setText("加载中...");
        }

        /**这个方法中的所有代码都会在子线程中运行，我们应该在这里去处理所有的耗时任务。任务一旦完成就可以通过return语句来将任务的执行结果返回，
         * 如果AsyncTask的第三个泛型参数指定的是Void，就可以不返回任务执行结果。
         * 注意，在这个方法中是不可以进行UI操作的，如果需要更新UI元素，比如说反馈当前任务的执行进度，可以调用publishProgress (Progress...)方法来完成。*/
        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 注：必须复写，从而自定义线程任务
        @Override
        protected Boolean doInBackground(String... voids) {
            try {
                int count=0;
                int length=1;
                while (count<99) {
                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        /**当在后台任务中调用了publishProgress(Progress...)方法后，onProgressUpdate (Progress...)方法就会很快被调用，
         * 该方法中携带的参数就是在后台任务中传递过来的。在这个方法中可以对UI进行操作，利用参数中的数值就可以对界面元素进行相应的更新。*/
        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        // 注：根据需求复写
        @Override
        protected void onProgressUpdate(Integer... values) {
            // 在这里更新下载进度
            mProgressBar.setProgress(values[0]);
            mAsyncText.setText("loading..."+values[0]+"%");
        }

        /**当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用。返回的数据会作为参数传递到此方法中，可以利用返回的数据来进行一些UI操作，
         * 比如说提醒任务执行的结果，以及关闭掉进度条对话框等。*/
        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        // 注：必须复写，从而自定义UI操作
        @Override
        protected void onPostExecute(Boolean result) {
            // 关闭进度对话框
            // 在这里提示下载结果
            if (result) {
                mAsyncText.setText("加载完成");
            }
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {
            mAsyncText.setText("加载取消");
            mProgressBar.setProgress(0);
        }
    }
}
