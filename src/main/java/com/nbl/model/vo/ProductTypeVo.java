package com.nbl.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.nbl.model.ProductCommon;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :平台产品类型包装类
 */
public class ProductTypeVo {

	// 产品类型："000光伏 系列","001电能系列" ,"002基金系列","003信托系列","004众筹"
	private String productType;
	// 产品id（查询条件）
	private String productId;
	// 产品公共信息
	private List<ProductCommon> productCommonList;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<ProductCommon> getProductCommonList() {
		return productCommonList;
	}

	public void setProductCommonList(List<ProductCommon> productCommonList) {
		this.productCommonList = new ArrayList<ProductCommon>();
		this.productCommonList.addAll(productCommonList);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
