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
import com.nbl.model.Position;
import com.nbl.service.manager.app.PositionApp;
import com.nbl.service.manager.dto.PositionReqDto;
import com.nbl.services.product.PositionService;

@Service("positionApp")
public class PositionAppImpl implements PositionApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(PositionAppImpl.class);
	
	@Resource
	private PositionService positionService;
	
	@Override
	public List<PositionReqDto> pageListQueryPosition(PageVO<PositionReqDto> pageVO, PositionReqDto reqDto) {
		PageVO<Position> pgVO = new PageVO<Position>();	
		Position position = new Position();
		BeanUtils.copyProperties(reqDto, position);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<Position> list = new ArrayList<Position>();
		list.add(position);
		pgVO.setList(list);
		
		List<Position> positionList = positionService.pageListQueryPosition(pgVO, position);
		if(positionList !=null && positionList.size()>0){
			Logger.info("-------List<Position> pageListQueryPosition---size----:{}:",positionList.size());
			List<PositionReqDto> reqList = new ArrayList<PositionReqDto>();
			Iterator<Position> it = positionList.iterator();
			while (it.hasNext()) {
				PositionReqDto reqDto2 = new PositionReqDto();
				Position cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	@Override
	public int pageCountQueryPosition(PositionReqDto reqDto) {
		Position position = new Position();
		BeanUtils.copyProperties(reqDto, position);
		return positionService.pageCountQueryPosition(position);
	}

	@Override
	public PositionReqDto positionDetail(String id) {
		Position position = positionService.positionDetail(id);
		PositionReqDto reqDto = new PositionReqDto();
		BeanUtils.copyProperties(position, reqDto);
		return reqDto;
	}

}
