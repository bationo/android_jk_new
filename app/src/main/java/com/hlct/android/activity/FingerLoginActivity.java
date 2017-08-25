package com.hlct.android.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlct.android.R;
import com.hlct.android.bean.MessageEvent;
import com.hlct.android.util.FingerUtils;
import com.hlct.android.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FingerLoginActivity extends AppCompatActivity {

    private static final String TAG = "FingerLoginActivity";
    private Context mContext;

    private ImageView mImage;
    private Button mBtnStart;
    private TextView mTextView;

    private FingerUtils mFingerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_login);
        mContext = FingerLoginActivity.this;
        mFingerUtils = new FingerUtils(this);   //初始化fingerUtils时必须使用Activity对象
        setupToolbar();
        initView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mFingerUtils.initUtils();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFingerUtils.close();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.activity_finger_login_image);
        mBtnStart = (Button) findViewById(R.id.activity_finger_login_start);
        mTextView = (TextView) findViewById(R.id.activity_finger_login_textView);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecog();
            }
        });
    }

    /**
     * 开始识别指纹
     */
    private void startRecog() {
        enableButton(mBtnStart, false);
        Log.e(TAG, mFingerUtils.getConnState());
        if (mFingerUtils.getConnState().equals("连接成功")) {
            mFingerUtils.getFingerPrintImage();             //获取指纹图像
            //mFingerUtils.getFingerChar();
            mFingerUtils.searchFingerChar();                //从指纹数据库中搜索指纹
        } else {
            new ToastUtil(mContext, mFingerUtils.getConnState(), 2000)
                    .setGravity(Gravity.CENTER, 0, 200)
                    .show();
            enableButton(mBtnStart, true);
        }
    }

    /**
     * 使用eventbus得到指纹图像
     *
     * @param bitmap 指纹图像
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBitmip(Bitmap bitmap) {
        if (bitmap != null) {
            Log.e(TAG, "get bitmap success");
            mImage.setImageBitmap(bitmap);
            enableButton(mBtnStart, true);
        }
    }

    /**
     * 使用EventBus得到fingerChar;
     *
     * @param messageEvent 消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getChar(MessageEvent messageEvent) {
        String mFingerChar = "";
        if (messageEvent.getPublish().equals("FingerUtils") && messageEvent.getSubscriber().equals(TAG)) {
            Object message = messageEvent.getMessage();
            if (message instanceof Integer) {
                int position = (int) message;
                mTextView.setText("成功找到指纹，数据库位置：" + position);
                enableButton(mBtnStart, true);
            } else if (message instanceof String) {
                String result = (String) message;
                mTextView.setText(result);
                enableButton(mBtnStart, true);
            }
        }
    }

    void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_white_64);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * 将button设置为可用和不可用的状态
     *
     * @param button button
     * @param b      true为可用,false为不可用
     */
    private void enableButton(Button button, boolean b) {
        if (b) {
            button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            button.setClickable(true);
        } else {
            button.setBackgroundColor(Color.GRAY);
            button.setClickable(false);
        }
    }
}
