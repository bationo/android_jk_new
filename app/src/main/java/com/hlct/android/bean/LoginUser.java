package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mengyuan.cheng on 2017/6/15.
 */
@Entity
public class LoginUser {
    /**
     * 登录名(唯一||不为空).
     */
    @Unique
    @NotNull
    private String login_name;
    /**
     * 登陆密码(不为空).
     */
    @NotNull
    private String login_password;
    /**
     * 指纹信息.
     */
    private String userFinger;
    /**
     * 人员类别.
     */
    private String userType;

    public LoginUser(String login_name, String login_password) {
        this.login_name = login_name;
        this.login_password = login_password;
    }

    public LoginUser(String login_name, String login_password, String userFinger) {
        this.login_name = login_name;
        this.login_password = login_password;
        this.userFinger = userFinger;
    }


    @Generated(hash = 1521885696)
    public LoginUser(@NotNull String login_name, @NotNull String login_password,
                     String userFinger, String userType) {
        this.login_name = login_name;
        this.login_password = login_password;
        this.userFinger = userFinger;
        this.userType = userType;
    }

    @Generated(hash = 1159929338)
    public LoginUser() {
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }

    public String getUserFinger() {
        return userFinger;
    }

    public void setUserFinger(String userFinger) {
        this.userFinger = userFinger;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
