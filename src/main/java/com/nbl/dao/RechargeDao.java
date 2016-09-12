package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.Recharge;
import com.nbl.model.SerialFund;
import com.nbl.model.vo.RechargeVo;

@Repository
public interface RechargeDao {
	int deleteByPrimaryKey(String id);

	int insert(Recharge record);

	int insertSelective(Recharge record);

	Recharge selectByPrimaryKey(String id);

	/**
	 * 根据客户信息（custId，createDate，status）分页查询充值订单
	 * @param pageVO
	 * @param rechargeVo
	 * @return
	 */
	List<Recharge> selectByCustInfo(@Param("pageVO") PageVO<RechargeVo> pageVO,
			@Param("rechargeVo") RechargeVo rechargeVo);
	
	/**
	 * 根据客户信息（custId，createDate，status）分页查询充值订单指定列（CREATE_TIME、REMARK、ID、AMT、STATUS）
	 * @param pageVO
	 * @param rechargeVo
	 * @return
	 */
	List<SerialFund> selectSerFndByCustInfo(@Param("pageVO") PageVO<RechargeVo> pageVO,
			@Param("rechargeVo") RechargeVo rechargeVo);

	int updateByPrimaryKeySelective(Recharge record);

	int updateByPrimaryKey(Recharge record);

	int updateByRechargeIdSelective(Recharge record);
	
	int getRchgOrderCount(@Param("rechargeVo") RechargeVo rechargeVo);
	
	/**
	 * @param id
	 * @return
	 * @description:根据身份证号查询个人详情
	 */
    Recharge selectById(String id);
    
    /**
	 * @param pageVO
	 * @param recharge
	 * @return
	 * @description:分页查询
	 */
	public List<Recharge> findListPage(@Param("pageVO")PageVO<Recharge> pageVO,@Param("recharge")Recharge recharge);
	
	/**
	 * @param Recharge
	 * @return
	 * @description:分页查询统计总数
	 */
	public int findListPageCount(@Param("recharge")Recharge recharge);
}