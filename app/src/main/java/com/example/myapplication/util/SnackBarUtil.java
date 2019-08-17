package com.example.myapplication.util;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

/**
 * Snackbar的工具类
 * addViewToSnackbar()方法：当需要自定义Snackbar时，调用这个方法在Snackbar的布局中添加一个新的控件
 * setAction()方法：为Snackbar中新添加的按钮设置文本和点击事件
 */
public class SnackBarUtil {
    /**
     * 当需要自定义Snackbar时，调用这个方法在Snackbar的布局中添加一个新的控件
     *
     * @param snackbar Snackbar对象
     * @param layoutId 新的布局ID
     * @param index    当index值为1时，将新布局添加到消息文本后面，action按钮前面
     */
    public static void addViewToSnackbar(Snackbar snackbar, int layoutId, int index) {
        // 获取snackbar的View（实就是SnackbarLayout）
        View snackbarview = snackbar.getView();
        // 将获取的View转换成SnackbarLayout
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;
        // 加载布局文件新建View
        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);
        // 设置新建布局参数
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置新建布局在Snackbar内垂直居中显示
        p.gravity = Gravity.CENTER_VERTICAL;
        // 将新建布局添加进snackbarLayout相应位置 在textView后面
        ((LinearLayout) snackbarLayout.getChildAt(0)).addView(add_view, index, p);
    }

    /**
     * 为Snackbar中新添加的按钮设置文本和点击事件
     *
     * @param snackbar        Snackbar对象
     * @param btn_id          新添加的按钮的ID
     * @param action_string   新添加的按钮中展示的文本
     * @param onClickListener 新添加的按钮的点击事件
     */
    public static void setAction(Snackbar snackbar, int btn_id, String action_string, View.OnClickListener onClickListener) {
        View view = snackbar.getView();//获取Snackbar的view
        if (view != null) {
            //为添加的按钮设置监听器
            ((Button) view.findViewById(btn_id)).setText(action_string);
            (view.findViewById(btn_id)).setOnClickListener(onClickListener);
        }
    }

    /**
     * 为Snackbar中新添加的ImageView设置展示的图片资源ID
     *
     * @param snackbar Snackbar对象
     * @param iv_id    ImageView的ID
     * @param res_id   要在ImageView中展示的图片的资源ID
     */
    public static void setImageAction(Snackbar snackbar, int iv_id, int res_id) {
        View view = snackbar.getView();//获取Snackbar的view
        if (view != null) {
            //为添加的按钮设置监听器
            ((ImageView) view.findViewById(iv_id)).setImageResource(res_id);
        }
    }
}