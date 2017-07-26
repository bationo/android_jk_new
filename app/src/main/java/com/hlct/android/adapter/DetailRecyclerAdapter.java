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
 * Created by lazylee on 2017/7/26.
 */

public class DetailRecyclerAdapter extends RecyclerView.Adapter<DetailViewHolder> {
    private Context mContext;
    private ArrayList<String> mList;

    public DetailRecyclerAdapter(Context mContext, ArrayList<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stocktaking_detail_view,parent,false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        //TODO item的点击事件 和 赋值
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.mTVAssertName.setText(mList.get(position));
        holder.mTVNumber.setText(mList.get(position));
        holder.mTVAssertRFID.setText(mList.get(position));
        holder.mTVAssertUser.setText(mList.get(position));
        holder.mTVAssertType.setText(mList.get(position));
        holder.mTVAssertUnit.setText(mList.get(position));
        holder.mTVAssertDepartment.setText(mList.get(position));

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
