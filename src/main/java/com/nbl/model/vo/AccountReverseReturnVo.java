package com.nbl.model.vo;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :冲正接口返回实体类(内部处理bean)
 */
public class AccountReverseReturnVo {


	private String payId;               //支付订单编号

	private long payerAmount;           //付款方账户余额（以份额为单位）
	
	private long payeeAmount;           //收款方账户余额（以份额为单位）
	
	private String accountDate;         //记账日期
	
	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public long getPayerAmount() {
		return payerAmount;
	}

	public void setPayerAmount(long payerAmount) {
		this.payerAmount = payerAmount;
	}

	public long getPayeeAmount() {
		return payeeAmount;
	}

	public void setPayeeAmount(long payeeAmount) {
		this.payeeAmount = payeeAmount;
	}

}
