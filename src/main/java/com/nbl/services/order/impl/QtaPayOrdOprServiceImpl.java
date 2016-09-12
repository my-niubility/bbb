package com.nbl.services.order.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ComConst;
import com.nbl.common.constants.SeqenceConstant;
import com.nbl.dao.PositionDao;
import com.nbl.dao.ProductCommonDao;
import com.nbl.model.AccountRule;
import com.nbl.model.Position;
import com.nbl.model.ProductCommon;
import com.nbl.service.business.constant.PositionCustType;
import com.nbl.service.business.constant.PositionHoldStatus;
import com.nbl.service.business.constant.PositionInvestType;
import com.nbl.service.business.constant.SubjectType;
import com.nbl.service.business.dto.req.PayAlyInfoDto;
import com.nbl.services.accrule.DealAccService;
import com.nbl.services.order.QtaPayOrdOprService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.utils.DateTimeUtils;

@Service("qtaPayOrdOprService")
public class QtaPayOrdOprServiceImpl implements QtaPayOrdOprService {
	@Resource
	private IdGeneratorService idGeneratorAppService;
	@Resource
	private PositionDao positionDao;
	@Resource
	private ProductCommonDao productCommonDao;
	@Resource
	private DealAccService dealAccService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createCustPosAcc(PayAlyInfoDto payAlyInfoDto, Long tradeTalAmt, Long purchasePortion) {

		String ruleId = dealAccService.getRuleId(ComConst.PURCH_PRD.OPT_CODE, ComConst.PURCH_PRD.PAY_ORDER);
		AccountRule accRule = dealAccService.getAccRule(ruleId);
		ProductCommon prdInfo = productCommonDao.selectByPrimaryKey(payAlyInfoDto.getProductId());

		Position position = new Position();
		BeanUtils.copyProperties(payAlyInfoDto, position);

		String positionId = idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_CA_SEQUENCE);
		position.setId(positionId);
		position.setCustId(payAlyInfoDto.getPayCustId());
		position.setCustName(payAlyInfoDto.getPayCustName());
		position.setPositionCustType(PositionCustType.INVENST.getValue());
		position.setSubjectNo(accRule.getPayeeSubjectNo());
		position.setSubjectType(SubjectType.DEBT.getValue());
		position.setUnitCost(prdInfo.getUnitCost());
		position.setInvenstAmt(payAlyInfoDto.getTradeTalAmt());
		position.setHoldStatus(PositionHoldStatus.HOLD.getValue());
		position.setInvestType(PositionInvestType.INVEST.getValue());
		position.setCreateTime(DateTimeUtils.now().toDate());
		position.setInitialPortion(new Long(0));
		position.setPossessPortion(new Long(0));
		position.setRemainTerms(prdInfo.getRemainTerms());
		position.setUpdateTime(DateTimeUtils.now().toDate());
		positionDao.insertSelective(position);
	}

}
