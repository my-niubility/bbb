package com.nbl.model.vo;

import java.util.List;


public class PositionVo {

	//持仓ID
	private String id;
	
	//持仓客户ID
	private String custId;
	
	//持仓客户名称
	private String custName;
	
	//合同号
	private String contractId;
	//产品编号
	private String productId;
	//产品名称
	private String productNane;
	//持仓人类型
	private String positionCustType;
	 //交易订单流水号
	private String orderId;
	//投资类型
	private String investType;
	//持有状态
    private List<String> holdStatus;
    
    private String subjectNo  ; //新增 科目编号
    
    private String subjectType ;//新增 资产负债标识（00 资产 01 负债 ）
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getPositionCustType() {
		return positionCustType;
	}
	public void setPositionCustType(String positionCustType) {
		this.positionCustType = positionCustType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
		this.investType = investType;
	}
	public List<String> getHoldStatus() {
		return holdStatus;
	}
	public void setHoldStatus(List<String> holdStatus) {
		this.holdStatus = holdStatus;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
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
    
}
