package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *  不应部门信息表的实体类
 */
@Entity
public class InfoBean{
	@Id
    private Long departmentId;				//部门id

    private Long departmentNumber;			//部门编号

    private String departmentName;			//部门名称

    private String departmentConnecter;		//部门联系人

    private String phone;					//部门电话

    private String departmentFax;			//部门传真

    private String departmentAddress;		//部门地址

    private String remark;					//备注

    private Long bankId;					//所属单位/银行id

	@Generated(hash = 22311076)
	public InfoBean(Long departmentId, Long departmentNumber, String departmentName,
									String departmentConnecter, String phone, String departmentFax,
									String departmentAddress, String remark, Long bankId) {
					this.departmentId = departmentId;
					this.departmentNumber = departmentNumber;
					this.departmentName = departmentName;
					this.departmentConnecter = departmentConnecter;
					this.phone = phone;
					this.departmentFax = departmentFax;
					this.departmentAddress = departmentAddress;
					this.remark = remark;
					this.bankId = bankId;
	}
	@Generated(hash = 134777477)
	public InfoBean() {
	}

	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDepartmentNumber() {
		return departmentNumber;
	}
	public void setDepartmentNumber(Long departmentNumber) {
		this.departmentNumber = departmentNumber;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentConnecter() {
		return departmentConnecter;
	}
	public void setDepartmentConnecter(String departmentConnecter) {
		this.departmentConnecter = departmentConnecter;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone  = phone;
	}
	public String getDepartmentFax() {
		return departmentFax;
	}
	public void setDepartmentFax(String departmentFax) {
		this.departmentFax = departmentFax;
	}
	public String getDepartmentAddress() {
		return departmentAddress;
	}
	public void setDepartmentAddress(String departmentAddress) {
		this.departmentAddress = departmentAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

}