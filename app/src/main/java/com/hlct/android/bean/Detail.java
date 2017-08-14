package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 对应的是 盘点详情表中的数据
 */
@Entity
public class Detail implements Serializable {

    private static final long serialVersionUID = 1111111221111111110L;

    /**
     * 详情id
     */
    @Id
    private Long detailId;              //盘点详情 id

    /**
     * 计划id
     */
    private Long planId;                //所属的盘点计划id

    /**
     * 盘点人员
     */
    private String inventroryName;

    /**
     * 盘点状态
     */
    private String inventoryState;

    /**
     * 盘点时间
     */
    private String inventoryTime;

    /**
     * 资产id
     */
    private Long propertyId;

    /**
     * 资产名称
     */
    private String propertyName;

    /**
     * 资产rfid
     */
    private String propertyRfid;

    /**
     * 使用人
     */
    private Long userId;

    /**
     * 备注
     */
    private String remark;

    private Long departmentID;



    @Generated(hash = 1919006003)
    public Detail(Long detailId, Long planId, String inventroryName,
            String inventoryState, String inventoryTime, Long propertyId,
            String propertyName, String propertyRfid, Long userId, String remark,
            Long departmentID) {
        this.detailId = detailId;
        this.planId = planId;
        this.inventroryName = inventroryName;
        this.inventoryState = inventoryState;
        this.inventoryTime = inventoryTime;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyRfid = propertyRfid;
        this.userId = userId;
        this.remark = remark;
        this.departmentID = departmentID;
    }

    @Generated(hash = 1665969126)
    public Detail() {
    }



    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getInventroryName() {
        return inventroryName;
    }

    public void setInventroryName(String inventroryName) {
        this.inventroryName = inventroryName;
    }

    public String getInventoryState() {
        return inventoryState;
    }

    public void setInventoryState(String inventoryState) {
        this.inventoryState = inventoryState;
    }

    public String getInventoryTime() {
        return inventoryTime;
    }

    public void setInventoryTime(String inventoryTime) {
        this.inventoryTime = inventoryTime;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyRfid() {
        return propertyRfid;
    }

    public void setPropertyRfid(String propertyRfid) {
        this.propertyRfid = propertyRfid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Long departmentID) {
        this.departmentID = departmentID;
    }

}