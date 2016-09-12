package com.nbl.services.risk.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.nbl.common.vo.PageVO;
import com.nbl.dao.RiskBlackWhiteDao;
import com.nbl.model.RiskBlackWhite;
import com.nbl.services.risk.RiskBlackWhiteService;
/**
 * @author gcs
 * @createdate 2016年8月3日	
 * @version 1.0
 * 对应business层黑白名单业务处理逻辑
 */

@Service("riskBlackWhiteService")
public class RiskBlackWhiteServiceImpl implements RiskBlackWhiteService {
	
	@Resource
	private RiskBlackWhiteDao riskBlackWhiteDao;
	
	@Override
	public List<RiskBlackWhite> pageListQueryBlackWhite(PageVO<RiskBlackWhite> pageVO, RiskBlackWhite blackWhite) {
		return riskBlackWhiteDao.findListPage(pageVO, blackWhite);
	}

	@Override
	public int pageCountQueryBlackWhite(RiskBlackWhite blackWhite) {
		return riskBlackWhiteDao.findListPageCount(blackWhite);
	}

}
