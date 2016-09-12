package com.nbl.services.product.feature;

import com.nbl.common.exception.MyBusinessCheckException;

public interface PrdFeatureService {
	public Object getProductFeatureInfo(String productPrefix, String productId) throws MyBusinessCheckException;
}
