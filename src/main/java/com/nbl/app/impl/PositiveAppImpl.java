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
import com.nbl.model.AccountReverse;
import com.nbl.service.manager.app.PositiveApp;
import com.nbl.service.manager.dto.AccountReverseDto;
import com.nbl.services.portion.PositiveService;

@Service("positiveApp")
public class PositiveAppImpl implements PositiveApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(PositiveAppImpl.class);
	
	@Resource
	private PositiveService positiveService;

	@Override
	public List<AccountReverseDto> pageListQueryPositive(PageVO<AccountReverseDto> pageVO, AccountReverseDto reqDto) {
		PageVO<AccountReverse> pgVO = new PageVO<AccountReverse>();	
		AccountReverse positive = new AccountReverse();
		BeanUtils.copyProperties(reqDto, positive);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<AccountReverse> list = new ArrayList<AccountReverse>();
		list.add(positive);
		pgVO.setList(list);
		
		List<AccountReverse> positiveList = positiveService.pageListQueryPositive(pgVO, positive);
		if(positiveList !=null && positiveList.size()>0){
			Logger.info("-------List<AccountReverse> pageListQueryAccountReverse---size----:{}:",positiveList.size());
			List<AccountReverseDto> reqList = new ArrayList<AccountReverseDto>();
			Iterator<AccountReverse> it = positiveList.iterator();
			while (it.hasNext()) {
				AccountReverseDto reqDto2 = new AccountReverseDto();
				AccountReverse cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	@Override
	public int pageCountQueryPositive(AccountReverseDto reqDto) {
		AccountReverse positive = new AccountReverse();
		BeanUtils.copyProperties(reqDto, positive);
		return positiveService.pageCountQueryPositive(positive);
	}

	@Override
	public AccountReverseDto positiveDetail(Integer reverseId) {
		AccountReverse positive = positiveService.positiveDetail(reverseId);
		AccountReverseDto reqDto = new AccountReverseDto();
		BeanUtils.copyProperties(positive, reqDto);
		return reqDto;
	}

}
