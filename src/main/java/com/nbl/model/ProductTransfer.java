package com.nbl.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductTransfer {
    private String id;

    private String contractId;

    private String positionId;

    private String productId;

    private String productNane;

    private String transferCustId;

    private String transferCustName;

    private Long transferAmt;

    private String transfereePurchaseId;

    private String transfereeCustId;

    private String transfereeCustName;

    private String status;

    private Long remainTerms;

    private BigDecimal positionEarning;

    private BigDecimal expectEarning;

    private Date transferTime;

    private Date settleTime;

    private String remark;

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

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
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

    public String getTransferCustId() {
        return transferCustId;
    }

    public void setTransferCustId(String transferCustId) {
        this.transferCustId = transferCustId == null ? null : transferCustId.trim();
    }

    public String getTransferCustName() {
        return transferCustName;
    }

    public void setTransferCustName(String transferCustName) {
        this.transferCustName = transferCustName == null ? null : transferCustName.trim();
    }

    public Long getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(Long transferAmt) {
        this.transferAmt = transferAmt;
    }

    public String getTransfereePurchaseId() {
        return transfereePurchaseId;
    }

    public void setTransfereePurchaseId(String transfereePurchaseId) {
        this.transfereePurchaseId = transfereePurchaseId == null ? null : transfereePurchaseId.trim();
    }

    public String getTransfereeCustId() {
        return transfereeCustId;
    }

    public void setTransfereeCustId(String transfereeCustId) {
        this.transfereeCustId = transfereeCustId == null ? null : transfereeCustId.trim();
    }

    public String getTransfereeCustName() {
        return transfereeCustName;
    }

    public void setTransfereeCustName(String transfereeCustName) {
        this.transfereeCustName = transfereeCustName == null ? null : transfereeCustName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Long getRemainTerms() {
        return remainTerms;
    }

    public void setRemainTerms(Long remainTerms) {
        this.remainTerms = remainTerms;
    }

    public BigDecimal getPositionEarning() {
        return positionEarning;
    }

    public void setPositionEarning(BigDecimal positionEarning) {
        this.positionEarning = positionEarning;
    }

    public BigDecimal getExpectEarning() {
        return expectEarning;
    }

    public void setExpectEarning(BigDecimal expectEarning) {
        this.expectEarning = expectEarning;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}