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
import com.nbl.model.RiskBlackWhite;
import com.nbl.service.manager.app.RiskBlackWhiteApp;
import com.nbl.service.manager.dto.RiskBlackWhiteDto;
import com.nbl.services.risk.RiskBlackWhiteService;
/**
 * @author gcs
 * @createdate 2016年8月3日	
 * @version 1.0
 * business层对应接口实现类
 */

@Service("riskBlackWhiteApp")
public class RiskBlackWhiteAppImpl implements RiskBlackWhiteApp {
	
	private static final Logger logger = LoggerFactory.getLogger(RiskBlackWhiteAppImpl.class);
	
	@Resource
	private RiskBlackWhiteService riskBlackWhiteService;
	
	@Override
	public List<RiskBlackWhiteDto> pageListQueryBlackWhite(PageVO<RiskBlackWhiteDto> pageVO, RiskBlackWhiteDto reqDto) {
		PageVO<RiskBlackWhite> pgVO = new PageVO<RiskBlackWhite>();	
		RiskBlackWhite blackWhite = new RiskBlackWhite();
		BeanUtils.copyProperties(reqDto, blackWhite);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<RiskBlackWhite> list = new ArrayList<RiskBlackWhite>();
		list.add(blackWhite);
		pgVO.setList(list);
		
		List<RiskBlackWhite> blackWhiteList = riskBlackWhiteService.pageListQueryBlackWhite(pgVO, blackWhite);
		if(blackWhiteList !=null && blackWhiteList.size()>0){
			logger.info("-------List<RiskBlackWhite> pageListQueryRiskBlackWhite---size----:{}:",blackWhiteList.size());
			List<RiskBlackWhiteDto> reqList = new ArrayList<RiskBlackWhiteDto>();
			Iterator<RiskBlackWhite> it = blackWhiteList.iterator();
			while (it.hasNext()) {
				RiskBlackWhiteDto reqDto2 = new RiskBlackWhiteDto();
				RiskBlackWhite cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	@Override
	public int pageCountQueryBlackWhite(RiskBlackWhiteDto reqDto) {
		RiskBlackWhite blackWhite = new RiskBlackWhite();
		BeanUtils.copyProperties(reqDto, blackWhite);
		return riskBlackWhiteService.pageCountQueryBlackWhite(blackWhite);
	}

}
