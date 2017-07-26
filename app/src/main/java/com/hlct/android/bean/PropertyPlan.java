package com.hlct.android.bean;

import java.io.Serializable;
import java.util.List;


public class PropertyPlan implements Serializable {
    private List<Detail> detail;
    private List<User> user;

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
