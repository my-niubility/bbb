package com.nbl.model;

import java.util.Date;

public class AccountBook {
    private Integer bookId;

    private String accountDate;

    private String ruleId;

    private String orderId;

    private String payId;

    private String optCode;

    private String drSubjectNo;

    private String drCustId;

    private String crSubjectNo;

    private String crCustId;

    private Long amount;

    private Date bookTime;

    private String bookState;

    private String payerFlag;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate == null ? null : accountDate.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode == null ? null : optCode.trim();
    }

    public String getDrSubjectNo() {
        return drSubjectNo;
    }

    public void setDrSubjectNo(String drSubjectNo) {
        this.drSubjectNo = drSubjectNo == null ? null : drSubjectNo.trim();
    }

    public String getDrCustId() {
        return drCustId;
    }

    public void setDrCustId(String drCustId) {
        this.drCustId = drCustId == null ? null : drCustId.trim();
    }

    public String getCrSubjectNo() {
        return crSubjectNo;
    }

    public void setCrSubjectNo(String crSubjectNo) {
        this.crSubjectNo = crSubjectNo == null ? null : crSubjectNo.trim();
    }

    public String getCrCustId() {
        return crCustId;
    }

    public void setCrCustId(String crCustId) {
        this.crCustId = crCustId == null ? null : crCustId.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
        this.bookState = bookState == null ? null : bookState.trim();
    }

    public String getPayerFlag() {
        return payerFlag;
    }

    public void setPayerFlag(String payerFlag) {
        this.payerFlag = payerFlag == null ? null : payerFlag.trim();
    }
}