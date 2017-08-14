package com.hlct.android.activity;

import android.content.Context;
import android.graphics.Point;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hlct.android.R;
import com.hlct.android.bean.Detail;
import com.hlct.android.bean.User;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.fragment.StocktakingDetailFragment;
import com.hlct.android.fragment.StocktakingFragment;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.UserDao;
import com.hlct.android.util.ActivityUtils;
import com.hlct.android.util.FileUtils;
import com.hlct.android.util.SharedPreferencesUtils;
import com.hlct.android.util.ToastUtil;

import java.util.List;

import static com.hlct.android.R.id.toolbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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
    private TextView mTVNavHeaderName;
    private TextView mTVNavHeaderDepartment;
    /****************misc********************/
    FragmentManager mManager;
    StocktakingFragment stocktakingFragment;
    StocktakingDetailFragment zFragment;
    private long userID;      //登陆的user 的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        ActivityUtils.getInstance().addActivity(this);
        mToolBar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(mToolBar);
        initView();
        fab.setOnClickListener(this);
        //侧滑导航栏 item的点击事件
        navigationView.setNavigationItemSelectedListener(this);
        //fragment 管理
        addFragment();
        initData();
    }

    /**
     * 加载预先需要的数据
     */
    private void initData() {
        userID = new SharedPreferencesUtils(mContext).getLoginUserID();
        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
        User user = daoSession.getUserDao().queryBuilder()
                .where(UserDao.Properties.Id.eq(userID))
                .unique();
        mTVNavHeaderDepartment.setText(user.getDepartmentName());
        mTVNavHeaderName.setText(user.getName());

    }

    /**
     * 将要展示的fragment放在transaction中，以备调用。
     */
    private void addFragment() {
        mManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mManager.beginTransaction();
        stocktakingFragment = new StocktakingFragment();
        mTransaction.add(R.id.activity_main_linear_container, stocktakingFragment, STOCKTAKING_FRAGMENT_TAG);
        /*zFragment = new StocktakingDetailFragment();
        mTransaction.add(R.id.activity_main_linear_container,zFragment,"zFragment");*/
        mTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    /**
     * 初始化view
     */
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //        drawer.setDrawerListener(toggle);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        Log.e(TAG ,"测量到屏幕宽度是"+outMetrics.widthPixels);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        Log.e(TAG ,"测量到屏幕宽度是"+point.x);
        getResources().getDimension(R.dimen.nav_width);
        mTVNavHeaderName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_tv_name);
        mTVNavHeaderDepartment = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_tv_department);
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
            if (!stocktakingFragment.isVisible()) {
                FragmentTransaction mTransaction = mManager.beginTransaction();
                mTransaction.show(stocktakingFragment);
                mTransaction.hide(zFragment);
                mTransaction.commit();
            }
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
                new ToastUtil(mContext, "再按一次退出", 1500)
                        .setGravity(Gravity.CENTER, 0, 200)
                        .show();
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityUtils.getInstance().destory();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                commitData();
                break;
            default:
                break;
        }
    }

    /**
     * 生成盘点文件
     */
    public void commitData() {
        //TODO 生成文件
        Snackbar.make(fab, "是否确定生成数据文件", Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
                        List<Detail> details = daoSession.getDetailDao().loadAll();
                        Gson gson = new Gson();
                        String string = gson.toJson(details);
                        Log.e(TAG, string);

                        if (FileUtils.writeStringToTxt(DatabaseConstant.FILE_PATH + "OUT_DATA.txt", string, false)) {
                            new ToastUtil(mContext, "文件生成成功", 2000)
                                    .setGravity(Gravity.CENTER, 0, 200)
                                    .show();
                            FileUtils.makeFileAvailable(mContext, DatabaseConstant.FILE_PATH + "OUT_DATA.txt");
                        } else {
                            new ToastUtil(mContext, "文件生成失败", 2000)
                                    .setGravity(Gravity.CENTER, 0, 200)
                                    .show();
                        }

                    }
                })
                .show();
    }
}
