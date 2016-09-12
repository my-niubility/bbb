package com.nbl.services.trade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nbl.common.constants.ComConst;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.TIncomeDao;
import com.nbl.model.TIncome;
import com.nbl.model.vo.IncomeVo;
import com.nbl.model.vo.TIncOrdVo;
import com.nbl.service.business.constant.IncomeType;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.res.SerFundQryRsltItemDto;
import com.nbl.services.trade.IncomeService;
import com.nbl.util.AmtParseUtil;
import com.nbl.utils.BeanParseUtils;

@Service("incomeService")
public class IncomeServiceImpl implements IncomeService {

	@Resource
	private TIncomeDao incomeDao;

	@Override
	public List<TIncome> pageListQueryIncome(PageVO<TIncome> pageVO, TIncome income) {
		return incomeDao.findListPage(pageVO, income);
	}

	@Override
	public int pageCountQueryIncome(TIncome income) {
		return incomeDao.findListPageCount(income);
	}

	@Override
	public TIncome incomeDetail(String id) {
		return incomeDao.selectById(id);
	}

	@Override
	public List<SerFundQryRsltItemDto> qryIncomeInFundFlow(SerFundQryDto serFundQryDto) {
		List<SerFundQryRsltItemDto> result = new ArrayList<SerFundQryRsltItemDto>();
		IncomeVo incomeVo = new IncomeVo();
		BeanUtils.copyProperties(serFundQryDto, incomeVo);
		//目前没用，先注释
	/*	List<String> incomeTypes = new ArrayList<String>();
		incomeTypes.add(IncomeType.INVEST.getValue());
		incomeTypes.add(IncomeType.TRANSFER_OUT.getValue());
		incomeTypes.add(IncomeType.TRANSFER.getValue());
		incomeTypes.add(IncomeType.COLLECT_TERM_INTEREST.getValue());
		incomeVo.setIncomeTypes(incomeTypes);*/
		incomeVo.setIsFinish(ComConst.TRUE);
		incomeVo.setEnabled(ComConst.TRUE);
		PageVO<IncomeVo> pageVO = new PageVO<IncomeVo>();
		pageVO.setStartSize(serFundQryDto.getStartIndex());
		pageVO.setSize(serFundQryDto.getRecordNum());
		List<TIncOrdVo> incomes = incomeDao.selectIncomeInFundFlow(pageVO, incomeVo);

		if (incomes == null) {
			return null;
		} else {
			for (TIncOrdVo income : incomes) {
				SerFundQryRsltItemDto item = new SerFundQryRsltItemDto();
				BeanParseUtils.copyProperties(income, item);
				item.setBenefit(AmtParseUtil.strToStrAmt(item.getBenefit()));
				result.add(item);
			}
		}
		return result;
	}

	@Override
	public String qryIncomeInFundFlowCount(SerFundQryDto serFundQryDto) {
		IncomeVo incomeVo = new IncomeVo();
		incomeVo.setIsFinish(ComConst.TRUE);
		incomeVo.setEnabled(ComConst.TRUE);
		BeanParseUtils.copyProperties(serFundQryDto, incomeVo);
		Integer count = incomeDao.selectIncomeInFundFlowCount(incomeVo);
		return count == null ? "0" : String.valueOf(count);
	}

}
