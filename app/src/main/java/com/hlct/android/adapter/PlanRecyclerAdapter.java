package com.hlct.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlct.android.R;

import java.util.ArrayList;

/**
 * Created by lazylee on 2017/7/25.
 */

public class PlanRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private ArrayList<String> mList;

    public PlanRecyclerAdapter(Context mContext, ArrayList<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stocktaking_plan_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //TODO item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.mTVTime.setText(mList.get(position));
        holder.mTVPerson.setText(mList.get(position));
        holder.mTVCount.setText(mList.get(position));
        holder.mTVCounted.setText(mList.get(position));
        holder.mTVStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO 点击事件：开始盘点
            }
        });
        holder.mTVCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO 点击事件：上传数据
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}
class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mTVTime;
    TextView mTVPerson;
    TextView mTVCount;
    TextView mTVCounted;
    TextView mTVStart;
    TextView mTVCommit;

    public MyViewHolder(View itemView) {
        super(itemView);
        mTVTime = (TextView) itemView.findViewById(R.id.stocktaking_plan_date);
        mTVPerson = (TextView) itemView.findViewById(R.id.stocktaking_plan_person);
        mTVCount = (TextView) itemView.findViewById(R.id.stocktaking_plan_count);
        mTVCounted = (TextView) itemView.findViewById(R.id.stocktaking_plan_counted);
        mTVStart = (TextView) itemView.findViewById(R.id.stocktaking_plan_tv_start);
        mTVCommit = (TextView) itemView.findViewById(R.id.stocktaking_plan_tv_commit);
    }
}
