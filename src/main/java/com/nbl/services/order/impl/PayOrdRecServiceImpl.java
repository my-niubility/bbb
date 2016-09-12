package com.nbl.services.order.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ComConst;
import com.nbl.common.constants.SeqenceConstant;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.dao.GeneralParameterDao;
import com.nbl.dao.PaymentDao;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.AccountRule;
import com.nbl.model.Payment;
import com.nbl.model.TradeOrder;
import com.nbl.model.vo.AccountBookingVo;
import com.nbl.model.vo.AccountReverseVo;
import com.nbl.service.business.constant.AccountBookState;
import com.nbl.service.business.constant.CheckStatus;
import com.nbl.service.business.constant.DebitCredtFlag;
import com.nbl.service.business.constant.IdPrefix;
import com.nbl.service.business.constant.ParamKeys;
import com.nbl.service.business.constant.PaymentStatus;
import com.nbl.service.business.dto.req.PayAlyInfoDto;
import com.nbl.service.business.dto.req.PayRltInfoDto;
import com.nbl.service.business.dto.res.PayAlyResultDto;
import com.nbl.service.user.dto.req.SerialRefDto;
import com.nbl.services.accrule.DealAccService;
import com.nbl.services.order.PayOrdRecService;
import com.nbl.services.product.AccountBookingService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.WorkService;
import com.nbl.services.remote.CustAccQueryService;
import com.nbl.services.remote.SerialRefService;
import com.nbl.utils.DateTimeUtils;

@Service("payOrdRecService")
public class PayOrdRecServiceImpl implements PayOrdRecService {
	private final static Logger logger = LoggerFactory.getLogger(PayOrdRecServiceImpl.class);

	@Resource
	private IdGeneratorService idGeneratorAppService;
	@Resource
	private PaymentDao paymentDao;
	@Resource
	private TradeOrderDao tradeOrderDao;
	@Resource
	private CustAccQueryService custAccQueryService;
	@Resource
	private WorkService workService;
	@Resource
	private DealAccService dealAccService;
	@Resource
	private AccountBookingService accountBookingService;
	@Resource
	private GeneralParameterDao generalParameterDao;
	@Resource
	private SerialRefService serialRefServiceBus;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Payment createPaymentOrder(PayAlyInfoDto payAlyInfoDto, TradeOrder tradeOrder) {
		Payment paymentOrder = new Payment();
		BeanUtils.copyProperties(payAlyInfoDto, paymentOrder);
		String paymentOrderId = IdPrefix.PAYMENT_ORDER.getValue() + payAlyInfoDto.getChannelCode() + idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		paymentOrder.setId(paymentOrderId);
		paymentOrder.setProductId(tradeOrder.getProductId());
		paymentOrder.setProductNane(tradeOrder.getProductNane());
		paymentOrder.setTotalId(payAlyInfoDto.getTradeOrderId());
		paymentOrder.setMerchantId(generalParameterDao.getParamValueByKey(ParamKeys.MERCHANT_ID_ZLZB.getValue()));
		paymentOrder.setPayAccount(custAccQueryService.getAccId(payAlyInfoDto.getPayCustId()));
		paymentOrder.setPayCustName(custAccQueryService.getCustName(payAlyInfoDto.getPayCustId()));
		paymentOrder.setRecCustId(generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_ZGPT.getValue()));
		paymentOrder.setRecCustName(generalParameterDao.getParamValueByKey(ParamKeys.CUST_NAME_ZGPT.getValue()));
		paymentOrder.setRecAccount(generalParameterDao.getParamValueByKey(ParamKeys.ACCOUNT_ID_ZLZB.getValue()));
		paymentOrder.setCreateTime(DateTimeUtils.now().toDate());
		paymentOrder.setUpdateTime(DateTimeUtils.now().toDate());
		paymentOrder.setPayDate(workService.getCurrentWorkDate());
		paymentOrder.setCheckStatus(CheckStatus.NOT.getValue());
		paymentOrder.setStatus(PaymentStatus.WAING.getValue());
		paymentOrder.setPayBankType(payAlyInfoDto.getPayBankType());
		paymentOrder.setPayBankCardNo(payAlyInfoDto.getPayBankCardNo());

