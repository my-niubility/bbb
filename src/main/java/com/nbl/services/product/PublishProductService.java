package com.nbl.services.product;

import com.nbl.service.business.dto.req.PublishSolarFinanceDto;
import com.nbl.service.business.dto.req.PublishSolarProductDto;
import com.nbl.service.business.dto.req.PublishSolarProjectDto;
import com.nbl.service.business.dto.req.PublishSolarRentDto;
import com.nbl.service.business.dto.req.PublishStandardProductDto;

/**
 * @author Donald
 * @createdate 2016年7月20日
 * @version 1.0 
 * @description :产品发布
 */
public interface PublishProductService {
	
	public boolean saveSolarProduct(PublishSolarProductDto productDto, PublishSolarProjectDto projectDto,
			PublishSolarFinanceDto financeDto, PublishSolarRentDto rentDto);
	
	/**
	 * @param standardDto
	 * @return
	 * @description:标准产品
	 */
	public boolean saveStandardProduct(PublishStandardProductDto standardDto);

}
