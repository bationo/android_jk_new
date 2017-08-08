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
    private Long planId;			//盘点计划id

    private String planNumber;		//盘点计划编号

	private Long personID;			//盘点人id

    private String inventoryPerson;	//盘点人姓名

    private String planTime;		//盘点人

    private String confirmPerson;	//审核人

    private String confirmTime;		//审核时间

    private Long departmentId;		//所要盘点的部门

    private String remark;			//备注

	@Generated(hash = 1948500185)
	public PlanBean(Long planId, String planNumber, Long personID,
									String inventoryPerson, String planTime, String confirmPerson,
									String confirmTime, Long departmentId, String remark) {
					this.planId = planId;
					this.planNumber = planNumber;
					this.personID = personID;
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

	public Long getPersonID() {
		return personID;
	}

	public void setPersonID(Long personID) {
		this.personID = personID;
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