package com.nbl.model;

import java.util.Date;

public class PrdTrdHis {
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 产品编号
	 */
	private String productId;
	/**
	 * 最后更新时间
	 */
	private Date updateTime;
	/**
	 * 产品单价
	 */
	private Long unitCost;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Long unitCost) {
		this.unitCost = unitCost;
	}

}