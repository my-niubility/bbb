package com.nbl.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.model.TDay;
@Repository
public interface TDayDao {
    int deleteByPrimaryKey(String day);

    int insert(TDay record);

    int insertSelective(TDay record);

    TDay selectByPrimaryKey(String day);
    
    TDay selectByFestivalFlag(Long festivalFlag);

    int updateByPrimaryKeySelective(TDay record);

    int updateByPrimaryKey(TDay record);
    /**
     * @param initDay
     * 根据模糊查询来判断  当年是否已初始化
     * 
     * */
    public int queryLikeDay(@Param("day") String initDay);
}