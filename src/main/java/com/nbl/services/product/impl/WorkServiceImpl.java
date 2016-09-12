package com.nbl.services.product.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.dao.TWorkDao;
import com.nbl.model.TWork;
import com.nbl.services.product.WorkService;

@Service("workService")
public class WorkServiceImpl implements WorkService {

	private static final Logger logger = LoggerFactory.getLogger(WorkServiceImpl.class);
	
	@Resource
	private TWorkDao workDao;
	
	/**
	 * @return
	 * @description:得到当前日切日期
	 */
	public String getCurrentWorkDate() {
		TWork work = getWork();
		
		String currentDate = work.getCurDate();
		
		logger.info("获取当前的交易日期为："+currentDate);
		
		return currentDate;
	}

	/**
	 * @return
	 * @description:得到日切对象
	 */
	public TWork getWork() {
		
		return workDao.getWork();
	}

}
