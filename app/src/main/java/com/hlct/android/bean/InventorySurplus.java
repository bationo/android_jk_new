package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 记录盘点盈余
 * Created by lazylee on 2017/9/4.
 */
@Entity
public class InventorySurplus {

    private long assertId;      //资产id

    private long planId;        //计划id

    @Generated(hash = 104373547)
    public InventorySurplus(long assertId, long planId) {
        this.assertId = assertId;
        this.planId = planId;
    }

    @Generated(hash = 349116252)
    public InventorySurplus() {
    }

    public long getAssertId() {
        return this.assertId;
    }

    public void setAssertId(long assertId) {
        this.assertId = assertId;
    }

    public long getPlanId() {
        return this.planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }
    
}
