
package com.nbl.services.product.feature.impl;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.common.constants.ErrorCode;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.services.context.AppContextService;
import com.nbl.services.product.feature.PrdFeatureService;
import com.nbl.utils.BeanParseUtils;
import com.nbl.utils.PropertyPlaceholder;

@Service("prdFeatureService")
public class PrdFeatureServiceImpl implements PrdFeatureService {
	private final static Logger logger = LoggerFactory.getLogger(PrdFeatureServiceImpl.class);

	@Override
	public Object getProductFeatureInfo(String subProdcutType, String productId) throws MyBusinessCheckException {
		Object qryResult = null;
		Object dto = null;
		String productConfigStr;
		// 产品细分类（PRODUCT_LITTLE_TYPE）|0-Dao代理bean名称；1-Dao代理bean实现类；2-Dao执行的方法；3-dubbo出参Dto
		String[] prodcutConfig;

		productConfigStr = (String) PropertyPlaceholder.getProperty(subProdcutType);
		if (productConfigStr == null) {
			logger.info("[there isn't subProdcutType]:" + subProdcutType);
			return null;
		}
		prodcutConfig = productConfigStr.split("\\|");

		try {
			Method method = Class.forName(prodcutConfig[1]).getMethod(prodcutConfig[2], String.class);
			Object controller = AppContextService.getBean(prodcutConfig[0]);
			qryResult = method.invoke(controller, productId);

			Class<?> clazz = Class.forName(prodcutConfig[3]);
			dto = clazz.newInstance();
		} catch (Exception e) {
			logger.error("[product handler reflect exception]:", e);
			throw new MyBusinessCheckException(ErrorCode.BUE002);
		}

		if (qryResult != null) {
			BeanParseUtils.copyProperties(qryResult, dto);
		}

		logger.info("[product feature]+" + dto.toString());

		return dto;
	}
}
