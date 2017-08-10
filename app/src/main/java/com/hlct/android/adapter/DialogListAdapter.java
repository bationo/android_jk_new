package com.hlct.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
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
    private long mPlanID;

    public DialogListAdapter(Context mContext, ArrayList<RFID> mList, long planID) {
        this.mContext = mContext;
        this.mList = mList;
        this.mPlanID = planID;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.dialog_rfid_scan_list_item, parent, false);
            holder = new ViewHolder();
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.dialog_rfid_scan_check);
            holder.mRFID = (TextView) convertView.findViewById(R.id.dialog_rfid_scan_rfid);
            holder.mLine = (LinearLayout) convertView.findViewById(R.id.dialog_rfid_scan_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mCheckBox.setChecked(mList.get(position).isCheck());
        holder.mCheckBox.setClickable(false);
        holder.mRFID.setText(mList.get(position).getRifd());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //mList.get(position).setCheck(isChecked);
            }
        });

        holder.mLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mCheckBox.isChecked()) {
                    //holder.mCheckBox.setChecked(false);
                } else {
                    //holder.mCheckBox.setChecked(true);
                }
            }
        });
        return convertView;

    }
}

class ViewHolder {
    LinearLayout mLine;
    CheckBox mCheckBox;
    TextView mRFID;

}
