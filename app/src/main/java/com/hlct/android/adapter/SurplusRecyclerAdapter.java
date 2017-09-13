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
import com.hlct.android.bean.BankInfo;
import com.hlct.android.bean.InventorySurplus;
import com.hlct.android.bean.MessageEvent;
import com.hlct.android.bean.User;
import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.AssetBeanDao;
import com.hlct.android.greendao.BankInfoDao;
import com.hlct.android.greendao.DaoSession;
import com.hlct.android.greendao.UserDao;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by lazylee on 2017/9/4.
 */

public class SurplusRecyclerAdapter extends RecyclerView.Adapter<SurplusViewHolder> {

    private static final String TAG = "SurplusRecyclerAdapter";
    private Context mContext;
    private List<InventorySurplus> mList;

    public SurplusRecyclerAdapter(Context mContext, List<InventorySurplus> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public SurplusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stocktaking_detail_view, parent, false);
        return new SurplusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurplusViewHolder holder, final int position) {
        //TODO item的点击事件 和 赋值
        DaoSession daoSession = DatabaseConstant.setupDatabase(mContext);
        AssetBean assetBean = daoSession.getAssetBeanDao().queryBuilder()
                .where(AssetBeanDao.Properties.Id.eq(mList.get(position).getAssertId()))
                .unique();
        User user = daoSession.getUserDao().queryBuilder()
                .where(UserDao.Properties.Id.eq(assetBean.getUserId()))
                .unique();
        BankInfo bankInfo = daoSession.getBankInfoDao().queryBuilder()
                .where(BankInfoDao.Properties.Id.eq(user.getBankId()))
                .unique();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(
                        new MessageEvent(TAG,"StocktakingDetailActivity",mList.get(position).getAssertId()));
                mContext.startActivity(new Intent(mContext, StocktakingDetailActivity.class));
            }
        });
        holder.mTVAssertName.setText(assetBean.getFacilityName());
        holder.mTVNumber.setText(assetBean.getFacilityNumber());
        holder.mTVAssertRFID.setText(assetBean.getRfid());
        holder.mTVAssertUser.setText(user.getName());
        holder.mTVAssertType.setText(assetBean.getFacilityType());
        holder.mTVAssertUnit.setText(bankInfo.getBankName());
        holder.mTVAssertDepartment.setText(user.getDepartmentName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}

class SurplusViewHolder extends RecyclerView.ViewHolder {
    TextView mTVAssertName;
    TextView mTVNumber;
    TextView mTVAssertRFID;
    TextView mTVAssertUser;
    TextView mTVAssertType;
    TextView mTVAssertDepartment;
    TextView mTVAssertUnit;

    public SurplusViewHolder(View itemView) {
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

