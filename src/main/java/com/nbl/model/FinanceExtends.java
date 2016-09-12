package com.nbl.model;

import java.util.Date;

public class FinanceExtends {
    private String extendsId;

    private String financeId;

    private String financeName;

    private String financeIntro;

    private String rentMessage;

    private Long rentTotal;

    private String rentTransfer;

    private String guaranteeType;

    private String otherRemark1;

    private String otherRemark2;

    private String otherRemark3;

    private Date createTime;

    public String getExtendsId() {
        return extendsId;
    }

    public void setExtendsId(String extendsId) {
        this.extendsId = extendsId == null ? null : extendsId.trim();
    }

    public String getFinanceId() {
        return financeId;
    }

    public void setFinanceId(String financeId) {
        this.financeId = financeId == null ? null : financeId.trim();
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName == null ? null : financeName.trim();
    }

    public String getFinanceIntro() {
        return financeIntro;
    }

    public void setFinanceIntro(String financeIntro) {
        this.financeIntro = financeIntro == null ? null : financeIntro.trim();
    }

    public String getRentMessage() {
        return rentMessage;
    }

    public void setRentMessage(String rentMessage) {
        this.rentMessage = rentMessage == null ? null : rentMessage.trim();
    }

    public Long getRentTotal() {
        return rentTotal;
    }

    public void setRentTotal(Long rentTotal) {
        this.rentTotal = rentTotal;
    }

    public String getRentTransfer() {
        return rentTransfer;
    }

    public void setRentTransfer(String rentTransfer) {
        this.rentTransfer = rentTransfer == null ? null : rentTransfer.trim();
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType == null ? null : guaranteeType.trim();
    }

    public String getOtherRemark1() {
        return otherRemark1;
    }

    public void setOtherRemark1(String otherRemark1) {
        this.otherRemark1 = otherRemark1 == null ? null : otherRemark1.trim();
    }

    public String getOtherRemark2() {
        return otherRemark2;
    }

    public void setOtherRemark2(String otherRemark2) {
        this.otherRemark2 = otherRemark2 == null ? null : otherRemark2.trim();
    }

    public String getOtherRemark3() {
        return otherRemark3;
    }

    public void setOtherRemark3(String otherRemark3) {
        this.otherRemark3 = otherRemark3 == null ? null : otherRemark3.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}