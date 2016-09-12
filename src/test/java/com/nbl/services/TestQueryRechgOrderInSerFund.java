package com.nbl.services;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.services.account.RechargeQryService;
import com.nbl.services.order.TrdOrdQryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestQueryRechgOrderInSerFund {
	
	@Resource
	RechargeQryService rechargeQryService;
	
	@Resource
	TrdOrdQryService trdOrdQryService;

	

	@Test
	public void testRechargeQryService() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		SerFundQryDto serFundQryDto = new SerFundQryDto();
		serFundQryDto.setCustId("CP2016072500002");
		serFundQryDto.setType("02");
		serFundQryDto.setRecordNum(-1);
		serFundQryDto.setStartIndex(0);
		//CommRespDto commRespDto = rechargeQryService.queryRechgOrderInSerFund(serFundQryDto);
		CommRespDto commRespDto = trdOrdQryService.queryTradeOrderInSerFund(serFundQryDto);
		if(commRespDto==null){
			System.out.println("查询结果为空");
		}else {
			System.out.println(commRespDto.getData());
		}

		

	}
	
	@Test
	public void testQueryTradeOrderInSerFund() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		SerFundQryDto serFundQryDto = new SerFundQryDto();
		serFundQryDto.setCustId("CP2016072500002");
		serFundQryDto.setType("02");
		serFundQryDto.setRecordNum(-1);
		serFundQryDto.setStartIndex(0);
		CommRespDto commRespDto=trdOrdQryService.queryTradeOrderInSerFund(serFundQryDto);
		if(commRespDto==null){
			System.out.println("查询结果为空");
		}else {
			System.out.println(commRespDto.getData());
		}

		

	}

	

	

}
