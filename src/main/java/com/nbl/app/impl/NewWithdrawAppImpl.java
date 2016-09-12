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
import com.nbl.model.WithDraw;
import com.nbl.service.manager.app.NewWithdrawApp;
import com.nbl.service.manager.dto.WithdrawReqDto;
import com.nbl.services.withdraw.NewWithdrawService;
@Service("newWithdrawApp")
public class NewWithdrawAppImpl implements NewWithdrawApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(NewWithdrawAppImpl.class);
	
	@Resource
	private NewWithdrawService newWithdrawService;
	
	@Override
	public List<WithdrawReqDto> pageListQueryWithdraw(PageVO<WithdrawReqDto> pageVO, WithdrawReqDto reqDto) {
		PageVO<WithDraw> pgVO = new PageVO<WithDraw>();	
		WithDraw withdraw = new WithDraw();
		BeanUtils.copyProperties(reqDto, withdraw);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<WithDraw> list = new ArrayList<WithDraw>();
		list.add(withdraw);
		pgVO.setList(list);
		
		List<WithDraw> withdrawList = newWithdrawService.pageListQueryWithDraw(pgVO, withdraw);
		if(withdrawList !=null && withdrawList.size()>0){
			Logger.info("-------List<WithDraw> pageListQueryWithDraw---size----:{}:",withdrawList.size());
			List<WithdrawReqDto> reqList = new ArrayList<WithdrawReqDto>();
			Iterator<WithDraw> it = withdrawList.iterator();
			while (it.hasNext()) {
				WithdrawReqDto reqDto2 = new WithdrawReqDto();
				WithDraw cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	@Override
	public int pageCountQueryWithdraw(WithdrawReqDto reqDto) {
		WithDraw withdraw = new WithDraw();
		BeanUtils.copyProperties(reqDto, withdraw);
		return newWithdrawService.pageCountQueryWithdraw(withdraw);
	}

	@Override
	public WithdrawReqDto withdrawDetail(String id) {
		WithDraw withdraw = newWithdrawService.withdrawDetail(id);
		WithdrawReqDto reqDto = new WithdrawReqDto();
		BeanUtils.copyProperties(withdraw, reqDto);
		return reqDto;
	}

}
