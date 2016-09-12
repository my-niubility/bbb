package com.nbl.services.parameter.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.GeneralParameterDao;
import com.nbl.model.GeneralParameter;
import com.nbl.services.parameter.GeneralParameterService;

/**
 * @author gcs
 * @createdate 2016年7月27日
 * @version 1.0 busiess层的实现接口类
 */

@Service("generalParameterService")
public class GeneralParameterServiceImpl implements GeneralParameterService {

	private static final Logger Logger = LoggerFactory.getLogger(GeneralParameterServiceImpl.class);

	@Resource
	private GeneralParameterDao genParameterDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zlebank.services.parameter.GeneralParameterService#
	 * pageListQueryGenParameter(com.zlebank.common.vo.PageVO,
	 * com.zlebank.model.GeneralParameter)
	 */
	@Override
	public List<GeneralParameter> pageListQueryGenParameter(PageVO<GeneralParameter> pageVO, GeneralParameter parameter) {
		return genParameterDao.findListPage(pageVO, parameter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zlebank.services.parameter.GeneralParameterService#
	 * pageCountQueryGenParameter(com.zlebank.model.GeneralParameter)
	 */
	@Override
	public int pageCountQueryGenParameter(GeneralParameter parameter) {
		return genParameterDao.findListPageCount(parameter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zlebank.services.parameter.GeneralParameterService#
	 * generalParameterModify(java.lang.String)
	 */
	@Override
	public GeneralParameter generalParameterDetail(Integer id) {
		return genParameterDao.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zlebank.services.parameter.GeneralParameterService#
	 * generalParameterModify(java.lang.Integer, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean generalParameterModify(Integer id, String code, String parName, String parValue, String reMark) {
		int num = genParameterDao.modifyParameter(id, code, parName, parValue, new Date(), reMark);

		Logger.info("-----generalParameterModify---row--:{}", new Date());
		if (num > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getValueByCode(String code) {
		PageVO<GeneralParameter> pageVO = new PageVO<GeneralParameter>();
		pageVO.setStartSize(0);
		pageVO.setSize(1);
		GeneralParameter condition = new GeneralParameter();
		condition.setCode(code);
		return genParameterDao.findListPage(pageVO, condition).get(0).getParValue();
	}

}
