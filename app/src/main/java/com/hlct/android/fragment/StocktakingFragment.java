package com.hlct.android.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlct.android.R;
import com.hlct.android.adapter.PlanRecyclerAdapter;
import com.hlct.android.bean.Detail;
import com.hlct.android.bean.PlanBean;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.DaoMaster;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.DetailDao;
import com.hlct.android.greendao.PlanBeanDao;
import com.hlct.android.util.FileUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lazylee on 2017/7/24.
 */

public class StocktakingFragment extends Fragment {

    private static String TAG = "StocktakingFragment";
    private Context mContext;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PlanRecyclerAdapter mRecyclerAdapter;

    /*****data*****/
    private ArrayList<PlanBean> mList = new ArrayList<>();
    private ArrayList<Detail> detailList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_stocktaking,container,false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity();
        initData();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.fragment_stocktaking_recycler);
        mRecyclerAdapter = new PlanRecyclerAdapter(mContext,mList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //TODO 设置下来加载更多
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.fragment_stocktaking_SR);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 下拉刷新加载更多数据
                if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /**
     * 加载数据填在recyclerView中。
     *
     */
    private void initData() {
        //TODO 加载数据
//        new DataThread().start();
        Gson gson = new Gson();

        try {
            String details = FileUtils.readFile(DatabaseConstant.FILE_PATH + "detail.txt");
            detailList = gson.fromJson(details, new TypeToken<ArrayList<Detail>>() {}.getType() );
            String plans = FileUtils.readFile(DatabaseConstant.FILE_PATH + "plan.txt");
            mList = gson.fromJson(plans, new TypeToken<ArrayList<PlanBean>>() {}.getType() );

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext,"文件解析失败",Toast.LENGTH_SHORT).show();
        }


    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }


    /**
     * 将数据写入数据库
     */
    private class DataThread extends  Thread{
        @Override
        public void run() {
            super.run();
            Gson gson = new Gson();
            ArrayList<Detail> detailList = new ArrayList<>();
            ArrayList<PlanBean> planList = new ArrayList<>();
            try {
                String details = FileUtils.readFile(DatabaseConstant.FILE_PATH + "detail.txt");
                detailList = gson.fromJson(details, new TypeToken<ArrayList<Detail>>() {}.getType() );
                String plans = FileUtils.readFile(DatabaseConstant.FILE_PATH + "plan.txt");
                planList = gson.fromJson(plans, new TypeToken<ArrayList<Detail>>() {}.getType() );
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mContext,"文件解析失败",Toast.LENGTH_SHORT).show();
            }
            //创建数据库
            DaoMaster.DevOpenHelper helper =
                    new DaoMaster.DevOpenHelper(mContext, DatabaseConstant.DATABASE_NAME, null);
            //获取可写数据库
            SQLiteDatabase db = helper.getWritableDatabase();
            //获取数据库对象
            DaoMaster dm = new DaoMaster(db);
            //获取Dao对象的管理者
            DaoSession daoSession = dm.newSession();

            DetailDao detailDao = daoSession.getDetailDao();
            detailDao.insertOrReplaceInTx(detailList);
            PlanBeanDao planBeanDao = daoSession.getPlanBeanDao();
            planBeanDao.insertOrReplaceInTx(planList);
        }
    }
}

