package com.nbl.services.account.impl;

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
import com.nbl.dao.WithDrawDao;
import com.nbl.model.WithDraw;
import com.nbl.service.business.constant.IdPrefix;
import com.nbl.service.business.constant.WithdrawPayStatus;
import com.nbl.service.business.constant.WithdrawStatus;
import com.nbl.service.user.dto.req.SerialRefDto;
import com.nbl.service.user.dto.req.WthdwAlyInfoDto;
import com.nbl.service.user.dto.res.SerialRefResultDto;
import com.nbl.services.account.WthOrdRecService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.WorkService;
import com.nbl.services.remote.SerialRefService;
import com.nbl.utils.DateTimeUtils;

@Service("wthOrdRecService")
public class WthOrdRecServiceImpl implements WthOrdRecService {

	private final static Logger logger = LoggerFactory.getLogger(WthOrdRecServiceImpl.class);

	@Resource
	private IdGeneratorService idGeneratorAppService;
	@Resource
	private WithDrawDao withDrawDao;
	@Resource
	private WorkService workService;
	@Resource
	private SerialRefService serialRefServiceBus;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void wthdwRespProcess(String wthdrwSerialNum, String retrunType, String resultInfo, String withdrawId, Date updateTime, String remark) throws MyBusinessCheckException {
		logger.info("wthdrwSerialNum:" + wthdrwSerialNum + "|" + "resultInfo:" + resultInfo + "|" + "withdrawId:" + withdrawId + "|updateTime:" + updateTime.toString() + "|remark:" + remark);

		SerialRefDto serialRefDto = new SerialRefDto();
		serialRefDto.setSerialId(wthdrwSerialNum);
		serialRefDto.setReturnCode(retrunType);
		serialRefDto.setReturnMessage(resultInfo);
		serialRefDto.setRemark(remark);
		SerialRefResultDto serialResp = serialRefServiceBus.updateSerialRef(serialRefDto);
		String withdrawOrderId = serialResp.getRefId();

		WithDraw withDraw = new WithDraw();
		withDraw.setId(withdrawOrderId);
		withDraw.setResultInfo(resultInfo);
		withDraw.setWithdrawId(withdrawId);
		withDraw.setUpdateTime(updateTime == null ? DateTimeUtils.now().toDate() : updateTime);
		withDraw.setRemark(remark);

		if (ComConst.SUCCESS.equals(retrunType)) {
			withDraw.setStatus(WithdrawPayStatus.ACCEPT.getValue());
		}
		if (ComConst.UNKNOWN.equals(retrunType)) {
			withDraw.setStatus(WithdrawPayStatus.BANKING.getValue());
		}
		if (ComConst.ERROR.equals(retrunType)) {
			withDraw.setStatus(WithdrawPayStatus.FAIL.getValue());
		}

		withDrawDao.updateByPrimaryKeySelective(withDraw);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public WithDraw createWithdrawOrder(WthdwAlyInfoDto wthdwAlyInfoDto) {
		WithDraw withDraw = new WithDraw();
		BeanUtils.copyProperties(wthdwAlyInfoDto, withDraw);
		String id = IdPrefix.WITHDRAW_ORDER.getValue() + wthdwAlyInfoDto.getChannelCode() + idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		withDraw.setId(id);
		withDraw.setStatus(WithdrawStatus.WAITING.getValue());
		withDraw.setCreateTime(DateTimeUtils.now().toDate());
		withDraw.setUpdateTime(DateTimeUtils.now().toDate());
		withDraw.setSettleDate(workService.getCurrentWorkDate());

		withDrawDao.insertSelective(withDraw);
		return withDraw;
	}

}
