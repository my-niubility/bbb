package com.nbl.services.product;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.Position;

public interface PositionService {
	
	/**
	 * @param pageVO
	 * @param reqDto
	 * @return
	 * @description:分页查询
	 */
	public List<Position> pageListQueryPosition(PageVO<Position> pageVO,Position position);
	
	/**
	 * @param position
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryPosition(Position position);
	
	/**
	 * @param id
	 * @return obj
	 * @description: 根据持仓流水ID来查询订单明细
	 */
	public Position positionDetail(String id);
}
