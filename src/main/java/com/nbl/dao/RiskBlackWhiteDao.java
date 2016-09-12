package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nbl.common.vo.PageVO;
import com.nbl.model.RiskBlackWhite;

public interface RiskBlackWhiteDao {
    int insert(RiskBlackWhite record);

    int insertSelective(RiskBlackWhite record);
    
    /**
   	 * @param pageVO
   	 * @param blackWhite
   	 * @return
   	 * @description:分页查询
   	 */
   	public List<RiskBlackWhite> findListPage(@Param("pageVO")PageVO<RiskBlackWhite> pageVO,@Param("blackWhite")RiskBlackWhite blackWhite);
   	
   	/**
   	 * @param blackWhite
   	 * @return
   	 * @description:分页查询统计总数
   	 */
   	public int findListPageCount(@Param("blackWhite")RiskBlackWhite blackWhite);
}