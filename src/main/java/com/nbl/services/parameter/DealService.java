package com.nbl.services.parameter;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.DealType;

/**
 * @author gcs
 * @createdate 2016年7月26日	
 * @version 1.0
 * busiess层对应的接口
 */

public interface DealService {
	
	/**
	 * @param pageVO
	 * @param deal
	 * @return
	 * @description:分页查询
	 */
	public List<DealType> pageListQueryDeal(PageVO<DealType> pageVO,DealType deal);
	
	/**
	 * @param deal
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryDeal(DealType deal);
	
}
