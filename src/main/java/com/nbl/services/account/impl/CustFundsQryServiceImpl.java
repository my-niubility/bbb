package com.nbl.services.account.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.common.dto.CommRespDto;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.PositionDao;
import com.nbl.dao.TIncomeDao;
import com.nbl.model.Position;
import com.nbl.model.TIncome;
import com.nbl.model.vo.IncomeVo;
import com.nbl.model.vo.PositionVo;
import com.nbl.service.business.constant.IncomeIsFinish;
import com.nbl.service.business.constant.IncomeType;
import com.nbl.service.business.constant.PositionHoldStatus;
import com.nbl.service.business.constant.VALIDFlag;
import com.nbl.service.user.dto.res.FundsInfoResultDto;
import com.nbl.services.account.CustFundsQryService;
import com.nbl.util.AmtParseUtil;

@Service("custFundsQryService")
public class CustFundsQryServiceImpl implements CustFundsQryService {

	private final static Logger logger = LoggerFactory.getLogger(CustFundsQryServiceImpl.class);

	@Resource
	private TIncomeDao tIncomeDao;
	@Resource
	private PositionDao positionDao;

	@Override
	public CommRespDto queryCustFunds(String custId) {
		logger.info("【enter queryCustFunds inparams is】:" + custId);
		// 累计收益
		Long accIncome = new Long(0);
		// 待收收益
		Long dueIn = new Long(0);
		// 持有资产
		Long fundInFloat = new Long(0);
		IncomeVo incomeVo = new IncomeVo();
		incomeVo.setCustId(custId);
		List<String> incomeTypes = new ArrayList<String>();
		incomeTypes.add(IncomeType.INVEST.getValue());
		incomeVo.setIncomeTypes(incomeTypes);
		incomeVo.setEnabled(VALIDFlag.VALID.getValue());

		List<TIncome> incomes = tIncomeDao.getTIncomes(incomeVo);
		if (incomes != null) {
			for (TIncome income : incomes) {
				if (IncomeIsFinish.FINISH.getValue().equals(income.getIsFinish())) {
					accIncome += income.getEarning();
				}
				if (IncomeIsFinish.NOTFINISH.getValue().equals(income.getIsFinish())) {
					dueIn += income.getEarning();
				}
			}
		}
		PositionVo positionVo = new PositionVo();
		positionVo.setCustId(custId);
		List<String> holdStatus = new ArrayList<String>();
		holdStatus.add(PositionHoldStatus.HOLD.getValue());
		holdStatus.add(PositionHoldStatus.TRANSFERING.getValue());
		holdStatus.add(PositionHoldStatus.TRANSFERINGWAITING.getValue());
		holdStatus.add(PositionHoldStatus.ONTHEWAY.getValue());
		holdStatus.add(PositionHoldStatus.WAITINGREFUND.getValue());
		positionVo.setHoldStatus(holdStatus);
		positionVo.setInvestType(IncomeType.INVEST.getValue());
		PageVO<PositionVo> pageVo = new PageVO<PositionVo>();
		pageVo.setSize(-1);
		List<Position> positions = positionDao.findListPage(pageVo, positionVo);

		if (positions != null) {
			for (Position position : positions) {
				fundInFloat += (position.getPossessPortion() * position.getUnitCost());
			}
		}
		FundsInfoResultDto funds = new FundsInfoResultDto(AmtParseUtil.longToStrAmt(accIncome), AmtParseUtil.longToStrAmt(dueIn), AmtParseUtil.longToStrAmt(fundInFloat));
		logger.info("【funds info is】:" + funds.toString());

		return new CommRespDto().success(funds);
	}

}
