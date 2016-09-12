package com.nbl.model.vo;

import java.util.List;

/**
 * @author AlanMa
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :多条件产品信息查询的條件
 */
public class ProIdxVo {

	/**
	 * 产品类型000-光伏；001-电能；002-基金；003-信托；004-众筹
	 */
	private String productType;
	/**
	 * 最小产品单价
	 */
	private Long unitCostMin;
	/**
	 * 最大产品单价
	 */
	private Long unitCostMax;
	/**
	 * 最小年化收益
	 */
	private String expectEarnRateMin;
	/**
	 * 最大年化收益
	 */
	private String expectEarnRateMax;
	/**
	 * 最小锁定期限
	 */
	private Long lockPeriodMin;
	/**
	 * 最大锁定期限
	 */
	private Long lockPeriodMax;
	/**
	 * 产品类型子类:201：货币型基金 202：债券型基 203：股票型基金 204：理财型基金
	 */
	private String productLittleType;
	
	/**
	 * 产品状态03：募集中 （立即购买）04：募结待审核 （已售罄）05：待还款 （还款中）09：已结束（已售罄）
	 */
	private List<String> productStatus;

	public ProIdxVo(String productType, Long unitCostMin, Long unitCostMax, String expectEarnRateMin, String expectEarnRateMax, Long lockPeriodMin, Long lockPeriodMax) {
		super();
		this.productType = productType;
		this.unitCostMin = unitCostMin;
		this.unitCostMax = unitCostMax;
		this.expectEarnRateMin = expectEarnRateMin;
		this.expectEarnRateMax = expectEarnRateMax;
		this.lockPeriodMin = lockPeriodMin;
		this.lockPeriodMax = lockPeriodMax;
	}

	public ProIdxVo() {
		super();
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getUnitCostMin() {
		return unitCostMin;
	}

	public void setUnitCostMin(Long unitCostMin) {
		this.unitCostMin = unitCostMin;
	}

	public Long getUnitCostMax() {
		return unitCostMax;
	}

	public void setUnitCostMax(Long unitCostMax) {
		this.unitCostMax = unitCostMax;
	}

	public String getExpectEarnRateMin() {
		return expectEarnRateMin;
	}

	public void setExpectEarnRateMin(String expectEarnRateMin) {
		this.expectEarnRateMin = expectEarnRateMin;
	}

	public String getExpectEarnRateMax() {
		return expectEarnRateMax;
	}

	public void setExpectEarnRateMax(String expectEarnRateMax) {
		this.expectEarnRateMax = expectEarnRateMax;
	}

	public Long getLockPeriodMin() {
		return lockPeriodMin;
	}

	public void setLockPeriodMin(Long lockPeriodMin) {
		this.lockPeriodMin = lockPeriodMin;
	}

	public Long getLockPeriodMax() {
		return lockPeriodMax;
	}

	public void setLockPeriodMax(Long lockPeriodMax) {
		this.lockPeriodMax = lockPeriodMax;
	}

	public String getProductLittleType() {
		return productLittleType;
	}

	public void setProductLittleType(String productLittleType) {
		this.productLittleType = productLittleType;
	}

	public List<String> getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(List<String> productStatus) {
		this.productStatus = productStatus;
	}

	@Override
	public String toString() {
		return "ProIdxVo [productType=" + productType + ", unitCostMin=" + unitCostMin + ", unitCostMax=" + unitCostMax + ", expectEarnRateMin=" + expectEarnRateMin + ", expectEarnRateMax="
				+ expectEarnRateMax + ", lockPeriodMin=" + lockPeriodMin + ", lockPeriodMax=" + lockPeriodMax + ", productLittleType=" + productLittleType + ", productStatus=" + productStatus + "]";
	}

}
