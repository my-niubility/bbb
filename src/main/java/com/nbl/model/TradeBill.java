package com.nbl.model;

import java.util.Date;

public class TradeBill {
    private String id;

    private String positionId;

    private String productId;

    private String productNane;

    private String custId;

    private String custName;

    private String payerFlag;

    private Long portion;

    private Long possessPortion;

    private String tradeOrderId;

    private String billType;

    private String tradeDate;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getPayerFlag() {
        return payerFlag;
    }

    public void setPayerFlag(String payerFlag) {
        this.payerFlag = payerFlag == null ? null : payerFlag.trim();
    }

    public Long getPortion() {
        return portion;
    }

    public void setPortion(Long portion) {
        this.portion = portion;
    }

    public Long getPossessPortion() {
        return possessPortion;
    }

    public void setPossessPortion(Long possessPortion) {
        this.possessPortion = possessPortion;
    }

    public String getTradeOrderId() {
        return tradeOrderId;
    }

    public void setTradeOrderId(String tradeOrderId) {
        this.tradeOrderId = tradeOrderId == null ? null : tradeOrderId.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate == null ? null : tradeDate.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}