package com.nbl.common.constants;

public class ProjectConstants {

	/**
	 * 成立条件 下线
	 */
	public static final String ESTABLISHCONDITION_BLOWLINE = "0";
	/**
	 * 成立条件 比例
	 */
	public static final String ESTABLISHCONDITION_RATIO = "1";
	
	/**
	 * 担保公司担保
	 */
	public static final String GUARANTEE_COMPANY = "0";
	/**
	 * 抵押担保
	 */
	public static final String GUARANTEE_GUARANTY = "1";
	/**
	 * 质押担保
	 */
	public static final String GUARANTEE_PLEDGE = "2";
	
	/**
	 * 销售类型为份额
	 */
	public static final String SALETYPE_PORTION = "01";
	/**
	 * 销售类型为金额
	 */
	public static final String SALETYPE_AMOUNT = "02";

	/**
	 * 偿还方式：等额本息还款
	 */
	public static final String REPAYMODE_EQUALINTEREST = "0";
	/**
	 * 偿还方式：一次还本付息
	 */
	public static final String REPAYMODE_ONCE = "1";
	/**
	 * 偿还方式：等额本金还款
	 */
	public static final String REPAYMODE_EQUAL_PRINCIPAL = "2";
	/**
	 * 偿还方式：每月还息到期还本
	 */
	public static final String REPAYMODE_PERIOD_REPAY_CAPITAL = "3";
	/**
	 * 偿还方式：每月还息到期还本,按实际天数计算
	 */
	public static final String REPAYMODE_PERIOD_REPAY_REALDAY = "4";
			
	/**
	 * 项目持仓状态：00：持有
	 */
	public static final String HOLD_STATUS00 = "00";
	/**
	 * 项目持仓状态：01：转让中
	 */
	public static final String HOLD_STATUS01 = "01";
	/**
	 * 项目持仓状态：02：转让等待中
	 */
	public static final String HOLD_STATUS02 = "02";
	/**
	 * 项目持仓状态：03：已转让
	 */
	public static final String HOLD_STATUS03 = "03";
	/**
	 * 项目持仓状态：04：已退款
	 */
	public static final String HOLD_STATUS04 = "04";
	/**
	 * 项目持仓状态：05：已还款
	 */
	public static final String HOLD_STATUS05 = "05";
	
	/**
	 * 项目持仓状态：06：持仓在途
	 */
	public static final String HOLD_STATUS06 = "06";
	
	/**
	 * 项目持仓状态：07：待退款
	 */
	public static final String HOLD_STATUS07 = "07";

	/**
	 * 持仓人类型: 01：资管
	 */
	public static final String POSITION_TYPE01 = "01";
	/**
	 * 持仓人类型: 02：慧投
	 */
	public static final String POSITION_TYPE02 = "02";
	/**
	 * 持仓人类型: 03：投资
	 */
	public static final String POSITION_TYPE03 = "03";

	/**
	 * 投资类别: 00:投资
	 */
	public static final String INVEST_TYPE00 = "00";
	/**
	 * 投资类别: 01:协议转让
	 */
	public static final String INVEST_TYPE01 = "01";
	
	/**
	 * 慧投custId
	 */
	public static final String INVEST_CUST_ID = "CTHUITOU";
	/**
	 * 慧投custNm
	 */
	public static final String INVEST_CUST_NM = "直投网";

	/**
	 * 订单状态00：支付失败 
	 */
	public static final String ORDER_STATUS_00 = "00";
	/**
	 * 订单状态 01:支付成功
	 */
	public static final String ORDER_STATUS_01 = "01";
	/**
	 * 订单状态02:未支付
	 */
	public static final String ORDER_STATUS_02 = "02";
	/**
	 * 订单状态03:等待付款
	 */
	public static final String ORDER_STATUS_03 = "03";
	/**
	 * 订单状态04:已取消
	 */
	public static final String ORDER_STATUS_04 = "04";

	/**
	 * 借贷标志(借DR ；贷CR)
	 */
	public static final String DRCR_FLAG_DR = "DR";
	/**
	 * 借贷标志(借DR ；贷CR)
	 */
	public static final String DRCR_FLAG_CR = "CR";
	
	/**
	 * 公告发布状态（00：删除 01：发布）
	 */
	public static final String PUBLISH_STATUS_DEL = "00";
	/**
	 * 公告发布状态（00：删除 01：发布）
	 */
	public static final String PUBLISH_STATUS_SET = "01";

