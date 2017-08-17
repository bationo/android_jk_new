package com.hlct.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hlct.android.R;
import com.hlct.android.bean.AssetBean;
import com.hlct.android.bean.Detail;
import com.hlct.android.bean.MessageEvent;
import com.hlct.android.bean.User;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.AssetBeanDao;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.DetailDao;
import com.hlct.android.greendao.UserDao;
import com.hlct.android.util.RfidScanDialog;
import com.hlct.android.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class StocktakingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    public static final String STOCKTAKING_DETAIL_ACTIVITY_TAG = "StocktakingDetailActivity";
    /**********view*************/
    private TextView mTVCancel;
    private TextView mTVAssertName;
    private TextView mTVAssertType;
    private TextView mTVAssertRFID;
    private TextView mTVUser;
    private TextView mTVDepartment;
    private TextView mTVUnit;
    private TextView mTVCosting;
    private BottomSheetDialog mBottomSheetDialog;
    private LinearLayout mLineFRDIScan;
    private LinearLayout mLineQRScan;
    private RfidScanDialog mDialog;
    private long detailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocktaking_detail);
        mContext = StocktakingDetailActivity.this;
        initView();


    }

    /**
     * 根据 detailID 从数据库中加载 detail实体类
     */
    private void initData() {
        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
        DetailDao detailDao = daoSession.getDetailDao();
        Detail detail = detailDao.queryBuilder()
                .where(DetailDao.Properties.DetailId.eq(detailId))
                .unique();
        UserDao userDao = daoSession.getUserDao();
        User user = userDao.queryBuilder()
                .where(UserDao.Properties.Id.eq(detail.getUserId()))
                .unique();
        AssetBean assetBean = daoSession.getAssetBeanDao().queryBuilder()
                .where(AssetBeanDao.Properties.Id.eq(detail.getPropertyId()))
                .unique();

        mTVAssertRFID.setText(assetBean.getRfid());
        mTVAssertName.setText(assetBean.getFacilityName());
        mTVAssertType.setText(assetBean.getFacilityType());
        mTVDepartment.setText(user.getDepartmentName());
        mTVUser.setText(user.getName());
        mTVUnit.setText(user.getBankName());
        mTVCosting.setText(assetBean.getCosting());
    }

    /**
     * 初始化布局view
     */
    private void initView() {
        mTVCancel = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_cancel);
        mTVCancel.setOnClickListener(this);
        mTVAssertName = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_assert_name);
        mTVAssertType = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_assert_type);
        mTVAssertRFID = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_assert_rfid);
        mTVUser = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_assert_user);
        mTVDepartment = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_assert_department);
        mTVUnit = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_assert_unit);
        mTVCosting = (TextView) findViewById(R.id.activity_stocktaking_detail_tv_assert_costing);

        View sheetView = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_layout,null);
        mBottomSheetDialog = new BottomSheetDialog(mContext);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);
        mLineFRDIScan = (LinearLayout) sheetView.findViewById(R.id.bottom_sheet_layout_line1);
        mLineQRScan = (LinearLayout) sheetView.findViewById(R.id.bottom_sheet_layout_line2);
        mLineQRScan.setOnClickListener(this);
        mLineFRDIScan.setOnClickListener(this);

        mDialog = new RfidScanDialog();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 接受 fragmentDetailStocktaking传递过来的detailId ，用来加载数据
     *
     * @param messageEvent 消息事件；
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveDetailId(MessageEvent messageEvent) {
        if(messageEvent.getPublish().equals("DetailRecyclerAdapter") &&
                messageEvent.getSubscriber().equals("StocktakingDetailActivity")){
            this.detailId = (long) messageEvent.getMessage();
        }
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_stocktaking_detail_tv_cancel:
                break;
//            case R.id.activity_stocktaking_detail_tv_scan:
//                mBottomSheetDialog.show();
//                break;
            case R.id.bottom_sheet_layout_line1:
                //TODO rfid扫描
                if(mBottomSheetDialog.isShowing()){
                    mBottomSheetDialog.dismiss();
                }
                mDialog.show(getSupportFragmentManager(),"dialog");
                break;
            case R.id.bottom_sheet_layout_line2:
                //TODO QR扫描
                new ToastUtil(mContext,"rfid 扫描",2000)
                        .setGravity(Gravity.CENTER,0,200)
                        .show();
                if(mBottomSheetDialog.isShowing()){
                    mBottomSheetDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }
}
