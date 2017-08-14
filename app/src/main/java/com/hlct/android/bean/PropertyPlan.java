package com.hlct.android.bean;

import java.io.Serializable;
import java.util.List;


public class PropertyPlan implements Serializable {

    private static final long serialVersionUID = 5981560250804078888L;
    private List<Detail> detail;
    private List<PlanBean> planList;
    private List<User> user;
    private List<InfoBean> info;
    private List<AssetBean> asset;
    private List<BankInfo> bankInfo;

    public List<PlanBean> getPlanList() {
        return planList;
    }

    public void setPlanList(List<PlanBean> planList) {
        this.planList = planList;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public List<AssetBean> getAsset() {
        return asset;
    }

    public void setAsset(List<AssetBean> asset) {
        this.asset = asset;
    }

    public List<BankInfo> getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(List<BankInfo> bankInfo) {
        this.bankInfo = bankInfo;
    }

    public List<Detail> getDetail() {
        return detail;
    }


    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }


    public List<User> getUser() {
        return user;
    }


    public void setUser(List<User> user) {
        this.user = user;
    }

}
