package com.nbl.model;

import java.util.Date;

public class EnergyProduct {
	private String productId;

	private String projectId;

	private String projectStep;

	private String projectScale;

	private String projectAddr;

	private String productSubCand;

	private String productSubNum;

	private String productSubType;

	private String projectCarbon;

	private String projectName;

	private String projectIntro;

	private String projectBenefit;

	private String projectStMes;

	private String rentRights;

	private String rentTrack;

	private String rentOperate;

	private String projectRemark1;

	private String projectRemark2;

	private String projectRemark3;

	private String projectRemark4;

	private Date createTime;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getProjectStep() {
		return projectStep;
	}

	public void setProjectStep(String projectStep) {
		this.projectStep = projectStep == null ? null : projectStep.trim();
	}

	public String getProjectScale() {
		return projectScale;
	}

	public void setProjectScale(String projectScale) {
		this.projectScale = projectScale == null ? null : projectScale.trim();
	}

	public String getProjectAddr() {
		return projectAddr;
	}

	public void setProjectAddr(String projectAddr) {
		this.projectAddr = projectAddr == null ? null : projectAddr.trim();
	}

	public String getProductSubCand() {
		return productSubCand;
	}

	public void setProductSubCand(String productSubCand) {
		this.productSubCand = productSubCand == null ? null : productSubCand.trim();
	}

	public String getProductSubNum() {
		return productSubNum;
	}

	public void setProductSubNum(String productSubNum) {
		this.productSubNum = productSubNum == null ? null : productSubNum.trim();
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType == null ? null : productSubType.trim();
	}

	public String getProjectCarbon() {
		return projectCarbon;
	}

	public void setProjectCarbon(String projectCarbon) {
		this.projectCarbon = projectCarbon == null ? null : projectCarbon.trim();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName == null ? null : projectName.trim();
	}

	public String getProjectIntro() {
		return projectIntro;
	}

	public void setProjectIntro(String projectIntro) {
		this.projectIntro = projectIntro == null ? null : projectIntro.trim();
	}

	public String getProjectBenefit() {
		return projectBenefit;
	}

	public void setProjectBenefit(String projectBenefit) {
		this.projectBenefit = projectBenefit == null ? null : projectBenefit.trim();
	}

	public String getProjectStMes() {
		return projectStMes;
	}

	public void setProjectStMes(String projectStMes) {
		this.projectStMes = projectStMes == null ? null : projectStMes.trim();
	}

	public String getRentRights() {
		return rentRights;
	}

	public void setRentRights(String rentRights) {
		this.rentRights = rentRights == null ? null : rentRights.trim();
	}

	public String getRentTrack() {
		return rentTrack;
	}

	public void setRentTrack(String rentTrack) {
		this.rentTrack = rentTrack == null ? null : rentTrack.trim();
	}

	public String getRentOperate() {
		return rentOperate;
	}

	public void setRentOperate(String rentOperate) {
		this.rentOperate = rentOperate == null ? null : rentOperate.trim();
	}

	public String getProjectRemark1() {
		return projectRemark1;
	}

	public void setProjectRemark1(String projectRemark1) {
		this.projectRemark1 = projectRemark1 == null ? null : projectRemark1.trim();
	}

	public String getProjectRemark2() {
		return projectRemark2;
	}

	public void setProjectRemark2(String projectRemark2) {
		this.projectRemark2 = projectRemark2 == null ? null : projectRemark2.trim();
	}

	public String getProjectRemark3() {
		return projectRemark3;
	}

	public void setProjectRemark3(String projectRemark3) {
		this.projectRemark3 = projectRemark3 == null ? null : projectRemark3.trim();
	}

	public String getProjectRemark4() {
		return projectRemark4;
	}

	public void setProjectRemark4(String projectRemark4) {
		this.projectRemark4 = projectRemark4 == null ? null : projectRemark4.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}