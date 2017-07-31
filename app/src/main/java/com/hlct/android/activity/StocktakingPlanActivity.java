package com.hlct.android.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import com.hlct.android.bean.PlanBean;
import com.hlct.android.fragment.StocktakingDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class StocktakingPlanActivity extends AppCompatActivity {

    public static String STOCKTAKING_PLAN_ACTIVITY_TAG = "StocktakingPlanActivity";
    private Context mContext;
    /*****************view********************/
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTVDate;
    private TextView mTVPerson;
    private TextView mTVCount;
    private TextView mTVCounted;
    private ArrayList<Fragment> mFragments;
    /*****************data*********************/
    private long id;   //计划id号
    private ArrayList<String> mTabs = new ArrayList<>();
    private String[] tabs = new String[]{"未盘点", "已盘点"};
    private ArrayList<PlanBean> mList = new ArrayList<PlanBean>();
    /*****************misc*********************/
    private FragmentPagerAdapterWithTab mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocktaking_plan);
        initView();
        initDate();

        mToolBar.setNavigationIcon(R.mipmap.back_white_64);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击返回事件
            }
        });
        /***********fragment view pager***************/
        FragmentManager manager = getSupportFragmentManager();
        mFragmentPagerAdapter = new FragmentPagerAdapterWithTab(manager, mFragments, mTabs);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     *
     * @param id 消息事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void onHandleId(Long id){
        this.id = id;
        Log.e("收到的id号是----->", id+"");
    }

    /**
     * 初始化 布局控件
     */
    private void initView() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.stocktaking_appbar_layout);
        mToolBar = (Toolbar) findViewById(R.id.stocktaking_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.stocktaking_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.stocktaking_viewpager);
        mTVDate = (TextView) findViewById(R.id.stocktaking_tv_date);
        mTVPerson = (TextView) findViewById(R.id.stocktaking_tv_person);
        mTVCount = (TextView) findViewById(R.id.stocktaking_tv_count);
        mTVCounted = (TextView) findViewById(R.id.stocktaking_tv_counted);
    }

    /**
     * 初始化 第一次页面生成时需要生成的数据
     */

    public void initDate() {
        for (String tab : tabs) {
            mTabs.add(tab);
        }
        mFragments = new ArrayList<>();
        StocktakingDetailFragment fragment = new StocktakingDetailFragment();
        StocktakingDetailFragment fragment1 = new StocktakingDetailFragment();
        mFragments.add(fragment);
        mFragments.add(fragment1);
    }
}
