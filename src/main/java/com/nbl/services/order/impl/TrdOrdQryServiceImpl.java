package com.nbl.services.order.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.nbl.common.constants.ComConst;
import com.nbl.common.constants.ErrorCode;
import com.nbl.common.constants.ProjectConstants;
import com.nbl.common.dto.CommRespDto;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.CustInvHis;
import com.nbl.model.PrdTrdHis;
import com.nbl.model.SerialFund;
import com.nbl.model.TradeOrder;
import com.nbl.model.TrdSumInfo;
import com.nbl.model.vo.TrdOrdVo;
import com.nbl.service.business.constant.OrdComOrderByCol;
import com.nbl.service.business.constant.OrderByFlag;
import com.nbl.service.business.constant.OrderStatus;
import com.nbl.service.business.constant.PaymentType;
import com.nbl.service.business.constant.RechargePayStatus;
import com.nbl.service.business.constant.SerFundQryType;
import com.nbl.service.business.constant.WithdrawStatus;
import com.nbl.service.business.dto.req.HisTrdOrdDto;
import com.nbl.service.business.dto.req.InvHisDto;
import com.nbl.service.business.dto.res.HisPrdTrdResDto;
import com.nbl.service.business.dto.res.HisPrdTrdResItemDto;
import com.nbl.service.business.dto.res.HisTrdOrdResDto;
import com.nbl.service.business.dto.res.InvHisResDto;
import com.nbl.service.business.dto.res.InvHisResItemDto;
import com.nbl.service.user.dto.req.RchgQryInfoDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.req.TrdOrdQryInfoDto;
import com.nbl.service.user.dto.req.WthdrQryInfoDto;
import com.nbl.service.user.dto.res.SerFundQryRsltItemDto;
import com.nbl.service.user.dto.res.SerialFundQryRsltDto;
import com.nbl.service.user.dto.res.TradeInfoItemDto;
import com.nbl.service.user.dto.res.TrdOrdQryRsltDto;
import com.nbl.services.account.RechargeQryService;
import com.nbl.services.account.WthdrQryService;
import com.nbl.services.order.TrdOrdQryService;
import com.nbl.services.product.WorkService;
import com.nbl.services.trade.IncomeService;
import com.nbl.util.AmtParseUtil;
import com.nbl.util.DateTimeUtils;
import com.nbl.util.ErrCodeUtil;
import com.nbl.utils.BeanParseUtils;

/**
 * 交易订单查询服务
 * 
 * @author AlanMa
 *
 */
@Service("trdOrdQryService")
public class TrdOrdQryServiceImpl implements TrdOrdQryService {

	private final static Logger logger = LoggerFactory.getLogger(TrdOrdQryServiceImpl.class);

	@Resource
	private TradeOrderDao tradeOrderDao;
	@Resource
	private TrdOrdQryService trdOrdQryService;
	@Resource
	private RechargeQryService rechargeQryService;
	@Resource
	private WthdrQryService wthdrQryService;
	@Resource
	private WorkService workService;
	@Resource
	private IncomeService incomeService;

	@Override
	public CommRespDto queryTradeOrder(TrdOrdQryInfoDto trdOrdQryInfoDto) {
		logger.info("enter business queryTradeOrder input params is:" + trdOrdQryInfoDto.toString());
		CommRespDto result = null;
		// 查询交易订单
		if (StringUtils.isEmpty(trdOrdQryInfoDto.getTradeOrderId())) {
			TrdOrdVo trdOrdVo = new TrdOrdVo();
			BeanUtils.copyProperties(trdOrdQryInfoDto, trdOrdVo);
			PageVO<TrdOrdVo> pageVO = new PageVO<TrdOrdVo>();

			int recordNum = trdOrdQryInfoDto.getRecordNum() == 0 ? ProjectConstants.RECORD_NUM : trdOrdQryInfoDto.getRecordNum();
			pageVO.setStartSize(trdOrdQryInfoDto.getStartIndex());
			pageVO.setSize(recordNum);

			if (StringUtils.isNotEmpty(trdOrdQryInfoDto.getOrderColumn()) && StringUtils.isNotEmpty(trdOrdQryInfoDto.getOrderFlag())) {
				StringBuffer orderBy = new StringBuffer();
				orderBy.append(OrdComOrderByCol.parseOf(trdOrdQryInfoDto.getOrderColumn()));
				orderBy.append(" ");
				orderBy.append(OrderByFlag.parseOf(trdOrdQryInfoDto.getOrderFlag()));
				pageVO.setOrderBy(orderBy.toString());
			}

			List<TrdSumInfo> trdSumInfos = tradeOrderDao.selectTrdOrdAndPrd(pageVO, trdOrdVo);

			if (trdSumInfos != null && trdSumInfos.size() > 0) {
				List<TradeInfoItemDto> list = parseEntityToVo(trdSumInfos);
				result = new CommRespDto().success(new TrdOrdQryRsltDto(list));
			} else {
				logger.warn("[trade order is null]");
				result = new CommRespDto().success();
			}
		} else {
			TradeOrder tradeOrder = tradeOrderDao.selectByPrimaryKey(trdOrdQryInfoDto.getTradeOrderId());
			List<TradeInfoItemDto> list = new ArrayList<TradeInfoItemDto>();
			TradeInfoItemDto item = new TradeInfoItemDto();
			//将单价和总金额单位由分转换为元
			BeanParseUtils.copyPropertiesAmt(tradeOrder, item, "unitCost","tradeTalAmt");
			list.add(item);
			result = new CommRespDto().success(list);
		}

		return result;
	}

