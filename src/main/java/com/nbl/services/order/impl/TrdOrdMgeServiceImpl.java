package com.nbl.services.order.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ComConst;
import com.nbl.common.constants.ErrorCode;
import com.nbl.common.dto.CommRespDto;
import com.nbl.dao.GeneralParameterDao;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.AccountRule;
import com.nbl.model.TradeOrder;
import com.nbl.model.vo.AccountReverseVo;
import com.nbl.model.vo.PrdBatchUpdVo;
import com.nbl.service.business.constant.OrderStatus;
import com.nbl.service.business.constant.ParamKeys;
import com.nbl.service.business.dto.req.CanlTrdOrdDto;
import com.nbl.service.business.dto.res.CanlTrdOrdResultDto;
import com.nbl.services.accrule.DealAccService;
import com.nbl.services.order.QtaTrdOrdOprService;
import com.nbl.services.order.TrdOrdMgeService;
import com.nbl.services.param.ParamService;
import com.nbl.services.product.AccountBookingService;
import com.nbl.services.product.WorkService;
import com.nbl.util.DateTimeUtils;
import com.nbl.util.ErrCodeUtil;

@Service("trdOrdMgeService")
public class TrdOrdMgeServiceImpl implements TrdOrdMgeService {

	private final static Logger logger = LoggerFactory.getLogger(TrdOrdMgeServiceImpl.class);

	@Resource
	private TradeOrderDao tradeOrderDao;
	@Resource
	private WorkService workService;
	@Resource
	private ParamService paramService;
	@Resource
	private AccountBookingService accountBookingService;
	@Resource
	private GeneralParameterDao generalParameterDao;
	@Resource
	private DealAccService dealAccService;
	@Resource
	private QtaTrdOrdOprService qtaTrdOrdOprService;

	@Override
	@Transactional
	public CommRespDto cancelTradeOrder(CanlTrdOrdDto canlTrdOrdDto) {
		logger.info("enter business cancelTradeOrder input params is:" + canlTrdOrdDto.toString());
		CommRespDto result = null;
		// 修改订单状态
		int resultNum = tradeOrderDao.updateOrderStatusById(canlTrdOrdDto.getTradeOrderId(), OrderStatus.ORDER_CANCEL.getValue());
		if (resultNum != 1) {
			String[] errMsg = ErrCodeUtil.getErrMsg(ErrorCode.BUD003);
			result = new CommRespDto().fail(errMsg[0], errMsg[1]);
		}
		cancelOrderFollowProcess(canlTrdOrdDto.getTradeOrderId());
		result = new CommRespDto().success(new CanlTrdOrdResultDto(String.valueOf(resultNum)));

		return result;
	}

	@Override
	@Transactional
	public CommRespDto disabledTradeOrder() {
		logger.info("enter business disabledTradeOrder");
		CommRespDto result = null;
		Date updateTime = null;
		try {
			updateTime = DateTimeUtils.getMinAgoDate(paramService.getValue(ParamKeys.DIS_TORD_TIME.getValue()));
		} catch (ParseException e) {
			logger.error("parse date error", e);
			String[] errMsg = ErrCodeUtil.getErrMsg(ErrorCode.BUE001);
			result = new CommRespDto().fail(errMsg[0], errMsg[1]);
		}
		int resultNum = tradeOrderDao.updateOrderStatusByUpdateTime(updateTime, OrderStatus.TO_PAY.getValue(), OrderStatus.ORDER_CANCEL.getValue());
		if (resultNum > 0) {
			cancelOrderFollowProcess(tradeOrderDao.selectCancelOrder(updateTime, OrderStatus.TO_PAY.getValue()));
		}
		result = new CommRespDto().success();
		return result;
	}

