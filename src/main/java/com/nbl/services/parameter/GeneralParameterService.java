package com.nbl.services.parameter;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.GeneralParameter;

/**
 * @author gcs
 * @createdate 2016年7月27日	
 * @version 1.0
 * busiess层中对应的接口
 */

public interface GeneralParameterService {
	
	/**
	 * @param pageVO
	 * @param parameter
	 * @return
	 * @description:分页查询
	 */
	public List<GeneralParameter> pageListQueryGenParameter(PageVO<GeneralParameter> pageVO,GeneralParameter parameter);
	
	/**
	 * @param parameter
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryGenParameter(GeneralParameter parameter);
	

	/**
	 * @param id
	 * @return
	 *	根据系统参数代码来修改数据
	 */
	
	public GeneralParameter generalParameterDetail(Integer id);
	
	/**
	 * @param id code parName parValue reMark
	 * @return
	 *	根据系统参数代码来修改数据
	 */
	
	public boolean generalParameterModify(Integer id,String code,String parName,String parValue,String reMark);

	public String getValueByCode(String code);
}
