package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 2111121112112111220L;

    private String LOGIN_NAME;

    private String PASSWORD;

    @Generated(hash = 1737919930)
    public User(String LOGIN_NAME, String PASSWORD) {
        this.LOGIN_NAME = LOGIN_NAME;
        this.PASSWORD = PASSWORD;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getLOGIN_NAME() {
        return LOGIN_NAME;
    }

    public void setLOGIN_NAME(String LOGIN_NAME) {
        this.LOGIN_NAME = LOGIN_NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}