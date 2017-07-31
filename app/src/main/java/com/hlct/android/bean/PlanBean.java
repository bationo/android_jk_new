package com.hlct.android.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

;

/**
 * 盘点计划对应的实体类
 */
@Entity
public class PlanBean{

	@Id
    private Long planId;
    private String planNumber;
    private String inventoryPerson;
    private String planTime;
    private String confirmPerson;
    private String confirmTime;
    private Long departmentId;
    private String remark;

	@Generated(hash = 1080765765)
	public PlanBean(Long planId, String planNumber, String inventoryPerson,
									String planTime, String confirmPerson, String confirmTime,
									Long departmentId, String remark) {
					this.planId = planId;
					this.planNumber = planNumber;
					this.inventoryPerson = inventoryPerson;
					this.planTime = planTime;
					this.confirmPerson = confirmPerson;
					this.confirmTime = confirmTime;
					this.departmentId = departmentId;
					this.remark = remark;
	}
	@Generated(hash = 1985632014)
	public PlanBean() {
	}

	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}
	public String getInventoryPerson() {
		return inventoryPerson;
	}
	public void setInventoryPerson(String inventoryPerson) {
		this.inventoryPerson = inventoryPerson;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getConfirmPerson() {
		return confirmPerson;
	}
	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}