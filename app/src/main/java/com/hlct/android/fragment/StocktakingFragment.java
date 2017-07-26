package com.hlct.android.fragment;

import android.content.Context;
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

import com.hlct.android.R;
import com.hlct.android.adapter.PlanRecyclerAdapter;

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
    private ArrayList<String> mList = new ArrayList<>();

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
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.fragment_stocktaking_recycler);
        mRecyclerAdapter = new PlanRecyclerAdapter(mContext,mList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        initData();
        //TODO 设置下来加载更多
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.fragment_stocktaking_SR);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO 下拉刷新加载更多数据
                try {
                    Thread.sleep(2000);
                    if(mSwipeRefreshLayout.isRefreshing()){
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        for (int i =0 ; i <10 ;i++){
            mList.add("item"+i);
        }
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }
}
