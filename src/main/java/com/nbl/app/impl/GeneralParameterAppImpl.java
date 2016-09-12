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
import com.nbl.model.GeneralParameter;
import com.nbl.service.manager.app.GeneralParameterApp;
import com.nbl.service.manager.dto.GeneralParameterDto;
import com.nbl.services.parameter.GeneralParameterService;
/**
 * @author gcs
 * @createdate 2016年7月27日	
 * @version 1.0
 * 给参数配置添加实现接口类
 */
@Service("generalParameterApp")
public class GeneralParameterAppImpl implements GeneralParameterApp {
	
	private static final Logger Logger = LoggerFactory.getLogger(GeneralParameterAppImpl.class);
	
	@Resource
	private GeneralParameterService generalParameterService;
	
	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.GeneralParameterApp#pageListQueryGenParameter(com.zlebank.common.vo.PageVO, com.zlebank.service.manager.dto.GeneralParameterDto)
	 */
	@Override
	public List<GeneralParameterDto> pageListQueryGenParameter(PageVO<GeneralParameterDto> pageVO,
			GeneralParameterDto reqDto) {
		PageVO<GeneralParameter> pgVO = new PageVO<GeneralParameter>();	
		GeneralParameter genParameter = new GeneralParameter();
		BeanUtils.copyProperties(reqDto, genParameter);
		BeanUtils.copyProperties(pageVO, pgVO);
		List<GeneralParameter> list = new ArrayList<GeneralParameter>();
		list.add(genParameter);
		pgVO.setList(list);
		
		List<GeneralParameter> GeneralParameterList = generalParameterService.pageListQueryGenParameter(pgVO, genParameter);
		if(GeneralParameterList !=null && GeneralParameterList.size()>0){
			Logger.info("-------List<GeneralParameter> pageListQueryGeneralParameter---size----:{}:",GeneralParameterList.size());
			List<GeneralParameterDto> reqList = new ArrayList<GeneralParameterDto>();
			Iterator<GeneralParameter> it = GeneralParameterList.iterator();
			while (it.hasNext()) {
				GeneralParameterDto reqDto2 = new GeneralParameterDto();
				GeneralParameter cpReq = it.next();
				BeanUtils.copyProperties(cpReq, reqDto2);
				reqList.add(reqDto2);
			}
			return reqList;
		}else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.GeneralParameterApp#pageCountQueryGenParameter(com.zlebank.service.manager.dto.GeneralParameterDto)
	 */
	@Override
	public int pageCountQueryGenParameter(GeneralParameterDto reqDto) {
		GeneralParameter genParameter = new GeneralParameter();
		BeanUtils.copyProperties(reqDto, genParameter);
		return generalParameterService.pageCountQueryGenParameter(genParameter);
	}

	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.GeneralParameterApp#generalParameterModify(java.lang.Integer)
	 */
	@Override
	public GeneralParameterDto generalParameterDetail(Integer id) {
		GeneralParameter positive = generalParameterService.generalParameterDetail(id);
		GeneralParameterDto reqDto = new GeneralParameterDto();
		BeanUtils.copyProperties(positive, reqDto);
		return reqDto;
	}

	/* (non-Javadoc)
	 * @see com.zlebank.service.manager.app.GeneralParameterApp#generalParameterModify(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean generalParameterModify(Integer id, String code, String parName, String parValue, String reMark) {
		return generalParameterService.generalParameterModify(id, code, parName, parValue, reMark);
	}

	@Override
	public String getValueByCode(String code) {
		return generalParameterService.getValueByCode(code);
	}


}