	/**
	 * 转让状态  00：转让中 01：等待付款 02：转让成功 03：撤销转让 04：转让失败
	 */
	public static final String TRANSFER_STATUS_00 = "00";
	/**
	 * 转让状态  00：转让中 01：等待付款 02：转让成功 03：撤销转让 04：转让失败
	 */
	public static final String TRANSFER_STATUS_01 = "01";
	/**
	 * 转让状态  00：转让中 01：等待付款 02：转让成功 03：撤销转让 04：转让失败
	 */
	public static final String TRANSFER_STATUS_02 = "02";
	/**
	 * 转让状态  00：转让中 01：等待付款 02：转让成功 03：撤销转让 04：转让失败
	 */
	public static final String TRANSFER_STATUS_03 = "03";
	/**
	 * 转让状态  00：转让中 01：等待付款 02：转让成功 03：撤销转让 04：转让失败
	 */
	public static final String TRANSFER_STATUS_04 = "04";


	/**
	 * 划付类型（01：划款、02：资产管理人还款 03：资产管理人退款 04：融资人还款 05：担保人还款06：投资购买 07：转让购买）
	 */
	public static final String FUNDS_TYPE_01 = "01";
	/**
	 * 划付类型（01：划款、02：资产管理人还款 03：资产管理人退款 04：融资人还款 05：担保人还款06：投资购买 07：转让购买）
	 */
	public static final String FUNDS_TYPE_02 = "02";
	/**
	 * 划付类型（01：划款、02：资产管理人还款 03：资产管理人退款 04：融资人还款 05：担保人还款06：投资购买 07：转让购买）
	 */
	public static final String FUNDS_TYPE_03 = "03";
	/**
	 * 划付类型（01：划款、02：资产管理人还款 03：资产管理人退款 04：融资人还款 05：担保人还款06：投资购买 07：转让购买）
	 */
	public static final String FUNDS_TYPE_04 = "04";
	/**
	 * 划付类型（01：划款、02：资产管理人还款 03：资产管理人退款 04：融资人还款 05：担保人还款06：投资购买 07：转让购买）
	 */
	public static final String FUNDS_TYPE_05 = "05";
	/**
	 * 划付类型（01：划款、02：资产管理人还款 03：资产管理人退款 04：融资人还款 05：担保人还款06：投资购买 07：转让购买）
	 */
	public static final String FUNDS_TYPE_06 = "06";
	/**
	 * 划付类型（01：划款、02：资产管理人还款 03：资产管理人退款 04：融资人还款 05：担保人还款06：投资购买 07：转让购买）
	 */
	public static final String FUNDS_TYPE_07 = "07";

	/**
	 * 担保人还款是否完成（01：完成 02：未完成）【用于融资人还款】
	 */
	public static final String GUARAN_PAY_STATUS_01 = "01";
	/**
	 * 担保人还款是否完成（01：完成 02：未完成）【用于融资人还款】
	 */
	public static final String GUARAN_PAY_STATUS_02 = "02";

	/**
	 * 担保数据来源（01：平台 02：融资人）【用户担保人还款】
	 */
	public static final String SOURCEE_ID_01 = "01";
	/**
	 * 担保数据来源（01：平台 02：融资人）【用户担保人还款】
	 */
	public static final String SOURCEE_ID_02 = "02";

	/**
	 * 是否请求担保人还款（0：否 1：是）【用于融资人还款】
	 */
	public static final String REQ_GUARAN_PAY_0 = "0";
	/**
	 * 是否请求担保人还款（0：否 1：是）【用于融资人还款】
	 */
	public static final String REQ_GUARAN_PAY_1 = "1";

	/**
	 * 支付状态（ 01：支付完成（支付动作完成，状态已同步回来） 02：支付中 03：未支付）
	 */
	public static final String PAY_STATUS_01 = "01";
	/**
	 * 支付状态（ 01：支付完成（支付动作完成，状态已同步回来） 02：支付中 03：未支付）
	 */
	public static final String PAY_STATUS_02 = "02";
	/**
	 * 支付状态（ 01：支付完成（支付动作完成，状态已同步回来） 02：支付中 03：未支付）
	 */
	public static final String PAY_STATUS_03 = "03";

