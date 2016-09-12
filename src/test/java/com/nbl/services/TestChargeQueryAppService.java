package com.nbl.services;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.common.vo.PageVO;
import com.nbl.service.business.dto.req.ChargeReqDto;
import com.nbl.service.business.dto.res.ChargeResDto;
import com.nbl.services.product.ChargeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestChargeQueryAppService {

	@Resource
	private ChargeService chargeService;

	@Test
	public void testChargeCountAppService() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		ChargeReqDto reqDto = new ChargeReqDto();
		reqDto.setAccountDateS("20120303");

		int count = chargeService.pageCountQueryCharge(reqDto);

		System.out.println("count=" + count);

	}

	@Test
	public void testChargePageAppService() {

		ChargeReqDto reqDto = new ChargeReqDto();
		reqDto.setAccountDateS("20160401");
		reqDto.setAccountDateE("20160408");
		reqDto.setBookState("00");
		reqDto.setPayerFlag("CR");

		PageVO<ChargeReqDto> pageVO = new PageVO<ChargeReqDto>();
		pageVO.setStartSize(0);
		pageVO.setSize(5);

		List<ChargeResDto> list = chargeService.pageListQueryCharge(pageVO, reqDto);

		if (list != null && list.size() > 0) {
			for (ChargeResDto dto : list) {
				System.out.println("dto=" + dto.getBookId());
			}

		}

	}

}
