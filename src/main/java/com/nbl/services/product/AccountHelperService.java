package com.nbl.services.product;

import com.nbl.model.AccountBook;
import com.nbl.model.AccountReverse;
import com.nbl.model.Position;
import com.nbl.model.TradeBill;
import com.nbl.model.vo.AccountBookingVo;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :账户系统的通用helper接口
 */
public interface AccountHelperService {
	
	/**
	 * @param accountAmount
	 *            原账户份额
	 * @param subjectType
	 *            资产负债标识（00 资产 01 负债 ）
	 * @param transAmount
	 *            交易份额
	 * @param drCrFlag
	 *            本次记账的标记，记借或记贷
	 * @return long
	 * @description:根据账户份额，借贷标记和交易份额，计算出记账后的账户份额
	 */
	public long calAccountAmount(long accountAmount, String subjectType, 
			long transAmount, String drCrFlag) ;
	
	/**
	 * @param accountPortion   
	 *                     账户份额
	 * @param productId
	 *                     产品编号       
	 * @description:根据记账后的账户份额，计算出预期收益
	 */
	public long calAccountEarning(long accountPortion, String productId ) ;
	


	/**
	 * @param invertAmt   
	 *                     投资金额
	 * @param productId
	 *                     产品编号       
	 * @return
	 * @description:根据投资金额，计算出预期收益（还款后，退款后，持仓表存入投资金额，消除收益金额重新计算）
	 */
	public long calAccountEarningByinvertAmt(long invertAmt, String productId ) ;

	
	/**
	 * @param custTPosition
	 * @param accountBookingVo
	 * @param drCrFlag
	 * @param billType
	 * @return
	 * @description:建记账流程的公用方法
	 */
	public TradeBill makeNewAccountBill(Position custTPosition,AccountBookingVo accountBookingVo,
			String drCrFlag,String billType);
	
	/**
	 * @param accountDate
	 * @param orderId
	 * @param payId
	 * @param optCode
	 * @param drSubjectNo
	 * @param drCustId
	 * @param crSubjectNo
	 * @param crCustId
	 * @param ruleId
	 * @param amount
	 * @param bookState
	 * @param payerFlag
	 * @return
	 * @description:构建记账接口的公用方法
	 */
	public AccountBook makeNewAccountBook(String accountDate, String orderId, String payId, String optCode,
			String drSubjectNo, String drCustId, String crSubjectNo, String crCustId,String ruleId, 
			long amount, String bookState,  String payerFlag);
	
	
	/**
	 * @param crCustId
	 * @param crSubjectNo
	 * @param drCustId
	 * @param drSubjectNo
	 * @param orgAccountDate
	 * @param orgAmount
	 * @param orgBookId
	 * @param orgPayId
	 * @param reverseAccountDate
	 * @param reverseAmount
	 * @param reverseDesc
	 * @param payId
	 * @return
	 * @description:构建新的AccountReverse
	 */
	public AccountReverse makeNewAccountReverse(String crCustId, String crSubjectNo, String drCustId,
			String drSubjectNo, String orgAccountDate, long orgAmount, long orgBookId, String orgPayId,
			String reverseAccountDate, long reverseAmount, String reverseDesc, String payId);

}
