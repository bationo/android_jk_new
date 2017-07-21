package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/7/5
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description : 网点信息实体类
 */
@Entity
public class BankInfo {
    @Id@Unique
    private Long bankId;//网点ID
    @NotNull
    private String bankName;//网点名称
    private String lineId;//线路信息
    private String bankTaskStatus;//网点任务状态

    @Generated(hash = 2036481339)
    public BankInfo(Long bankId, @NotNull String bankName, String lineId,
            String bankTaskStatus) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.lineId = lineId;
        this.bankTaskStatus = bankTaskStatus;
    }

    @Generated(hash = 1911969190)
    public BankInfo() {
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBankTaskStatus() {
        return bankTaskStatus;
    }

    public void setBankTaskStatus(String bankTaskStatus) {
        this.bankTaskStatus = bankTaskStatus;
    }

}
