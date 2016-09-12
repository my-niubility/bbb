package com.nbl.model;

public class InterstSubsidy {
    private String paymentId;

    private String custId;

    private String custName;

    private String projectId;

    private Long investAmt;

    private Long expectEarning;

    private String paymentDate;

    private String beginInterestDate;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Long getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(Long investAmt) {
        this.investAmt = investAmt;
    }

    public Long getExpectEarning() {
        return expectEarning;
    }

    public void setExpectEarning(Long expectEarning) {
        this.expectEarning = expectEarning;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate == null ? null : paymentDate.trim();
    }

    public String getBeginInterestDate() {
        return beginInterestDate;
    }

    public void setBeginInterestDate(String beginInterestDate) {
        this.beginInterestDate = beginInterestDate == null ? null : beginInterestDate.trim();
    }
}