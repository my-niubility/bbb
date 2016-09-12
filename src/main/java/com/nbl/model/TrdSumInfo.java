package com.nbl.model;

import java.util.Date;

/**
 * @author AlanMa
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :交易概要信息（交易订单+产品）
 */
public class TrdSumInfo {
	private String id;
	private String productId;
	private String productNane;
	private Long tradeTalAmt;
	private Long purchasePortion;
	private String orderStatus;
	private Date createTime;
	private Long expectEarnRate;
	private Long unitCost;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductNane() {
		return productNane;
	}

	public void setProductNane(String productNane) {
		this.productNane = productNane;
	}



	public Long getTradeTalAmt() {
		return tradeTalAmt;
	}

	public void setTradeTalAmt(Long tradeTalAmt) {
		this.tradeTalAmt = tradeTalAmt;
	}

	public Long getPurchasePortion() {
		return purchasePortion;
	}

	public void setPurchasePortion(Long purchasePortion) {
		this.purchasePortion = purchasePortion;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getExpectEarnRate() {
		return expectEarnRate;
	}

	public void setExpectEarnRate(Long expectEarnRate) {
		this.expectEarnRate = expectEarnRate;
	}

	public Long getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Long unitCost) {
		this.unitCost = unitCost;
	}

	@Override
	public String toString() {
		return "TrdSumInfo [id=" + id + ", productId=" + productId + ", productNane=" + productNane + ", tradeTalAmt=" + tradeTalAmt + ", purchasePortion=" + purchasePortion + ", orderStatus="
				+ orderStatus + ", createTime=" + createTime + ", expectEarnRate=" + expectEarnRate + ", unitCost=" + unitCost + "]";
	}


}