	private List<TradeInfoItemDto> parseEntityToVo(List<TrdSumInfo> trdSumInfos) {
		List<TradeInfoItemDto> list = new ArrayList<TradeInfoItemDto>();
		for (TrdSumInfo tradeOrder : trdSumInfos) {
			TradeInfoItemDto newRecharge = new TradeInfoItemDto();
			BeanParseUtils.copyPropertiesAmt(tradeOrder, newRecharge, "unitCost","tradeTalAmt");
			newRecharge.setExpectEarnRate(AmtParseUtil.longToStrPercent(tradeOrder.getExpectEarnRate()));
			list.add(newRecharge);
		}
		return list;
	}

	@Override
	public CommRespDto querySerialFund(SerFundQryDto serFundQryDto) {
		logger.info("enter business querySerialFund input params is:" + serFundQryDto.toString());
		CommRespDto serialFundQryRsltDto = null;
		if (SerFundQryType.RECHARGE.getValue().equals(serFundQryDto.getType())) {
			serialFundQryRsltDto = rechargeQryService.queryRechgOrderInSerFund(serFundQryDto);
		}
		if (SerFundQryType.PURCHASE.getValue().equals(serFundQryDto.getType())) {
			serialFundQryRsltDto = trdOrdQryService.queryTradeOrderInSerFund(serFundQryDto);
		}
		if (SerFundQryType.WITHDRAW.getValue().equals(serFundQryDto.getType())) {
			serialFundQryRsltDto = wthdrQryService.queryWthdrOrderInSerFund(serFundQryDto);
		}
		if (SerFundQryType.BENEFIT.getValue().equals(serFundQryDto.getType())) {
			List<SerFundQryRsltItemDto> incomes = incomeService.qryIncomeInFundFlow(serFundQryDto);
			if (incomes == null) {
				serialFundQryRsltDto = new CommRespDto().success();
			} else {
				serialFundQryRsltDto = new CommRespDto().success(new SerialFundQryRsltDto(incomes));
			}
		}
		if (SerFundQryType.REDEEM.getValue().equals(serFundQryDto.getType())) {
			// TODO
		}

		return serialFundQryRsltDto;
	}

	@Override
	public CommRespDto queryTradeOrderInSerFund(SerFundQryDto serFundQryDto) {
		logger.info("enter business queryTradeOrderInSerFund input params is:" + serFundQryDto.toString());
		CommRespDto result = null;
		// 查询交易订单
		TrdOrdVo trdOrdVo = new TrdOrdVo();
		BeanUtils.copyProperties(serFundQryDto, trdOrdVo);
		
		PageVO<TrdOrdVo> pageVO = new PageVO<TrdOrdVo>();
		pageVO.setStartSize(serFundQryDto.getStartIndex());
		pageVO.setSize(serFundQryDto.getRecordNum());
		
		List<SerialFund> tradeOrders = new ArrayList<>();
		String paymentType=serFundQryDto.getPaymentType();
		
		//payment为"00"为用户余额查询时查询用户所有使用余额支付的的订单
		if(PaymentType.ACCOUNT_PAYMENT.getValue().equals(paymentType)){
			tradeOrders = tradeOrderDao.selectSerFndByCustInfoWithPayType(pageVO, trdOrdVo, paymentType);
		}else {
			//不设置paymentType则为资金流水查询
			//设置订单状态为支付成功
			trdOrdVo.setStatus(OrderStatus.PAY_SUCCESS.getValue());
			tradeOrders = tradeOrderDao.selectSerFndByCustInfo(pageVO, trdOrdVo);
		}
		
		if (tradeOrders != null && tradeOrders.size() > 0) {
			List<SerFundQryRsltItemDto> list = parseEntityToSerFndVo(tradeOrders);
			result = new CommRespDto().success(new SerialFundQryRsltDto(list));
		} else {
			logger.warn("[trade order is null]");
			result = new CommRespDto().success();
		}

		return result;
	}

