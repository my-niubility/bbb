package com.nbl.services.product.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ErrorCodeMessage;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.dao.AccountBookDao;
import com.nbl.dao.AccountReverseDao;
import com.nbl.dao.AccountRuleDao;
import com.nbl.dao.PositionDao;
import com.nbl.dao.TradeBillDao;
import com.nbl.model.AccountBook;
import com.nbl.model.AccountReverse;
import com.nbl.model.AccountRule;
import com.nbl.model.Position;
import com.nbl.model.TradeBill;
import com.nbl.model.vo.AccountBookingReturnVo;
import com.nbl.model.vo.AccountBookingVo;
import com.nbl.model.vo.AccountReverseReturnVo;
import com.nbl.model.vo.AccountReverseVo;
import com.nbl.model.vo.PositionVo;
import com.nbl.service.business.constant.AccountBillType;
import com.nbl.service.business.constant.AccountBookState;
import com.nbl.service.business.constant.CustSubject;
import com.nbl.service.business.constant.DebitCredtFlag;
import com.nbl.services.product.AccountBookingService;
import com.nbl.services.product.AccountHelperService;
import com.nbl.services.product.WorkService;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :记账服务接口实现类
 */
@Service("accountBookingService")
public class AccountBookingServiceImpl implements AccountBookingService {

	private static final Logger logger = LoggerFactory.getLogger(AccountBookingServiceImpl.class);

	@Resource
	private TradeBillDao tradeBillDao;

	@Resource
	private AccountRuleDao accountRuleDao;

	@Resource
	private PositionDao custTPositionDao;

	@Resource
	private AccountBookDao accountBookDao;

	@Resource
	private AccountReverseDao accountReverseDao;

	@Resource
	private WorkService workService;

	@Resource
	private AccountHelperService accountHelperService;

	/**
	 * @param accountBookingVoList
	 * @return
	 * @description:记账接口（记账流水为未对账）
	 */
	@Transactional(readOnly = false)
	public List<AccountBookingReturnVo> doAccountBooking(List<AccountBookingVo> accountBookingVoList) {

		// 获取当前记账日期
		String accountDate = workService.getCurrentWorkDate();
		List<AccountBookingReturnVo> returnList = new ArrayList<AccountBookingReturnVo>();

		for (AccountBookingVo dto : accountBookingVoList) {

			AccountRule accountRule = accountRuleDao.findAccountRuleByKey(dto.getRuleId());
			if (accountRule == null) {
				logger.error("[" + dto.getRuleId() + "]" + ErrorCodeMessage.ACCOUNT_006_ERROR.getDisplayName());
				throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_006_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_006_ERROR.getDisplayName());
			}

			// 检查记账规则，必须配有一借一贷
			if (!((accountRule.getPayerFlag().equals(DebitCredtFlag.FLAG_DR.getValue()) && accountRule.getPayeeFlag().equals(DebitCredtFlag.FLAG_CR.getValue()))
					|| (accountRule.getPayerFlag().equals(DebitCredtFlag.FLAG_CR.getValue()) && accountRule.getPayeeFlag().equals(DebitCredtFlag.FLAG_DR.getValue())))) {
				logger.error("[" + dto.getRuleId() + "]" + ErrorCodeMessage.ACCOUNT_007_ERROR.getDisplayName());
				throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_007_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_007_ERROR.getDisplayName());
			}

			// 确定交易的借贷方
			String drSubject2, drCustId, crSubject2, crCustId, payerFlag;
			if (accountRule.getPayerFlag().equals(DebitCredtFlag.FLAG_DR.getValue())) {
				drSubject2 = accountRule.getPayerSubjectNo();
				drCustId = dto.getPayerCustID();
				crSubject2 = accountRule.getPayeeSubjectNo();
				crCustId = dto.getPayeeCustID();
				payerFlag = DebitCredtFlag.FLAG_DR.getValue();
			} else {
				drSubject2 = accountRule.getPayeeSubjectNo();
				drCustId = dto.getPayeeCustID();
				crSubject2 = accountRule.getPayerSubjectNo();
				crCustId = dto.getPayerCustID();
				payerFlag = DebitCredtFlag.FLAG_CR.getValue();
			}

			// 确认借方账号(持仓ID)
			String drAccountId = this.fetchAccountId(drSubject2, drCustId, dto.getProductId());
			// 确认贷方账号(持仓ID)
			String crAccountId = this.fetchAccountId(crSubject2, crCustId, dto.getProductId());

			// 借方记账
			long drLeftAmount = this.doRecordAccount(drAccountId, accountDate, dto, DebitCredtFlag.FLAG_DR.getValue(), AccountBillType.BOOK.getValue());
			// 贷方记账
			long crLeftAmount = this.doRecordAccount(crAccountId, accountDate, dto, DebitCredtFlag.FLAG_CR.getValue(), AccountBillType.BOOK.getValue());

