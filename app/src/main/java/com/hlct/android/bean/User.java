package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 2111121112112111220L;

    /**
     * 登录名
     */
    private String LOGIN_NAME;

    /**
     * 登陆密码
     */
    private String PASSWORD;

    /**
     * 姓名
     */
    private String NAME;

    /**
     * 部门
     */
    private String DEPARTMENT_NAME;

    @Generated(hash = 779813423)
    public User(String LOGIN_NAME, String PASSWORD, String NAME,
            String DEPARTMENT_NAME) {
        this.LOGIN_NAME = LOGIN_NAME;
        this.PASSWORD = PASSWORD;
        this.NAME = NAME;
        this.DEPARTMENT_NAME = DEPARTMENT_NAME;
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

    public String getNAME() {
        return this.NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDEPARTMENT_NAME() {
        return this.DEPARTMENT_NAME;
    }

    public void setDEPARTMENT_NAME(String DEPARTMENT_NAME) {
        this.DEPARTMENT_NAME = DEPARTMENT_NAME;
    }
}