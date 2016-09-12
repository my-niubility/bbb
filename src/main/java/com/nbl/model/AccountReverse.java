package com.nbl.model;

import java.util.Date;

public class AccountReverse {
    private Integer reverseId;

    private String drSubjectNo;

    private String drCustId;

    private String crSubjectNo;

    private String crCustId;

    private Long reverseAmount;

    private String reverseDesc;

    private String reverseAccountDate;

    private Date reverseTime;

    private Long orgBookId;

    private String orgPayId;

    private Long orgAmount;

    private String orgAccountDate;

    private String payId;

    public Integer getReverseId() {
        return reverseId;
    }

    public void setReverseId(Integer reverseId) {
        this.reverseId = reverseId;
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

    public Long getReverseAmount() {
        return reverseAmount;
    }

    public void setReverseAmount(Long reverseAmount) {
        this.reverseAmount = reverseAmount;
    }

    public String getReverseDesc() {
        return reverseDesc;
    }

    public void setReverseDesc(String reverseDesc) {
        this.reverseDesc = reverseDesc == null ? null : reverseDesc.trim();
    }

    public String getReverseAccountDate() {
        return reverseAccountDate;
    }

    public void setReverseAccountDate(String reverseAccountDate) {
        this.reverseAccountDate = reverseAccountDate == null ? null : reverseAccountDate.trim();
    }

    public Date getReverseTime() {
        return reverseTime;
    }

    public void setReverseTime(Date reverseTime) {
        this.reverseTime = reverseTime;
    }

    public Long getOrgBookId() {
        return orgBookId;
    }

    public void setOrgBookId(Long orgBookId) {
        this.orgBookId = orgBookId;
    }

    public String getOrgPayId() {
        return orgPayId;
    }

    public void setOrgPayId(String orgPayId) {
        this.orgPayId = orgPayId == null ? null : orgPayId.trim();
    }

    public Long getOrgAmount() {
        return orgAmount;
    }

    public void setOrgAmount(Long orgAmount) {
        this.orgAmount = orgAmount;
    }

    public String getOrgAccountDate() {
        return orgAccountDate;
    }

    public void setOrgAccountDate(String orgAccountDate) {
        this.orgAccountDate = orgAccountDate == null ? null : orgAccountDate.trim();
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }
}