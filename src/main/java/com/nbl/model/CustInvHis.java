package com.nbl.model;

import java.util.Date;

public class CustInvHis {

	/**
	 * 产品编号
	 */
	private String productId;
	/**
	 * 购买数量
	 */
	private Long purchasePortion;
	/**
	 * 购买金额
	 */
	private Long tradeTalAmt;
	/**
	 * 购买时间
	 */
	private Date updateTime;
	/**
	 * 偿还方式（0：等额本息还款 1：一次还本付息 2:等额本金 3：每月还息到期还本 4:每月还息到期还本(天)
	 */
	private String repayMode;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getPurchasePortion() {
		return purchasePortion;
	}

	public void setPurchasePortion(Long purchasePortion) {
		this.purchasePortion = purchasePortion;
	}

	public Long getTradeTalAmt() {
		return tradeTalAmt;
	}

	public void setTradeTalAmt(Long tradeTalAmt) {
		this.tradeTalAmt = tradeTalAmt;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRepayMode() {
		return repayMode;
	}

	public void setRepayMode(String repayMode) {
		this.repayMode = repayMode;
	}

	@Override
	public String toString() {
		return "CustInvHis [productId=" + productId + ", purchasePortion=" + purchasePortion + ", tradeTalAmt=" + tradeTalAmt + ", updateTime=" + updateTime + ", repayMode=" + repayMode + "]";
	}

}