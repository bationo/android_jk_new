package com.hlct.android.activity;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hlct.android.R;
import com.hlct.android.adapter.FragmentPagerAdapterWithTab;
import com.hlct.android.bean.Detail;
import com.hlct.android.bean.PlanBean;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.fragment.StocktakingDetailFragment;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.DetailDao;
import com.hlct.android.greendao.PlanBeanDao;
import com.hlct.android.util.RfidScanDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class StocktakingPlanActivity extends AppCompatActivity implements View.OnClickListener {

    public static String STOCKTAKING_PLAN_ACTIVITY_TAG = "StocktakingPlanActivity";
    private Context mContext;
    /*****************view********************/
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTVInvenory;
    private TextView mTVDate;
    private TextView mTVPerson;
    private TextView mTVPlanNumber;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private TabItem tabItem1;
    private TabItem tabItem2;
    private TextView tabItemTV1;
    private TextView tabItemCount1;
    private TextView tabItemTV2;
    private TextView tabItemCount2;

    private RfidScanDialog mDialog;
    /*****************data*********************/
    private long id;   //计划id号
    private ArrayList<String> mTabs = new ArrayList<>();
    private ArrayList<PlanBean> mList = new ArrayList<>();
    /*****************misc*********************/
    private FragmentPagerAdapterWithTab mFragmentPagerAdapter;
    StocktakingDetailFragment fragment;
    StocktakingDetailFragment fragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocktaking_plan);
        mContext = StocktakingPlanActivity.this;
        initView();
        initDate();
        mToolBar.setNavigationIcon(R.mipmap.back_white_64);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /***********fragment view pager***************/
        FragmentManager manager = getSupportFragmentManager();
        mFragmentPagerAdapter = new FragmentPagerAdapterWithTab(manager, mFragments);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        //mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("tab position------->", "" + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        //tabItemTV1.setTextColor(Color.parseColor("#FF4081"));
                        /*((TextView) tab.getCustomView().findViewById(R.id.tab_layout_item_tv1)).
                                setTextColor(Color.parseColor("#FF4081"));*/

                        break;
                    case 1:
                        /*((TextView) tab.getCustomView().findViewById(R.id.tab_layout_item_tv2)).
                                setTextColor(Color.parseColor("#FF4081"));*/
                        //tabItemTV2.setTextColor(Color.parseColor("#FF4081"));
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        /*((TextView) tab.getCustomView().findViewById(R.id.tab_layout_item_tv1)).
                                setTextColor(Color.parseColor("#757575"));*/
                        tabItemTV1.setTextColor(Color.parseColor("#757575"));
                        break;
                    case 1:
                        /*((TextView) tab.getCustomView().findViewById(R.id.tab_layout_item_tv2)).
                                setTextColor(Color.parseColor("#757575"));*/
                        tabItemTV2.setTextColor(Color.parseColor("#757575"));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //TODO 将列表刷新或者返回顶部
            }

        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               /* Log.e("viewpager scroll ---->", "position = " + position + ", positionOffset=" + positionOffset
                        + ", positonOffsetPixel=" + positionOffsetPixels);*/
                switch (position) {
                    case 0:
                        if (positionOffset > 0.5) {
                            tabItemTV1.setTextColor(Color.parseColor("#757575"));
                            tabItemTV2.setTextColor(Color.parseColor("#FF4081"));
                        } else {
                            tabItemTV1.setTextColor(Color.parseColor("#FF4081"));
                            tabItemTV2.setTextColor(Color.parseColor("#757575"));
                        }
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(STOCKTAKING_PLAN_ACTIVITY_TAG, "onResume()");
        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
        PlanBean planBean = daoSession.getPlanBeanDao().queryBuilder()
                .where(PlanBeanDao.Properties.PlanId.eq(id))
                .unique();
        List<Detail> details = daoSession.getDetailDao().queryBuilder()
                .where(DetailDao.Properties.PlanId.eq(id))
                .where(DetailDao.Properties.InventoryState.eq("未盘点"))
                .list();
        List<Detail> detailList = daoSession.getDetailDao().queryBuilder()
                .where(DetailDao.Properties.PlanId.eq(id))
                .where(DetailDao.Properties.InventoryState.eq("已盘点"))
                .list();
        mTVDate.setText(planBean.getPlanTime());
        mTVPerson.setText(planBean.getInventoryPerson());
        mTVPlanNumber.setText(planBean.getPlanNumber());
        tabItemCount1.setText(details.size() + "");
        tabItemCount2.setText(detailList.size() + "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(STOCKTAKING_PLAN_ACTIVITY_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        Log.e(STOCKTAKING_PLAN_ACTIVITY_TAG, "onStop()");
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收id
     *
     * @param id 消息事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onHandleId(Long id) {
        this.id = id;
        Log.e(STOCKTAKING_PLAN_ACTIVITY_TAG, "收到的id号是----->" + id);
    }

    /**
     * 初始化 布局控件
     */
    private void initView() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.activity_stocktaking_plan_appbar_layout);
        mToolBar = (Toolbar) findViewById(R.id.activity_stocktaking_plan_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.activity_stocktaking_plan_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.activity_stocktaking_plan_viewpager);
        mTVDate = (TextView) findViewById(R.id.activity_stocktaking_plan_tv_date);
        mTVPerson = (TextView) findViewById(R.id.activity_stocktaking_plan_tv_person);
        mTVPlanNumber = (TextView) findViewById(R.id.activity_stocktaking_plan_tv_number);
        mTVInvenory = (TextView) findViewById(R.id.activity_stocktaking_plan_tv_start_inventory);
        mTVInvenory.setOnClickListener(this);

        tabItem1 = (TabItem) findViewById(R.id.tab_item1);
        tabItem2 = (TabItem) findViewById(R.id.tab_item2);
        tabItemTV1 = (TextView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_layout_item_tv1);
        tabItemCount1 = (TextView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_layout_item_count1);
        tabItemTV2 = (TextView) mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tab_layout_item_tv2);
        tabItemCount2 = (TextView) mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tab_layout_item_count2);

        mDialog = new RfidScanDialog();
    }

    /**
     * 初始化 第一次页面生成时需要生成的数据
     */
    public void initDate() {
        Log.e("initData", "收到的id号是----->" + id);
        //TODO 区别开哪个是盘点过的,那个是没有盘点过得
        fragment = new StocktakingDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status",getResources().getString(R.string.not_inventory));
        fragment.setArguments(bundle);
        fragment1 = new StocktakingDetailFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("status",getResources().getString(R.string.have_inventory));
        fragment1.setArguments(bundle1);
        mFragments.add(fragment);
        mFragments.add(fragment1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_stocktaking_plan_tv_start_inventory:
                mDialog.show(getSupportFragmentManager(),"dialog");
                break;
            default:
                break;
        }
    }

    public  void refreshView(){
        onResume();
        fragment.onResume();
        fragment1.onResume();
    }
}
