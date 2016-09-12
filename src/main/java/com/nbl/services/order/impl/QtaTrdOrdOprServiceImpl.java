package com.nbl.services.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nbl.common.constants.ErrorCode;
import com.nbl.common.constants.SeqenceConstant;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.TradeOrderDao;
import com.nbl.model.ProductCommon;
import com.nbl.model.TradeOrder;
import com.nbl.model.vo.PrdBatchUpdVo;
import com.nbl.service.business.constant.IdPrefix;
import com.nbl.service.business.constant.OrderStatus;
import com.nbl.service.business.constant.ProductStatus;
import com.nbl.service.business.dto.req.PrdPchInfoDto;
import com.nbl.services.accrule.DealAccService;
import com.nbl.services.order.QtaTrdOrdOprService;
import com.nbl.services.product.AccountBookingService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.WorkService;
import com.nbl.util.ErrCodeUtil;
import com.nbl.utils.DateTimeUtils;

@Service("qtaTrdOrdOprService")
public class QtaTrdOrdOprServiceImpl implements QtaTrdOrdOprService {

	private final static Logger logger = LoggerFactory.getLogger(QtaTrdOrdOprServiceImpl.class);
	@Resource
	private IdGeneratorService idGeneratorService;
	@Resource
	private TradeOrderDao tradeOrderDao;
	@Resource
	private WorkService workService;
	@Resource
	private DealAccService dealAccService;
	@Resource
	private AccountBookingService accountBookingService;
	@Resource
	private ProductCommonDao productCommonDao;

	@Override
	public void unlockQuota(String productId, Long purchasePortion) throws MyBusinessRuntimeException {
		logger.info("QtaTrdOrdOprService.unLockPrdQuota [productId]:" + productId + " [purchasePortion]:" + purchasePortion);
		ProductCommon product = productCommonDao.selectByPrimaryKeyForUpdate(productId);
		if (product == null) {
			logger.error("query  ElectricProduct by  productId: [" + productId + "] result is empty");
			throw new MyBusinessRuntimeException(ErrCodeUtil.getErrMsgStr(ErrorCode.BUD002, productId));
		}

		if (product.getLockScale() < purchasePortion) {
			throw new MyBusinessRuntimeException(ErrorCode.BUB005);
		}
		product.setLockScale(product.getLockScale() - purchasePortion);
		product.setFinanceScale(product.getFinanceScale() + purchasePortion);

		productCommonDao.updateByPrimaryKeySelective(product);
	}

	@Override
	public TradeOrder createTradeOrder(PrdPchInfoDto prdPchInfoDto) {
		TradeOrder tradeOrder = new TradeOrder();
		BeanUtils.copyProperties(prdPchInfoDto, tradeOrder);
		String tradeOrderId = IdPrefix.TRADE_ORDER.getValue() + prdPchInfoDto.getChannelCode() + idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		tradeOrder.setId(tradeOrderId);
		tradeOrder.setTradeDate(workService.getCurrentWorkDate());
		tradeOrder.setOrderStatus(OrderStatus.TO_PAY.getValue());
		tradeOrder.setCreateTime(DateTimeUtils.now().toDate());
		tradeOrder.setUpdateTime(DateTimeUtils.now().toDate());
		tradeOrderDao.insertSelective(tradeOrder);
		return tradeOrder;
	}

	@Override
	public void lockQuota(String productId, Long purchasePortion) throws MyBusinessCheckException {
		logger.info("QtaTrdOrdOprService.lockPrdQuota [productId]:" + productId + " [purchasePortion]:" + purchasePortion);
		ProductCommon product = productCommonDao.selectByPrimaryKeyForUpdate(productId);
		if (product.getFinanceScale() < purchasePortion) {
			throw new MyBusinessCheckException(ErrorCode.BUB004);
		}
		product.setLockScale(product.getLockScale() + purchasePortion);
		product.setFinanceScale(product.getFinanceScale() - purchasePortion);
		productCommonDao.updateByPrimaryKeySelective(product);
	}

	@Override
	public void cleanLockQuota(String productId, Long purchasePortion) throws MyBusinessCheckException {
		ProductCommon product = productCommonDao.selectByPrimaryKeyForUpdate(productId);
		if (product.getLockScale() < purchasePortion) {
			throw new MyBusinessRuntimeException(ErrorCode.BUB005);
		}
		product.setLockScale(product.getLockScale() - purchasePortion);
		if (product.getLockScale() == 0 && product.getFinanceScale() == 0) {
			product.setProductStatus(ProductStatus.PRODUCT_STATUS04.getValue());
		}
		productCommonDao.updateByPrimaryKeySelective(product);
	}

	@Override
	public int unlockQuota(List<PrdBatchUpdVo> prdBatchUpdVos) {
		return productCommonDao.updateByPrdIdsAndPorts(prdBatchUpdVos);
	}

}
