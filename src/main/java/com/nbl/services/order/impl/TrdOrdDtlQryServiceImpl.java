package com.nbl.services.order.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.dao.PaymentDao;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.Payment;
import com.nbl.model.TradeOrder;
import com.nbl.service.business.dto.res.OrderResDto;
import com.nbl.service.business.dto.res.PayOrderResDto;
import com.nbl.service.business.dto.res.TradeOrderResDto;
import com.nbl.service.user.dto.req.TrdOrdDtlQryDto;
import com.nbl.services.order.TrdOrdDtlQryService;
import com.nbl.services.product.feature.PrdFeatureService;
import com.nbl.utils.BeanParseUtils;

@Service("trdOrdDtlQryService")
public class TrdOrdDtlQryServiceImpl implements TrdOrdDtlQryService {

	private final static Logger logger = LoggerFactory.getLogger(TrdOrdDtlQryServiceImpl.class);
	@Resource
	private TradeOrderDao tradeOrderDao;
	@Resource
	private PaymentDao paymentDao;
	@Resource
	private ProductCommonDao productCommonDao;
	@Resource
	private PrdFeatureService prdFeatureService;

	@Override
	public CommRespDto queryTrdOrdDtl(TrdOrdDtlQryDto trdOrdDtlQryDto) {

		logger.info("enter business queryTrdOrdDtl input params is:" + trdOrdDtlQryDto.toString());
		CommRespDto returnMsg = null;
		// 查询交易订单
		TradeOrder tradeOrder = tradeOrderDao.selectByPrimaryKey(trdOrdDtlQryDto.getTradeOrderId());
		TradeOrderResDto toDto = new TradeOrderResDto();
		BeanParseUtils.copyPropertiesAmt(tradeOrder, toDto, "amt", "tradeTalAmt", "redEnvAmt");
		// 查询支付订单
		List<Payment> payOrders = paymentDao.selectByTradeOrderId(trdOrdDtlQryDto.getTradeOrderId());
		List<PayOrderResDto> poDtos = new ArrayList<PayOrderResDto>();

		for (Payment payOrder : payOrders) {
			PayOrderResDto poDto = new PayOrderResDto();
			BeanParseUtils.copyProperties(payOrder, poDto, "tradeTalAmt", "redEnvAmt");
			poDtos.add(poDto);
		}

		returnMsg = new CommRespDto().success(new OrderResDto(toDto, poDtos));

		return returnMsg;
	}

}
