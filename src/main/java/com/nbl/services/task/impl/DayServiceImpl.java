package com.nbl.services.task.impl;



import javax.annotation.Resource;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.dao.TDayDao;
import com.nbl.model.TDay;
import com.nbl.service.business.constant.WorkDayType;
import com.nbl.service.manager.dto.TDayDto;
import com.nbl.services.task.DayService;
/**
 * @author gcs
 * @createdate 2016年8月1日	
 * @version 1.0
 * 
 */
@Service("dayService")
public class DayServiceImpl implements DayService {
	
	private static final Logger Logger = LoggerFactory.getLogger(DayServiceImpl.class);

	@Resource
	private TDayDao dayDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void initDay(String initDay) {
		Validate.matchesPattern(initDay, "\\d{4}","年份不正确.");
		TDayDto entity = null;
		
		// 星期几.
		int dayOfWeek;
		// 是否为工作日.
		String workDayType = null;
		int unit = 1;
		// 根据传入的年份设置并把月和日设置为1.
		LocalDate date = new LocalDate(Integer.valueOf(initDay), unit, unit);
		// 最大天数.
		int maxDay = date.dayOfYear().getMaximumValue();
		// 最小天数.
		int minDay = date.dayOfYear().getMinimumValue();
		// 先减少一天，再从1月1日开始.
		date = date.minusDays(unit);
		for ( int i = minDay; i <= maxDay; i++ ) {
			// 增加一天.
			date = date.plusDays(unit);
			dayOfWeek = date.dayOfWeek().get();
			workDayType = checkWorkDayType(dayOfWeek);

			String id = date.toString("yyyyMMdd");
			TDay workDay = this.dayDao.selectByPrimaryKey(id);
			if(workDay!=null){
				entity = new TDayDto();
				BeanUtils.copyProperties(workDay, entity);
			}
			// 新增
			if ( null == entity ) {
				workDay = new TDay();
				workDay.setDay(id);
				workDay.setFestivalFlag(Short.valueOf(workDayType));
				this.dayDao.insert(workDay);
			}
			// 修改
			else {
				entity.setFestivalFlag( Short.valueOf(workDayType));
				TDay workCalendar = new TDay();
				BeanUtils.copyProperties(entity, workCalendar);
				this.dayDao.updateByPrimaryKey(workCalendar);
			}
		}
	}
	public static String checkWorkDayType( int dayOfWeek ) {
		switch ( dayOfWeek ) {
			/* 工作日. */
			case 6:
			case 7:
				return WorkDayType.YES.getValue().toString();
			/* 非工作日. */
			default:
				return WorkDayType.NO.getValue().toString();
		}
	}
	
	
	@Override
	public boolean workDayTypeModify(String day, Short workDayType) {
		TDay workCalendar = new TDay();
		workCalendar.setDay(day);
		workCalendar.setFestivalFlag(workDayType);
		int num = this.dayDao.updateByPrimaryKey(workCalendar);
		Logger.info("-----workDayTypeModify-----:{}"+day+workDayType);
		if (num>0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean queryLikeDay(String initDay) {
		int num = dayDao.queryLikeDay(initDay);
		Logger.info("---当年有多少天  num---"+num);
		if (num>0) {
			return false;
		} else {
			return true;
		}
	}

}
