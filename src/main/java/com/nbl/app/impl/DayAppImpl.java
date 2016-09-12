package com.nbl.app.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.service.manager.app.DayApp;
import com.nbl.services.task.DayService;
/**
 * @author gcs
 * @createdate 2016年8月1日	
 * @version 1.0
 * 业务层初始化日期接口
 */
@Service("dayApp")
public class DayAppImpl implements DayApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(DayAppImpl.class);
	
	@Resource
	private DayService dayService;
	
	@Override
	public void initDay(String initDay) {
		dayService.initDay(initDay);
	}

	@Override
	public boolean workDayTypeModify(String day, Short workDayType) {
		return dayService.workDayTypeModify(day,workDayType);
	}

	@Override
	public boolean queryLikeDay(String initDay) {
		return dayService.queryLikeDay(initDay);
	}

}
