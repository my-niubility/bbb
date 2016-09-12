package com.nbl.app.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.model.ProductAudit;
import com.nbl.service.business.app.ProductAuditApp;
import com.nbl.service.business.constant.ProductAuditResult;
import com.nbl.service.business.constant.ProductAuditType;
import com.nbl.service.business.dto.req.ProductAuditReqDto;
import com.nbl.services.product.ProductService;

/**
 * @author Donald
 * @createdate 2016年8月2日
 * @version 1.0 
 * @description :产品审核功能
 */
@Service("productAuditApp")
public class ProductAuditAppImpl implements ProductAuditApp {
	
	@Resource
	private ProductService productService;

	@Override
	public boolean productPublishAudit(ProductAuditReqDto reqDto) {
		
		ProductAudit productAudit = new ProductAudit();
		productAudit.setDescription(reqDto.getDescription());
		productAudit.setPassed(reqDto.getPassed().equals(ProductAuditResult.PASSED)?"1":"0");
		productAudit.setType(reqDto.getType().equals(ProductAuditType.RELEASE_AUDIT)?"01":"");
		productService.productAudit(reqDto.getProductId(), productAudit);
		
		return true;
	}

	@Override
	public boolean productEstablishAudit(ProductAuditReqDto reqDto) {
		
		ProductAudit productAudit = new ProductAudit();
		productAudit.setDescription(reqDto.getDescription());
		productAudit.setPassed(reqDto.getPassed().equals(ProductAuditResult.PASSED)?"1":"0");
		productAudit.setType(reqDto.getType().equals(ProductAuditType.RAISE_AUDIT)?"02":"");
		productService.projectFound(reqDto.getProductId(), productAudit);
		return true;
	}

}
