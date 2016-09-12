package com.nbl.services.task;
/**
 * @author gcs
 * @createdate 2016年8月1日	
 * @version 1.0
 * 日期实现业务层
 */

public interface DayService {
	/**
	 * @param initDay
	 * @return obj
	 * @description: 根据日期来初始化数据
	 */
	public void initDay(String initDay);
	/**
	 * @param day 
	 * @param workDayType 1是工作日   0不是
	 * 修改工作日期
	 * */
	public boolean workDayTypeModify(String day,Short workDayType);
	
	/**
	 * @param initDay
	 * @return boolean
	 * 根据传过来的年来判断是否已初始化
	 * */
	public boolean queryLikeDay(String initDay);
}
