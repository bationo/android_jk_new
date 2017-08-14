package com.hlct.android.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

@Entity
public class AssetBean{

	@Id
    private Long id;					//资产id

    private Long supplierId;			//供应商id

    private String facilityName;		//设备名称

    private String facilityNumber;		//设备编号

    private String facilityType;		//设备类别

    private Long bankId;				//所属单位id

    private String bankName;			//所属单位名称

    private Long departmentId;		//所属部门id

    private String departmentName;		//所属部门名称

    private String prooertySource;		//设备来源

    private String status;				//设备状态

    private Date useDate;				//启用日期

    private String userName;			//使用人姓名

    private String rfid;				//设备rfid号

    private Long warehouseId;			//所属仓库id

    private String costing;				//成本

    private Long userId;				//使用人id




	@Generated(hash = 866593665)
	public AssetBean(Long id, Long supplierId, String facilityName,
									String facilityNumber, String facilityType, Long bankId,
									String bankName, Long departmentId, String departmentName,
									String prooertySource, String status, Date useDate, String userName,
									String rfid, Long warehouseId, String costing, Long userId) {
					this.id = id;
					this.supplierId = supplierId;
					this.facilityName = facilityName;
					this.facilityNumber = facilityNumber;
					this.facilityType = facilityType;
					this.bankId = bankId;
					this.bankName = bankName;
					this.departmentId = departmentId;
					this.departmentName = departmentName;
					this.prooertySource = prooertySource;
					this.status = status;
					this.useDate = useDate;
					this.userName = userName;
					this.rfid = rfid;
					this.warehouseId = warehouseId;
					this.costing = costing;
					this.userId = userId;
	}
	@Generated(hash = 596597220)
	public AssetBean() {
	}

	


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getFacilityNumber() {
		return facilityNumber;
	}
	public void setFacilityNumber(String facilityNumber) {
		this.facilityNumber = facilityNumber;
	}
	public String getFacilityType() {
		return facilityType;
	}
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
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
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getProoertySource() {
		return prooertySource;
	}
	public void setProoertySource(String prooertySource) {
		this.prooertySource = prooertySource;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getCosting() {
		return costing;
	}
	public void setCosting(String costing) {
		this.costing = costing;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}