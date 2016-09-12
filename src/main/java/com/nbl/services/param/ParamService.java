package com.nbl.services.param;

/**
 * @author AlanMa
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :配置参数管理服务
 */
public interface ParamService {

	/**
	 * @return
	 * @description:得到参数值
	 */
	public String getValue(String paramKey);
}
