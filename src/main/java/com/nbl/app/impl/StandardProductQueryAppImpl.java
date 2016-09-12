package com.nbl.app.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.service.business.dto.req.StandardProductQueryDto;
import com.nbl.service.manager.app.StandardProductQueryApp;
import com.nbl.services.product.ProductQueryService;

/**
 * @author Donald
 * @createdate 2016年7月21日
 * @version 1.0 
 * @description :管理平台专用标准 
 */
@Service("standardProductQueryApp")
public class StandardProductQueryAppImpl implements StandardProductQueryApp {

	@Resource
	private ProductQueryService productQueryService;
	
	@Override
	public List<StandardProductQueryDto> pageListQueryStProduct(PageVO<StandardProductQueryDto> pageVO,
			StandardProductQueryDto reqDto) {
		
		return productQueryService.pageListQueryStProduct(pageVO, reqDto);
	}

	@Override
	public int pageCountQueryStProduct(StandardProductQueryDto reqDto) {
		return productQueryService.pageCountQueryStProduct(reqDto);
	}

	@Override
	public StandardProductQueryDto detailQuery(String productId) {
		return productQueryService.detailQuery(productId);
	}

}
