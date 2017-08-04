package com.hlct.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.hlct.android.R;
import com.hlct.android.fragment.StocktakingDetailFragment;
import com.hlct.android.fragment.StocktakingFragment;
import com.hlct.android.util.ActivityUtils;
import com.hlct.android.util.ToastUtil;

import static com.hlct.android.R.id.toolbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String TAG = "MainActivity";
    private static String STOCKTAKING_FRAGMENT_TAG = "stocktakingFragment";
    private Context mContext;

    //退出时间标志.
    private long mExitTime;

    /***************view*********************/
    private FrameLayout mFrameLayout;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    FloatingActionButton fab;
    NavigationView navigationView;

    /****************misc********************/
    FragmentManager mManager;
    StocktakingFragment stocktakingFragment;
    StocktakingDetailFragment zFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        ActivityUtils.getInstance().addActivity(this);
        mToolBar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(mToolBar);

        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 悬浮按钮点击事件
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //侧滑导航栏 item的点击事件
        navigationView.setNavigationItemSelectedListener(this);
        //fragment 管理
        addFragment();
    }

    /**
     * 将要展示的fragment放在transaction中，以备调用。
     */
    private void addFragment() {
        mManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mManager.beginTransaction();
        stocktakingFragment = new StocktakingFragment();
        mTransaction.add(R.id.activity_main_linear_container,stocktakingFragment,STOCKTAKING_FRAGMENT_TAG);
        /*zFragment = new StocktakingDetailFragment();
        mTransaction.add(R.id.activity_main_linear_container,zFragment,"zFragment");*/
        mTransaction.commit();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //        drawer.setDrawerListener(toggle);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_stocktaking) {
            //TODO 重复点击报错
            FragmentTransaction mTransaction = mManager.beginTransaction();
            mTransaction.show(stocktakingFragment);
            mTransaction.hide(zFragment);
            mTransaction.commit();

        } else if (id == R.id.nav_gallery) {
            /*FragmentTransaction mTransaction = mManager.beginTransaction();
            mTransaction.show(zFragment);
            mTransaction.hide(stocktakingFragment);
            mTransaction.commit();*/

        } else if (id == R.id.nav_slideshow) {
            /*FragmentTransaction mTransaction = mManager.beginTransaction();
            mTransaction.show(stocktakingFragment);
            mTransaction.hide(zFragment);
            mTransaction.commit();*/

        } else if (id == R.id.nav_manage) {
            /*FragmentTransaction mTransaction = mManager.beginTransaction();
            mTransaction.show(zFragment);
            mTransaction.hide(stocktakingFragment);
            mTransaction.commit();*/

        } else if (id == R.id.nav_share) {
            /*FragmentTransaction mTransaction = mManager.beginTransaction();
            mTransaction.show(stocktakingFragment);
            mTransaction.hide(zFragment);
            mTransaction.commit();*/

        } else if (id == R.id.nav_send) {
            /*FragmentTransaction mTransaction = mManager.beginTransaction();
            mTransaction.show(zFragment);
            mTransaction.hide(stocktakingFragment);
            mTransaction.commit();*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 双击返回退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.d("onKeyDown", "" + keyCode);
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //showSnackbar(getWindow().getDecorView(), "再按一次退出");
                new ToastUtil(mContext,"再按一次退出",1500)
                        .setGravity(Gravity.CENTER,0,200)
                        .show();
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityUtils.getInstance().destory();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
