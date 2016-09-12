package com.nbl.model;

import java.math.BigDecimal;
import java.util.Date;

public class TIncome {
    
	private String id;

    private String custId;

    private String projectId;

    private String positionId;

    private String custName;

    private String projectName;

    private Long earning;

    private Long capital;

    private Long amt;

    private String accountDate;

    private String repayMode;

    private String incomeType;

    private Long proIncomeTerm;

    private String isFinish;

    private String enabled;

    private String remark;

    private Date createDate;

    private BigDecimal expectAnnuRate;

    private BigDecimal annuRate;

    private Long expectEarning;

    private String productType;

    private Long redEnvAmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Long getEarning() {
        return earning;
    }

    public void setEarning(Long earning) {
        this.earning = earning;
    }

    public Long getCapital() {
        return capital;
    }

    public void setCapital(Long capital) {
        this.capital = capital;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate == null ? null : accountDate.trim();
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode == null ? null : repayMode.trim();
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType == null ? null : incomeType.trim();
    }

    public Long getProIncomeTerm() {
        return proIncomeTerm;
    }

    public void setProIncomeTerm(Long proIncomeTerm) {
        this.proIncomeTerm = proIncomeTerm;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish == null ? null : isFinish.trim();
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    

    public BigDecimal getExpectAnnuRate() {
		return expectAnnuRate;
	}

	public void setExpectAnnuRate(BigDecimal expectAnnuRate) {
		this.expectAnnuRate = expectAnnuRate;
	}

	public BigDecimal getAnnuRate() {
		return annuRate;
	}

	public void setAnnuRate(BigDecimal annuRate) {
		this.annuRate = annuRate;
	}

	public Long getExpectEarning() {
        return expectEarning;
    }

    public void setExpectEarning(Long expectEarning) {
        this.expectEarning = expectEarning;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public Long getRedEnvAmt() {
        return redEnvAmt;
    }

    public void setRedEnvAmt(Long redEnvAmt) {
        this.redEnvAmt = redEnvAmt;
    }
}