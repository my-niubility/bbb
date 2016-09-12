package com.nbl.app.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.service.manager.app.ProductShelfApp;
import com.nbl.service.manager.dto.ProductShelfDto;
import com.nbl.services.product.ProductShelfService;
@Service("productShelfApp")
public class ProductShelfAppImpl implements ProductShelfApp {
	
	@Resource
	private ProductShelfService productShelfService;
	
	@Override
	public boolean setNewProductShelf(Map<String, List<String>> productMap) {
		
		return productShelfService.setNewProductShelf(productMap);
	}

	@Override
	public boolean offAndUpProductShelf(Map<String, List<String>> productMap,String[] productAll) {
		
		return productShelfService.offAndUpProductShelf(productMap,productAll);
	}

	@Override
	public List<ProductShelfDto> getAllUpShelfProduct() {
		
		return productShelfService.getAllUpShelfProduct();
	}

}
