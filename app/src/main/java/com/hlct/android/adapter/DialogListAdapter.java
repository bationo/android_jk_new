package com.hlct.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hlct.android.R;
import com.hlct.android.uhf.RFID;

import java.util.ArrayList;

/**
 * Created by lazylee on 2017/8/7.
 */

public class DialogListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<RFID> mList;

    public DialogListAdapter(Context mContext, ArrayList<RFID> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView =LayoutInflater.from(mContext).
                    inflate(R.layout.dialog_rfid_scan_list_item,parent,false);
            holder = new ViewHolder();
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.dialog_rfid_scan_check);
            holder.mRFID = (TextView) convertView.findViewById(R.id.dialog_rfid_scan_rfid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mCheckBox.setChecked(mList.get(position).isCheck());
        holder.mRFID.setText(mList.get(position).getRifd());
        return convertView;

    }
}
 class ViewHolder{
     CheckBox mCheckBox;
     TextView mRFID;

 }
