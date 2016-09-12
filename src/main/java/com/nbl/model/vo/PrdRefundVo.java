package com.nbl.model.vo;

/**
 * @author AlanMa
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :多条件产品信息查询的條件
 */
public class PrdRefundVo {

	private String productId;

	private String productName;

	private String financeId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFinanceId() {
		return financeId;
	}

	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}

}
