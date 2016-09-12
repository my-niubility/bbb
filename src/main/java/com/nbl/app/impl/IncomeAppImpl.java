package com.nbl.app.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.model.TIncome;
import com.nbl.service.manager.app.IncomeApp;
import com.nbl.service.manager.dto.IncomeDto;
import com.nbl.services.trade.IncomeService;

@Service("incomeApp")
public class IncomeAppImpl implements IncomeApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(IncomeAppImpl.class);
	@Resource
	private IncomeService incomeService;
	
	@Override
	public List<IncomeDto> pageListQueryIncome(PageVO<IncomeDto> pageVO, IncomeDto reqDto) {
		PageVO<TIncome> pgVO = new PageVO<TIncome>();	
		TIncome income = new TIncome();
		BeanUtils.copyProperties(reqDto, income);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<TIncome> list = new ArrayList<TIncome>();
		list.add(income);
		pgVO.setList(list);
		
		List<TIncome> incomeList = incomeService.pageListQueryIncome(pgVO, income);
		if(incomeList !=null && incomeList.size()>0){
			Logger.info("-------List<TIncome> pageListQueryTIncome---size----:{}:",incomeList.size());
			List<IncomeDto> reqList = new ArrayList<IncomeDto>();
			Iterator<TIncome> it = incomeList.iterator();
			while (it.hasNext()) {
				IncomeDto reqDto2 = new IncomeDto();
				TIncome cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	@Override
	public int pageCountQueryIncome(IncomeDto reqDto) {
		TIncome income = new TIncome();
		BeanUtils.copyProperties(reqDto, income);
		return incomeService.pageCountQueryIncome(income);
	}

	@Override
	public IncomeDto incomeDetail(String id) {
		TIncome income = incomeService.incomeDetail(id);
		IncomeDto reqDto = new IncomeDto();
		BeanUtils.copyProperties(income, reqDto);
		return reqDto;
	}

}
