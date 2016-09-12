package com.nbl.services.product.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.AccountBookDao;
import com.nbl.model.AccountBook;
import com.nbl.model.vo.AccountBookVo;
import com.nbl.service.business.dto.req.ChargeReqDto;
import com.nbl.service.business.dto.res.ChargeResDto;
import com.nbl.services.product.ChargeService;

@Service("chargeService")
public class ChargeServiceImpl implements ChargeService {
	
	private final static Logger logger = LoggerFactory.getLogger(ChargeServiceImpl.class);
	
	@Resource
	public AccountBookDao accountBookDao;

	@Override
	public List<ChargeResDto> pageListQueryCharge(PageVO<ChargeReqDto> pageVO, ChargeReqDto reqDto) {
		
		List<AccountBookVo> list = new ArrayList<AccountBookVo>();
		AccountBookVo accountBookVo = new AccountBookVo();
		//copy accountbook
		BeanUtils.copyProperties(reqDto, accountBookVo);
		PageVO<AccountBookVo> pgVo = new PageVO<AccountBookVo>();
		//copy pageVo
		BeanUtils.copyProperties(pageVO, pgVo,new String[]{"list"});
		list.add(accountBookVo);
		pgVo.setList(list);
		//调用数据层DAO
		List<AccountBook> queryList = accountBookDao.findListPage(pgVo, accountBookVo);
		
		logger.info("管理平台调用开始，查询结果小大为："+queryList.size());
		
		if(queryList!=null && queryList.size()>0){
			
			List<ChargeResDto> retList = new ArrayList<ChargeResDto>();
			
			for(AccountBook ac : queryList){
				ChargeResDto retDto = new ChargeResDto();
				//copy
				BeanUtils.copyProperties(ac, retDto);
				logger.info("结果集ChargeResDto.bookId："+retDto.getBookId());
				retList.add(retDto);			
			}
			return retList;			
		}else{
			return null;
		}
	}

	@Override
	public int pageCountQueryCharge(ChargeReqDto reqDto) {
		
		AccountBookVo accbook = new AccountBookVo();
		//copy
		BeanUtils.copyProperties(reqDto, accbook);
		
		return accountBookDao.findListPageCount(accbook);
	}

	@Override
	public ChargeResDto detailQueryCharge(String chargeId) {
		
		AccountBook accbook = accountBookDao.selectByPrimaryKey(Integer.valueOf(chargeId));
		
		ChargeResDto resDto = new ChargeResDto();
		//copy
		BeanUtils.copyProperties(accbook, resDto);
		
		return resDto;
	}

	
	

}
