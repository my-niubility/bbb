package com.nbl.model;

import java.util.Date;

public class DealType extends DealTypeKey {
    private String ruleId;

    private String dealName;

    private String showName;

    private String choiceCode;

    private Date createDate;

    private Date updateDate;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName == null ? null : dealName.trim();
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    public String getChoiceCode() {
        return choiceCode;
    }

    public void setChoiceCode(String choiceCode) {
        this.choiceCode = choiceCode == null ? null : choiceCode.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}