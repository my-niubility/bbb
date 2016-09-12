package com.nbl.app.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.service.business.app.PublishProductApp;
import com.nbl.service.business.dto.req.PublishSolarFinanceDto;
import com.nbl.service.business.dto.req.PublishSolarProductDto;
import com.nbl.service.business.dto.req.PublishSolarProjectDto;
import com.nbl.service.business.dto.req.PublishSolarRentDto;
import com.nbl.service.business.dto.req.PublishStandardProductDto;
import com.nbl.service.business.dto.res.PublishResultDto;
import com.nbl.services.product.PublishProductService;

@Service("publishProductApp")
public class PublishProductAppImpl implements PublishProductApp {

	@Resource
	private PublishProductService publishProductService;
	
	@Override
	public PublishResultDto publishSolarProduct(PublishSolarProductDto productDto, PublishSolarProjectDto projectDto,
			PublishSolarFinanceDto financeDto, PublishSolarRentDto rentDto) {
		
		boolean flag = publishProductService.saveSolarProduct(productDto, projectDto, financeDto, rentDto);
		PublishResultDto retDto = new PublishResultDto();
		retDto.setFlag(flag);
		return retDto;
	}

	@Override
	public PublishResultDto publishStandardProduct(PublishStandardProductDto standardDto) {
		
		boolean flag = publishProductService.saveStandardProduct(standardDto);
		PublishResultDto retDto = new PublishResultDto();
		retDto.setFlag(flag);
		return retDto;
	}

}
