package com.nbl.services.account.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.model.Recharge;
import com.nbl.service.user.dto.req.RchgNoticeDto;
import com.nbl.service.user.dto.req.RechgAlyInfoDto;
import com.nbl.service.user.dto.req.RechgAlyThdInfoDto;
import com.nbl.service.user.dto.res.RechgAlyResultDto;
import com.nbl.service.user.dto.res.RechgAlyThdResultDto;
import com.nbl.services.account.RchgOrdRecService;
import com.nbl.services.account.RechargeService;
import com.nbl.services.remote.RechargeThdService;
import com.nbl.services.sendmsg.StaMsgSender;
import com.nbl.utils.BeanParseUtils;

/**
 * 充值服务
 * 
 * @author AlanMa
 *
 */
@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {
	private final static Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

	@Resource
	private RechargeThdService rechargeThdService;
	@Resource
	private RchgOrdRecService rchgOrdRecService;
	@Resource
	private StaMsgSender rechgNotMsg;

	@Override
	public CommRespDto rechargeApply(RechgAlyInfoDto rechgAlyInfoDto) {
		logger.info("【enter business rechargeApply input params is】:" + rechgAlyInfoDto.toString());
		// 创建充值订单
		Recharge recharge = rchgOrdRecService.createRechargeOrder(rechgAlyInfoDto);

		// 调用第三方支付，发送充值申请
		RechgAlyThdInfoDto rechgReq = new RechgAlyThdInfoDto();
		BeanParseUtils.copyProperties(rechgAlyInfoDto, rechgReq);
		rechgReq.setRechargeOrderId(recharge.getId());

		logger.info("【rechargeThdService.rechargeApply input params is】:" + rechgReq.toString());
		CommRespDto commRespDto = rechargeThdService.rechargeApply(rechgReq);

		RechgAlyThdResultDto thdPtyRslt = (RechgAlyThdResultDto) commRespDto.getData();

		RechgAlyResultDto resp = new RechgAlyResultDto();
		BeanParseUtils.copyProperties(thdPtyRslt, resp);
		resp.setCustId(rechgAlyInfoDto.getCustId());
		resp.setId(recharge.getId());
		resp.setUpdateDateTime(thdPtyRslt.getUpdateTime().toString());

		logger.info("[third party return msg]:" + thdPtyRslt.toString());
		// 更新充值订单
		try {
			rchgOrdRecService.rechgRespProcess(thdPtyRslt.getSerailNum(), commRespDto.getResIdentifier().getReturnType(), thdPtyRslt.getResultInfo(), thdPtyRslt.getRechargeId(),
					thdPtyRslt.getUpdateTime(), thdPtyRslt.getRemark());
		} catch (MyBusinessCheckException e) {
			logger.error("【rechgRespProcess exception】", e);
			return new CommRespDto().fail(e.getErrorCode(), e.getErrMsgKey());
		}

		logger.info("[update recharge order success]");
		// 发送站内信
		rechgNotMsg.sendStationMessage(rechgAlyInfoDto.getCustId(), recharge,commRespDto);

		return commRespDto;
	}

	@Override
	public void rechargeNotice(RchgNoticeDto rchgNoticeDto) throws MyBusinessCheckException {
		rchgOrdRecService.rechgRespProcess(rchgNoticeDto.getSerailNum(), rchgNoticeDto.getReturnType(), rchgNoticeDto.getResultInfo(), rchgNoticeDto.getRechargeId(), rchgNoticeDto.getUpdateTime(),
				rchgNoticeDto.getRemark());
	}

}
