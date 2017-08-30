package com.hlct.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlct.android.R;
import com.hlct.android.activity.MainActivity;
import com.hlct.android.activity.StocktakingPlanActivity;
import com.hlct.android.bean.Detail;
import com.hlct.android.bean.PlanBean;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.DetailDao;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by lazylee on 2017/7/25.
 */

public class PlanRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private static final String TAG = "PlanRecyclerAdapter";
    private Context mContext;
    private List<PlanBean> mList;

    public PlanRecyclerAdapter(Context mContext, List<PlanBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stocktaking_plan_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //TODO item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(mList.get(position).getPlanId());
                mContext.startActivity(new Intent(mContext.getApplicationContext(), StocktakingPlanActivity.class));
            }
        });
        holder.mTVTitle.setText(mList.get(position).getPlanNumber());
        holder.mTVTime.setText(mList.get(position).getPlanTime());
        holder.mTVPerson.setText(mList.get(position).getInventoryPerson());
        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
        List<Detail> details = daoSession.getDetailDao().queryBuilder()
                .where(DetailDao.Properties.PlanId.eq(mList.get(position).getPlanId()))
                .list();
        holder.mTVCount.setText(details.size() + "");
        List<Detail> detailList = daoSession.getDetailDao().queryBuilder()
                .where(DetailDao.Properties.PlanId.eq(mList.get(position).getPlanId()))
                .where(DetailDao.Properties.InventoryState.eq("已盘点"))
                .list();
        holder.mTVCounted.setText(detailList.size() + "");
        holder.mTVStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击事件：开始盘点
                EventBus.getDefault().postSticky(mList.get(position).getPlanId());
                mContext.startActivity(new Intent(mContext.getApplicationContext(), StocktakingPlanActivity.class));
            }
        });
        holder.mTVCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击事件：上传数据
                ((MainActivity)mContext).commitData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mTVTitle;
    TextView mTVTime;
    TextView mTVPerson;
    TextView mTVCount;
    TextView mTVCounted;
    TextView mTVStart;
    TextView mTVCommit;

    public MyViewHolder(View itemView) {
        super(itemView);
        mTVTitle = (TextView) itemView.findViewById(R.id.stocktaking_plan_title);
        mTVTime = (TextView) itemView.findViewById(R.id.stocktaking_plan_date);
        mTVPerson = (TextView) itemView.findViewById(R.id.stocktaking_plan_person);
        mTVCount = (TextView) itemView.findViewById(R.id.stocktaking_plan_count);
        mTVCounted = (TextView) itemView.findViewById(R.id.stocktaking_plan_counted);
        mTVStart = (TextView) itemView.findViewById(R.id.stocktaking_plan_tv_start);
        mTVCommit = (TextView) itemView.findViewById(R.id.stocktaking_plan_tv_commit);
    }
}
