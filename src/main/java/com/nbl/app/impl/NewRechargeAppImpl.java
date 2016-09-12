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
import com.nbl.model.Recharge;
import com.nbl.service.manager.app.NewRechargeApp;
import com.nbl.service.manager.dto.RechargeReqDto;
import com.nbl.services.recharge.NewRechargeService;

@Service("newRechargeApp")
public class NewRechargeAppImpl implements NewRechargeApp{
	private static final Logger Logger = LoggerFactory.getLogger(RechargeAppImpl.class);
	
	@Resource
	private NewRechargeService newRechargeService;
	
	@Override
	public List<RechargeReqDto> pageListQueryRecharge(PageVO<RechargeReqDto> pageVO, RechargeReqDto reqDto) {
		PageVO<Recharge> pgVO = new PageVO<Recharge>();	
		Recharge recharge = new Recharge();
		BeanUtils.copyProperties(reqDto, recharge);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<Recharge> list = new ArrayList<Recharge>();
		list.add(recharge);
		pgVO.setList(list);
		
		List<Recharge> rechargeList = newRechargeService.pageListQueryRecharge(pgVO, recharge);
		if(rechargeList !=null && rechargeList.size()>0){
			Logger.info("-------List<Recharge> pageListQueryRecharge---size----:{}:",rechargeList.size());
			List<RechargeReqDto> reqList = new ArrayList<RechargeReqDto>();
			Iterator<Recharge> it = rechargeList.iterator();
			while (it.hasNext()) {
				RechargeReqDto reqDto2 = new RechargeReqDto();
				Recharge cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	@Override
	public int pageCountQueryRecharge(RechargeReqDto reqDto) {
		Recharge Recharge = new Recharge();
		BeanUtils.copyProperties(reqDto, Recharge);
		return newRechargeService.pageCountQueryRecharge(Recharge);
	}

	@Override
	public RechargeReqDto rechargeDetail(String id) {
		Recharge recharge = newRechargeService.rechargeDetail(id);
		RechargeReqDto reqDto = new RechargeReqDto();
		BeanUtils.copyProperties(recharge, reqDto);
		return reqDto;
	}

}
