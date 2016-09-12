package com.nbl.model;

import java.util.Date;

public class Position {
    private String id;

    private String contractId;

    private String custId;

    private String custName;

    private String productId;

    private String productNane;

    private String positionCustType;

    private String orderId;

    private String subjectNo;

    private String subjectType;

    private Long possessPortion;

    private Long initialPortion;

    private Long unitCost;

    private Long invenstAmt;

    private Long remainCapital;

    private Long transferOutAmt;

    private Long recAmt;

    private Long earningAmt;

    private Long expectEarning;

    private String holdStatus;

    private Long remainTerms;

    private Date expireDate;

    private String remark;

    private String investType;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
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

    public String getPositionCustType() {
        return positionCustType;
    }

    public void setPositionCustType(String positionCustType) {
        this.positionCustType = positionCustType == null ? null : positionCustType.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getSubjectNo() {
        return subjectNo;
    }

    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo == null ? null : subjectNo.trim();
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    public Long getPossessPortion() {
        return possessPortion;
    }

    public void setPossessPortion(Long possessPortion) {
        this.possessPortion = possessPortion;
    }

    public Long getInitialPortion() {
        return initialPortion;
    }

    public void setInitialPortion(Long initialPortion) {
        this.initialPortion = initialPortion;
    }

    public Long getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Long unitCost) {
        this.unitCost = unitCost;
    }

    public Long getInvenstAmt() {
        return invenstAmt;
    }

    public void setInvenstAmt(Long invenstAmt) {
        this.invenstAmt = invenstAmt;
    }

    public Long getRemainCapital() {
        return remainCapital;
    }

    public void setRemainCapital(Long remainCapital) {
        this.remainCapital = remainCapital;
    }

    public Long getTransferOutAmt() {
        return transferOutAmt;
    }

    public void setTransferOutAmt(Long transferOutAmt) {
        this.transferOutAmt = transferOutAmt;
    }

    public Long getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(Long recAmt) {
        this.recAmt = recAmt;
    }

    public Long getEarningAmt() {
        return earningAmt;
    }

    public void setEarningAmt(Long earningAmt) {
        this.earningAmt = earningAmt;
    }

    public Long getExpectEarning() {
        return expectEarning;
    }

    public void setExpectEarning(Long expectEarning) {
        this.expectEarning = expectEarning;
    }

    public String getHoldStatus() {
        return holdStatus;
    }

    public void setHoldStatus(String holdStatus) {
        this.holdStatus = holdStatus == null ? null : holdStatus.trim();
    }

    public Long getRemainTerms() {
        return remainTerms;
    }

    public void setRemainTerms(Long remainTerms) {
        this.remainTerms = remainTerms;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType == null ? null : investType.trim();
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
}