package com.nbl.model;

import java.util.Date;

public class Payment {
    private String id;

    private String payId;

    private String totalId;

    private String batchId;

    private String contractId;

    private String productId;

    private String productNane;

    private String fundsType;

    private Long tradeAmt;

    private String merchantId;

    private String payCustId;

    private String payCustName;

    private String payAccount;

    private String recCustId;

    private String recCustName;

    private String recAccount;

    private String payBankType;

    private String payBankCardNo;

    private String payDate;

    private String status;

    private String resultInfo;

    private String paymentType;

    private String checkStatus;

    private String unbalanceReason;

    private String retreatSource;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long tradeTalAmt;

    private Long redEnvAmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getTotalId() {
        return totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId == null ? null : totalId.trim();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId == null ? null : batchId.trim();
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

    public String getFundsType() {
        return fundsType;
    }

    public void setFundsType(String fundsType) {
        this.fundsType = fundsType == null ? null : fundsType.trim();
    }

    public Long getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(Long tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getPayCustId() {
        return payCustId;
    }

    public void setPayCustId(String payCustId) {
        this.payCustId = payCustId == null ? null : payCustId.trim();
    }

    public String getPayCustName() {
        return payCustName;
    }

    public void setPayCustName(String payCustName) {
        this.payCustName = payCustName == null ? null : payCustName.trim();
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getRecCustId() {
        return recCustId;
    }

    public void setRecCustId(String recCustId) {
        this.recCustId = recCustId == null ? null : recCustId.trim();
    }

    public String getRecCustName() {
        return recCustName;
    }

    public void setRecCustName(String recCustName) {
        this.recCustName = recCustName == null ? null : recCustName.trim();
    }

    public String getRecAccount() {
        return recAccount;
    }

    public void setRecAccount(String recAccount) {
        this.recAccount = recAccount == null ? null : recAccount.trim();
    }

    public String getPayBankType() {
        return payBankType;
    }

    public void setPayBankType(String payBankType) {
        this.payBankType = payBankType == null ? null : payBankType.trim();
    }

    public String getPayBankCardNo() {
        return payBankCardNo;
    }

    public void setPayBankCardNo(String payBankCardNo) {
        this.payBankCardNo = payBankCardNo == null ? null : payBankCardNo.trim();
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate == null ? null : payDate.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo == null ? null : resultInfo.trim();
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getUnbalanceReason() {
        return unbalanceReason;
    }

    public void setUnbalanceReason(String unbalanceReason) {
        this.unbalanceReason = unbalanceReason == null ? null : unbalanceReason.trim();
    }

    public String getRetreatSource() {
        return retreatSource;
    }

    public void setRetreatSource(String retreatSource) {
        this.retreatSource = retreatSource == null ? null : retreatSource.trim();
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

	@Override
	public String toString() {
		return "Payment [id=" + id + ", payId=" + payId + ", totalId=" + totalId + ", batchId=" + batchId + ", contractId=" + contractId + ", productId=" + productId + ", productNane=" + productNane
				+ ", fundsType=" + fundsType + ", tradeAmt=" + tradeAmt + ", merchantId=" + merchantId + ", payCustId=" + payCustId + ", payCustName=" + payCustName + ", payAccount=" + payAccount
				+ ", recCustId=" + recCustId + ", recCustName=" + recCustName + ", recAccount=" + recAccount + ", payBankType=" + payBankType + ", payBankCardNo=" + payBankCardNo + ", payDate="
				+ payDate + ", status=" + status + ", resultInfo=" + resultInfo + ", paymentType=" + paymentType + ", checkStatus=" + checkStatus + ", unbalanceReason=" + unbalanceReason
				+ ", retreatSource=" + retreatSource + ", remark=" + remark + ", createTime=" + createTime + ", updateTime=" + updateTime + ", tradeTalAmt=" + tradeTalAmt + ", redEnvAmt=" + redEnvAmt
				+ "]";
	}
    
}