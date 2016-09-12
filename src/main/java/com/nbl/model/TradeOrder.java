package com.nbl.model;

import java.util.Date;

public class TradeOrder {
	
	private String id;

	private String contractId;

	private String productId;

	private String productNane;

	private String assetCustId;

	private String assetCustName;

	private String purchaseCustId;

	private String purchaseCustName;

	private String transferCustId;

	private String transferCustName;

	private String invenstType;

	private Long amt;
	
	private Long minTradeTalAmt;
	
	private Long maxTradeTalAmt;
	
	private Long tradeTalAmt;

	private Long redEnvAmt;

	private Long interest;

	private Long principal;

	private Long purchasePortion;

	private Long repayTerm;

	private String payOrderNo;

	private String tradeDate;

	private String orderStatus;

	private String transferId;

	private String remark;

	private Date createTime;

	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId == null ? null : contractId.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProductNane() {
		return productNane;
	}

	public void setProductNane(String productNane) {
		this.productNane = productNane == null ? null : productNane.trim();
	}

	public String getAssetCustId() {
		return assetCustId;
	}

	public void setAssetCustId(String assetCustId) {
		this.assetCustId = assetCustId == null ? null : assetCustId.trim();
	}

	public String getAssetCustName() {
		return assetCustName;
	}

	public void setAssetCustName(String assetCustName) {
		this.assetCustName = assetCustName == null ? null : assetCustName.trim();
	}

	public String getPurchaseCustId() {
		return purchaseCustId;
	}

	public void setPurchaseCustId(String purchaseCustId) {
		this.purchaseCustId = purchaseCustId == null ? null : purchaseCustId.trim();
	}

	public String getPurchaseCustName() {
		return purchaseCustName;
	}

	public void setPurchaseCustName(String purchaseCustName) {
		this.purchaseCustName = purchaseCustName == null ? null : purchaseCustName.trim();
	}

	public String getTransferCustId() {
		return transferCustId;
	}

	public void setTransferCustId(String transferCustId) {
		this.transferCustId = transferCustId == null ? null : transferCustId.trim();
	}

	public String getTransferCustName() {
		return transferCustName;
	}

	public void setTransferCustName(String transferCustName) {
		this.transferCustName = transferCustName == null ? null : transferCustName.trim();
	}

	public String getInvenstType() {
		return invenstType;
	}

	public void setInvenstType(String invenstType) {
		this.invenstType = invenstType;
	}

	public Long getAmt() {
		return amt;
	}

	public void setAmt(Long amt) {
		this.amt = amt;
	}

	public Long getInterest() {
		return interest;
	}

	public void setInterest(Long interest) {
		this.interest = interest;
	}

	public Long getPrincipal() {
		return principal;
	}

	public void setPrincipal(Long principal) {
		this.principal = principal;
	}

	public Long getPurchasePortion() {
		return purchasePortion;
	}

	public void setPurchasePortion(Long purchasePortion) {
		this.purchasePortion = purchasePortion;
	}

	public Long getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(Long repayTerm) {
		this.repayTerm = repayTerm;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate == null ? null : tradeDate.trim();
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus == null ? null : orderStatus.trim();
	}

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId == null ? null : transferId.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getTradeTalAmt() {
		return tradeTalAmt;
	}

	public void setTradeTalAmt(Long tradeTalAmt) {
		this.tradeTalAmt = tradeTalAmt;
	}

	public Long getRedEnvAmt() {
		return redEnvAmt;
	}

	public void setRedEnvAmt(Long redEnvAmt) {
		this.redEnvAmt = redEnvAmt;
	}
	
	public Long getMinTradeTalAmt() {
		return minTradeTalAmt;
	}

	public void setMinTradeTalAmt(Long minTradeTalAmt) {
		this.minTradeTalAmt = minTradeTalAmt == null ? null : minTradeTalAmt;
	}

	public Long getMaxTradeTalAmt() {
		return maxTradeTalAmt;
	}

	public void setMaxTradeTalAmt(Long maxTradeTalAmt) {
		this.maxTradeTalAmt = maxTradeTalAmt == null ? null : maxTradeTalAmt;
	}

	@Override
	public String toString() {
		return "TradeOrder [id=" + id + ", contractId=" + contractId + ", productId=" + productId + ", productNane=" + productNane + ", assetCustId=" + assetCustId + ", assetCustName=" + assetCustName
				+ ", purchaseCustId=" + purchaseCustId + ", purchaseCustName=" + purchaseCustName + ", transferCustId=" + transferCustId + ", transferCustName=" + transferCustName + ", invenstType="
				+ invenstType + ", amt=" + amt + ", minTradeTalAmt=" + minTradeTalAmt + ", maxTradeTalAmt=" + maxTradeTalAmt + ", tradeTalAmt=" + tradeTalAmt + ", redEnvAmt=" + redEnvAmt
				+ ", interest=" + interest + ", principal=" + principal + ", purchasePortion=" + purchasePortion + ", repayTerm=" + repayTerm + ", payOrderNo=" + payOrderNo + ", tradeDate="
				+ tradeDate + ", orderStatus=" + orderStatus + ", transferId=" + transferId + ", remark=" + remark + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
}