	private void cancelOrderFollowProcess(String orderId) {
		// 记账工具反向处理
		TradeOrder tradeOrder = tradeOrderDao.selectByPrimaryKey(orderId);
		List<AccountReverseVo> accountReverseVoList = new ArrayList<AccountReverseVo>();
		AccountReverseVo vo = new AccountReverseVo();
		String ruleId = dealAccService.getRuleId(ComConst.PURCH_PRD.OPT_CODE_REVERSE, ComConst.PURCH_PRD.TRADE_ORDER);
		AccountRule accRule = dealAccService.getAccRule(ruleId);
		String custIdZGPT = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_ZGPT.getValue());
		String custIdYF = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_YF.getValue());

		vo.setPayerCustID(custIdYF);
		vo.setDrCustId(custIdYF);
		vo.setDrSubjectNo(accRule.getPayerSubjectNo());
		vo.setOptCode(ComConst.PURCH_PRD.OPT_CODE_REVERSE);
		vo.setPayeeCustID(custIdZGPT);
		vo.setCrCustId(custIdZGPT);
		vo.setCrSubjectNo(accRule.getPayeeSubjectNo());
		vo.setOrgAccountDate(tradeOrder.getTradeDate());
		// 交易订单冲正用TradeOrderId替代PaymentOrderId
		vo.setOrgPayId(tradeOrder.getId());
		vo.setOrgTradeId(tradeOrder.getId());
		vo.setTradeDate(tradeOrder.getTradeDate());
		vo.setAmount(tradeOrder.getTradeTalAmt());
		vo.setOrgAmount(tradeOrder.getTradeTalAmt());
		vo.setReverseAmount(tradeOrder.getTradeTalAmt());
		// 交易订单冲正用TradeOrderId替代PaymentOrderId
		vo.setPayId(tradeOrder.getId());
		vo.setReverseAccountDate(workService.getCurrentWorkDate());
		vo.setReverseDesc(ComConst.SIM_DATA.REVERSE_DESC);
		vo.setReverseTime(DateTimeUtils.now().toDate());
		vo.setRuleId(ruleId);
		accountReverseVoList.add(vo);
		accountBookingService.doAccountReverse(accountReverseVoList);
		// 解锁份额，还原产品表份额
		qtaTrdOrdOprService.unlockQuota(tradeOrder.getProductId(), tradeOrder.getPurchasePortion());
	}

	private void cancelOrderFollowProcess(List<TradeOrder> tradeOrders) {

		List<AccountReverseVo> accountReverseVoList = new ArrayList<AccountReverseVo>();
		List<PrdBatchUpdVo> list = new ArrayList<PrdBatchUpdVo>();
		for (TradeOrder tradeOrder : tradeOrders) {
			AccountReverseVo vo = new AccountReverseVo();
			String ruleId = dealAccService.getRuleId(ComConst.PURCH_PRD.OPT_CODE_REVERSE, ComConst.PURCH_PRD.TRADE_ORDER);
			AccountRule accRule = dealAccService.getAccRule(ruleId);
			String custIdZGPT = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_ZGPT.getValue());
			String custIdYF = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_YF.getValue());

			vo.setPayerCustID(custIdYF);
			vo.setDrCustId(custIdYF);
			vo.setDrSubjectNo(accRule.getPayerSubjectNo());
			vo.setOptCode(ComConst.PURCH_PRD.OPT_CODE_REVERSE);
			vo.setPayeeCustID(custIdZGPT);
			vo.setCrCustId(custIdZGPT);
			vo.setCrSubjectNo(accRule.getPayeeSubjectNo());
			vo.setOrgAccountDate(tradeOrder.getTradeDate());
			// 交易订单冲正用TradeOrderId替代PaymentOrderId
			vo.setOrgPayId(tradeOrder.getId());
			vo.setOrgTradeId(tradeOrder.getId());
			vo.setTradeDate(tradeOrder.getTradeDate());
			vo.setAmount(tradeOrder.getTradeTalAmt());
			vo.setOrgAmount(tradeOrder.getTradeTalAmt());
			vo.setReverseAmount(tradeOrder.getTradeTalAmt());
			// 交易订单冲正用TradeOrderId替代PaymentOrderId
			vo.setPayId(tradeOrder.getId());
			vo.setReverseAccountDate(workService.getCurrentWorkDate());
			vo.setReverseDesc(ComConst.SIM_DATA.REVERSE_DESC);
			vo.setReverseTime(DateTimeUtils.now().toDate());
			vo.setRuleId(ruleId);
			accountReverseVoList.add(vo);

			PrdBatchUpdVo prdVo = new PrdBatchUpdVo();
			prdVo.setPortion(tradeOrder.getPurchasePortion());
			prdVo.setPrdId(tradeOrder.getProductId());
			list.add(prdVo);
		}
		// 记账工具反向处理
		accountBookingService.doAccountReverse(accountReverseVoList);
		// 解锁份额，还原产品表份额
		qtaTrdOrdOprService.unlockQuota(list);
	}

}
