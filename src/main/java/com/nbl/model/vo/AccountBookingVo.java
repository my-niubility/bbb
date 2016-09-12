package com.nbl.model.vo;

import java.util.Date;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :记账请求(内部处理bean)
 */
public class AccountBookingVo{
	
	private String productId ;    //产品编号                                            必填
	
	private String productName ;  //产品名称                                            必填
	
	private String orderId ;      //交易订单流水号                           
	
	private String tradeDate;     //交易日期（yyyyMMdd）                  必填
	
	private String payId ;        //支付订单编号                                      必填
	 
	private String payerCustID;   //付款方客户编号                                   必填
	
	private String payeeCustID;   //收款方客户编号                                   必填
	
	private long amount ;         //记账份额（必须大于0，否则报错）   必填
	
	private String optCode;       //交易类型
	
	private String ruleId;        //记账规则                                              必填 
	/**
	 * 记账接口流水ID
	 */
	private long bookId;
	
	/**
	 * 账期，YYYYMMDD
	 */
	private String accountDate;

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
	 * 调用时间
	 */
	private Date bookTime;
	
	/**
	 * 记账状态
	 */
	private String bookState;
	
	/**
	 * 付款方借贷标志
	 */
	private String payerFlag;

	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getOptCode() {
		return optCode;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
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

	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}

	public String getBookState() {
		return bookState;
	}

	public void setBookState(String bookState) {
		this.bookState = bookState;
	}

	public String getPayerFlag() {
		return payerFlag;
	}

	public void setPayerFlag(String payerFlag) {
		this.payerFlag = payerFlag;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
