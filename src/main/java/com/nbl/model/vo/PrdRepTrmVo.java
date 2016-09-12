package com.nbl.model.vo;

import java.util.List;

public class PrdRepTrmVo {
	private String id;

	private String productId;

	private String productName;

	private String repayCustId;

	private String repayCustName;

	private Long repayTerm;

	private String repayEndDate;

	private Long repayInterest;

	private Long amt;

	private Long repayAmt;

	private Short enabled;

	private List<Short> isFinish;

	private String repayType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getRepayCustId() {
		return repayCustId;
	}

	public void setRepayCustId(String repayCustId) {
		this.repayCustId = repayCustId == null ? null : repayCustId.trim();
	}

	public String getRepayCustName() {
		return repayCustName;
	}

	public void setRepayCustName(String repayCustName) {
		this.repayCustName = repayCustName == null ? null : repayCustName.trim();
	}

	public Long getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(Long repayTerm) {
		this.repayTerm = repayTerm;
	}

	public String getRepayEndDate() {
		return repayEndDate;
	}

	public void setRepayEndDate(String repayEndDate) {
		this.repayEndDate = repayEndDate == null ? null : repayEndDate.trim();
	}

	public Long getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(Long repayInterest) {
		this.repayInterest = repayInterest;
	}

	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	public Long getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(Long repayAmt) {
		this.repayAmt = repayAmt;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public List<Short> getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(List<Short> isFinish) {
		this.isFinish = isFinish;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType == null ? null : repayType.trim();
	}
}
