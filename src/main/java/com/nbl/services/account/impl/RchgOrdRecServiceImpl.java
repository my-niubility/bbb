package com.nbl.services.account.impl;

import java.text.ParseException;
import java.util.Date;

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
import com.nbl.dao.RechargeDao;
import com.nbl.model.Recharge;
import com.nbl.service.business.constant.IdPrefix;
import com.nbl.service.business.constant.RechargePayStatus;
import com.nbl.service.business.constant.RechargeType;
import com.nbl.service.user.dto.req.RechgAlyInfoDto;
import com.nbl.service.user.dto.req.SerialRefDto;
import com.nbl.service.user.dto.res.SerialRefResultDto;
import com.nbl.services.account.RchgOrdRecService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.WorkService;
import com.nbl.services.remote.SerialRefService;
import com.nbl.utils.DateTimeUtils;

@Service("rchgOrdRecService")
public class RchgOrdRecServiceImpl implements RchgOrdRecService {
	private final static Logger logger = LoggerFactory.getLogger(RchgOrdRecServiceImpl.class);

	@Resource
	private IdGeneratorService idGeneratorService;
	@Resource
	private RechargeDao rechargeDao;
	@Resource
	private WorkService workService;
	@Resource
	private SerialRefService serialRefServiceBus;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Recharge createRechargeOrder(RechgAlyInfoDto rechgAlyInfoDto) {
		Recharge recharge = new Recharge();
		BeanUtils.copyProperties(rechgAlyInfoDto, recharge);
		String id = IdPrefix.RECHARGE_ORDER.getValue() + rechgAlyInfoDto.getChannelCode() + idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		recharge.setId(id);
		recharge.setStatus(RechargePayStatus.HANDLING.getValue());
		recharge.setCreateTime(DateTimeUtils.now().toDate());
		recharge.setUpdateTime(DateTimeUtils.now().toDate());

		try {
			recharge.setSettleDate(DateTimeUtils.parseDateTime(workService.getCurrentWorkDate()).toDate());
		} catch (ParseException e) {
			logger.error("parse time exception", e);
		}

		if (RechargeType.OFFLINE.getValue().equals(rechgAlyInfoDto.getRechargeType())) {
			String rechargeCode = IdPrefix.RECHARGE_ORDER.getValue() + rechgAlyInfoDto.getChannelCode() + idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.CU_PK_SEQUENCE);
			recharge.setRechargeCode(rechargeCode);
			recharge.setStatus(RechargePayStatus.SUCCESS_OFFLINE.getValue());
		}

		rechargeDao.insertSelective(recharge);
		return recharge;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void rechgRespProcess(String rechargeSerialNum, String retrunType, String resultInfo, String rechargeId, Date updateTime, String remark) throws MyBusinessCheckException {
		logger.info("[rechgRespProcess] inparams is [rechargeSerialNum]:" + rechargeSerialNum + "[retrunType]:" + retrunType + "[resultInfo]:" + resultInfo + "[rechargeId]:" + rechargeId + "[remark]:"
				+ remark);

		SerialRefDto serialRefDto = new SerialRefDto();
		serialRefDto.setSerialId(rechargeSerialNum);
		serialRefDto.setReturnCode(retrunType);
		serialRefDto.setReturnMessage(resultInfo);
		serialRefDto.setRemark(remark);
		SerialRefResultDto serialResp = serialRefServiceBus.updateSerialRef(serialRefDto);
		String rechargeOrderId = serialResp.getRefId();

		Recharge recharge = new Recharge();
		recharge.setId(rechargeOrderId);
		recharge.setRechargeId(rechargeId);
		recharge.setResultInfo(resultInfo);
		recharge.setRemark(remark);
		recharge.setUpdateTime(updateTime == null ? DateTimeUtils.now().toDate() : updateTime);
		if (ComConst.SUCCESS.equals(retrunType)) {
			recharge.setStatus(RechargePayStatus.SUCCESS.getValue());
		}
		if (ComConst.UNKNOWN.equals(retrunType)) {
			recharge.setStatus(RechargePayStatus.CALLBACK_HANDLING.getValue());
		}
		if (ComConst.ERROR.equals(retrunType)) {
			recharge.setStatus(RechargePayStatus.FAIL.getValue());
		}
		rechargeDao.updateByPrimaryKeySelective(recharge);
	}

}
