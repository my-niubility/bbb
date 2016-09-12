package com.nbl.services.order.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ComConst;
import com.nbl.common.constants.ErrorCode;
import com.nbl.common.constants.SeqenceConstant;
import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.dao.PositionDao;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.Payment;
import com.nbl.model.Position;
import com.nbl.model.TradeOrder;
import com.nbl.service.business.constant.OrderStatus;
import com.nbl.service.business.constant.PaymentStatus;
import com.nbl.service.business.constant.PaymentType;
import com.nbl.service.business.constant.PositionCustType;
import com.nbl.service.business.dto.req.PayAlyInfoDto;
import com.nbl.service.business.dto.req.PayRltInfoDto;
import com.nbl.service.business.dto.res.PayAlyResultDto;
import com.nbl.services.order.OrderPaymentService;
import com.nbl.services.order.PayOrdRecService;
import com.nbl.services.order.QtaPayOrdOprService;
import com.nbl.services.order.QtaTrdOrdOprService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.remote.PaymentThdService;
import com.nbl.services.sendmsg.StaMsgSender;
import com.nbl.util.MapUtil;

@Service("orderPaymentService")
public class OrderPaymentServiceImpl implements OrderPaymentService {
	private final static Logger logger = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);

	@Resource
	private IdGeneratorService idGeneratorAppService;
	@Resource
	private TradeOrderDao tradeOrderDao;
	@Resource
	private PositionDao positionDao;
	@Resource
	private PaymentThdService paymentThdService;
	@Resource
	private PayOrdRecService payOrdRecService;
	@Resource
	private QtaPayOrdOprService qtaPayOrdOprService;
	@Resource
	private QtaTrdOrdOprService qtaTrdOrdOprService;
	@Resource
	private StaMsgSender payNotMsg;

	@Override
	@Transactional
	public CommRespDto paymentApplyQuick(PayAlyInfoDto payAlyInfoDto) {
		return paymentGenProcess(payAlyInfoDto, PaymentType.SHORTCUT_PAYMENT.getValue());
	}

	@Override
	@Transactional
	public CommRespDto paymentApplyBalance(PayAlyInfoDto payAlyInfoDto) {
		return paymentGenProcess(payAlyInfoDto, PaymentType.ACCOUNT_PAYMENT.getValue());
	}

	@Override
	public CommRespDto paymentApplyGateway(PayAlyInfoDto payAlyInfoDto) {
		return paymentGenProcess(payAlyInfoDto, PaymentType.GATEWAY_PAYMENT_RECHARGE.getValue());
	}

	@Override
	public void paymentAlyQckNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException {
		payOrdRecService.paymentNotice(payRltInfoDto);
	}

	@Override
	public void paymentAlyBalNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException {
		payOrdRecService.paymentNotice(payRltInfoDto);
	}

	@Override
	public void paymentAlyGtyNotice(PayRltInfoDto payRltInfoDto) throws MyBusinessCheckException {
		payOrdRecService.paymentNotice(payRltInfoDto);
	}

	/**
	 * 支付通用处理
	 * 
	 * @param payAlyInfoDto
	 * @return
	 */
	private CommRespDto paymentGenProcess(PayAlyInfoDto payAlyInfoDto, String paymentType) {
		CommRespDto thdPtyRsltResp = null;
		TradeOrder tradeOrder = tradeOrderDao.selectByPrimaryKey(payAlyInfoDto.getTradeOrderId());
		Payment paymentOrder = null;
		Long bookId = null;
		PayAlyResultDto thdPtyRslt = null;
		try {
			checkBeforePayment(tradeOrder, payAlyInfoDto);
			// 创建支付订单
			paymentOrder = payOrdRecService.createPaymentOrder(payAlyInfoDto, tradeOrder);
			payAlyInfoDto.setPayCustName(tradeOrder.getPurchaseCustName());
			BeanUtils.copyProperties(tradeOrder, payAlyInfoDto);

			logger.info("[payment order record success orderId is]:" + paymentOrder.getId());
			String bookIdStr = idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
			bookId = Long.parseLong(bookIdStr.substring(8, bookIdStr.length()));

			Position position = isExistAcc(payAlyInfoDto.getPayCustId(), tradeOrder.getProductId());
			if (position == null) {
				// 用户建立账户（份额管理）
				qtaPayOrdOprService.createCustPosAcc(payAlyInfoDto, tradeOrder.getTradeTalAmt(), tradeOrder.getPurchasePortion());
			}
			// 调用第三方进行支付
			payAlyInfoDto.setPaymentId(paymentOrder.getId());
			payAlyInfoDto.setPayAccount(paymentOrder.getPayAccount());
			thdPtyRsltResp = paymentThdService.paymentApply(payAlyInfoDto);
			logger.info("[third party return msg]:" + thdPtyRsltResp.toString());
			// 第三方支付结果处理
			if (!PaymentType.GATEWAY_PAYMENT_RECHARGE.getValue().equals(payAlyInfoDto.getPaymentType())) {
				thdPtyRslt = (PayAlyResultDto) thdPtyRsltResp.getData();
				// TODO 根据支付通道编码，判断是同步应答还是异步应答
				// 更新支付流水表应答状态
				payOrdRecService.recordThdResponse(thdPtyRslt, thdPtyRsltResp.getResIdentifier().getReturnType(), thdPtyRsltResp.getResIdentifier().getReturnMsg());
			}
		} catch (MyBusinessCheckException e) {
			logger.error("payment exception", e);
			return new CommRespDto().fail(e.getErrorCode(), e.getErrMsgKey());
		}

		try {
			// TODO 需要根据渠道路由信息确定是否同步，此处暂写死
			boolean isSyn = !PaymentType.GATEWAY_PAYMENT_RECHARGE.getValue().equals(payAlyInfoDto.getPaymentType());
			if (isSyn) {
				if (ComConst.SUCCESS.equals(thdPtyRsltResp.getResIdentifier().getReturnType())) {
					// 记录分录
					payOrdRecService.recordAccBook(bookId, paymentOrder.getPayCustId(), paymentOrder.getTotalId(), paymentOrder.getPayDate(), tradeOrder.getProductId(), tradeOrder.getProductNane(),
							tradeOrder.getPurchasePortion());
					// 更新业务订单、支付订单状态
					payOrdRecService.updateOrderStatus(thdPtyRslt.getResultInfo(), paymentOrder.getTotalId(), OrderStatus.PAY_SUCCESS.getValue(), paymentOrder.getId(),
							PaymentStatus.SUCCESSFUL.getValue(), thdPtyRslt.getThdPayOrdId());
				} else {
					// TODO 判断是否可对交易订单再次支付，如:因余额不足支付失败，允许再次对交易订单进行支付
					boolean isPayAgain = false;
					if (isPayAgain) {
						// TODO 修改支付订单状态
					} else {
						// 更新业务订单、支付订单状态
						payOrdRecService.updateOrderStatus(thdPtyRsltResp.getResIdentifier().getReturnMsg(), paymentOrder.getTotalId(), OrderStatus.PAY_FAIL.getValue(), paymentOrder.getId(),
								PaymentStatus.FAILURE.getValue(), thdPtyRslt.getThdPayOrdId());
					}
				}
				// 清零产品锁定份额
				qtaTrdOrdOprService.cleanLockQuota(tradeOrder.getProductId(), tradeOrder.getPurchasePortion());
				// 判断产品
				// 发送站内信
				payNotMsg.sendStationMessage(payAlyInfoDto.getPayCustId(), tradeOrder, thdPtyRsltResp);
			} else {
				return thdPtyRsltResp;
			}
		} catch (MyBusinessCheckException e) {
			logger.error(e.getErrorCode() + ":" + e.getErrMsgKey());
			// 记账工具反向处理
			payOrdRecService.recordAccBookReverse(tradeOrder.getId(), paymentOrder.getId(), bookId, tradeOrder.getPurchasePortion(), paymentOrder.getPayDate(), paymentOrder.getPayCustId(),
					paymentOrder.getPayDate(), tradeOrder.getProductId(), tradeOrder.getProductNane(), tradeOrder.getPurchasePortion());
			// 修改订单状态 交易订单-处理中 支付订单-失败
			payOrdRecService.updateOrderStatus(thdPtyRsltResp.getResIdentifier().getReturnMsg(), paymentOrder.getTotalId(), OrderStatus.ORDER_HOLD.getValue(), paymentOrder.getId(),
					PaymentStatus.FAILURE.getValue(), thdPtyRslt.getThdPayOrdId());
			return new CommRespDto().fail(e.getErrorCode(), e.getErrMsgKey());
		} catch (MyBusinessRuntimeException e) {
			logger.error("recordAccBook failed", e);
			// 修改订单状态 交易订单-处理中 支付订单-失败
			payOrdRecService.updateOrderStatus(thdPtyRsltResp.getResIdentifier().getReturnMsg(), paymentOrder.getTotalId(), OrderStatus.ORDER_HOLD.getValue(), paymentOrder.getId(),
					PaymentStatus.FAILURE.getValue(), thdPtyRslt.getThdPayOrdId());
		}

		return thdPtyRsltResp;
	}

	private void checkBeforePayment(TradeOrder tradeOrder, PayAlyInfoDto payAlyInfoDto) throws MyBusinessCheckException {
		String orderStatus = tradeOrder.getOrderStatus();
		if (OrderStatus.PAY_SUCCESS.getValue().equals(orderStatus) || OrderStatus.ORDER_CANCEL.getValue().equals(orderStatus)) {
			throw new MyBusinessCheckException(ErrorCode.BUC003, tradeOrder.getId(), OrderStatus.parseOf(orderStatus).getDisplayName());
		}
		if (!tradeOrder.getAmt().equals(payAlyInfoDto.getTradeTalAmt())) {
			throw new MyBusinessCheckException(ErrorCode.BUC004);
		}
	}

	private Position isExistAcc(String custId, String productId) {
		Map<String, String> condMap = MapUtil.toStrMap("custId", custId, "productId", productId, "positionCustType", PositionCustType.INVENST.getValue());
		Position position = positionDao.selectByBusUniqCond(condMap);
		return position;
	}

}
