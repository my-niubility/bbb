package com.nbl.services.risk;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.RiskBlackWhite;

/**
 * @author gcs
 * @createdate 2016年8月3日	
 * @version 1.0
 * business层对应的黑白名单接口
 */

public interface RiskBlackWhiteService {
	
	/**
	 * @param pageVO
	 * @param blackWhite
	 * @return
	 * @description:分页查询
	 */
	public List<RiskBlackWhite> pageListQueryBlackWhite(PageVO<RiskBlackWhite> pageVO,RiskBlackWhite blackWhite);
	
	/**
	 * @param blackWhite
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryBlackWhite(RiskBlackWhite blackWhite);
	
}