	private List<SerFundQryRsltItemDto> parseEntityToSerFndVo(List<SerialFund> tradeOrders) {
		List<SerFundQryRsltItemDto> list = new ArrayList<SerFundQryRsltItemDto>();
		for (SerialFund tradeOrder : tradeOrders) {
			SerFundQryRsltItemDto newRecharge = new SerFundQryRsltItemDto();
			BeanParseUtils.copyPropertiesAmt(tradeOrder, newRecharge,"benefit", "expend");
			list.add(newRecharge);
		}
		return list;
	}

	@Override
	public CommRespDto queryHisTrdOrd(HisTrdOrdDto hisTrdOrdDto) {
		Date[] updateDate = null;
		String trdOrdStatus = null;
		// 历史成交订单数
		Integer hisTrdOrdNum = tradeOrderDao.getTrdOrdCount();
		hisTrdOrdNum = hisTrdOrdNum == null ? 0 : hisTrdOrdNum;

		// 支昨日成交订单数
		if (hisTrdOrdDto.getUpdateDateStart() == null || hisTrdOrdDto.getUpdateDateEnd() == null) {
			String workdate = workService.getCurrentWorkDate();
			Date date = null;
			try {
				date = DateTimeUtils.getYesterday(workdate);
				updateDate = DateTimeUtils.parseDateTimeInterval(date);
			} catch (ParseException e) {
				logger.error("parse work date error", e);
				String[] errMsg = ErrCodeUtil.getErrMsg(ErrorCode.BUE001, "");
				return new CommRespDto().fail(errMsg[0], errMsg[1]);
			}
		} else {
			updateDate = new Date[2];
			updateDate[0] = hisTrdOrdDto.getUpdateDateStart();
			updateDate[1] = hisTrdOrdDto.getUpdateDateEnd();
		}
		Integer yesTrdOrdNum = tradeOrderDao.getTrdOrdCountByDate(updateDate[0], updateDate[1]);
		yesTrdOrdNum = yesTrdOrdNum == null ? 0 : yesTrdOrdNum;
		// 已发放租金
		trdOrdStatus = StringUtils.isNotEmpty(hisTrdOrdDto.getOrderStatus()) ? hisTrdOrdDto.getOrderStatus() : OrderStatus.PAY_SUCCESS.getValue();
		Integer rent = tradeOrderDao.getTrdOrdCountByStatus(trdOrdStatus);
		rent = rent == null ? 0 : rent;

		HisTrdOrdResDto result = new HisTrdOrdResDto(Integer.toString(hisTrdOrdNum), Integer.toString(yesTrdOrdNum), Integer.toString(rent));
		return new CommRespDto().success(result);
	}

	@Override
	public CommRespDto queryHisPrdTrd() {
		logger.info("enter business queryHisPrdTrd");
		CommRespDto result = null;
		List<PrdTrdHis> prdTrdHiss = tradeOrderDao.getTrdOrdAndPrdIdx();
		if (prdTrdHiss != null && prdTrdHiss.size() > 0) {
			List<HisPrdTrdResItemDto> list = parseEntityToHisPrdVo(prdTrdHiss);
			result = new CommRespDto().success(new HisPrdTrdResDto(list));
		} else {
			logger.warn("[trade order is null]");
			result = new CommRespDto().success();
		}
		return result;
	}

	private List<HisPrdTrdResItemDto> parseEntityToHisPrdVo(List<PrdTrdHis> prdTrdHiss) {
		List<HisPrdTrdResItemDto> list = new ArrayList<HisPrdTrdResItemDto>();
		for (PrdTrdHis prdTrdHis : prdTrdHiss) {
			HisPrdTrdResItemDto hisPrdTrd = new HisPrdTrdResItemDto();
			BeanParseUtils.copyPropertiesToString(prdTrdHis, hisPrdTrd, ComConst.TRUE);
			long distanceTime = DateTimeUtils.getDistanceTime(prdTrdHis.getUpdateTime(), DateTimeUtils.now().toDate());
			hisPrdTrd.setDistanceTime(String.valueOf(distanceTime));
			list.add(hisPrdTrd);
		}
		return list;
	}

	@Override
	public CommRespDto qryCustInvestHistory(InvHisDto invHisDto) {
		logger.info("enter business qryCustInvestHistory input params is:" + invHisDto.toString());
		CommRespDto result = null;
		List<CustInvHis> prdTrdHis = tradeOrderDao.getCustInvHis(invHisDto.getCustId(), OrderStatus.PAY_SUCCESS.getValue());
		if (prdTrdHis != null && prdTrdHis.size() > 0) {
			List<InvHisResItemDto> list = parseEntityToInvHisVo(prdTrdHis);
			result = new CommRespDto().success(new InvHisResDto(list));
		} else {
			logger.warn("[trade order is null]");
			result = new CommRespDto().success();
		}
		return result;
	}

