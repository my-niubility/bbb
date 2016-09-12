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
import com.nbl.model.BillBalance;
import com.nbl.service.manager.app.BillBalanceApp;
import com.nbl.service.manager.dto.BillBalanceReqDto;
import com.nbl.services.task.BillBalanceService;

/**
 * @author gcs
 * @createdate 2016年7月25日	
 * @version 1.0
 * 对应APP对的业务逻辑
 */

@Service("billBalanceApp")
public class BillBalanceAppImpl implements BillBalanceApp {

	private static final Logger Logger = LoggerFactory.getLogger(BillBalanceAppImpl.class);
	
	@Resource
	private  BillBalanceService billBalanceService;
	
	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.BillBalanceApp#pageListQueryBalance(com.zlebank.common.vo.PageVO, com.zlebank.service.manager.dto.BillBalanceReqDto)
	 */
	@Override
	public List<BillBalanceReqDto> pageListQueryBalance(PageVO<BillBalanceReqDto> pageVO, BillBalanceReqDto reqDto) {
		PageVO<BillBalance> pgVO = new PageVO<BillBalance>();	
		BillBalance balance = new BillBalance();
		BeanUtils.copyProperties(reqDto, balance);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<BillBalance> list = new ArrayList<BillBalance>();
		list.add(balance);
		pgVO.setList(list);
		
		List<BillBalance> balanceList = billBalanceService.pageListQueryBalance(pgVO, balance);
		if(balanceList !=null && balanceList.size()>0){
			Logger.info("-------List<BillBalance> pageListQueryBillBalance---size----:{}:",balanceList.size());
			List<BillBalanceReqDto> reqList = new ArrayList<BillBalanceReqDto>();
			Iterator<BillBalance> it = balanceList.iterator();
			while (it.hasNext()) {
				BillBalanceReqDto reqDto2 = new BillBalanceReqDto();
				BillBalance cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.BillBalanceApp#pageCountQueryBalance(com.zlebank.service.manager.dto.BillBalanceReqDto)
	 */
	@Override
	public int pageCountQueryBalance(BillBalanceReqDto reqDto) {
		BillBalance balance = new BillBalance();
		BeanUtils.copyProperties(reqDto, balance);
		return billBalanceService.pageCountQueryBalance(balance);
	}

	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.BillBalanceApp#BalanceDetail(java.lang.String)
	 */
	@Override
	public BillBalanceReqDto BalanceDetail(Integer id) {
		BillBalance balance = billBalanceService.BalanceDetail(id);
		BillBalanceReqDto reqDto = new BillBalanceReqDto();
		BeanUtils.copyProperties(balance, reqDto);
		return reqDto;
	}

}
