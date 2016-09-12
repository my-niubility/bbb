	package com.nbl.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nbl.model.Payment;

@Repository
public interface PaymentDao {
	int deleteByPrimaryKey(String id);

	int insert(Payment record);

	int insertSelective(Payment record);

	Payment selectByPrimaryKey(String id);

	Payment selectByPayId(String payId);

	List<Payment> selectByTradeOrderId(String tradeOrderId);

	int updateByPrimaryKeySelective(Payment record);

	int updateByPrimaryKey(Payment record);
	//根据产品productId 来获取投资人是否用红包抵扣券
	List<Payment> selectByProductId (String productId);
	
	//全部注册实名数
    public int queryCountPayment();
    public int queryNoCountPayment();
    //上月注册实名数
    public int queryLastMonthCount();
    public int queryNoLastMonthCount();
    //本月注册实名数
    public int queryMonthCount();
    public int queryNoMonthCount();
    //本周注册实名数
    public int queryWeekCount();
    public int queryNoWeekCount();
}