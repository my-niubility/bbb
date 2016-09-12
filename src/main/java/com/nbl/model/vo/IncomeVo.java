package com.nbl.model.vo;

import java.util.Date;
import java.util.List;

public class IncomeVo {

	private String id;

    private String custId;

    private String projectId;

    private String positionId;

    private String repayMode;

    private List<String> incomeTypes;

    private Long proIncomeTerm;

    private String isFinish;

    private String enabled;
    
    private Date beginDate;
    
    private Date endDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}


	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRepayMode() {
		return repayMode;
	}

	public void setRepayMode(String repayMode) {
		this.repayMode = repayMode;
	}

	public List<String> getIncomeTypes() {
		return incomeTypes;
	}

	public void setIncomeTypes(List<String> incomeTypes) {
		this.incomeTypes = incomeTypes;
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
		this.isFinish = isFinish;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    
}
