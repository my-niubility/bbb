package com.nbl.model;

public class PrdExhInfo {
	/**
	 * 产品编号
	 */
	private String productId;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 年化收益率
	 */
	private String expectEarnRate;

	/**
	 * 锁定期(天数)
	 */
	private String lockPeriod;
	/**
	 * 单价（分）
	 */
	private String unitCost;
	/**
	 * 返租方式
	 */
	private String repayRentType;
	/**
	 * 预产碳豆
	 */
	private String projectCarbon;
	/**
	 * 融资规模（块）
	 */
	private String financeScale;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目概况
	 */
	private String projectIntro;
	/**
	 * 当前状态描述
	 */
	private String projectStMes;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getExpectEarnRate() {
		return expectEarnRate;
	}
	public void setExpectEarnRate(String expectEarnRate) {
		this.expectEarnRate = expectEarnRate;
	}
	public String getLockPeriod() {
		return lockPeriod;
	}
	public void setLockPeriod(String lockPeriod) {
		this.lockPeriod = lockPeriod;
	}
	public String getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(String unitCost) {
		this.unitCost = unitCost;
	}
	public String getRepayRentType() {
		return repayRentType;
	}
	public void setRepayRentType(String repayRentType) {
		this.repayRentType = repayRentType;
	}
	public String getProjectCarbon() {
		return projectCarbon;
	}
	public void setProjectCarbon(String projectCarbon) {
		this.projectCarbon = projectCarbon;
	}
	public String getFinanceScale() {
		return financeScale;
	}
	public void setFinanceScale(String financeScale) {
		this.financeScale = financeScale;
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
	public String getProjectStMes() {
		return projectStMes;
	}
	public void setProjectStMes(String projectStMes) {
		this.projectStMes = projectStMes;
	}
}