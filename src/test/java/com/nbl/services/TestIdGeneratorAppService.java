package com.nbl.services;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.common.constants.SeqenceConstant;
import com.nbl.services.product.IdGeneratorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/applicationContext.xml" , 
		"classpath*:/spring/applicationContext-dataSource.xml"})
public class TestIdGeneratorAppService {
	
	@Resource
	private IdGeneratorService idGeneratorAppService;
	
	@Test
	public void testIdGeneratorAppService(){
		
		//获取产品序列号
		String pdSeq = idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_NP_SEQUENCE);
		//获取项目序列号
		String pjSeq = idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_CT_SEQUENCE);

		System.out.println("productId==="+pdSeq+",projectId==="+pjSeq);

		
	}
	
	

}
