package com.nbl.services.account.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.model.WithDraw;
import com.nbl.service.user.dto.req.WithdrawAlyThdInfoDto;
import com.nbl.service.user.dto.req.WthdwAlyInfoDto;
import com.nbl.service.user.dto.req.WthdwNoticeDto;
import com.nbl.service.user.dto.res.WthdrwAlyThdResultDto;
import com.nbl.service.user.dto.res.WthdwAlyResultDto;
import com.nbl.services.account.WithdrawService;
import com.nbl.services.account.WthOrdRecService;
import com.nbl.services.remote.WithdrawThdService;
import com.nbl.services.sendmsg.StaMsgSender;
import com.nbl.utils.BeanParseUtils;

/**
 * 提现服务
 * 
 * @author AlanMa
 *
 */
@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {
	private final static Logger logger = LoggerFactory.getLogger(WithdrawServiceImpl.class);

	@Resource
	private WithdrawThdService withdrawThdService;
	@Resource
	private WthOrdRecService wthOrdRecService;
	@Resource
	private StaMsgSender wthdrNotMsg;

	@Override
	public CommRespDto withDrawApply(WthdwAlyInfoDto wthdwAlyInfoDto) {
		logger.info("【enter business withDrawApply input params is】:" + wthdwAlyInfoDto.toString());
		// 创建提现订单
		WithDraw withDraw = wthOrdRecService.createWithdrawOrder(wthdwAlyInfoDto);

		// 调用第三方支付，发送提现申请
		WithdrawAlyThdInfoDto wthdrwReq = new WithdrawAlyThdInfoDto();
		BeanParseUtils.copyProperties(wthdwAlyInfoDto, wthdrwReq);
		wthdrwReq.setWithdrawOrderId(withDraw.getId());

		logger.info("【withdrawThdService.withdrawApply input params is】:" + wthdrwReq.toString());
		CommRespDto commRespDto = withdrawThdService.withdrawApply(wthdrwReq);

		WthdrwAlyThdResultDto thdPtyRslt = (WthdrwAlyThdResultDto) commRespDto.getData();

		WthdwAlyResultDto resp = new WthdwAlyResultDto();
		BeanParseUtils.copyProperties(thdPtyRslt, resp);
		resp.setCustId(wthdwAlyInfoDto.getCustId());
		resp.setId(withDraw.getId());
		resp.setUpdateDateTime(thdPtyRslt.getUpdateTime().toString());

		logger.info("[third party return msg]:" + thdPtyRslt.toString());
		// 更新提現订单
		try {
			wthOrdRecService.wthdwRespProcess(thdPtyRslt.getSerailNum(), commRespDto.getResIdentifier().getReturnType(), thdPtyRslt.getResultInfo(), thdPtyRslt.getWithdrawId(),
					thdPtyRslt.getUpdateTime(), thdPtyRslt.getRemark());
		} catch (MyBusinessCheckException e) {
			logger.error("【wthdwRespProcess exception】", e);
			return new CommRespDto().fail(e.getErrorCode(), e.getErrMsgKey());
		}

		logger.info("[update withdraw order success]");

		// 发送站内信
		wthdrNotMsg.sendStationMessage(wthdwAlyInfoDto.getCustId(), withDraw, commRespDto);

		return commRespDto;
	}

	@Override
	public void rechargeNotice(WthdwNoticeDto rchgNoticeDto) throws MyBusinessCheckException {
		wthOrdRecService.wthdwRespProcess(rchgNoticeDto.getId(), rchgNoticeDto.getReturnType(), rchgNoticeDto.getResultInfo(), rchgNoticeDto.getWithdrawId(), rchgNoticeDto.getUpdateTime(),
				rchgNoticeDto.getRemark());
	}

}
