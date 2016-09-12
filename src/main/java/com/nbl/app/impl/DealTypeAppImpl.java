package com.nbl.app.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.model.BillBalance;
import com.nbl.model.DealType;
import com.nbl.service.manager.app.DealTypeApp;
import com.nbl.service.manager.dto.DealTypeDto;
import com.nbl.services.parameter.DealService;

/**
 * @author gcs
 * @createdate 2016年7月26日	
 * @version 1.0
 * business层的业务逻辑
 */
@Service("dealTypeApp")
public class DealTypeAppImpl implements DealTypeApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(DealTypeAppImpl.class);
	
	
	@Resource
	private DealService dealService;
	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.DealTypeApp#pageListQueryDeal(com.zlebank.common.vo.PageVO, com.zlebank.service.manager.dto.DealTypeDto)
	 */
	@Override
	public List<DealTypeDto> pageListQueryDeal(PageVO<DealTypeDto> pageVO, DealTypeDto reqDto) {
		PageVO<DealType> pgVO = new PageVO<DealType>();	
		DealType deal = new DealType();
		BeanUtils.copyProperties(reqDto, deal);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<DealType> list = new ArrayList<DealType>();
		list.add(deal);
		pgVO.setList(list);
		
		List<DealType> dealList = dealService.pageListQueryDeal(pgVO, deal);
		if(dealList !=null && dealList.size()>0){
			Logger.info("-------List<DealType> pageListQueryDealType---size----:{}:",dealList.size());
			List<DealTypeDto> reqList = new ArrayList<DealTypeDto>();
			Iterator<DealType> it = dealList.iterator();
			while (it.hasNext()) {
				DealTypeDto reqDto2 = new DealTypeDto();
				DealType cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.DealTypeApp#pageCountQueryDeal(com.zlebank.service.manager.dto.DealTypeDto)
	 */
	@Override
	public int pageCountQueryDeal(DealTypeDto reqDto) {
		DealType deal = new DealType();
		BeanUtils.copyProperties(reqDto, deal);
		return dealService.pageCountQueryDeal(deal);
	}

}