	/**
	 * 状态（01：完成 02：初始状态 03：部分完成）
	 */
	public static final String FUNDS_STATUS_01 = "01";
	/**
	 * 状态（01：完成 02：初始状态 03：部分完成）
	 */
	public static final String FUNDS_STATUS_02 = "02";
	/**
	 * 状态（01：完成 02：初始状态 03：部分完成）
	 */
	public static final String FUNDS_STATUS_03 = "03";
	
	/**
	 * 投资收入记录  有效标识   （0：无效 1：有效）
	 */
	public static final String INCOME_STATUS_0 = "0";
	/**
	 * 投资收入记录  有效标识   （0：无效 1：有效）
	 */
	public static final String INCOME_STATUS_1 = "1";
	/**
	 * 投资收入记录  完成标识   （0：未完成 1：已完成）
	 */
	public static final String INCOME_FINISH_0 = "0";
	/**
	 * 投资收入记录  完成标识   （0：未完成 1：已完成）
	 */
	public static final String INCOME_FINISH_1 = "1";
	/**
	 * 投资收入记录  收入类型   （00：投资 01：转出 02：协议转让 03: 募集期利息）
	 */
	public static final String INCOME_TYPE_00 = "00";
	/**
	 * 投资收入记录  收入类型   （00：投资 01：转出 02：协议转让 03: 募集期利息）
	 */
	public static final String INCOME_TYPE_01 = "01";
	/**
	 * 投资收入记录  收入类型   （00：投资 01：转出 02：协议转让 03: 募集期利息）
	 */
	public static final String INCOME_TYPE_02 = "02";
	/**
	 * 投资收入记录  收入类型   （00：投资 01：转出 02：协议转让 03: 募集期利息）
	 */
	public static final String INCOME_TYPE_03 = "03";

	/**
	 * 项目成立标志（00 未成立  01 成立）
	 */
	public static final String ESTABLISH_TYPE_00 = "00";
	/**
	 * 项目成立标志（00 未成立  01 成立）
	 */
	public static final String ESTABLISH_TYPE_01 = "01";

	/**
	 * 明细对账状态（00 不平账  01 平账）
	 */
	public static final String DEAL_CHECK_STATUS_00 = "00";
	/**
	 * 明细对账状态（00 不平账  01 平账）
	 */
	public static final String DEAL_CHECK_STATUS_01 = "01";
	/**
	 * 总览对账状态（00 预对账  01 对账中 02 差错处理中 03 对账一致）
	 */
	public static final String TOTAL_CHECK_STATUS_00 = "00";
	/**
	 * 总览对账状态（00 预对账  01 对账中 02 差错处理中 03 对账一致）
	 */
	public static final String TOTAL_CHECK_STATUS_01 = "01";
	/**
	 * 总览对账状态（00 预对账  01 对账中 02 差错处理中 03 对账一致）
	 */
	public static final String TOTAL_CHECK_STATUS_02 = "02";
	/**
	 * 总览对账状态（00 预对账  01 对账中 02 差错处理中 03 对账一致）
	 */
	public static final String TOTAL_CHECK_STATUS_03 = "03";
	/**
	 * 差错状态（00 慧投未知证联成功  01 慧投失败证联成功 02 慧投成功证联失败）
	 */
	public static final String FAILURE_TCHECK_STATUS_00 = "00";
	/**
	 * 差错状态（00 慧投未知证联成功  01 慧投失败证联成功 02 慧投成功证联失败）
	 */
	public static final String FAILURE_TCHECK_STATUS_01 = "01";
	/**
	 * 差错状态（00 慧投未知证联成功  01 慧投失败证联成功 02 慧投成功证联失败）
	 */
	public static final String FAILURE_TCHECK_STATUS_02 = "02";
	
	/**
	 * 是否已读状态（0：未读 1：已读）
	 */
	public static final short IS_READ_STATUS_0 = 0;
	/**
	 * 是否已读状态（0：未读 1：已读）
	 */
	public static final short IS_READ_STATUS_1 = 1;
	/**
	 * 是否删除状态（0：初始 1：删除 2:预删除）
	 */
	public static final short IS_DELETE_STATUS_0 = 0;
	/**
	 * 是否删除状态（0：初始 1：删除 2:预删除）
	 */
	public static final short IS_DELETE_STATUS_1 = 1;
	/**
	 * 是否删除状态（0：初始 1：删除 2:预删除）
	 */
	public static final short IS_DELETE_STATUS_2 = 2;
	/**
	 * 默认查询记录数
	 */
	public static final int RECORD_NUM  = 200;
	
	
	
}
