package com.nbl.model;

import java.util.Date;

public class SerialFund {
	/**
	 * 交易时间
	 */
	private Date tradeTime;

	/**
	 * 说明
	 */
	private String remark;

	/**
	 * 订单号
	 */
	private String orderId;

	/**
	 * 收入
	 */
	private Long benefit;

	/**
	 * 支出
	 */
	private Long expend;

	/**
	 * 状态
	 */
	private String status;

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getBenefit() {
		return benefit;
	}

	public void setBenefit(Long benefit) {
		this.benefit = benefit;
	}

	public Long getExpend() {
		return expend;
	}

	public void setExpend(Long expend) {
		this.expend = expend;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
  
}