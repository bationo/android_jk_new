package com.hlct.android.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hlct.android.R;
import com.hlct.android.adapter.DetailRecyclerAdapter;
import com.hlct.android.bean.Detail;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.DetailDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lazylee on 2017/7/26.
 */

public class StocktakingDetailFragment extends Fragment {

    // 注意，tag用于 打印LOG信息时最多 23个字符
    private static String TAG = "StocktakingDetailFragment";
    private Context mContext;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DetailRecyclerAdapter mRecyclerAdapter;

    /*****data*****/
    private List<Detail> mList = new ArrayList<>();
    private long planId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_stocktaking_detail, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity();

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.fragment_stocktaking_detail_recycler);
        mRecyclerAdapter = new DetailRecyclerAdapter(mContext, mList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //设置下来加载更多
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.fragment_stocktaking_detail_SR);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 下拉刷新加载更多数据
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("DTFragment------->", "onPause方法调用");
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        Log.e("DTFragment------->", "onResume方法调用");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 加载数据填在recyclerView中。
     */
    private void initData() {
        //TODO 加载数据
        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
        Log.e("DTFragment------->", "planid ====" + planId);
        List<Detail> list = daoSession.getDetailDao().queryBuilder()
                .where(DetailDao.Properties.PlanId.eq(planId))
                .where(DetailDao.Properties.InventoryState.eq("未盘点"))
                .orderAsc(DetailDao.Properties.DetailId)
                .list();
        mList.clear();
        mList.addAll(list);
        mRecyclerAdapter.notifyDataSetChanged();
        //TODO 根据 是否盘点| planId  条件查询到结果
       /* mList = daoSession.getDetailDao().queryBuilder()
                .where(DetailDao.Properties.InventoryState.eq("未盘点"))
                .orderAsc(DetailDao.Properties.DetailId)
                .list();*/
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }


    /**
     * 接收id
     *
     * @param id 消息事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onHandleId(Long id) {
        this.planId = id;
        Log.e("DTFragment------->", "收到的planid号是----->" + planId);
    }

}
