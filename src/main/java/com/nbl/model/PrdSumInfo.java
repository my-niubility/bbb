package com.nbl.model;

import java.math.BigDecimal;
import java.util.Date;

public class PrdSumInfo {

	private String productId;

	private String productName;

	private String productType;

	private BigDecimal expectEarnRate;

	private Long unitCost;

	private String lockPeriod;

	private String repayRentType;
	private Long financePortion;
	private Long financeScale;
	private String projectId;
	private String projectName;
	private String projectIntro;
	private String projectBenefit;
	private String projectStMes;
	private String rentRights;
	private Date createTime;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType == null ? null : productType.trim();
	}

	public BigDecimal getExpectEarnRate() {
		return expectEarnRate;
	}

	public void setExpectEarnRate(BigDecimal expectEarnRate) {
		this.expectEarnRate = expectEarnRate;
	}

	public Long getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Long unitCost) {
		this.unitCost = unitCost;
	}

	public String getLockPeriod() {
		return lockPeriod;
	}

	public void setLockPeriod(String lockPeriod) {
		this.lockPeriod = lockPeriod == null ? null : lockPeriod.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRepayRentType() {
		return repayRentType;
	}

	public void setRepayRentType(String repayRentType) {
		this.repayRentType = repayRentType;
	}

	public Long getFinancePortion() {
		return financePortion;
	}

	public void setFinancePortion(Long financePortion) {
		this.financePortion = financePortion;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIntro() {
		return projectIntro;
	}

	public void setProjectIntro(String projectIntro) {
		this.projectIntro = projectIntro;
	}

	public String getProjectBenefit() {
		return projectBenefit;
	}

	public void setProjectBenefit(String projectBenefit) {
		this.projectBenefit = projectBenefit;
	}

	public String getProjectStMes() {
		return projectStMes;
	}

	public void setProjectStMes(String projectStMes) {
		this.projectStMes = projectStMes;
	}

	public String getRentRights() {
		return rentRights;
	}

	public void setRentRights(String rentRights) {
		this.rentRights = rentRights;
	}

	public Long getFinanceScale() {
		return financeScale;
	}

	public void setFinanceScale(Long financeScale) {
		this.financeScale = financeScale;
	}

}