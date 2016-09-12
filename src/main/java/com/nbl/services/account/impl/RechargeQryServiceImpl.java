package com.nbl.services.account.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.nbl.common.constants.ComConst;
import com.nbl.common.constants.ProjectConstants;
import com.nbl.common.dto.CommRespDto;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.RechargeDao;
import com.nbl.model.Recharge;
import com.nbl.model.SerialFund;
import com.nbl.model.vo.RechargeVo;
import com.nbl.service.business.constant.OrdComOrderByCol;
import com.nbl.service.business.constant.OrderByFlag;
import com.nbl.service.business.constant.RechargePayStatus;
import com.nbl.service.user.dto.req.RchgQryInfoDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.res.RchgQryResultDto;
import com.nbl.service.user.dto.res.RchgQryRltItemDto;
import com.nbl.service.user.dto.res.SerFundQryRsltItemDto;
import com.nbl.service.user.dto.res.SerialFundQryRsltDto;
import com.nbl.services.account.RechargeQryService;
import com.nbl.utils.BeanParseUtils;

/**
 * @author AlanMa
 *
 */
@Service("rechargeQryService")
public class RechargeQryServiceImpl implements RechargeQryService {

	private final static Logger logger = LoggerFactory.getLogger(RechargeQryServiceImpl.class);

	@Resource
	private RechargeDao rechargeDao;

	@Override
	public CommRespDto queryRechgOrder(RchgQryInfoDto rchgQryInfoDto) {
		logger.info("【enter business queryRechgOrder input params is】:" + rchgQryInfoDto.toString());
		CommRespDto result = null;
		if (StringUtils.isEmpty(rchgQryInfoDto.getOrderId())) {
			RechargeVo rchgVo = new RechargeVo();
			BeanParseUtils.copyProperties(rchgQryInfoDto, rchgVo);
			PageVO<RechargeVo> pageVO = new PageVO<RechargeVo>();
			int recordNum = rchgQryInfoDto.getRecordNum() == 0 ? ProjectConstants.RECORD_NUM : rchgQryInfoDto.getRecordNum();
			pageVO.setStartSize(rchgQryInfoDto.getStartIndex());
			pageVO.setSize(recordNum);

			if (StringUtils.isNotEmpty(rchgQryInfoDto.getOrderColumn()) && StringUtils.isNotEmpty(rchgQryInfoDto.getOrderFlag())) {
				StringBuffer orderBy = new StringBuffer();
				orderBy.append(OrdComOrderByCol.parseOf(rchgQryInfoDto.getOrderColumn()));
				orderBy.append(" ");
				orderBy.append(OrderByFlag.parseOf(rchgQryInfoDto.getOrderFlag()));
				pageVO.setOrderBy(orderBy.toString());
			}

			List<Recharge> recharges = rechargeDao.selectByCustInfo(pageVO, rchgVo);

			if (recharges != null && recharges.size() > 0) {
				List<RchgQryRltItemDto> list = parseEntityToVo(recharges);
				result = new CommRespDto().success(new RchgQryResultDto(list));
			} else {
				logger.warn("[recharge order is null]");
				result = new CommRespDto().success();
			}
		} else {
			Recharge recharge = rechargeDao.selectByPrimaryKey(rchgQryInfoDto.getOrderId());
			List<Recharge> recharges = new ArrayList<Recharge>();
			recharges.add(recharge);
			List<RchgQryRltItemDto> list = parseEntityToVo(recharges);
			result = new CommRespDto().success(new RchgQryResultDto(list));
		}

		return result;
	}

	private List<RchgQryRltItemDto> parseEntityToVo(List<Recharge> recharges) {
		List<RchgQryRltItemDto> list = new ArrayList<RchgQryRltItemDto>();
		for (Recharge recharge : recharges) {
			RchgQryRltItemDto newRecharge = new RchgQryRltItemDto();
			BeanParseUtils.copyPropertiesAmt(recharge, newRecharge, "amt");
			list.add(newRecharge);
		}
		return list;
	}

	@Override
	public CommRespDto queryRechgOrderInSerFund(SerFundQryDto serFundQryDto) {
		logger.info("【enter business queryRechgOrderInSerFund input params is】:" + serFundQryDto.toString());
		CommRespDto result = null;

		RechargeVo rchgVo = new RechargeVo();
		BeanParseUtils.copyProperties(serFundQryDto, rchgVo);
		PageVO<RechargeVo> pageVO = new PageVO<RechargeVo>();
		pageVO.setStartSize(serFundQryDto.getStartIndex());
		pageVO.setSize(serFundQryDto.getRecordNum());
		//设置查询条件为充值成功
		rchgVo.setStatus(RechargePayStatus.SUCCESS.getValue());
		List<SerialFund> recharges = rechargeDao.selectSerFndByCustInfo(pageVO, rchgVo);

		if (recharges != null && recharges.size() > 0) {
			List<SerFundQryRsltItemDto> list = parseEntityToSerFndVo(recharges);
			result = new CommRespDto().success(new SerialFundQryRsltDto(list));
		} else {
			logger.warn("[recharge order is null]");
			result = new CommRespDto().success();
		}

		return result;
	}

	private List<SerFundQryRsltItemDto> parseEntityToSerFndVo(List<SerialFund> recharges) {
		List<SerFundQryRsltItemDto> list = new ArrayList<SerFundQryRsltItemDto>();
		for (SerialFund recharge : recharges) {
			SerFundQryRsltItemDto newRecharge = new SerFundQryRsltItemDto();
			BeanParseUtils.copyPropertiesAmt(recharge, newRecharge, "benefit", "expend");
			list.add(newRecharge);
		}
		return list;
	}

	@Override
	public String queryRchgOrderCount(RchgQryInfoDto rchgQryInfoDto) {
		String countStr = null;
		RechargeVo rchgVo = new RechargeVo();
		BeanParseUtils.copyProperties(rchgQryInfoDto, rchgVo);
		Integer count = rechargeDao.getRchgOrderCount(rchgVo);
		countStr = count == null ? null : Integer.toString(count);
		return countStr;
	}
}