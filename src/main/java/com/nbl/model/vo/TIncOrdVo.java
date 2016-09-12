package com.nbl.model.vo;

/**
 * @author AlanMa
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description 资金流水——收益
 */
public class TIncOrdVo{
	
	/**
	 *交易时间
	 */
	private String tradeTime;
	/**
	 *说明
	 */
	private String remark;
	/**
	 *订单号
	 */
	private String orderId;
	/**
	 *收入
	 */
	private String benefit;
	/**
	 *支出
	 */
	private String expend;
	/**
	 *状态
	 */
	private String status;
	
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
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
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}
	public String getExpend() {
		return expend;
	}
	public void setExpend(String expend) {
		this.expend = expend;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
