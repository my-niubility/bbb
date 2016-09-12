package com.nbl.services.product.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.PositionDao;
import com.nbl.model.Position;
import com.nbl.services.product.PositionService;

@Service("positionService")
public class PositionServiceImpl implements PositionService {
	
	@Resource
	private PositionDao positionDao;
	
	@Override
	public List<Position> pageListQueryPosition(PageVO<Position> pageVO, Position position) {
		return positionDao.findListPageTable(pageVO, position);
	}

	@Override
	public int pageCountQueryPosition(Position position) {
		return positionDao.findListPageCountTable(position);
	}

	@Override
	public Position positionDetail(String id) {
		return positionDao.selectById(id);
	}

}
