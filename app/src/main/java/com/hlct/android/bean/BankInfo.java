package com.hlct.android.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BankInfo {

	@Id
    private Long id;
	
    private String bankId;

    private String bankName;

    private String bankLevel;

    private String bankAddress;
	@Transient
    private Long parentsId;

	@Generated(hash = 1593952253)
	public BankInfo(Long id, String bankId, String bankName, String bankLevel,
									String bankAddress) {
					this.id = id;
					this.bankId = bankId;
					this.bankName = bankName;
					this.bankLevel = bankLevel;
					this.bankAddress = bankAddress;
	}
	@Generated(hash = 1911969190)
	public BankInfo() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankLevel() {
		return bankLevel;
	}
	public void setBankLevel(String bankLevel) {
		this.bankLevel = bankLevel;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public Long getParentsId() {
		return parentsId;
	}
	public void setParentsId(Long parentsId) {
		this.parentsId = parentsId;
	}

}