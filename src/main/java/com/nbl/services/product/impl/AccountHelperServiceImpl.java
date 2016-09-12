package com.nbl.services.product.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.dao.ProductCommonDao;
import com.nbl.model.AccountBook;
import com.nbl.model.AccountReverse;
import com.nbl.model.Position;
import com.nbl.model.ProductCommon;
import com.nbl.model.TradeBill;
import com.nbl.model.vo.AccountBookingVo;
import com.nbl.service.business.constant.DebitCredtFlag;
import com.nbl.service.business.constant.RepayMode;
import com.nbl.service.business.constant.SubjectType;
import com.nbl.services.product.AccountHelperService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.ProductService;
import com.nbl.services.product.RateCountUtilService;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :记账服务帮助接口实现类
 */
@Service("accountHelperService")
public class AccountHelperServiceImpl implements AccountHelperService {

	private static Logger logger = LoggerFactory.getLogger(AccountHelperServiceImpl.class);

	@Resource
	private IdGeneratorService idGeneratorService;

	@Resource
	private ProductService productService;

	@Resource
	private ProductCommonDao productCommonDao;

	@Resource
	private RateCountUtilService rateCountUtilService;

	@Override
	public long calAccountAmount(long accountAmount, String subjectType, long transAmount, String drCrFlag) {
		long accountLeftAmt = 0;
		if (subjectType.equals(SubjectType.ASSETS.getValue())) {
			if (drCrFlag.equals(DebitCredtFlag.FLAG_DR.getValue())) {
				// 如果是借记账户，记账是借，账户余额=账户原金额+交易金额
				accountLeftAmt = accountAmount + transAmount;
			} else {
				// 如果是借记账户，记账是贷，账户余额=账户原金额-交易金额
				accountLeftAmt = accountAmount - transAmount;
			}
		} else {
			if (drCrFlag.equals(DebitCredtFlag.FLAG_DR.getValue())) {
				// 如果是贷记账户，记账是借，账户余额=账户原金额-交易金额
				accountLeftAmt = accountAmount - transAmount;
			} else {
				// 如果是贷记账户，记账是贷，账户余额=账户原金额+交易金额
				accountLeftAmt = accountAmount + transAmount;
			}
		}
		return accountLeftAmt;
	}

	@Override
	public long calAccountEarning(long accountPortion, String productId) {
		Map<String, String> retMap = this.getProductMessage(productId);

		long investAmout = new BigDecimal(accountPortion).multiply(new BigDecimal(retMap.get("unitCost"))).longValue();
		Long investPeriodDay = null;
		Long investPeriodMonth = null;

		if (StringUtils.isNotEmpty(retMap.get("financeTerm"))) {
			// 基金产品融资期限为空
			investPeriodDay = Long.parseLong(retMap.get("financeTerm")); // 融资期限-天
			investPeriodMonth = investPeriodDay / 30;// 融资期限 -月
		} else {
			// 基金默认投资期限为1天
			investPeriodDay = new Long(1);
		}

		BigDecimal yearRate = new BigDecimal(retMap.get("expectEarnRate")); // 年华收益率
		String repayType = retMap.get("repayMode");// 还款方式

		long expectEarning = rateCountUtilService.getExpectEarning(investAmout, investPeriodDay, investPeriodMonth, yearRate, repayType, null).getTotal_lixi();
		return expectEarning;
	}

	public long calAccountEarningByinvertAmt(long invertAmt, String productId) {

		Map<String, String> retMap = this.getProductMessage(productId);

		long investAmout = invertAmt;

		long investPeriodDay = Long.parseLong(retMap.get("financeTerm")); // 融资期限
																			// -
																			// 天
		long investPeriodMonth = investPeriodDay / 30;// 融资期限 -月
		BigDecimal yearRate = new BigDecimal(retMap.get("expectEarnRate"));// 年华收益率
		String repayType = retMap.get("repayMode");// 还款方式

		long expectEarning = rateCountUtilService.getExpectEarning(investAmout, investPeriodDay, investPeriodMonth, yearRate, repayType, null).getTotal_lixi();
		return expectEarning;
	}

