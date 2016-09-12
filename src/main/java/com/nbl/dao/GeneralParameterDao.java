package com.nbl.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.GeneralParameter;

@Repository
public interface GeneralParameterDao {
	int deleteByPrimaryKey(Integer id);

	int insert(GeneralParameter record);

	int insertSelective(GeneralParameter record);

	GeneralParameter selectByPrimaryKey(Integer id);

	String getParamValueByKey(String key);

	int updateByPrimaryKeySelective(GeneralParameter record);

	int updateByPrimaryKey(GeneralParameter record);
	
	/**
	 * @param pageVO
	 * @param genParameter
	 * @return
	 * @description:分页查询
	 */
	public List<GeneralParameter> findListPage(@Param("pageVO")PageVO<GeneralParameter> pageVO,@Param("genParameter")GeneralParameter genParameter);
	
	/**
	 * @param genParameter
	 * @return
	 * @description:分页查询统计总数
	 */
	public int findListPageCount(@Param("genParameter")GeneralParameter genParameter);
	
	/**
	 * @param id code parName parValue updateTime reMark
	 * @return
	 * @description:分页查询统计总数
	 */
	public int modifyParameter(@Param("id")Integer id,@Param("code")String code,@Param("parName")String parName,@Param("parValue")String parValue,@Param("updateTime")Date updateTime,@Param("reMark")String reMark);
}