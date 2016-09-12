package com.nbl.services.order.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ComConst;
import com.nbl.common.constants.ErrorCode;
import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.dao.GeneralParameterDao;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.AccountRule;
import com.nbl.model.ProductCommon;
import com.nbl.model.TradeOrder;
import com.nbl.model.vo.AccountBookingVo;
import com.nbl.service.business.constant.AccountBookState;
import com.nbl.service.business.constant.DebitCredtFlag;
import com.nbl.service.business.constant.ParamKeys;
import com.nbl.service.business.constant.ProductStatus;
import com.nbl.service.business.dto.req.PrdPchInfoDto;
import com.nbl.service.business.dto.res.PrdCheckResDto;
import com.nbl.service.business.dto.res.PrdPchResultDto;
import com.nbl.services.accrule.DealAccService;
import com.nbl.services.order.PrdPchService;
import com.nbl.services.order.QtaTrdOrdOprService;
import com.nbl.services.product.AccountBookingService;
import com.nbl.services.product.WorkService;
import com.nbl.utils.DateTimeUtils;

@Service("prdPchService")
public class PrdPchServiceImpl implements PrdPchService {
	private final static Logger logger = LoggerFactory.getLogger(PrdPchServiceImpl.class);

	@Resource
	private WorkService workService;
	@Resource
	private DealAccService dealAccService;
	@Resource
	private AccountBookingService accountBookingService;
	@Resource
	private QtaTrdOrdOprService qtaTrdOrdOprService;
	@Resource
	private ProductCommonDao productCommonDao;
	@Resource
	private GeneralParameterDao generalParameterDao;
	@Resource
	private TradeOrderDao tradeOrderDao;

	@Override
	@Transactional
	public CommRespDto buyNow(PrdPchInfoDto prdPchInfoDto) {
		logger.info("【enter business buyNow input params is】:" + prdPchInfoDto.toString());

		TradeOrder tradeOrder = null;
		try {
			// 锁定产品表的份额
			qtaTrdOrdOprService.lockQuota(prdPchInfoDto.getProductId(), prdPchInfoDto.getPurchasePortion());
			logger.info("[lockQuota successful]");
			// 创建交易订单——登记交易订单表（T_TRADE_ORDER）
			tradeOrder = qtaTrdOrdOprService.createTradeOrder(prdPchInfoDto);
		} catch (MyBusinessCheckException e) {
			logger.error(e.getErrorCode() + ":" + e.getErrMsgKey());
			return new CommRespDto().fail(e.getErrorCode(), e.getErrMsgKey());
		}

		// 记账处理
		recordAccBook(prdPchInfoDto, tradeOrder);

		return new CommRespDto().success(new PrdPchResultDto(tradeOrder.getId(), tradeOrder.getOrderStatus()));

	}

	@Override
	public PrdCheckResDto buyNowChkPrdInfo(PrdPchInfoDto prdPchInfoDto) throws MyBusinessCheckException {
		logger.info("PrdPchServiceImpl.buyNowChkPrdInfo [prdPchInfoDto]:" + prdPchInfoDto.toString());
		ProductCommon prdComm = productCommonDao.selectByPrimaryKey(prdPchInfoDto.getProductId());
		if (prdComm == null)
			throw new MyBusinessCheckException(ErrorCode.BUC002, prdPchInfoDto.getProductId());
		if (prdComm.getFinanceScale() <= 0)
			throw new MyBusinessCheckException(ErrorCode.BUB001, prdPchInfoDto.getProductId() + prdComm.getProductName());
		if (prdComm.getCollectStartTime().getTime() - DateTimeUtils.now().toDate().getTime() >= 0)
			throw new MyBusinessCheckException(ErrorCode.BUB002, new DateTimeUtils(prdComm.getCollectStartTime()).toDateTimeString());
		if (prdPchInfoDto.getPurchasePortion() - prdComm.getInvestMax() > 0)
			throw new MyBusinessCheckException(ErrorCode.BUB003);
		if (prdComm.getFinanceScale() - prdPchInfoDto.getPurchasePortion() < 0)
			throw new MyBusinessCheckException(ErrorCode.BUB006);
		if (!ProductStatus.PRODUCT_STATUS03.getValue().equals(prdComm.getProductStatus())) {
			throw new MyBusinessCheckException(ErrorCode.BUB007);
		}

		PrdCheckResDto result = new PrdCheckResDto();
		BeanUtils.copyProperties(prdComm, result);
		return result;
	}

	/**
	 * 记账
	 * 
	 * @param prdPchInfoDto
	 * @param tradeOrder
	 */
	private void recordAccBook(PrdPchInfoDto prdPchInfoDto, TradeOrder tradeOrder) {
		List<AccountBookingVo> accBookVos = new ArrayList<AccountBookingVo>();
		String ruleId = dealAccService.getRuleId(ComConst.PURCH_PRD.OPT_CODE, ComConst.PURCH_PRD.TRADE_ORDER);
		AccountRule accRule = dealAccService.getAccRule(ruleId);
		String custIdYF = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_YF.getValue());
		String custIdZGPT = generalParameterDao.getParamValueByKey(ParamKeys.CUST_ID_ZGPT.getValue());

		AccountBookingVo accBookVo = new AccountBookingVo();
		accBookVo.setProductId(prdPchInfoDto.getProductId());
		accBookVo.setProductName(prdPchInfoDto.getProductNane());
		accBookVo.setOrderId(tradeOrder.getId());
		accBookVo.setTradeDate(tradeOrder.getTradeDate());
		accBookVo.setPayerCustID(custIdZGPT);
		accBookVo.setPayeeCustID(custIdYF);
		accBookVo.setAmount(prdPchInfoDto.getPurchasePortion());
		accBookVo.setOptCode(ComConst.PURCH_PRD.OPT_CODE);
		accBookVo.setRuleId(ruleId);
		accBookVo.setAccountDate(workService.getCurrentWorkDate());
		accBookVo.setDrSubjectNo(accRule.getPayerSubjectNo());
		accBookVo.setDrCustId(custIdZGPT);
		accBookVo.setCrSubjectNo(accRule.getPayeeSubjectNo());
		accBookVo.setCrCustId(custIdYF);
		accBookVo.setBookTime(DateTimeUtils.now().toDate());
		accBookVo.setBookState(AccountBookState.VALID.getValue());
		accBookVo.setPayerFlag(DebitCredtFlag.FLAG_DR.getValue());
		accBookVos.add(accBookVo);

		accountBookingService.doAccountBooking(accBookVos);

	}
}