	@Override
	public TradeBill makeNewAccountBill(Position custTPosition, AccountBookingVo accountBookDTO, String drCrFlag, String billType) {
		TradeBill accountBill = new TradeBill();
		accountBill.setId(idGeneratorService.getNext_15Bit_Sequence());
		accountBill.setPositionId(custTPosition.getId());
		accountBill.setProductId(custTPosition.getProductId());
		accountBill.setProductNane(custTPosition.getProductNane());
		accountBill.setCustId(custTPosition.getCustId());
		accountBill.setCustName(custTPosition.getCustName());
		accountBill.setPayerFlag(drCrFlag);
		accountBill.setPortion(accountBookDTO.getAmount());
		accountBill.setPossessPortion(custTPosition.getPossessPortion());
		accountBill.setTradeOrderId(accountBookDTO.getOrderId());
		accountBill.setTradeDate(accountBookDTO.getTradeDate());
		accountBill.setCreateTime(new Date());
		accountBill.setBillType(billType);
		return accountBill;
	}

	@Override
	public AccountBook makeNewAccountBook(String accountDate, String orderId, String payId, String optCode, String drSubjectNo, String drCustId, String crSubjectNo, String crCustId, String ruleId,
			long amount, String bookState, String payerFlag) {
		AccountBook accountBook = new AccountBook();
		accountBook.setAccountDate(accountDate);
		accountBook.setAmount(amount);
		accountBook.setBookTime(new Date());
		accountBook.setCrCustId(crCustId);
		accountBook.setCrSubjectNo(crSubjectNo);
		accountBook.setDrCustId(drCustId);
		accountBook.setDrSubjectNo(drSubjectNo);
		accountBook.setOptCode(optCode);
		accountBook.setOrderId(orderId);
		// 商品订单下单，未进行支付，无支付订单号，用交易订单号带代替支付订单号，以作反向记账之用
		accountBook.setPayId(StringUtils.isEmpty(payId) ? orderId : payId);
		accountBook.setRuleId(ruleId);
		accountBook.setBookState(bookState);
		accountBook.setPayerFlag(payerFlag);
		return accountBook;
	}

	@Override
	public AccountReverse makeNewAccountReverse(String crCustId, String crSubjectNo, String drCustId, String drSubjectNo, String orgAccountDate, long orgAmount, long orgBookId, String orgPayId,
			String reverseAccountDate, long reverseAmount, String reverseDesc, String payId) {
		AccountReverse accountReverse = new AccountReverse();
		accountReverse.setCrCustId(crCustId);
		accountReverse.setCrSubjectNo(crSubjectNo);
		accountReverse.setDrCustId(drCustId);
		accountReverse.setDrSubjectNo(drSubjectNo);
		accountReverse.setOrgAccountDate(orgAccountDate);
		accountReverse.setOrgAmount(orgAmount);
		accountReverse.setOrgBookId(orgBookId);
		accountReverse.setOrgPayId(orgPayId);
		accountReverse.setReverseAccountDate(reverseAccountDate);
		accountReverse.setReverseAmount(reverseAmount);
		accountReverse.setReverseDesc(reverseDesc);
		accountReverse.setReverseTime(new Date());
		accountReverse.setPayId(payId);
		return accountReverse;
	}

	/**
	 * @param productId
	 * @param productPrefix
	 * @return
	 * @description:根据产品ID查询产品信息
	 */
	private Map<String, String> getProductMessage(String productId) {

		Map<String, String> retMap = new HashMap<String, String>();

		ProductCommon productCommon = productCommonDao.selectByPrimaryKey(productId);

		retMap.put("unitCost", productCommon.getUnitCost().toString());
		retMap.put("financeTerm", productCommon.getHoldPeriod());
		retMap.put("expectEarnRate", productCommon.getExpectEarnRate().toString());
		retMap.put("repayMode", RepayMode.MODEPERIODREPAYREALDAY.getValue());

		return retMap;
	}

}
