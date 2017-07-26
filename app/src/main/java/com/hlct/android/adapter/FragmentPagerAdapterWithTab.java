package com.hlct.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by lazylee on 2017/7/25.
 */

public class FragmentPagerAdapterWithTab extends FragmentPagerAdapter{
    private ArrayList<Fragment> mList;
    private ArrayList<String> mTab;
    public FragmentPagerAdapterWithTab(FragmentManager fm) {
        super(fm);
    }
    public FragmentPagerAdapterWithTab(FragmentManager fm, ArrayList<Fragment> mList, ArrayList<String> mTab) {
        super(fm);
        this.mList = mList;
        this.mTab = mTab;
    }
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return mTab.get(position);
    }
}
