package com.nbl.services.product;

import com.nbl.model.TWork;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :日切SERVICE
 */
public interface WorkService {


	/**
	 * @return
	 * @description:得到当前日切日期
	 */
	public String getCurrentWorkDate();

	/**
	 * @return
	 * @description:得到日切对象
	 */
	public TWork getWork();
}
