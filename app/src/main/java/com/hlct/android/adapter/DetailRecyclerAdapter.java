package com.hlct.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hlct.android.R;
import com.hlct.android.activity.StocktakingDetailActivity;
import com.hlct.android.bean.AssetBean;
import com.hlct.android.bean.Detail;
import com.hlct.android.bean.MessageEvent;
import com.hlct.android.bean.User;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.AssetBeanDao;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.UserDao;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by lazylee on 2017/7/26.
 */

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailViewHolder> {
    private Context mContext;
    private List<Detail> mList;

    public DetailRecyclerAdapter(Context mContext, List<Detail> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stocktaking_detail_view, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, final int position) {
        //TODO item的点击事件 和 赋值
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(
                        new MessageEvent("DetailRecyclerAdapter","StocktakingDetailActivity",mList.get(position).getDetailId()));
                mContext.startActivity(new Intent(mContext, StocktakingDetailActivity.class));
            }
        });
        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
        AssetBean assetBean = daoSession.getAssetBeanDao().queryBuilder()
                .where(AssetBeanDao.Properties.Id.eq(mList.get(position).getPropertyId()))
                .unique();
        User user = daoSession.getUserDao().queryBuilder()
                .where(UserDao.Properties.Id.eq(mList.get(position).getUserId()))
                .unique();
        holder.mTVAssertName.setText(assetBean.getFacilityName());
        holder.mTVNumber.setText(assetBean.getFacilityNumber());
        holder.mTVAssertRFID.setText(assetBean.getRfid());
        holder.mTVAssertUser.setText(user.getName());
        holder.mTVAssertType.setText(assetBean.getFacilityType());
        holder.mTVAssertUnit.setText(user.getBankName());
        holder.mTVAssertDepartment.setText(user.getDepartmentName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class DetailViewHolder extends RecyclerView.ViewHolder {
    TextView mTVAssertName;
    TextView mTVNumber;
    TextView mTVAssertRFID;
    TextView mTVAssertUser;
    TextView mTVAssertType;
    TextView mTVAssertDepartment;
    TextView mTVAssertUnit;

    public DetailViewHolder(View itemView) {
        super(itemView);
        mTVAssertName = (TextView) itemView.findViewById(R.id.stocktaking_detail_card_assert_name);
        mTVNumber = (TextView) itemView.findViewById(R.id.stocktaking_detail_card_assert_num);
        mTVAssertRFID = (TextView) itemView.findViewById(R.id.stocktaking_detail_card_assert_rfid);
        mTVAssertUser = (TextView) itemView.findViewById(R.id.stocktaking_detail_card_assert_user);
        mTVAssertType = (TextView) itemView.findViewById(R.id.stocktaking_detail_card_assert_type);
        mTVAssertDepartment = (TextView) itemView.findViewById(R.id.stocktaking_detail_card_assert_department);
        mTVAssertUnit = (TextView) itemView.findViewById(R.id.stocktaking_detail_card_assert_unit);
    }
}
