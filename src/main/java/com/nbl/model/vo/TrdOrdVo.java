package com.nbl.model.vo;

import java.util.Date;

/**
 * @author AlanMa
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :根据客户信息（custId，createDate，status）分页查询充值订单的條件
 */
public class TrdOrdVo{
	
	/**
	 * 用户编号（电话）
	 */
	private String userId;

	/**
	 * 客户编号
	 */
	private String custId;
	/**
	 * 起始日期
	 */
	private Date beginDate;
	/**
	 * 终止日期
	 */
	private Date endDate;
	/**
	 * 状态：00:等待付款、01:支付成功、02:支付失败
	 */
	private String status;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TrdOrdVo(String userId, String custId, Date beginDate, Date endDate, String status) {
		this.userId = userId;
		this.custId = custId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.status = status;
	}
	public TrdOrdVo() {
		super();
	}
	
}
