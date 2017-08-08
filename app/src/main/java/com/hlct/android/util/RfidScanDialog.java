package com.hlct.android.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.handheld.UHF.UhfManager;
import com.hlct.android.R;
import com.hlct.android.adapter.DialogListAdapter;
import com.hlct.android.uhf.RFID;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.pda.serialport.Tools;

/**
 * Created by lazylee on 2017/8/6.
 */

public class RfidScanDialog extends DialogFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private ListView mListView;
    private TextView mCommit;
    private TextView mScan;
    private DialogListAdapter mAdapter;
    private ArrayList<RFID> mList = new ArrayList<>();

    private UhfManager uhfManager;
    private int power = 0;//rate of work
    private int area = 0;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;


    private boolean runFlag = true;      //判断是否正在进行RFID扫描
    private boolean startFlag = false;   //判断btnStart的状态

    private String rfidTag;
    public RfidScanDialog() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("RfidScanDialog ---->", "onCreate");
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("RfidScanDialog ---->", "onCreateView");
        mRootView = inflater.inflate(R.layout.dialog_rfid_scan, container, false);
        rfidTag = getTag();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("RfidScanDialog ---->", "onViewCreate");
        mContext = getActivity();
        mListView = (ListView) mRootView.findViewById(R.id.dialog_rfid_scan_list);
        mScan = (TextView) mRootView.findViewById(R.id.dialog_rfid_scan_btn);
        mScan.setOnClickListener(this);
        mCommit = (TextView) mRootView.findViewById(R.id.dialog_rfid_scan_commit);
        mAdapter = new DialogListAdapter(mContext, mList);
        mListView.setAdapter(mAdapter);
        Log.e("on create view---->", "set adapter");
        ScanThread scanThread = new ScanThread();
        scanThread.start();
    }

    @Override
    public void onStart() {
        Log.e("RfidScanDialog ---->", "onStart");
        super.onStart();
        EventBus.getDefault().register(this);
        uhfManager = UhfManager.getInstance();
        if (uhfManager == null) {
            Log.e("uhfmanager ---->", "打开失败");
            return;
        } else {
            Log.e("uhfmanager ---->", "打开成功");
        }
        uhfManager.setOutputPower(power);
        uhfManager.setWorkArea(area);
    }

    @Override
    public void onStop() {
        Log.e("RfidScanDialog ---->", "onStop");
        super.onStop();
        EventBus.getDefault().unregister(this);
        startFlag = false;
        if (uhfManager != null) {
            uhfManager.close();
            uhfManager = null;
        }
    }

    @Override
    public void onDestroy() {
        Log.e("RfidScanDialog ---->", "onDestroy");
        super.onDestroy();
        startFlag = false;
        if (uhfManager != null) {
            uhfManager.close();
            uhfManager = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_rfid_scan_btn:
                if (!startFlag) {
                    startFlag = true;
                    mScan.setText(R.string.stop_scan);
                    setButtonClickable(mCommit, false);
                } else {
                    startFlag = false;
                    mScan.setText(R.string.start_scan);
                    setButtonClickable(mCommit, true);
                }
                break;
            case R.id.dialog_rfid_scan_commit:
                if (isVisible()) {
                    this.dismiss();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置button的状态
     *
     * @param button 要设定的button
     * @param flag   button是否能够点击
     */
    private void setButtonClickable(TextView button, boolean flag) {
        button.setClickable(flag);
        if (flag) {
            button.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            button.setTextColor(Color.GRAY);
        }
    }

    /**
     * 子线程处理扫描结果
     */
    private class ScanThread extends Thread {
        private List<byte[]> epcList;

        @Override
        public void run() {
            super.run();
            while (runFlag) {
                if (startFlag) {
                    // manager.stopInventoryMulti()
                    epcList = uhfManager.inventoryRealTime(); // inventory real time
                    if (epcList != null && !epcList.isEmpty()) {
                        // play sound
                        new AudioManagerUtil(mContext).playDiOnce();
                        for (byte[] epc : epcList) {

                            String epcStr = Tools.Bytes2HexString(epc,
                                    epc.length);
                            EventBus.getDefault().post(epcStr);
                        }
                    }
                    epcList = null;
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    /**
     * 在主线程中处理messageEvent
     *
     * @param rfid 扫描到的rfid号
     */
    @Subscribe(threadMode = ThreadMode.MAIN)          //订阅事件FirstEvent
    public void onEventMainThread(String rfid) {
        Log.e("接收到rfid---->", rfid);

        boolean isContained = false;  //判断list是否包含此rfid
        for (RFID r : mList) {
            if (r.getRifd().equals(rfid)) {
                isContained = true;
            }
        }
        if (!isContained) {
            RFID mRFID = new RFID();
            mRFID.setRifd(rfid);
            if(rfid.equals(rfidTag)){
                mRFID.setCheck(true);
            }
            mList.add(mRFID);
            isContained = false;
        }
        Log.e("mlist siza---->", mList.size() + "");
        mAdapter.notifyDataSetChanged();
    }
}
