package com.nbl.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.nbl.services.product.ProductShelfService;
import com.nbl.services.product.impl.ProductQueryServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestShelfProductAppService {

	private final static Logger logger = LoggerFactory.getLogger(ProductQueryServiceImpl.class);

	@Resource
	private ProductShelfService productShelfService;

	@Test
	public void testPageListService() {

		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		
		productShelfService.getAllUpShelfProduct();
		
		System.out.println("~~~~~~~~~~~~~end~~~~~~~~~~~~~~~");
	}

	
	@Test
	public void testShelfInsert() {

		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		
		Map<String, List<String>> productMap = new HashMap<String, List<String>>();		
		List<String> list = Lists.newArrayList("shelf1","shelf2","shelf3","shelf5","shelf7");
		productMap.put("PD2016072500001", list);
		
		productShelfService.setNewProductShelf(productMap);
		
		System.out.println("~~~~~~~~~~~~~end~~~~~~~~~~~~~~~");
	}

	@Test
	public void testShelfUpdate() {

		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		
		Map<String, List<String>> productMap = new HashMap<String, List<String>>();		
		List<String> list = Lists.newArrayList("shelf1","shelf4","shelf7");
		productMap.put("PD2016072500001", list);
		
//		productShelfService.offAndUpProductShelf(productMap,productAll);
		
		System.out.println("~~~~~~~~~~~~~end~~~~~~~~~~~~~~~");
	}

}
