package com.nbl.services;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.common.vo.PageVO;
import com.nbl.service.business.dto.req.StandardProductQueryDto;
import com.nbl.services.product.ProductQueryService;
import com.nbl.services.product.impl.ProductQueryServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestStandardProductQueryAppService {

	private final static Logger logger = LoggerFactory.getLogger(ProductQueryServiceImpl.class);

	@Resource
	private ProductQueryService productQueryService;

	@Test
	public void testPageListService() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		PageVO<StandardProductQueryDto> pgVo = new PageVO<StandardProductQueryDto>();
		StandardProductQueryDto reqDto = new StandardProductQueryDto();
		//reqDto.setProductId("PD2016072100001");
		pgVo.setSize(8);
		pgVo.setStartSize(0);
		List<StandardProductQueryDto> list = productQueryService.pageListQueryStProduct(pgVo, reqDto);
		
		if(list !=null){
			Iterator<StandardProductQueryDto> it = list.iterator();
			while(it.hasNext()){
				StandardProductQueryDto product = it.next();
				logger.info("--------查询产品ID:{}、STATUS:{},createtime:{}---------",product.getProductId(),product.getProductStatus(),product.getCreateTime());
			}
		}

	}



}
