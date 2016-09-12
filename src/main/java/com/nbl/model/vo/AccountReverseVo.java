package com.nbl.model.vo;

import java.util.Date;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :冲正接口请求实体类（内部处理bean）
 */
public class AccountReverseVo {


	private String orgTradeId;          // 必填 | 原交易订单编号                                                                     
	
	private String orgPayId ;           // 必填 | 原支付订单编号
	
	private long amount;                // 必填 |冲正份额（必须大于0，否则报错）
	
	private String ruleId;        		// 必填 |记账规则 (用于，冲正时记账，不过是反方向记账)
	
	private String payerCustID;   		// 必填|付款方客户编号                                 
	
	private String payeeCustID;   		// 必填|收款方客户编号                                  
	
	private String optCode;       		//交易类型
	
	private String dealCode;      		//业务步骤编码

	private String reverseDesc;         //必填|冲正原因
	
	private String tradeDate;           //必填|交易日期（yyyyMMdd）
	
	/**
	 * 记账冲正流水id
	 */
	private long reverseId;

	/**
	 * 借方科目
	 */
	private String drSubjectNo;

	/**
	 * 借方客户编号
	 */
	private String drCustId;

	/**
	 * 贷方科目
	 */
	private String crSubjectNo;

	/**
	 * 贷方客户编号
	 */
	private String crCustId;

	/**
	 * 冲正金额
	 */
	private long reverseAmount;

	/**
	 * 冲正账期，YYYYMMDD
	 */
	private String reverseAccountDate;

	/**
	 * 冲正时间
	 */
	private Date reverseTime;

	/**
	 * 原记账接口流水id
	 */
	private long orgBookId;

	/**
	 * 记原记账金额
	 */
	private long orgAmount;

	/**
	 * 原账期，YYYYMMDD
	 */
	private String orgAccountDate;
	
	/**
	 * 支付订单号
	 */
	private String payId;
	
	
	public String getOrgTradeId() {
		return orgTradeId;
	}

	public void setOrgTradeId(String orgTradeId) {
		this.orgTradeId = orgTradeId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getReverseDesc() {
		return reverseDesc;
	}

	public void setReverseDesc(String reverseDesc) {
		this.reverseDesc = reverseDesc;
	}

	public String getOptCode() {
		return optCode;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getDealCode() {
		return dealCode;
	}

	public void setDealCode(String dealCode) {
		this.dealCode = dealCode;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getOrgPayId() {
		return orgPayId;
	}

	public void setOrgPayId(String orgPayId) {
		this.orgPayId = orgPayId;
	}

	public String getPayerCustID() {
		return payerCustID;
	}

	public void setPayerCustID(String payerCustID) {
		this.payerCustID = payerCustID;
	}

	public String getPayeeCustID() {
		return payeeCustID;
	}

	public void setPayeeCustID(String payeeCustID) {
		this.payeeCustID = payeeCustID;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public long getReverseId() {
		return reverseId;
	}

	public void setReverseId(long reverseId) {
		this.reverseId = reverseId;
	}

	public String getDrSubjectNo() {
		return drSubjectNo;
	}

	public void setDrSubjectNo(String drSubjectNo) {
		this.drSubjectNo = drSubjectNo;
	}

	public String getDrCustId() {
		return drCustId;
	}

	public void setDrCustId(String drCustId) {
		this.drCustId = drCustId;
	}

	public String getCrSubjectNo() {
		return crSubjectNo;
	}

	public void setCrSubjectNo(String crSubjectNo) {
		this.crSubjectNo = crSubjectNo;
	}

	public String getCrCustId() {
		return crCustId;
	}

	public void setCrCustId(String crCustId) {
		this.crCustId = crCustId;
	}

	public long getReverseAmount() {
		return reverseAmount;
	}

	public void setReverseAmount(long reverseAmount) {
		this.reverseAmount = reverseAmount;
	}

	public String getReverseAccountDate() {
		return reverseAccountDate;
	}

	public void setReverseAccountDate(String reverseAccountDate) {
		this.reverseAccountDate = reverseAccountDate;
	}

	public Date getReverseTime() {
		return reverseTime;
	}

	public void setReverseTime(Date reverseTime) {
		this.reverseTime = reverseTime;
	}

	public long getOrgBookId() {
		return orgBookId;
	}

	public void setOrgBookId(long orgBookId) {
		this.orgBookId = orgBookId;
	}

	public long getOrgAmount() {
		return orgAmount;
	}

	public void setOrgAmount(long orgAmount) {
		this.orgAmount = orgAmount;
	}

	public String getOrgAccountDate() {
		return orgAccountDate;
	}

	public void setOrgAccountDate(String orgAccountDate) {
		this.orgAccountDate = orgAccountDate;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}
	
}
