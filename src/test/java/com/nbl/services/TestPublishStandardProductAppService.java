package com.nbl.services;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.service.business.dto.req.PublishStandardProductDto;
import com.nbl.services.product.PublishProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestPublishStandardProductAppService {

	@Resource
	private PublishProductService publishProductService;

	@Test
	public void testChargeCountAppService() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		PublishStandardProductDto  standardDto = new PublishStandardProductDto();
		
		standardDto.setBackday("12");
		standardDto.setBlEstabValue("10");
		standardDto.setBlockday("30");
		standardDto.setContrday("20160701");
		standardDto.setEnddt("20160706");
		standardDto.setEstablish("0");
		standardDto.setFinanceId("CB20010009977");
		standardDto.setFinanceName("北京大白有限公司");
		standardDto.setHoldday("300");
		standardDto.setNonbackday("20160721");
		standardDto.setProductName("北京雍和宫和平一号");
		standardDto.setRate("12.97");
		standardDto.setRentMode("1");
		standardDto.setRentType("1");
		standardDto.setScale("500");
		standardDto.setScaleLimit("10");
		standardDto.setScaleLimitFlag("1");
		standardDto.setStartdt("20160701");
		standardDto.setSubsidyDay("10");
		standardDto.setSubsidyFlag("1");
		standardDto.setSubsidyRate("2.50");
		standardDto.setTlockday("30");
		standardDto.setTransferFlag("1");
		standardDto.setUnitcost("1000");
		standardDto.setXxEstabValue(null);
		
		boolean flag = publishProductService.saveStandardProduct(standardDto);
		
		System.out.println("------flag------"+flag);

	}



}