	private List<InvHisResItemDto> parseEntityToInvHisVo(List<CustInvHis> prdTrdHiss) {
		List<InvHisResItemDto> list = new ArrayList<InvHisResItemDto>();
		for (CustInvHis prdTrdHis : prdTrdHiss) {
			InvHisResItemDto invHis = new InvHisResItemDto();
			BeanParseUtils.copyPropertiesAmt(prdTrdHis, invHis, "tradeTalAmt");
			list.add(invHis);
		}
		return list;
	}

	@Override
	public String queryTradeOrderCount(TrdOrdQryInfoDto trdOrdQryInfoDto) {
		String countStr = null;
		TrdOrdVo trdOrdVo = new TrdOrdVo();
		BeanUtils.copyProperties(trdOrdQryInfoDto, trdOrdVo);
		Integer count = tradeOrderDao.getTrdOrdCountByCondition(trdOrdVo);
		countStr = count == null ? null : Integer.toString(count);
		return countStr;
	}

	@Override
	public String querySerialFundCount(SerFundQryDto serFundQryDto) {
		logger.info("enter business querySerialFundCount input params is:" + serFundQryDto.toString());
		String countStr = null;
		if (SerFundQryType.RECHARGE.getValue().equals(serFundQryDto.getType())) {
			RchgQryInfoDto rchgQryInfoDto = new RchgQryInfoDto();
			BeanUtils.copyProperties(serFundQryDto, rchgQryInfoDto);
			//设置查询条件为充值成功
			rchgQryInfoDto.setStatus(RechargePayStatus.SUCCESS.getValue());
			countStr = rechargeQryService.queryRchgOrderCount(rchgQryInfoDto);
		}
		if (SerFundQryType.PURCHASE.getValue().equals(serFundQryDto.getType())) {
			TrdOrdQryInfoDto trdOrdQryInfoDto = new TrdOrdQryInfoDto();
			BeanUtils.copyProperties(serFundQryDto, trdOrdQryInfoDto);
			//设置查询条件为购买成功
			trdOrdQryInfoDto.setStatus(OrderStatus.PAY_SUCCESS.getValue());
			countStr = trdOrdQryService.queryTradeOrderCount(trdOrdQryInfoDto);
		}
		if (SerFundQryType.WITHDRAW.getValue().equals(serFundQryDto.getType())) {
			WthdrQryInfoDto wthdrQryInfoDto = new WthdrQryInfoDto();
			BeanUtils.copyProperties(serFundQryDto, wthdrQryInfoDto);
			//设置查询条件为取现成功
			wthdrQryInfoDto.setStatus(WithdrawStatus.SUCCESSFUL.getValue());
			countStr = wthdrQryService.queryWthdrOrderCount(wthdrQryInfoDto);
		}
		if (SerFundQryType.BENEFIT.getValue().equals(serFundQryDto.getType())) {
			countStr = incomeService.qryIncomeInFundFlowCount(serFundQryDto);
		}
		if (SerFundQryType.REDEEM.getValue().equals(serFundQryDto.getType())) {
			// TODO 暂无赎回
		}
		return countStr;
	}

	@Override
	public CommRespDto querySerialFund4Withdr(SerFundQryDto serFundQryDto) {
		logger.info("enter business querySerialFund input params is:" + serFundQryDto.toString());
		CommRespDto serialFundQryRsltDto = null;
		if (SerFundQryType.RECHARGE.getValue().equals(serFundQryDto.getType())) {
			serialFundQryRsltDto = rechargeQryService.queryRechgOrderInSerFund(serFundQryDto);
		}
		if (SerFundQryType.PURCHASE.getValue().equals(serFundQryDto.getType())) {
			serialFundQryRsltDto = trdOrdQryService.queryTradeOrderInSerFund(serFundQryDto);
		}
		if (SerFundQryType.WITHDRAW.getValue().equals(serFundQryDto.getType())) {
			serialFundQryRsltDto = wthdrQryService.queryWthdrOrderInSerFund4Wthdr(serFundQryDto);
		}
		if (SerFundQryType.BENEFIT.getValue().equals(serFundQryDto.getType())) {
			List<SerFundQryRsltItemDto> incomes = incomeService.qryIncomeInFundFlow(serFundQryDto);
			if (incomes == null) {
				serialFundQryRsltDto = new CommRespDto().success();
			} else {
				serialFundQryRsltDto = new CommRespDto().success(incomes);
			}
		}
		if (SerFundQryType.REDEEM.getValue().equals(serFundQryDto.getType())) {
			// TODO
		}

		return serialFundQryRsltDto;
	}
}
