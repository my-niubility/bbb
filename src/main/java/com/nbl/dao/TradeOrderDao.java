package com.nbl.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.CustInvHis;
import com.nbl.model.PrdTrdHis;
import com.nbl.model.SerialFund;
import com.nbl.model.TradeOrder;
import com.nbl.model.TrdSumInfo;
import com.nbl.model.vo.TrdOrdVo;

@Repository
public interface TradeOrderDao {
	int deleteByPrimaryKey(String id);

	int insert(TradeOrder record);

	int insertSelective(TradeOrder record);

	TradeOrder selectByPrimaryKey(String id);

	/**
	 * 根据客户信息（custId，createDate，status）分页查询提现订单
	 * 
	 * @param pageVO
	 * @param rechargeVo
	 * @return
	 */
	List<TradeOrder> selectByCustInfo(@Param("pageVO") PageVO<TrdOrdVo> pageVO, @Param("trdOrdVo") TrdOrdVo trdOrdVo);

	/**
	 * 查询交易订单和产品信息
	 * 
	 * @param pageVO
	 * @param trdOrdVo
	 * @return
	 */
	List<TrdSumInfo> selectTrdOrdAndPrd(@Param("pageVO") PageVO<TrdOrdVo> pageVO, @Param("trdOrdVo") TrdOrdVo trdOrdVo);

	/**
	 * 根据客户信息（custId，createDate，status）分页查询提现订单指定列（CREATE_TIME、REMARK、ID、AMT、
	 * STATUS）
	 * 
	 * @param pageVO
	 * @param rechargeVo
	 * @return
	 */
	List<SerialFund> selectSerFndByCustInfo(@Param("pageVO") PageVO<TrdOrdVo> pageVO, @Param("trdOrdVo") TrdOrdVo trdOrdVo);

	/**
	 * 获取交易订单总数
	 * 
	 * @return
	 */
	Integer getTrdOrdCount();

	/**
	 * 根据订单时间获取交易订单总数
	 * 
	 * @return
	 */
	Integer getTrdOrdCountByDate(@Param("updateDateStart") Date updateDateStart, @Param("updateDateEnd") Date updateDateEnd);

	/**
	 * 根据定案状态获取交易金额
	 * 
	 * @return
	 */
	Integer getTrdOrdCountByStatus(String status);

	/**
	 * 获取交易订单和产品索引信息
	 * 
	 * @return
	 */
	List<PrdTrdHis> getTrdOrdAndPrdIdx();

	/**
	 * 获取客户最近投资记录
	 * 
	 * @return
	 */
	List<CustInvHis> getCustInvHis(@Param("custId") String custId, @Param("status") String orderStatus);

	/**
	 * 根据订单号更新订单状态
	 * 
	 * @return
	 */
	int updateOrderStatusById(@Param("id") String id, @Param("status") String status);

	/**
	 * 根据订单时间更新订单状态
	 * 
	 * @return
	 */
	int updateOrderStatusByUpdateTime(@Param("time") Date time, @Param("orgStatus") String orgStatus, @Param("newStatus") String newStatus);

	int updateByPrimaryKeySelective(TradeOrder record);

	int updateByPrimaryKey(TradeOrder record);

	int getTrdOrdCountByCondition(@Param("trdOrdVo") TrdOrdVo trdOrdVo);

	/**
	 * @param id
	 * @return
	 * @description:根据身份证号查询个人详情
	 */
	TradeOrder selectById(String id);

	/**
	 * @param pageVO
	 * @param TradeOrder
	 * @return
	 * @description:分页查询
	 */
	public List<TradeOrder> findListPage(@Param("pageVO") PageVO<TradeOrder> pageVO, @Param("tradeOrder") TradeOrder tradeOrder);

	/**
	 * @param TradeOrder
	 * @return
	 * @description:分页查询统计总数
	 */
	public int findListPageCount(@Param("tradeOrder") TradeOrder tradeOrder);
	
	
	/**
	 * 根据时间查询需要取消的订单
	 * @param time
	 * @param orgStatus
	 * @return
	 */
	List<TradeOrder> selectCancelOrder(@Param("time") Date time, @Param("orgStatus") String orgStatus);
	
	/**
	 * T_TRADE_ORDER、T_PAYMENT连表查询用户余额支付的金额合计
	 * @param pageVO
	 * @param trdOrdVo
	 * @param payment
	 * @return
	 */
	List<SerialFund> selectSerFndByCustInfoWithPayType(@Param("pageVO") PageVO<TrdOrdVo> pageVO, @Param("trdOrdVo") TrdOrdVo trdOrdVo, @Param("paymentType") String paymentType);
	
	/**
	 * 通过Id获取order状态用于支付token判断
	 * @param id
	 * @return
	 */
	String getStatus(String id);

}