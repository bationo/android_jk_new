package com.hlct.android.uhf;

/**
 * Created by lazylee on 2017/8/7.
 */

public class RFID {
    private boolean isCheck;
    private String rifd;

    public RFID() {
    }

    public RFID(boolean isCheck, String rifd) {
        this.isCheck = isCheck;
        this.rifd = rifd;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getRifd() {
        return rifd;
    }

    public void setRifd(String rifd) {
        this.rifd = rifd;
    }

    @Override
    public String toString() {
        return "RFID{" +
                "isCheck=" + isCheck +
                ", rifd='" + rifd + '\'' +
                '}';
    }
}
