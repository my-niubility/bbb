package com.nbl.app.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.nbl.dao.PaymentDao;
import com.nbl.service.manager.app.PaymentApp;
/**
 * @author gcs
 * @createdate 2016年8月10日	
 * @version 1.0
 * business层支付订单实现的接口
 */
@Service("paymentApp")
public class PaymentAppImpl implements PaymentApp {
	
	@Resource
	private PaymentDao paymentDao;

	@Override
	public int queryCountPayment() {
		return paymentDao.queryCountPayment();
	}

	@Override
	public int queryNoCountPayment() {
		return paymentDao.queryNoCountPayment();
	}

	@Override
	public int queryLastMonthCount() {
		return paymentDao.queryLastMonthCount();
	}

	@Override
	public int queryNoLastMonthCount() {
		return paymentDao.queryNoLastMonthCount();
	}

	@Override
	public int queryMonthCount() {
		return paymentDao.queryMonthCount();
	}

	@Override
	public int queryNoMonthCount() {
		return paymentDao.queryNoMonthCount();
	}

	@Override
	public int queryWeekCount() {
		return paymentDao.queryWeekCount();
	}

	@Override
	public int queryNoWeekCount() {
		return paymentDao.queryNoWeekCount();
	}
}
