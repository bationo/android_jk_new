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
import android.view.LayoutInflater;
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

    private TabItem tabItem1;
    private TabItem tabItem2;
    private TextView tabItemTV1;
    private TextView tabItemCount1;
    private TextView tabItemTV2;
    private TextView tabItemCount2;
    /*****************data*********************/
    private long id;   //计划id号
    private ArrayList<String> mTabs = new ArrayList<>();
    private String[] tabs = new String[]{"未盘点", "已盘点"};
    private ArrayList<PlanBean> mList = new ArrayList<>();
    /*****************misc*********************/
    private FragmentPagerAdapterWithTab mFragmentPagerAdapter;

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
                //TODO 点击返回事件
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
                Log.e("tab position------->",""+tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        ((TextView)tab.getCustomView().findViewById(R.id.tab_layout_item_tv1)).
                                setTextColor(Color.parseColor("#FF4081"));
                        /*tabItemTV1.setTextColor(Color.parseColor("#FF4081"));
                        tabItemTV2.setTextColor(Color.parseColor("#757575"));*/
                        break;
                    case 1:
                        ((TextView)tab.getCustomView().findViewById(R.id.tab_layout_item_tv2)).
                                setTextColor(Color.parseColor("#FF4081"));
                        /*tabItemTV2.setTextColor(Color.parseColor("#FF4081"));
                        tabItemTV1.setTextColor(Color.parseColor("#757575"));*/
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        ((TextView)tab.getCustomView().findViewById(R.id.tab_layout_item_tv1)).
                                setTextColor(Color.parseColor("#757575"));
                        /*tabItemTV1.setTextColor(Color.parseColor("#FF4081"));
                        tabItemTV2.setTextColor(Color.parseColor("#757575"));*/
                        break;
                    case 1:
                        ((TextView)tab.getCustomView().findViewById(R.id.tab_layout_item_tv2)).
                                setTextColor(Color.parseColor("#757575"));
                        /*tabItemTV2.setTextColor(Color.parseColor("#FF4081"));
                        tabItemTV1.setTextColor(Color.parseColor("#757575"));*/
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    @Override
    protected void onStop() {
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
        mAppBarLayout = (AppBarLayout) findViewById(R.id.stocktaking_appbar_layout);
        mToolBar = (Toolbar) findViewById(R.id.stocktaking_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.stocktaking_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.stocktaking_viewpager);
        mTVDate = (TextView) findViewById(R.id.stocktaking_tv_date);
        mTVPerson = (TextView) findViewById(R.id.stocktaking_tv_person);
        mTVCount = (TextView) findViewById(R.id.stocktaking_tv_count);
        mTVCounted = (TextView) findViewById(R.id.stocktaking_tv_counted);
        tabItem1 = (TabItem) findViewById(R.id.tab_item1);
        tabItem2 = (TabItem) findViewById(R.id.tab_item2);
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.tab_layout_item,null);
        tabItemTV1 = (TextView) view1.findViewById(R.id.tab_layout_item_tv1);
        tabItemCount1 = (TextView) view1.findViewById(R.id.tab_layout_item_count1);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.tab_layout_item2,null);
        tabItemTV2 = (TextView) view2.findViewById(R.id.tab_layout_item_tv2);
        tabItemCount2 = (TextView) view2.findViewById(R.id.tab_layout_item_count2);

    }

    /**
     * 初始化 第一次页面生成时需要生成的数据
     */

    public void initDate() {
        /*for (String tab : tabs) {
            mTabs.add(tab);
        }*/

        mFragments = new ArrayList<>();
        StocktakingDetailFragment fragment = new StocktakingDetailFragment();
        StocktakingDetailFragment fragment1 = new StocktakingDetailFragment();
        mFragments.add(fragment);
        mFragments.add(fragment1);
    }
}
