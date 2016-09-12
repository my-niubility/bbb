package com.nbl.services.account.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.nbl.common.constants.ProjectConstants;
import com.nbl.common.dto.CommRespDto;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.WithDrawDao;
import com.nbl.model.SerialFund;
import com.nbl.model.WithDraw;
import com.nbl.model.vo.WithdrawVo;
import com.nbl.service.business.constant.OrdComOrderByCol;
import com.nbl.service.business.constant.OrderByFlag;
import com.nbl.service.business.constant.WithdrawStatus;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.req.WthdrQryInfoDto;
import com.nbl.service.user.dto.res.SerFundQryRsltItemDto;
import com.nbl.service.user.dto.res.SerialFundQryRsltDto;
import com.nbl.service.user.dto.res.WthdrQryResultDto;
import com.nbl.service.user.dto.res.WthdrQryRltItemDto;
import com.nbl.services.account.WthdrQryService;
import com.nbl.util.DateTimeUtils;
import com.nbl.utils.BeanParseUtils;

@Service("wthdrQryService")
public class WthdrQryServiceImpl implements WthdrQryService {

	private final static Logger logger = LoggerFactory.getLogger(WthdrQryServiceImpl.class);

	@Resource
	private WithDrawDao withDrawDao;

	@Override
	public CommRespDto queryWthdrOrder(WthdrQryInfoDto wthdrQryInfoDto) {
		logger.info("【enter business queryWthdrOrder input params is】:" + wthdrQryInfoDto.toString());
		CommRespDto result = null;
		//交易订单为空，查询多条记录
		if (StringUtils.isEmpty(wthdrQryInfoDto.getOrderId())) {
			WithdrawVo wthdrVo = new WithdrawVo();
			BeanParseUtils.copyProperties(wthdrQryInfoDto, wthdrVo);
			PageVO<WithdrawVo> pageVO = new PageVO<WithdrawVo>();
			int recordNum = wthdrQryInfoDto.getRecordNum() == 0 ? ProjectConstants.RECORD_NUM : wthdrQryInfoDto.getRecordNum();
			pageVO.setStartSize(wthdrQryInfoDto.getStartIndex());
			pageVO.setSize(recordNum);
			
			if (StringUtils.isNotEmpty(wthdrQryInfoDto.getOrderColumn()) && StringUtils.isNotEmpty(wthdrQryInfoDto.getOrderFlag())) {
				StringBuffer orderBy = new StringBuffer();
				orderBy.append(OrdComOrderByCol.parseOf(wthdrQryInfoDto.getOrderColumn()));
				orderBy.append(" ");
				orderBy.append(OrderByFlag.parseOf(wthdrQryInfoDto.getOrderFlag()));
				pageVO.setOrderBy(orderBy.toString());
			}

			List<WithDraw> withdraws = withDrawDao.selectByCustInfo(pageVO, wthdrVo);

			if (withdraws != null && withdraws.size() > 0) {
				List<WthdrQryRltItemDto> list = parseEntityToVo(withdraws);
				result = new CommRespDto().success(new WthdrQryResultDto(list));
			} else {
				logger.warn("[withdraw order is null]");
				result = new CommRespDto().success();
			}
		} else {
			//交易订单不为空，查询某条订单
			WithDraw withDraw = withDrawDao.selectByPrimaryKey(wthdrQryInfoDto.getOrderId());
			List<WithDraw> withDraws = new ArrayList<WithDraw>();
			withDraws.add(withDraw);
			List<WthdrQryRltItemDto> list = parseEntityToVo(withDraws);
			result = new CommRespDto().success(new WthdrQryResultDto(list));
		}

		return result;
	}

	private List<WthdrQryRltItemDto> parseEntityToVo(List<WithDraw> withDraws) {
		List<WthdrQryRltItemDto> list = new ArrayList<WthdrQryRltItemDto>();
		for (WithDraw withDraw : withDraws) {
			WthdrQryRltItemDto newRecharge = new WthdrQryRltItemDto();
			BeanParseUtils.copyPropertiesAmt(withDraw, newRecharge, "amt");
			list.add(newRecharge);
		}
		return list;
	}

	@Override
	public CommRespDto queryWthdrOrderInSerFund(SerFundQryDto serFundQryDto) {
		logger.info("【enter business queryWthdrOrderInSerFund input params is】:" + serFundQryDto.toString());
		CommRespDto result = null;
		WithdrawVo wthdrVo = new WithdrawVo();
		BeanParseUtils.copyProperties(serFundQryDto, wthdrVo);
		PageVO<WithdrawVo> pageVO = new PageVO<WithdrawVo>();
		pageVO.setStartSize(serFundQryDto.getStartIndex());
		pageVO.setSize(serFundQryDto.getRecordNum());
		//设置查询条件为提现成功
		wthdrVo.setStatus(WithdrawStatus.SUCCESSFUL.getValue());
		List<SerialFund> withDraws = withDrawDao.selectSerFndByCustInfo(pageVO, wthdrVo);

		if (withDraws != null && withDraws.size() > 0) {
			List<SerFundQryRsltItemDto> list = parseEntityToSerFndVo(withDraws);
			result = new CommRespDto().success(new SerialFundQryRsltDto(list));
		} else {
			logger.warn("[withdraw order is null]");
			result = new CommRespDto().success();
		}

		return result;
	}

	private List<SerFundQryRsltItemDto> parseEntityToSerFndVo(List<SerialFund> withDraws) {
		List<SerFundQryRsltItemDto> list = new ArrayList<SerFundQryRsltItemDto>();
		for (SerialFund withDraw : withDraws) {
			SerFundQryRsltItemDto newWithDraw = new SerFundQryRsltItemDto();
			BeanParseUtils.copyPropertiesAmt(withDraw, newWithDraw, "benefit", "expend");
			list.add(newWithDraw);
		}
		return list;
	}

	@Override
	public String queryWthdrOrderCount(WthdrQryInfoDto wthdrQryInfoDto) {
		String countStr = null;
		WithdrawVo wthdrVo = new WithdrawVo();
		BeanParseUtils.copyProperties(wthdrQryInfoDto, wthdrVo);
		Integer count = withDrawDao.getWthdrOrderCount(wthdrVo);
		countStr = count == null ? null : Integer.toString(count);
		return countStr;
	}

	@Override
	public CommRespDto queryWthdrOrderInSerFund4Wthdr(SerFundQryDto serFundQryDto) {
		logger.info("【enter business queryWthdrOrderInSerFund4Wthdr input params is】:" + serFundQryDto.toString());
		CommRespDto result = null;
		WithdrawVo wthdrVo = new WithdrawVo();
		BeanParseUtils.copyProperties(serFundQryDto, wthdrVo);
		PageVO<WithdrawVo> pageVO = new PageVO<WithdrawVo>();
		pageVO.setStartSize(serFundQryDto.getStartIndex());
		pageVO.setSize(serFundQryDto.getRecordNum());
		List<SerialFund> withDraws = withDrawDao.selectSerFndByCustInfo4Withdr(pageVO, wthdrVo);

		if (withDraws != null && withDraws.size() > 0) {
			List<SerFundQryRsltItemDto> list = parseEntityToSerFndVo(withDraws);
			result = new CommRespDto().success(new SerialFundQryRsltDto(list));
		} else {
			logger.warn("[withdraw order is null]");
			result = new CommRespDto().success();
		}

		return result;
	}

}
