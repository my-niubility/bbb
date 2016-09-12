package com.nbl.model;

public class CheckMsgBeforehand {
    private String id;

    private String htSeqid;

    private String zlSeqid;

    private String entrustDate;

    private String liqDate;

    private String checkDate;

    private String merchantId;

    private Long htTransAmt;

    private Long zlTransAmt;

    private String htStatus;

    private String zlStatus;

    private String busiType;

    private String custId;

    private String custName;

    private String orderId;

    private String checkStatus;

    private String msgType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getHtSeqid() {
        return htSeqid;
    }

    public void setHtSeqid(String htSeqid) {
        this.htSeqid = htSeqid == null ? null : htSeqid.trim();
    }

    public String getZlSeqid() {
        return zlSeqid;
    }

    public void setZlSeqid(String zlSeqid) {
        this.zlSeqid = zlSeqid == null ? null : zlSeqid.trim();
    }

    public String getEntrustDate() {
        return entrustDate;
    }

    public void setEntrustDate(String entrustDate) {
        this.entrustDate = entrustDate == null ? null : entrustDate.trim();
    }

    public String getLiqDate() {
        return liqDate;
    }

    public void setLiqDate(String liqDate) {
        this.liqDate = liqDate == null ? null : liqDate.trim();
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate == null ? null : checkDate.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public Long getHtTransAmt() {
        return htTransAmt;
    }

    public void setHtTransAmt(Long htTransAmt) {
        this.htTransAmt = htTransAmt;
    }

    public Long getZlTransAmt() {
        return zlTransAmt;
    }

    public void setZlTransAmt(Long zlTransAmt) {
        this.zlTransAmt = zlTransAmt;
    }

    public String getHtStatus() {
        return htStatus;
    }

    public void setHtStatus(String htStatus) {
        this.htStatus = htStatus == null ? null : htStatus.trim();
    }

    public String getZlStatus() {
        return zlStatus;
    }

    public void setZlStatus(String zlStatus) {
        this.zlStatus = zlStatus == null ? null : zlStatus.trim();
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType == null ? null : busiType.trim();
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }
}