		paymentDao.insert(paymentOrder);
		return paymentOrder;
	}

	@Override
	public void paymentNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException {
		// TODO 记录流水关联表
		// SerialRefDto serialRefDto = new SerialRefDto();
		// serialRefDto.setSerialId(thdPtyRslt.getPaymentSerialNum());
		// serialRefDto.setReturnCode(thdPtyRsltResp.getResIdentifier().getReturnType());
		// serialRefDto.setReturnMessage(thdPtyRsltResp.getResIdentifier().getReturnMsg());
		// serialRefServiceBus.updateSerialRef(serialRefDto);

		// String bookIdStr =
		// idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		// Long bookId = Long.parseLong(bookIdStr.substring(8,
		// bookIdStr.length()));
		// Payment payment =
		// paymentDao.selectByPayId(payRltInfoDto.getPayThdSeqNum());
		// TradeOrder tradeOrder =
		// tradeOrderDao.selectByPrimaryKey(payment.getTotalId());
		// try {
		// // 1.记录分录
		// recordAccBook(bookId, payment.getPayCustId(), payment.getId(),
		// payment.getPayDate(), payment.getProductId(),
		// payment.getProductNane(), tradeOrder.getPurchasePortion());
		// // 2.更新业务订单、支付订单状态
		// updateOrderStatus(payRltInfoDto.getResultInfo(), tradeOrder.getId(),
		// tradeOrder.getOrderStatus(), payment.getId(),
		// payment.getStatus(),payRltInfoDto.get);
		// } catch (MyBusinessCheckException e) {
		// recordAccBookReverse(tradeOrder.getId(), payment.getId(), bookId,
		// tradeOrder.getPurchasePortion(), payment.getPayDate(),
		// payment.getPayCustId(), payment.getPayDate(),
		// tradeOrder.getProductId(), tradeOrder.getProductNane(),
		// tradeOrder.getPurchasePortion());
		// throw e;
		// }
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void recordAccBook(Long bookId, String custId, String payOrderId, String tradeDate, String productId, String productName, Long purchasePortion) throws MyBusinessRuntimeException {
		String ruleId = dealAccService.getRuleId(ComConst.PURCH_PRD.OPT_CODE, ComConst.PURCH_PRD.PAY_ORDER);
		AccountRule accRule = dealAccService.getAccRule(ruleId);

		List<AccountBookingVo> accBookVos = new ArrayList<AccountBookingVo>();
		String custIdYF = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_YF.getValue());
		AccountBookingVo accBookVo = new AccountBookingVo();
		accBookVo.setBookId(bookId);
		accBookVo.setProductId(productId);
		accBookVo.setProductName(productName);
		accBookVo.setOrderId(payOrderId);
		accBookVo.setTradeDate(tradeDate);
		accBookVo.setPayerCustID(custIdYF);
		accBookVo.setPayeeCustID(custId);
		accBookVo.setAmount(purchasePortion);
		accBookVo.setOptCode(ComConst.PURCH_PRD.OPT_CODE);
		accBookVo.setRuleId(accRule.getRuleId());
		accBookVo.setAccountDate(workService.getCurrentWorkDate());
		accBookVo.setDrSubjectNo(accRule.getPayerSubjectNo());
		accBookVo.setDrCustId(custIdYF);
		accBookVo.setCrSubjectNo(accRule.getPayeeSubjectNo());
		accBookVo.setCrCustId(custId);
		accBookVo.setBookTime(DateTimeUtils.now().toDate());
		accBookVo.setBookState(AccountBookState.VALID.getValue());
		accBookVo.setPayerFlag(DebitCredtFlag.FLAG_DR.getValue());
		accBookVos.add(accBookVo);
		accountBookingService.doAccountBooking(accBookVos);

	}

	@Override
	public void updateOrderStatus(String resultInfo, String tradeOrderId, String trdOrdStatus, String payOrdId, String payOrdStatus, String thdPayOrdId) {
		TradeOrder tradeOrder = new TradeOrder();
		tradeOrder.setId(tradeOrderId);
		tradeOrder.setOrderStatus(trdOrdStatus);
		tradeOrder.setUpdateTime(DateTimeUtils.now().toDate());
		tradeOrderDao.updateByPrimaryKeySelective(tradeOrder);

		Payment payment = new Payment();
		payment.setId(payOrdId);
		payment.setStatus(payOrdStatus);
		payment.setUpdateTime(DateTimeUtils.now().toDate());
		payment.setResultInfo(resultInfo);
		payment.setPayId(thdPayOrdId);
		paymentDao.updateByPrimaryKeySelective(payment);
	}

	/**
	 * 记账冲正
	 * 
	 * @param orgTradeId
	 * @param orgPayId
	 * @param orgBookId
	 * @param orgAmount
	 * @param orgAccountDate
	 * @param custId
	 * @param tradeDate
	 * @param productId
	 * @param productName
	 * @param amount
	 * @throws MyBusinessCheckException
	 */
	@Override
	public void recordAccBookReverse(String orgTradeId, String orgPayId, Long orgBookId, Long orgAmount, String orgAccountDate, String custId, String tradeDate, String productId, String productName,
			Long amount) {
		String ruleId = dealAccService.getRuleId(ComConst.PURCH_PRD.OPT_CODE_REVERSE, ComConst.PURCH_PRD.PAY_ORDER);
		AccountRule accountRule = dealAccService.getAccRule(ruleId);
		List<AccountReverseVo> accBookRecvVos = new ArrayList<AccountReverseVo>();

		String custIdYF = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_YF.getValue());

		AccountReverseVo accBookRecvVo = new AccountReverseVo();
		accBookRecvVo.setOrgTradeId(orgTradeId);
		accBookRecvVo.setOrgPayId(orgPayId);
		accBookRecvVo.setOrgBookId(orgBookId);
		accBookRecvVo.setOrgAmount(orgAmount);
		accBookRecvVo.setOrgAccountDate(orgAccountDate);

		accBookRecvVo.setAmount(amount);
		accBookRecvVo.setRuleId(ruleId);
		accBookRecvVo.setPayerCustID(custId);
		accBookRecvVo.setPayeeCustID(custIdYF);
		accBookRecvVo.setOptCode(ComConst.PURCH_PRD.OPT_CODE_REVERSE);
		accBookRecvVo.setReverseDesc(ComConst.SIM_DATA.REVERSE_DESC);
		accBookRecvVo.setTradeDate(tradeDate);
		String reverseId = idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		accBookRecvVo.setReverseId(Long.parseLong(reverseId.substring(8, reverseId.length())));
		accBookRecvVo.setDrSubjectNo(accountRule.getPayerSubjectNo());
		accBookRecvVo.setDrCustId(custId);
		accBookRecvVo.setCrSubjectNo(accountRule.getPayeeSubjectNo());
		accBookRecvVo.setCrCustId(custIdYF);
		accBookRecvVo.setReverseAmount(amount);
		accBookRecvVo.setReverseAccountDate(tradeDate);
		accBookRecvVo.setReverseTime(DateTimeUtils.now().toDate());
		String payOrderId = idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		accBookRecvVo.setPayId(payOrderId);

		accBookRecvVos.add(accBookRecvVo);

		accountBookingService.doAccountReverse(accBookRecvVos);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void recordThdResponse(PayAlyResultDto thdPtyRslt, String retrunCode, String returnMessage) throws MyBusinessCheckException {
		SerialRefDto serialRefDto = new SerialRefDto();
		serialRefDto.setSerialId(thdPtyRslt.getPaymentSerialNum());
		serialRefDto.setReturnCode(retrunCode);
		serialRefDto.setReturnMessage(returnMessage);
		serialRefServiceBus.updateSerialRef(serialRefDto);
	}

}