			// 保存记账接口数据
			AccountBook accountBook = accountHelperService.makeNewAccountBook(accountDate, dto.getOrderId(), dto.getPayId(), dto.getOptCode(), drAccountId, drCustId, crAccountId, crCustId,
					dto.getRuleId(), dto.getAmount(), AccountBookState.VALID.getValue(), payerFlag);
			accountBookDao.insert(accountBook);

			// 封装返回实体
			AccountBookingReturnVo returnDto = new AccountBookingReturnVo();
			returnDto.setPayId(dto.getPayId());
			// 返回收付款方账户余额：先根据记账规则来判断借贷方谁是收付款方；只有收付款方账户是客户虚户时才返回余额，是其他账户的话返回-1
			if (accountRule.getPayerFlag().equals(DebitCredtFlag.FLAG_DR.getValue())) {
				if (drAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
					returnDto.setPayerAmount(drLeftAmount);
				} else {
					returnDto.setPayerAmount(-1);
				}
				if (crAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
					returnDto.setPayeeAmount(crLeftAmount);
				} else {
					returnDto.setPayeeAmount(-1);
				}
			} else {
				if (crAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
					returnDto.setPayerAmount(crLeftAmount);
				} else {
					returnDto.setPayerAmount(-1);
				}
				if (drAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
					returnDto.setPayeeAmount(drLeftAmount);
				} else {
					returnDto.setPayeeAmount(-1);
				}
			}
			returnDto.setAccountDate(accountDate);// 记账日期
			returnList.add(returnDto);
		}
		return returnList;
	}

	/**
	 * @param subject2
	 *            （借贷）二级科目号
	 * @param custId
	 *            持仓人ID
	 * @param productId
	 *            产品id
	 * @return
	 * @description:确认借（贷）方的持仓
	 */
	private String fetchAccountId(String subject2, String custId, String productId) {

		String accountId = "";
		// 如果记账规则中取到的科目大于二级，则直接使用该科目
		// 使用choice_code的原则：如果根据subject2和custId只查到一个科目，则直接使用该科目；如果查到多个科目，则用choice_code来匹配科目的最后两位
		if (subject2.length() > 7) {
			logger.info("----------subject2.length() > 7----------");
			accountId = subject2;
		} else {
			PositionVo positionVo = new PositionVo();
			positionVo.setCustId(custId);
			positionVo.setSubjectNo(subject2);
			positionVo.setProductId(productId);
			List<Position> cpList = custTPositionDao.findPositionList(positionVo);
			logger.info("---custId:" + custId + "subject2:" + subject2 + "productId:" + productId);
			logger.info("cpList.size():" + cpList.size() + "cpList:accountId =" + ((Position) cpList.get(0)).getId());
			if (cpList == null || cpList.size() == 0) {
				logger.error(ErrorCodeMessage.ACCOUNT_005_ERROR.getDisplayName() + "[custId=" + custId + "],[subject2=" + subject2 + "],[productId=" + productId + "]");
				throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_005_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_005_ERROR.getDisplayName());
			}
			accountId = ((Position) cpList.get(0)).getId();
		}
		return accountId;
	}

	/**
	 * @param accountId
	 * @param accountDate
	 * @param dto
	 * @param drCrFlag
	 * @param billType
	 * @return 记账后的账户余额
	 * @description:记账方法，记账和冲正均使用
	 */
	@Transactional(readOnly = false)
	private long doRecordAccount(String accountId, String accountDate, AccountBookingVo dto, String drCrFlag, String billType) {
		Position account = custTPositionDao.selectByPrimaryKey(accountId);
		if (account == null) {
			logger.error(ErrorCodeMessage.ACCOUNT_009_ERROR.getDisplayName());
			throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_009_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_009_ERROR.getDisplayName());
		}

		long accountLeftAmt = accountHelperService.calAccountAmount(account.getPossessPortion(), account.getSubjectType(), dto.getAmount(), drCrFlag);

		// 写数据库之前检查余额是否正确
		if (accountLeftAmt < 0) {
			logger.error("账户id[" + accountId + "]的余额不足");
			throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_008_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_008_ERROR.getDisplayName());
		}

		account.setPossessPortion(accountLeftAmt);// 现有份额
		account.setInitialPortion(accountLeftAmt);// 初始份额
		account.setUpdateTime(new Date());

		long expectEarning = accountHelperService.calAccountEarning(accountLeftAmt, account.getProductId());
		account.setExpectEarning(expectEarning); // 预期收益

		custTPositionDao.updateAccountAmt(account);

		TradeBill accountBill = accountHelperService.makeNewAccountBill(account, dto, drCrFlag, billType);
		tradeBillDao.insert(accountBill);

		return accountLeftAmt;
	}

	/**
	 * @param accountReverseVoList
	 * @return
	 * @description:冲正逻辑实现
	 */
	@Transactional(readOnly = false)
	public List<AccountReverseReturnVo> doAccountReverse(List<AccountReverseVo> accountReverseDTOList) {

		List<AccountReverseReturnVo> returnList = new ArrayList<AccountReverseReturnVo>();
		// 获取当前记账日期
		String accountDate = workService.getCurrentWorkDate();
		// 逻辑：根据支付订单可能会查询到2条记账接口流水（日终的情况下）根据记录可以区分2条记录的先后顺序
		for (AccountReverseVo dto : accountReverseDTOList) {
			List<AccountBook> accountBookList = accountBookDao.findAccountBookByPayId(dto.getOrgPayId());
			for (AccountBook accountBook : accountBookList) {
				if (accountBook == null) {
					logger.error("根据支付订单号[" + dto.getOrgPayId() + "]找不到对应的记账接口流水");
					throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_014_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_014_ERROR.getDisplayName());
				}
				// 只能全额冲正
				if (accountBook.getAmount() != dto.getAmount()) {
					logger.error(ErrorCodeMessage.ACCOUNT_015_ERROR.getDisplayName());
					throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_015_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_015_ERROR.getDisplayName());
				}
				// 如果原记账接口流水状态为“已冲正”，抛错
				if (accountBook.getBookState().equals(AccountBookState.REVERSE.getValue())) {
					logger.error("支付订单号[" + dto.getOrgPayId() + "]对应的记账接口流水已冲正");
					throw new MyBusinessRuntimeException(ErrorCodeMessage.ACCOUNT_014_ERROR.getValue(), ErrorCodeMessage.ACCOUNT_019_ERROR.getDisplayName());
				}
				AccountBookingVo orgBookDto = new AccountBookingVo();
				orgBookDto.setAmount(dto.getAmount());
				orgBookDto.setOptCode(accountBook.getOptCode());
				orgBookDto.setPayId(dto.getOrgPayId());
				orgBookDto.setOrderId(accountBook.getOrderId());
				orgBookDto.setTradeDate(dto.getTradeDate());

				String drAccountId = accountBook.getCrSubjectNo();// 原来的贷方科目，记成借
				String crAccountId = accountBook.getDrSubjectNo();// 原来的借方科目，记成贷

				// 贷方记账
				long crLeftAmount = this.doRecordAccount(crAccountId, accountDate, orgBookDto, DebitCredtFlag.FLAG_CR.getValue(), AccountBillType.REVERSE.getValue());
				// 借方记账
				long drLeftAmount = this.doRecordAccount(drAccountId, accountDate, orgBookDto, DebitCredtFlag.FLAG_DR.getValue(), AccountBillType.REVERSE.getValue());

				// 更新原记账接口流水状态为“已冲正”
				accountBook.setBookState(AccountBookState.REVERSE.getValue());
				accountBookDao.updateAccountBookState(accountBook);

				// 写记账冲正表
				AccountReverse accountReverse = accountHelperService.makeNewAccountReverse(accountBook.getDrCustId(), accountBook.getDrSubjectNo(), accountBook.getCrCustId(),
						accountBook.getCrSubjectNo(), accountBook.getAccountDate(), accountBook.getAmount(), accountBook.getBookId(), accountBook.getPayId(), accountDate, accountBook.getAmount(),
						dto.getReverseDesc(), dto.getOrgPayId());
				accountReverseDao.insert(accountReverse);

				// 封装返回实体
				AccountReverseReturnVo returnDto = new AccountReverseReturnVo();
				returnDto.setPayId(dto.getOrgPayId());
				// 返回收付款方账户余额：先根据记账规则来判断借贷方谁是收付款方；只有收付款方账户是客户虚户时才返回余额，是其他账户的话返回-1
				// 与原记账接口的收付款方相同
				if (accountBook.getPayerFlag().equals(DebitCredtFlag.FLAG_CR.getValue())) {
					if (drAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
						returnDto.setPayerAmount(drLeftAmount);
					} else {
						returnDto.setPayerAmount(-1);
					}
					if (crAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
						returnDto.setPayeeAmount(crLeftAmount);
					} else {
						returnDto.setPayeeAmount(-1);
					}
				} else {
					if (crAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
						returnDto.setPayerAmount(crLeftAmount);
					} else {
						returnDto.setPayerAmount(-1);
					}
					if (drAccountId.substring(0, 4).equals(CustSubject.BEIFUJIN.getValue())) {
						returnDto.setPayeeAmount(drLeftAmount);
					} else {
						returnDto.setPayeeAmount(-1);
					}
				}
				returnDto.setAccountDate(accountDate);// 记账日期
				returnList.add(returnDto);
			}
		}
		return returnList;
	}
}
