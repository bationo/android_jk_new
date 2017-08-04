package com.hlct.android.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hlct.android.R;

/**
 * Created by lazylee on 2017/8/2.
 */

public class ToastUtil {

    private static Toast toast;
    private Context mContext;

    /**
     * 修改原布局的Toast
     */
    public ToastUtil(Context context) {
        this.mContext = context;
        toast=new Toast(mContext);
    }

    /**
     * 完全自定义布局Toast
     * @param context
     * @param text
     */
    public ToastUtil(Context context, CharSequence text, int duration){
        this.mContext = context;
        if(toast != null){
            toast.cancel();
        }
        toast = new Toast(mContext);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);
        TextView textViev = (TextView) view.findViewById(R.id.toast_text);
        textViev.setText(text);
        // 动画效果实现 不成功
        /*AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE,
                0f,Animation.ABSOLUTE,0,Animation.ABSOLUTE,0,Animation.ABSOLUTE,100);
        translateAnimation.setDuration(500);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        view.setAnimation(animationSet);*/
        toast.setView(view);
        //toast.getView().setAnimation(animationSet);
        toast.setDuration(duration);
    }

    /**
     * Set the location at which the notification should appear on the screen.
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @return
     */
    public ToastUtil setGravity(int gravity,int xOffset,int yOffset){
        toast.setGravity(gravity,xOffset,yOffset);
        return this;
    }


    /**
     * 显示Toast
     *
     */
    public void show (){
        toast.show();
    }

    /**
     * 得到Toast
     * @return
     */
    public Toast getToast(){
        return toast;
    }
}
