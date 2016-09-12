package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.SerialFund;
import com.nbl.model.WithDraw;
import com.nbl.model.vo.WithdrawVo;

@Repository
public interface WithDrawDao {
	int deleteByPrimaryKey(String id);

	int insert(WithDraw record);

	int insertSelective(WithDraw record);

	WithDraw selectByPrimaryKey(String id);

	/**
	 * 根据客户信息（custId，createDate，status）分页查询提现订单
	 * 
	 * @param pageVO
	 * @param rechargeVo
	 * @return
	 */
	List<WithDraw> selectByCustInfo(@Param("pageVO") PageVO<WithdrawVo> pageVO,
			@Param("withdrawVo") WithdrawVo withdrawVo);
	
	/**
	 * 根据客户信息（custId，createDate，status）分页查询提现订单指定列（CREATE_TIME、REMARK、ID、AMT、STATUS）
	 * 
	 * @param pageVO
	 * @param rechargeVo
	 * @return
	 */
	List<SerialFund> selectSerFndByCustInfo(@Param("pageVO") PageVO<WithdrawVo> pageVO,
			@Param("withdrawVo") WithdrawVo withdrawVo);
	

	int updateByPrimaryKeySelective(WithDraw record);

	int updateByPrimaryKey(WithDraw record);
	
	int getWthdrOrderCount(@Param("withdrawVo") WithdrawVo withdrawVo);
	
	/**
	 * @param id
	 * @return
	 * @description:根据身份证号查询个人详情
	 */
    WithDraw selectById(String id);
    
    /**
	 * @param pageVO
	 * @param withdraw
	 * @return
	 * @description:分页查询
	 */
	public List<WithDraw> findListPage(@Param("pageVO")PageVO<WithDraw> pageVO,@Param("withdraw")WithDraw withdraw);
	
	/**
	 * @param withdraw
	 * @return
	 * @description:分页查询统计总数
	 */
	public int findListPageCount(@Param("withdraw")WithDraw withdraw);
	/**
	 * 资金流水查询，其中提现包括查询状态为处理中、已受理、提现成功
	 * @param pageVO
	 * @param wthdrVo
	 * @return
	 */
	List<SerialFund> selectSerFndByCustInfo4Withdr(@Param("pageVO") PageVO<WithdrawVo> pageVO, @Param("withdrawVo") WithdrawVo wthdrVo);
}