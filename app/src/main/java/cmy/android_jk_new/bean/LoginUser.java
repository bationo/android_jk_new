package cmy.android_jk_new.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by mengyuan.cheng on 2017/6/15.
 */
@Entity
public class LoginUser {
    /**
     * 登录名(唯一).
     */
    @Unique
     private String loginName ;
    /**
     * 登陆密码.
     */
    private String passWord;
    /**
     * 指纹信息.
     */
    private String userFinger;
    /**
     * 人员类别.
     */
    private String userType;

    @Generated(hash = 1917159828)
    public LoginUser(String loginName, String passWord, String userFinger,
            String userType) {
        this.loginName = loginName;
        this.passWord = passWord;
        this.userFinger = userFinger;
        this.userType = userType;
    }

    @Generated(hash = 1159929338)
    public LoginUser() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
