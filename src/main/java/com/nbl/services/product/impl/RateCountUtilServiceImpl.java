package com.nbl.services.product.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ProjectConstants;
import com.nbl.dao.PaymentDao;
import com.nbl.dao.ProductRepayTermsDao;
import com.nbl.dao.TIncomeDao;
import com.nbl.model.Payment;
import com.nbl.model.Position;
import com.nbl.model.ProductCommon;
import com.nbl.model.ProductRepayTerms;
import com.nbl.model.RepayTerm;
import com.nbl.model.TIncome;
import com.nbl.model.vo.ProductTypeVo;
import com.nbl.service.business.constant.FundsType;
import com.nbl.service.business.constant.ProductRepayEnabled;
import com.nbl.service.business.dto.res.RateCountMsgDto;
import com.nbl.service.business.dto.res.RateCountUtilResponseDto;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.RateCountUtilService;
import com.nbl.utils.DateTimeUtils;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0
 * @description :等额本息、等额本金、到期一次付息还本、每月付息到期还本服务接口实现类。
 */
@Service("rateCountUtilService")
public class RateCountUtilServiceImpl implements RateCountUtilService {

	// 日志
	private final Logger logger = LoggerFactory.getLogger(RateCountUtilServiceImpl.class);
	// 序列号生成
	@Resource
	private IdGeneratorService idGenerator;
	// 项目还款期次表
	@Resource
	private ProductRepayTermsDao productRepayTermsDao;
	// 支付订单表
	@Resource
	private PaymentDao paymentDao;
	// 投资收入表
	@Resource
	private TIncomeDao tIncomeDao;

	/**
	 * @descripses 等额本息计算实现
	 * @param project
	 *            项目信息
	 * @param investScale
	 *            项目实际融资金额
	 * @return
	 */
	@Override
	@Transactional
	public String[] averageCapital(ProductTypeVo product, List<Position> cpList) {

		// 首先处理不同产品类型
		Map<String, String> retMap = this.getProductMessage(product);
		// 持有天数
		Long periondDay = Long.valueOf(retMap.get("periondDay"));
		// 年化利率
		BigDecimal earnRate = new BigDecimal(retMap.get("earnRate"));
		// 产品ID
		String productId = retMap.get("productId");
		// 产品类型
		String productType = retMap.get("productType");
		// 根据产品ID 获取所有投资人是否用红包抵扣
		List<Payment> payList = paymentDao.selectByProductId(productId);
		// 红包抵扣金额
		Long redEnvAmt = 0L;
		// 实际利息
		long exEarning = 0l;
		// 实际收益率
		BigDecimal expectAnnuRate = new BigDecimal(0);
		// 数据库已换算到天数
		Long investPeriodDay = periondDay;
		// 融资期限（月）
		Long financeTerm = new BigDecimal(investPeriodDay).divide(new BigDecimal(30), 0, RoundingMode.HALF_UP).longValue();
		// 年化利率
		BigDecimal yearRate = earnRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 15, RoundingMode.HALF_UP);

		logger.info("融资期限（月）=" + financeTerm + ",年换利率=" + yearRate + ",月利率=" + monthRate);
		/**
		 * 依次处理各投资人的投资金额信息
		 */
		int countPerson = cpList.size();
		for (int i = 0; i < countPerson; i++) {

			/**
			 * 等额本息（本+息）计算公式：P 投资额 R 月利率 N 贷款期数（月数） N R x (R+1) 本+息 = P x
			 * ———————————— N (1+R) - 1
			 */

			Position cp = cpList.get(i);
			// 当持仓人和支付表的付款方ID一样 时把红包抵扣券赋值
			for (Payment payment : payList) {
				if ((cp.getCustId().equals(payment.getPayCustId())) && payment.getFundsType().equals(FundsType.FundsType6.getValue())) {
					redEnvAmt = payment.getRedEnvAmt();
				}
			}
			// 已融资到的资金规模
			BigDecimal scale = new BigDecimal(cp.getPossessPortion() * cp.getUnitCost());
			BigDecimal radd1n_benxi = monthRate.add(new BigDecimal(1)).pow(financeTerm.intValue());
			BigDecimal up_benxi = monthRate.multiply(radd1n_benxi);
			BigDecimal down_benxi = radd1n_benxi.subtract(new BigDecimal(1));
			BigDecimal rate_benxi = scale.multiply(up_benxi.divide(down_benxi, 15, RoundingMode.HALF_UP));
			logger.info("投资总额对应的等额本息每个月还款（本+息）=" + rate_benxi);

			/**
			 * 等额本息（每月本金）计算公式：P 投资额 R 月利率 n 第n个月 N 贷款期数（月数） n-1 R x (R+1) 本金 = P
			 * x —————————————— N (1+R) - 1
			 */
			// 总共的期数
			int n = financeTerm.intValue();
			// 前n-1期总共利息
			long rate_total_lixi = 0L;
			// 前n-1期总共本金
			long rate_total_benjin = 0L;
			for (int j = 1; j <= n; j++) {
				if (j <= n - 1) {
					BigDecimal radd1n_benjin = monthRate.add(new BigDecimal(1)).pow(j - 1);
					BigDecimal up_benjin = monthRate.multiply(radd1n_benjin);
					BigDecimal down_benjin = radd1n_benxi.subtract(new BigDecimal(1));
					BigDecimal rate_benjin = scale.multiply(up_benjin.divide(down_benjin, 15, RoundingMode.HALF_UP));
					long rate_benjin_long = rate_benjin.setScale(0, RoundingMode.HALF_UP).longValue();
					long lixi_long = rate_benxi.subtract(new BigDecimal(rate_benjin_long)).setScale(0, RoundingMode.HALF_UP).longValue();
					logger.info("投资总额对应的等额本息第" + j + "个月还款本金=" + rate_benjin_long);
					logger.info("投资总额对应的等额本息第" + j + "个月还款利息=" + lixi_long);
					if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
						exEarning = lixi_long + redEnvAmt;
					} else {
						exEarning = lixi_long;
					}
					expectAnnuRate = BigDecimal.valueOf((exEarning / rate_benjin_long) * 100);
					logger.info("投资总额对应的等额本息第" + j + "个月还款实际利息=" + exEarning);
					logger.info("投资总额对应的等额本息第" + j + "个月还款实际年化=" + expectAnnuRate);
					rate_total_lixi = rate_total_lixi + lixi_long;
					rate_total_benjin = rate_total_benjin + rate_benjin_long;
					// 投资者收入记录
					this.saveTIncome(cp, rate_benjin_long, rate_benjin_long + lixi_long, lixi_long, exEarning, earnRate, expectAnnuRate, redEnvAmt, productType, Long.valueOf(j),
							ProjectConstants.REPAYMODE_EQUALINTEREST, "等额本息还款");
					logger.info("投资总额对应的前" + j + "-1期总利息金额=" + rate_total_lixi);

				} else {
					// 投资总金额
					long benjin_total = cp.getPossessPortion() * cp.getUnitCost();
					// 预期收益
					long lixi_total = cp.getExpectEarning();
					// 最后一期本金
					long last_benjin = benjin_total - rate_total_benjin;
					// 最后一期利息
					long last_lixi = lixi_total - rate_total_lixi;
					if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
						exEarning = last_lixi + redEnvAmt;
					} else {
						exEarning = last_lixi;
					}
					expectAnnuRate = BigDecimal.valueOf((exEarning / last_benjin) * 100);
					logger.info("投资总额对应的等额本息第" + j + "个月还款实际利息=" + exEarning);
					logger.info("投资总额对应的等额本息第" + j + "个月还款实际年化=" + expectAnnuRate);
					// 投资者收入记录
					this.saveTIncome(cp, last_benjin, last_benjin + last_lixi, last_lixi, exEarning, earnRate, expectAnnuRate, redEnvAmt, productType, Long.valueOf(j),
							ProjectConstants.REPAYMODE_EQUALINTEREST, "等额本息还款");
				}
			}

		}
		/**
		 * 说明：所有投资者处理完毕后，再由投资者投资信息生成资管人对应还款金额、期次等信息
		 * 1、查询投资者在相同项目id、相同还款期次下的信息，统计出还款本金、还款利息等信息
		 */
		int n = financeTerm.intValue();
		for (int j = 1; j <= n; j++) {
			Map<String, Object> map = tIncomeDao.selectSumAmtAndCapital(productId, String.valueOf(j), ProjectConstants.REPAYMODE_EQUALINTEREST);
			// 利息
			long earning = Long.valueOf(map.get("EARNING").toString());
			// 本金
			long capital = Long.valueOf(map.get("CAPITAL").toString());
			// 总金额
			long amt = Long.valueOf(map.get("AMT").toString());
			logger.info("产品" + productId + ":第" + j + "期需还款的本金是" + capital + ",利息是" + earning + ",总金额是" + amt);
			// 项目还款期次信息
			this.saveProjectRepayTerms(retMap, capital, amt, earning, j);

		}

		return new String[] {};
	}

	/**
	 * @descripses 等额本金计算实现
	 * @param project
	 *            项目信息
	 * @param investScale
	 *            项目实际融资金额
	 * @return
	 */
	@Override
	@Transactional
	public String[] averagePrincipal(ProductTypeVo product, List<Position> cpList) {

		// 首先处理不同产品类型
		Map<String, String> retMap = this.getProductMessage(product);
		// 持有天数
		Long periondDay = Long.valueOf(retMap.get("periondDay"));
		// 年化利率
		BigDecimal earnRate = new BigDecimal(retMap.get("earnRate"));
		// 产品ID
		String productId = retMap.get("productId");
		// 产品类型
		String productType = retMap.get("productType");
		// 根据产品ID 获取所有投资人是否用红包抵扣
		List<Payment> payList = paymentDao.selectByProductId(productId);
		// 红包抵扣金额
		Long redEnvAmt = 0L;
		// 实际利息
		long exEarning = 0l;
		// 实际收益率
		BigDecimal expectAnnuRate = new BigDecimal(0);
		// 数据库已换算到天数
		Long investPeriodDay = periondDay;
		// 融资期限（月）
		Long financeTerm = new BigDecimal(investPeriodDay).divide(new BigDecimal(30), 0, RoundingMode.HALF_UP).longValue();
		// 年化利率
		BigDecimal yearRate = earnRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 月利率
		BigDecimal monthRate = yearRate.divide(new BigDecimal(12), 15, RoundingMode.HALF_UP);
		// 总共的期数
		int n = financeTerm.intValue();

		/**
		 * 依次处理各投资人的投资金额信息
		 */
		int countPerson = cpList.size();
		for (int i = 0; i < countPerson; i++) {
			/**
			 * 每月还本付息金额 = P/N + (P-L)*R 其中，P 投资总额 N 投资总期数 R 月利率 L 累计还款总额 每月本金 =
			 * P/N 每月利息 = (P-L)*R
			 */
			Position cp = cpList.get(i);
			// 当持仓人和支付表的付款方ID一样 时把红包抵扣券赋值
			for (Payment payment : payList) {
				if ((cp.getCustId().equals(payment.getPayCustId())) && payment.getFundsType().equals(FundsType.FundsType6.getValue())) {
					redEnvAmt = payment.getRedEnvAmt();
				}
			}
			// 已融资到的资金规模
			BigDecimal scale = new BigDecimal(cp.getPossessPortion() * cp.getUnitCost());

			// 每月本金
			BigDecimal month_benjin = scale.divide(new BigDecimal(financeTerm), 15, RoundingMode.HALF_UP);
			long month_benjin_long = month_benjin.setScale(0, RoundingMode.HALF_UP).longValue();
			long add_month_benjin = 0L;
			long last_month_benjin = 0L;
			for (int k = 1; k <= n; k++) {
				add_month_benjin = add_month_benjin + month_benjin_long;
			}
			// 比较每月平均本金与投资总金额，处理精度问题，消除差异
			long total_scale = cp.getPossessPortion() * cp.getUnitCost();
			if (total_scale > add_month_benjin) {
				last_month_benjin = month_benjin_long + (total_scale - add_month_benjin);
			} else if (total_scale < add_month_benjin) {
				last_month_benjin = month_benjin_long - (add_month_benjin - total_scale);
			} else {
				last_month_benjin = month_benjin_long;
			}
			long invest_total_lixi = cp.getExpectEarning();
			long total_lixi = 0L;
			// n-1个月还款本金总额
			BigDecimal month_repay_benjin = new BigDecimal(0);

			for (int j = 1; j <= n; j++) {

				if (j < n) {
					if (j > 1) {
						month_repay_benjin = month_repay_benjin.add(month_benjin);
					}
					// 每月利息
					BigDecimal month_lixi = new BigDecimal(0);
					month_lixi = (scale.subtract(month_repay_benjin)).multiply(monthRate);
					long month_lixi_long = month_lixi.setScale(0, RoundingMode.HALF_UP).longValue();
					logger.info("投资总额对应的等额本金第" + j + "个月还款利息=" + month_lixi);
					total_lixi = total_lixi + month_lixi_long;

					if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
						exEarning = month_lixi_long + redEnvAmt;
					} else {
						exEarning = month_lixi_long;
					}
					expectAnnuRate = BigDecimal.valueOf((exEarning / month_benjin_long) * 100);
					logger.info("投资总额对应的等额本金第" + j + "个月还款实际利息=" + exEarning);
					logger.info("投资总额对应的等额本金第" + j + "个月还款实际年化=" + expectAnnuRate);
					// 每月本金+利息
					long month_benjin_lixi = 0L;
					month_benjin_lixi = month_benjin_long + month_lixi_long;
					logger.info("投资总额对应的等额本金第" + j + "个月还款本金+利息=" + month_benjin_lixi);
					// 投资者收入记录
					this.saveTIncome(cp, month_benjin_long, month_benjin_lixi, month_lixi_long, exEarning, earnRate, expectAnnuRate, redEnvAmt, productType, j,
							ProjectConstants.REPAYMODE_EQUAL_PRINCIPAL, "等额本金还款");

				} else if (j == n) {
					// 每月利息
					BigDecimal month_lixi = new BigDecimal(0);
					month_lixi = (scale.subtract(month_repay_benjin)).multiply(monthRate);
					long month_lixi_long = month_lixi.setScale(0, RoundingMode.HALF_UP).longValue();
					long last_lixi_long = invest_total_lixi - total_lixi;
					logger.info("投资总额对应的等额本金第" + j + "个月还款利息=" + month_lixi);

					if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
						exEarning = last_lixi_long + redEnvAmt;
					} else {
						exEarning = last_lixi_long;
					}
					expectAnnuRate = BigDecimal.valueOf((exEarning / month_benjin_long) * 100);
					logger.info("投资总额对应的等额本金第" + j + "个月还款实际利息=" + exEarning);
					logger.info("投资总额对应的等额本金第" + j + "个月还款实际年化=" + expectAnnuRate);
					// 每月本金+利息
					long month_benjin_lixi = 0L;
					month_benjin_lixi = month_benjin_long + month_lixi_long;
					logger.info("投资总额对应的等额本金第" + j + "个月还款本金+利息=" + month_benjin_lixi);
					// 投资者收入记录
					this.saveTIncome(cp, last_month_benjin, last_month_benjin + last_lixi_long, last_lixi_long, exEarning, earnRate, expectAnnuRate, redEnvAmt, productType, j,
							ProjectConstants.REPAYMODE_EQUAL_PRINCIPAL, "等额本金还款");
				}
			}

		}

		/**
		 * 说明：所有投资者处理完毕后，再由投资者投资信息生成资管人对应还款金额、期次等信息
		 * 1、查询投资者在相同项目id、相同还款期次下的信息，统计出还款本金、还款利息等信息
		 */
		for (int j = 1; j <= n; j++) {
			Map<String, Object> map = tIncomeDao.selectSumAmtAndCapital(productId, String.valueOf(j), ProjectConstants.REPAYMODE_EQUAL_PRINCIPAL);
			// 利息
			long earning = Long.valueOf(map.get("EARNING").toString());
			// 本金
			long capital = Long.valueOf(map.get("CAPITAL").toString());
			// 总金额
			long amt = Long.valueOf(map.get("AMT").toString());
			logger.info("项目" + productId + ":第" + j + "期需还款的本金是" + capital + ",利息是" + earning + ",总金额是" + amt);
			// 项目还款期次信息
			this.saveProjectRepayTerms(retMap, capital, amt, earning, j);

		}

		return new String[] {};
	}

	/**
	 * @descripses 到期一次付息还本计算实现
	 * @param project
	 *            项目信息
	 * @param investScale
	 *            项目实际融资金额
	 * @return
	 */
	@Override
	@Transactional
	public String[] periodRepayCapital(ProductTypeVo product, List<Position> cpList) {

		// 首先处理不同产品类型
		Map<String, String> retMap = this.getProductMessage(product);
		// 产品ID
		String productId = retMap.get("productId");
		// 预期年化利率
		BigDecimal earnRate = new BigDecimal(retMap.get("earnRate"));
		// 实际年化利率
		BigDecimal expectAnnuRate = new BigDecimal(0);
		// 产品类型
		String productType = retMap.get("productType");
		// 根据产品ID 获取所有投资人是否用红包抵扣
		List<Payment> payList = paymentDao.selectByProductId(productId);
		// 红包抵扣金额
		Long redEnvAmt = 0L;

		/**
		 * 依次处理各投资人的投资金额信息
		 */
		int n = cpList.size();
		for (int i = 0; i < n; i++) {
			Position cp = cpList.get(i);
			// 当持仓人和支付表的付款方ID一样 时把红包抵扣券赋值
			for (Payment payment : payList) {
				if ((cp.getCustId().equals(payment.getPayCustId())) && payment.getFundsType().equals(FundsType.FundsType6.getValue())) {
					redEnvAmt = payment.getRedEnvAmt();
				}
			}
			// 已融资到的资金规模
			long scale = cp.getPossessPortion() * cp.getUnitCost();
			// 投资者投资的预期收益
			long invest_total_lixi = cp.getExpectEarning();
			// 投资者投资的实际收益
			long earning = 0l;
			if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
				earning = invest_total_lixi + redEnvAmt;
			} else {
				earning = redEnvAmt;
			}
			expectAnnuRate = BigDecimal.valueOf((earning / scale) * 100);
			logger.info("投资总额对应的到期一次付息还本实际利息=" + earning);
			logger.info("投资总额对应的到期一次付息还本实际年化=" + expectAnnuRate);
			// 投资者收入记录
			this.saveTIncome(cp, scale, invest_total_lixi + scale, invest_total_lixi, earning, earnRate, expectAnnuRate, redEnvAmt, productType, 1, ProjectConstants.REPAYMODE_ONCE, "到期一次还本付息");
		}

		/**
		 * 说明：所有投资者处理完毕后，再由投资者投资信息生成资管人对应还款金额、期次等信息
		 * 1、查询投资者在相同项目id、相同还款期次下的信息，统计出还款本金、还款利息等信息
		 */
		Map<String, Object> map = tIncomeDao.selectSumAmtAndCapital(productId, String.valueOf(1), ProjectConstants.REPAYMODE_ONCE);
		// 利息
		long earning = Long.valueOf(map.get("EARNING").toString());
		// 本金
		long capital = Long.valueOf(map.get("CAPITAL").toString());
		// 总金额
		long amt = Long.valueOf(map.get("AMT").toString());
		logger.info("项目" + productId + ":第" + 1 + "期需还款的本金是" + capital + ",利息是" + earning + ",总金额是" + amt);
		// 项目还款期次信息
		this.saveProjectRepayTerms(retMap, capital, amt, earning, 1);

		return new String[] {};
	}

	/**
	 * @descripses 每月付息到期还本计算实现
	 * @param project
	 *            项目信息
	 * @param investScale
	 *            项目实际融资金额
	 * @return
	 */
	@Override
	@Transactional
	public String[] monthCapPerPrincipal(ProductTypeVo product, List<Position> cpList) {

		// 首先处理不同产品类型
		Map<String, String> retMap = this.getProductMessage(product);
		// 持有天数
		Long periondDay = Long.valueOf(retMap.get("periondDay"));
		// 预期年化利率
		BigDecimal earnRate = new BigDecimal(retMap.get("earnRate"));
		// 实际年化利率
		BigDecimal expectAnnuRate = new BigDecimal(0);
		// 产品类型
		String productType = retMap.get("productType");
		// 产品ID
		String productId = retMap.get("productId");
		// 根据产品ID 获取所有投资人是否用红包抵扣
		List<Payment> payList = paymentDao.selectByProductId(productId);
		// 红包抵扣金额
		Long redEnvAmt = 0L;
		// 实际利息
		Long expectEarning = 0L;
		// 数据库已换算到天数
		Long investPeriodDay = periondDay;
		// 融资期限（月）
		Long financeTerm = new BigDecimal(investPeriodDay).divide(new BigDecimal(30), 0, RoundingMode.HALF_UP).longValue();
		int n = financeTerm.intValue();
		/**
		 * 依次处理各投资人的投资金额信息
		 */
		int countPerson = cpList.size();
		for (int i = 0; i < countPerson; i++) {
			Position cp = cpList.get(i);
			// 当持仓人和支付表的付款方ID一样 时把红包抵扣券赋值
			for (Payment payment : payList) {
				if ((cp.getCustId().equals(payment.getPayCustId())) && payment.getFundsType().equals(FundsType.FundsType6.getValue())) {
					redEnvAmt = payment.getRedEnvAmt();
				}
			}

			// 已融资到的资金规模
			long scale = cp.getPossessPortion() * cp.getUnitCost();
			// 投资总额对应的利息
			BigDecimal totalRate = new BigDecimal(cp.getExpectEarning());
			long total_rate_long = totalRate.longValue();
			logger.info("投资总额对应的利息=" + totalRate);
			// 如果是一个月的融资其，则本金跟利息一块还
			BigDecimal perMonthRate = totalRate.divide(new BigDecimal(financeTerm), 15, RoundingMode.HALF_UP);

			long perMonthRate_long = perMonthRate.setScale(0, RoundingMode.HALF_UP).longValue();
			if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
				expectEarning = perMonthRate_long + redEnvAmt;
			} else {
				expectEarning = perMonthRate_long;
			}
			// 实际的年化
			expectAnnuRate = BigDecimal.valueOf((expectEarning / scale) * 100);
			logger.info("投资总额对应的每月付息到期还本实际利息=" + expectEarning);
			logger.info("投资总额对应的每月付息到期还本实际年化=" + expectAnnuRate);
			long add_month_rate_long = 0L;
			long last_month_rate_long = 0L;
			for (int j = 1; j <= n; j++) {
				if (j < n) {
					add_month_rate_long = add_month_rate_long + perMonthRate_long;
					// 投资者收入记录
					this.saveTIncome(cp, 0, perMonthRate_long, perMonthRate_long, expectEarning, earnRate, expectAnnuRate, redEnvAmt, productType, j, ProjectConstants.REPAYMODE_PERIOD_REPAY_CAPITAL,
							"每月付息到期还本");

				} else if (j == n) {
					last_month_rate_long = (total_rate_long - add_month_rate_long);
					// 实际的利息
					if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
						expectEarning = last_month_rate_long + redEnvAmt;
					} else {
						expectEarning = last_month_rate_long;
					}

					// 实际的年化
					expectAnnuRate = BigDecimal.valueOf((expectEarning / scale) * 100);
					logger.info("投资总额对应的每月付息到期还本实际利息 j=n =" + expectEarning);
					logger.info("投资总额对应的每月付息到期还本实际年化 j=n =" + expectAnnuRate);
					// 投资者收入记录
					this.saveTIncome(cp, scale, last_month_rate_long + scale, last_month_rate_long, expectEarning, earnRate, expectAnnuRate, redEnvAmt, productType, j,
							ProjectConstants.REPAYMODE_PERIOD_REPAY_CAPITAL, "每月付息到期还本");
				}
			}

		}

		/**
		 * 说明：所有投资者处理完毕后，再由投资者投资信息生成资管人对应还款金额、期次等信息
		 * 1、查询投资者在相同项目id、相同还款期次下的信息，统计出还款本金、还款利息等信息
		 */
		for (int j = 1; j <= n; j++) {

			Map<String, Object> map = tIncomeDao.selectSumAmtAndCapital(productId, String.valueOf(j), ProjectConstants.REPAYMODE_PERIOD_REPAY_CAPITAL);
			// 利息
			long earning = Long.valueOf(map.get("EARNING").toString());
			// 本金
			long capital = Long.valueOf(map.get("CAPITAL").toString());
			// 总金额
			long amt = Long.valueOf(map.get("AMT").toString());
			logger.info("项目" + productId + ":第" + j + "期需还款的本金是" + capital + ",利息是" + earning + ",总金额是" + amt);
			// 项目还款期次信息
			this.saveProjectRepayTerms(retMap, capital, amt, earning, j);
		}

		return new String[] {};
	}

	/**
	 * @Title: realDayCapPerPrincipal
	 * @Description: 每月付息到期还本计算实现(按实际天数计算)
	 * @param @param
	 *            project
	 * @param @param
	 *            cpList
	 * @param @return
	 * @return String[]
	 */
	@Override
	@Transactional
	public String[] realDayCapPerPrincipal(ProductTypeVo product, List<Position> cpList) {

		// 首先处理不同产品类型
		Map<String, String> retMap = this.getProductMessage(product);
		// 持有天数
		Long periondDay = Long.valueOf(retMap.get("periondDay"));
		// 预期年化利率
		BigDecimal earnRate = new BigDecimal(retMap.get("earnRate"));
		// 实际年化利率
		BigDecimal expectAnnuRate = new BigDecimal(0);
		// 产品类型
		String productType = retMap.get("productType");
		// 产品ID
		String productId = retMap.get("productId");
		// 根据产品ID 获取所有投资人是否用红包抵扣
		List<Payment> payList = paymentDao.selectByProductId(productId);
		// 红包抵扣金额
		Long redEnvAmt = 0L;
		// 项目成立日期
		String establishDate = retMap.get("establishDate");

		// 时间处理类（jdk8）
		LocalDate estabDate = LocalDate.of(Integer.parseInt(establishDate.substring(0, 4)), Integer.parseInt(establishDate.substring(4, 6)), Integer.parseInt(establishDate.substring(6, 8)));
		LocalDate addDate = estabDate.minusDays(periondDay);
		String endDate = addDate.format(DateTimeFormatter.BASIC_ISO_DATE);

		String flagDate = establishDate;
		// 每一期的天数
		List<RepayTerm> repayTermList = new ArrayList<RepayTerm>();
		// 计算从成立日期到结束日期的月数
		int monthCount = 0;
		while (endDate.compareTo(flagDate) > 0) {
			++monthCount;
			estabDate.minusMonths(monthCount);

			// 记录上次计息结束日期
			String preDate = flagDate;
			flagDate = estabDate.format(DateTimeFormatter.BASIC_ISO_DATE);
			if (flagDate.compareTo(endDate) > 0) {
				flagDate = endDate;
			}
			RepayTerm repayTerm = new RepayTerm();
			repayTerm.setTerm(monthCount);
			// jdk1.8特性
			long pre = LocalDate.of(Integer.parseInt(preDate.substring(0, 4)), Integer.parseInt(preDate.substring(4, 6)), Integer.parseInt(preDate.substring(6, 8))).toEpochDay();

			long fla = LocalDate.of(Integer.parseInt(flagDate.substring(0, 4)), Integer.parseInt(flagDate.substring(4, 6)), Integer.parseInt(flagDate.substring(6, 8))).toEpochDay();
			// 相差天数
			repayTerm.setPeriod(fla - pre);
			repayTerm.setRepayEndDate(flagDate);
			repayTermList.add(repayTerm);
		}

		// 融资期限（月）
		Long financeTerm = new BigDecimal(monthCount).longValue();
		int n = financeTerm.intValue();
		/**
		 * 依次处理各投资人的投资金额信息
		 */
		int countPerson = cpList.size();
		for (int i = 0; i < countPerson; i++) {
			Position cp = cpList.get(i);
			// 当持仓人和支付表的付款方ID一样 时把红包抵扣券赋值
			for (Payment payment : payList) {
				if ((cp.getCustId().equals(payment.getPayCustId())) && payment.getFundsType().equals(FundsType.FundsType6.getValue())) {
					redEnvAmt = payment.getRedEnvAmt();
				}
			}
			// 已融资到的资金规模
			long scale = cp.getPossessPortion() * cp.getUnitCost();
			// 投资总额对应的利息
			BigDecimal totalRate = new BigDecimal(cp.getExpectEarning());
			long total_rate_long = totalRate.longValue();
			logger.info("投资总额对应的利息=" + totalRate);

			// 平均每一天的利息
			BigDecimal perDayRate = totalRate.divide(new BigDecimal(periondDay), 15, RoundingMode.HALF_UP);

			long add_month_rate_long = 0L;
			long last_month_rate_long = 0L;
			for (int j = 1; j <= n; j++) {

				// 计算 本期利息=每天的利息*天数
				RepayTerm repayTerm = repayTermList.get(j - 1);
				BigDecimal periodDay = new BigDecimal(repayTerm.getPeriod());
				BigDecimal earning = periodDay.multiply(perDayRate);
				long earning_long = earning.setScale(0, RoundingMode.HALF_UP).longValue();
				// 实际的利息
				long expectEarning = 0l;
				if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
					expectEarning = earning_long + redEnvAmt;
				} else {
					expectEarning = earning_long;
				}
				// 实际年化
				expectAnnuRate = BigDecimal.valueOf((expectEarning / scale) * 100);
				logger.info("投资总额对应的每月付息到期还本(按天计算)实际利息  =" + expectEarning);
				logger.info("投资总额对应的每月付息到期还本(按天计算)实际年化  =" + expectAnnuRate);
				if (j < n) {
					add_month_rate_long = add_month_rate_long + earning_long;
					// 投资者收入记录
					this.saveTIncome(cp, 0, earning_long, earning_long, expectEarning, earnRate, expectAnnuRate, redEnvAmt, productType, j, ProjectConstants.REPAYMODE_PERIOD_REPAY_REALDAY,
							"每月付息到期还本(按实际天数计算)");

				} else if (j == n) {

					if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
						last_month_rate_long = (total_rate_long - add_month_rate_long + redEnvAmt);
					} else {
						last_month_rate_long = (total_rate_long - add_month_rate_long);
					}
					// 实际利息
					if (StringUtils.isNotEmpty(redEnvAmt.toString())) {
						expectEarning = last_month_rate_long + redEnvAmt;
					} else {
						expectEarning = last_month_rate_long;
					}
					// 实际年化
					expectAnnuRate = BigDecimal.valueOf((expectEarning / scale) * 100);
					logger.info("投资总额对应的每月付息到期还本（按天计算）实际利息 j=n =" + expectEarning);
					logger.info("投资总额对应的每月付息到期还本（按天计算）实际年化 j=n =" + expectAnnuRate);
					// 投资者收入记录
					this.saveTIncome(cp, scale, last_month_rate_long + scale, last_month_rate_long, expectEarning, earnRate, expectAnnuRate, redEnvAmt, productType, j,
							ProjectConstants.REPAYMODE_PERIOD_REPAY_REALDAY, "每月付息到期还本(按实际天数计算)");
				}
			}

		}

		/**
		 * 说明：所有投资者处理完毕后，再由投资者投资信息生成资管人对应还款金额、期次等信息
		 * 1、查询投资者在相同项目id、相同还款期次下的信息，统计出还款本金、还款利息等信息
		 */
		for (int j = 1; j <= n; j++) {
			RepayTerm repayTerm = repayTermList.get(j - 1);
			Map<String, Object> map = tIncomeDao.selectSumAmtAndCapital(productId, String.valueOf(j), ProjectConstants.REPAYMODE_PERIOD_REPAY_REALDAY);
			// 利息
			long earning = Long.valueOf(map.get("EARNING").toString());
			// 本金
			long capital = Long.valueOf(map.get("CAPITAL").toString());
			// 总金额
			long amt = Long.valueOf(map.get("AMT").toString());
			logger.info("项目" + productId + ":第" + j + "期需还款的本金是" + capital + ",利息是" + earning + ",总金额是" + amt);
			// 项目还款期次信息
			this.saveProjectRepayTermsOther(retMap, capital, amt, earning, j, repayTerm.getRepayEndDate());
		}

		return new String[] {};
	}

	/**
	 * @descripses 根据投资金额、投资期限（天）、年化利率三要素算出预期收益
	 * @param investAmt
	 *            投资金额
	 * @param investPeriodDay
	 *            投资期限（天数）
	 * @param investPeriodMonth
	 *            投资期限（月）
	 * @param yearRate
	 *            年化利率
	 * @param yearDay
	 *            年换天标准（360 或365 不输入则默认365天）
	 * @return
	 */
	@Override
	public RateCountUtilResponseDto getExpectEarning(Long investAmt, Long investPeriodDay, Long investPeriodMonth, BigDecimal yearRate, String type, String yearDay) {

		RateCountUtilResponseDto reDto = new RateCountUtilResponseDto();
		// 等额本息
		if (ProjectConstants.REPAYMODE_EQUALINTEREST.equals(type)) {

			reDto = this.getAverageCapital(investAmt, investPeriodMonth, yearRate);
			// 等额本金
		} else if (ProjectConstants.REPAYMODE_EQUAL_PRINCIPAL.equals(type)) {

			reDto = this.getAveragePrincipal(investAmt, investPeriodMonth, yearRate);
			// 一次还本付息
		} else if (ProjectConstants.REPAYMODE_ONCE.equals(type)) {

			reDto = this.getPeriodRepayCapital(investAmt, investPeriodDay, yearRate, yearDay);
			// 每月付息到期还本
		} else if (ProjectConstants.REPAYMODE_PERIOD_REPAY_CAPITAL.equals(type)) {
			reDto = this.getMonthCapPerPrincipal(investAmt, investPeriodMonth, yearRate, yearDay);
			// 每月付息到期还本,按实际天数计算总息
		} else if (ProjectConstants.REPAYMODE_PERIOD_REPAY_REALDAY.equals(type)) {
			reDto = this.getRealDayCapPerPrincipal(investAmt, investPeriodDay, investPeriodMonth, yearRate, yearDay);
		}

		return reDto;
	}

	/**
	 * @descripse 投资收入记录入库
	 * @param cp
	 *            持仓记录
	 * @param capital
	 *            本金
	 * @param amt
	 *            总金额
	 * @param earning
	 *            实际利息
	 * @param expectEarning
	 *            预期利息
	 * @param regEnvAmt
	 *            红包抵扣券
	 * @param expectAnnuRate
	 *            预期收益
	 * @param annuRate
	 *            实际收益
	 * @param productType
	 *            产品类型
	 * @param term
	 *            期数
	 * @param remark
	 *            备注
	 */
	@Transactional
	private void saveTIncome(Position cp, long capital, long amt, long expectEarning, long earning, BigDecimal expectAnnuRate, BigDecimal annuRate, Long redEnvAmt, String productType, long term,
			String repayMode, String remark) {

		/**
		 * 投资收入入库处理
		 */
		TIncome record = new TIncome();
		// 到账日期
		record.setAccountDate(null);
		// 总金额
		record.setAmt(amt);
		// 本金
		record.setCapital(capital);
		// 创建时间
		record.setCreateDate(new Date());
		// 客户id
		record.setCustId(cp.getCustId());
		// 客户名称
		record.setCustName(cp.getCustName());
		// 红包抵扣券
		record.setRedEnvAmt(redEnvAmt);
		// 预期利息
		record.setExpectEarning(expectEarning);
		// 实际利息
		record.setEarning(earning);
		// 预期收益
		record.setExpectAnnuRate(expectAnnuRate);
		// 实际收益
		record.setAnnuRate(annuRate);
		// 是否有效（0：无效 1：有效）
		record.setEnabled(ProjectConstants.INCOME_STATUS_1);
		// 主键id
		record.setId(idGenerator.getNext_15Bit_Sequence());
		// 收入类型（00：投资 01：转出 02：协议转让 03: 募集期利息）
		record.setIncomeType(ProjectConstants.INCOME_TYPE_00);
		// 是否完成还款（0：未完成 1：已完成）
		record.setIsFinish(ProjectConstants.INCOME_FINISH_0);
		// 持仓ID
		record.setPositionId(cp.getId());
		// 投资利息期数
		record.setProIncomeTerm(term);
		// 项目ID
		record.setProjectId(cp.getProductId());
		// 项目名称
		record.setProjectName(cp.getProductNane());
		// 备注
		record.setRemark(remark);
		// 收益偿还方式（0：等额本息还款 1：一次还本付息 2: 等额本金 3： 每月还息到期还本 ）
		record.setRepayMode(repayMode);
		// 产品类型
		record.setProductType(productType);
		tIncomeDao.insert(record);
	}

	/**
	 * @param map
	 * @param amt
	 *            本金
	 * @param repay_amt
	 *            还款总金额
	 * @param repayInter
	 *            还款利息
	 * @param repayTerm
	 *            还款期次
	 * @description: 资管人还款期次信息表
	 */
	@Transactional
	private void saveProjectRepayTerms(Map<String, String> map, long amt, long repay_amt, long repayInter, long repayTerm) {

		// 利息返回方式
		String repayMode = map.get("repayMode");
		// 项目成立日期
		String establishDate = map.get("establishDate");

		ProductRepayTerms record = new ProductRepayTerms();
		record.setAmt(amt);
		record.setId(idGenerator.getNext_15Bit_Sequence());
		if (repayTerm == 1) {
			// 默认第一期有效
			record.setEnabled(ProductRepayEnabled.VALID.getValue());
		} else {
			// 其他期次无效
			record.setEnabled(ProductRepayEnabled.INVALID.getValue());
		}
		// 默认未完成
		record.setIsFinish(Short.valueOf("0"));
		record.setProductId(map.get("productId"));
		record.setProductName(map.get("productName"));
		record.setRepayCustId(map.get("financeId"));
		record.setRepayCustName(map.get("financeName"));
		record.setRepayAmt(repay_amt);
		String str8date = null;
		// 时间处理类（jdk8）
		LocalDate estDate = LocalDate.of(Integer.parseInt(establishDate.substring(0, 4)), Integer.parseInt(establishDate.substring(4, 6)), Integer.parseInt(establishDate.substring(6, 8)));
		/**
		 * 处理一次到期还本付息方式的最后还款日期（成立日期+具体天数）Long.valueOf(map.get("periondDay"))
		 */
		if ("1".equals(repayMode)) {
			estDate = estDate.minusDays(Long.valueOf(map.get("periondDay")));
			str8date = estDate.format(DateTimeFormatter.BASIC_ISO_DATE);
		} else if ("4".equals(repayMode)) {
			estDate = estDate.minusMonths(repayTerm);
			str8date = estDate.format(DateTimeFormatter.BASIC_ISO_DATE);
		} else {
			estDate = estDate.minusMonths(repayTerm);
			str8date = estDate.format(DateTimeFormatter.BASIC_ISO_DATE);
		}
		record.setRepayEndDate(str8date);
		record.setRepayInterest(repayInter);
		record.setRepayTerm(repayTerm);
		// 还款类型（01：资管人还款 02：融资人还款 03：担保人还款）
		record.setRepayType("01");
		productRepayTermsDao.insert(record);

	}

	/**
	 * @descripse 资管人还款期次信息表（提供给每月付息到期还本--天为单位计算）
	 * @param amt
	 *            本金
	 * @param repay_amt
	 *            还款总金额
	 * @param repayInter
	 *            还款利息
	 * @param repayTerm
	 *            还款期次
	 * @param projectId
	 *            项目id
	 * @param repayDate
	 *            还款日期
	 */
	@Transactional
	private void saveProjectRepayTermsOther(Map<String, String> map, long amt, long repay_amt, long repayInter, long repayTerm, String repayDate) {
		ProductRepayTerms record = new ProductRepayTerms();
		record.setAmt(amt);
		record.setId(idGenerator.getNext_15Bit_Sequence());
		if (repayTerm == 1) {
			// 默认第一期有效
			record.setEnabled(ProductRepayEnabled.VALID.getValue());
		} else {
			// 其他期次无效
			record.setEnabled(ProductRepayEnabled.INVALID.getValue());
		}
		// 默认未完成
		record.setIsFinish(Short.valueOf("0"));
		record.setProductId(map.get("productId"));
		record.setProductName(map.get("productName"));
		record.setRepayCustId(map.get("financeId"));// 承租方id
		record.setRepayCustName(map.get("financeName"));// 承租方名称
		record.setRepayAmt(repay_amt);
		record.setRepayEndDate(repayDate);
		record.setRepayInterest(repayInter);
		record.setRepayTerm(repayTerm);
		// 还款类型（01：资管人还款 02：融资人还款 03：担保人还款）
		record.setRepayType("01");
		productRepayTermsDao.insert(record);

	}

	/**
	 * @descripses 收益计算工具：等额本息--->预期收益
	 * @param investAmt
	 *            投资金额
	 * @param investPeriod
	 *            投资期限（月）
	 * @param yearRate
	 *            年化利率
	 * @return 预期收益
	 */
	private RateCountUtilResponseDto getAverageCapital(Long investAmt, Long investPeriod, BigDecimal yearRate) {

		// 融资期限（月）
		Long financeTerm = investPeriod;
		// 年化利率
		BigDecimal yearRate_ = yearRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 月利率
		BigDecimal monthRate = yearRate_.divide(new BigDecimal(12), 15, RoundingMode.HALF_UP);

		/**
		 * 等额本息（本+息）计算公式：P 投资额 R 月利率 N 贷款期数（月数） N R x (R+1) 本+息 = P x
		 * ———————————— N (1+R) - 1
		 */
		// 已融资到的资金规模
		BigDecimal scale = new BigDecimal(investAmt);
		BigDecimal radd1n_benxi = monthRate.add(new BigDecimal(1)).pow(financeTerm.intValue());
		BigDecimal up_benxi = monthRate.multiply(radd1n_benxi);
		BigDecimal down_benxi = radd1n_benxi.subtract(new BigDecimal(1));
		BigDecimal rate_benxi = scale.multiply(up_benxi.divide(down_benxi, 15, RoundingMode.HALF_UP));

		/**
		 * 等额本息（每月本金）计算公式：P 投资额 R 月利率 n 第n个月 N 贷款期数（月数） n-1 R x (R+1) 本金 = P x
		 * —————————————— N (1+R) - 1
		 */
		// 总共的期数
		int n = financeTerm.intValue();
		// 总共利息
		BigDecimal rate_total_lixi = new BigDecimal(0);
		// 总共本金
		BigDecimal rate_total_benjin = new BigDecimal(0);

		RateCountUtilResponseDto reDto = new RateCountUtilResponseDto();
		List<RateCountMsgDto> list = new ArrayList<RateCountMsgDto>();
		for (int j = 1; j <= n; j++) {
			if (j < n) {
				BigDecimal radd1n_benjin = monthRate.add(new BigDecimal(1)).pow(j - 1);
				BigDecimal up_benjin = monthRate.multiply(radd1n_benjin);
				BigDecimal down_benjin = radd1n_benxi.subtract(new BigDecimal(1));
				BigDecimal rate_benjin = scale.multiply(up_benjin.divide(down_benjin, 15, RoundingMode.HALF_UP));
				rate_total_lixi = rate_total_lixi.add((rate_benxi.subtract(rate_benjin)));
				rate_total_benjin = rate_total_benjin.add(rate_benjin);
				RateCountMsgDto msg = new RateCountMsgDto();
				long benjin = rate_benjin.setScale(0, RoundingMode.HALF_UP).longValue();
				long lixi = rate_benxi.subtract(rate_benjin).setScale(0, RoundingMode.HALF_UP).longValue();
				msg.setBenjin(benjin);
				msg.setLixi(lixi);
				msg.setBenxi(benjin + lixi);
				msg.setPeriod(j);
				list.add(msg);
			} else {
				// 最后一期的本金 = 投资金额 - 前n-1期的本金总和
				BigDecimal last_benjin = scale.subtract(rate_total_benjin);
				// 总利息
				rate_total_lixi = rate_total_lixi.add((rate_benxi.subtract(last_benjin)));
				RateCountMsgDto msg = new RateCountMsgDto();
				long benjin = last_benjin.setScale(0, RoundingMode.HALF_UP).longValue();
				long lixi = rate_benxi.subtract(last_benjin).setScale(0, RoundingMode.HALF_UP).longValue();
				msg.setBenjin(benjin);
				msg.setLixi(lixi);
				msg.setBenxi(benjin + lixi);
				msg.setPeriod(j);
				list.add(msg);
			}
		}

		reDto.setTotal_lixi(rate_total_lixi.setScale(0, RoundingMode.HALF_UP).longValue());
		reDto.setList(list);
		return reDto;
	}

	/**
	 * @descripses 收益计算工具：等额本金--->预期收益
	 * @param investAmt
	 *            投资金额
	 * @param investPeriod
	 *            投资期限（月）
	 * @param yearRate
	 *            年化利率
	 * @return 预期收益
	 */
	private RateCountUtilResponseDto getAveragePrincipal(Long investAmt, Long investPeriod, BigDecimal yearRate) {

		// 融资期限（月）
		Long financeTerm = investPeriod;
		// 年化利率
		BigDecimal yearRate_ = yearRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 月利率
		BigDecimal monthRate = yearRate_.divide(new BigDecimal(12), 15, RoundingMode.HALF_UP);

		/**
		 * 每月还本付息金额 = P/N + (P-L)*R 其中，P 投资总额 N 投资总期数 R 月利率 L 累计还款总额 每月本金 = P/N
		 * 每月利息 = (P-L)*R
		 */
		// 总共的期数
		int n = financeTerm.intValue();
		// 已融资到的资金规模
		BigDecimal scale = new BigDecimal(investAmt);

		// 每月本金
		BigDecimal month_benjin = scale.divide(new BigDecimal(financeTerm), 15, RoundingMode.HALF_UP);
		long month_benjin_long = month_benjin.setScale(0, RoundingMode.HALF_UP).longValue();
		long add_month_benjin = 0L;
		long last_month_benjin = 0L;
		for (int k = 1; k <= n; k++) {
			add_month_benjin = add_month_benjin + month_benjin_long;
		}
		// 比较每月平均本金与投资总金额，处理精度问题，消除差异
		if (investAmt > add_month_benjin) {
			last_month_benjin = month_benjin_long + (investAmt - add_month_benjin);
		} else {
			last_month_benjin = month_benjin_long;
		}
		// n-1个月还款本金总额
		BigDecimal month_repay_benjin = new BigDecimal(0);
		BigDecimal total_lixi = new BigDecimal(0);

		BigDecimal total_benjin = new BigDecimal(0);

		RateCountUtilResponseDto reDto = new RateCountUtilResponseDto();
		List<RateCountMsgDto> list = new ArrayList<RateCountMsgDto>();
		for (int j = 1; j <= n; j++) {
			if (j > 1) {
				month_repay_benjin = month_repay_benjin.add(month_benjin);
			}
			// 每月利息
			BigDecimal month_lixi = new BigDecimal(0);
			month_lixi = (scale.subtract(month_repay_benjin)).multiply(monthRate);
			long month_lixi_long = month_lixi.setScale(0, RoundingMode.HALF_UP).longValue();
			total_lixi = total_lixi.add(month_lixi);
			total_benjin = total_benjin.add(month_benjin);
			if (j < n) {
				RateCountMsgDto msg = new RateCountMsgDto();
				msg.setBenjin(month_benjin_long);
				msg.setLixi(month_lixi_long);
				msg.setBenxi(month_benjin_long + month_lixi_long);
				msg.setPeriod(j);
				list.add(msg);
			} else {
				RateCountMsgDto msg = new RateCountMsgDto();
				msg.setBenjin(last_month_benjin);
				msg.setLixi(month_lixi_long);
				msg.setBenxi(last_month_benjin + month_lixi_long);
				msg.setPeriod(j);
				list.add(msg);
			}
		}
		reDto.setTotal_lixi(total_lixi.setScale(0, RoundingMode.HALF_UP).longValue());
		reDto.setList(list);
		return reDto;
	}

	/**
	 * @descripses 收益计算工具：到期一次还本付息--->预期收益
	 * @param investAmt
	 *            投资金额
	 * @param investPeriod
	 *            投资期限（天数）
	 * @param yearRate
	 *            年化利率
	 * @param yearDay
	 *            年换天标准（360 或365）
	 * @return 预期收益
	 */
	private RateCountUtilResponseDto getPeriodRepayCapital(Long investAmt, Long investPeriod, BigDecimal yearRate, String yearDay) {
		// 年化收益率
		BigDecimal expectEarnRate = yearRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 融资期限（天数）
		BigDecimal termDate = new BigDecimal(investPeriod);
		// 已融资到的资金规模
		BigDecimal scale = new BigDecimal(investAmt);

		/**
		 * 融资规模应还的利息(公式：投资规模*年化收益率*融资期限（月）*30/360 或者 365)
		 */
		String ydF = "365";
		if (yearDay != null && !"".equals(yearDay)) {
			ydF = yearDay;
		}
		// 投资时间转换
		BigDecimal tranferDateScale = termDate.divide(new BigDecimal(ydF), 15, RoundingMode.HALF_UP);
		// 投资总额对应的利息
		BigDecimal totalRate = scale.multiply(tranferDateScale).multiply(expectEarnRate);
		// 总利息精度处理，金额精度保留小数点后两位
		BigDecimal scaleTotalRate = totalRate.divide(new BigDecimal(1), 15, RoundingMode.HALF_UP);

		long total_lixi = scaleTotalRate.setScale(0, RoundingMode.HALF_UP).longValue();

		RateCountUtilResponseDto reDto = new RateCountUtilResponseDto();
		List<RateCountMsgDto> list = new ArrayList<RateCountMsgDto>();
		RateCountMsgDto msg = new RateCountMsgDto();
		msg.setBenjin(investAmt);
		msg.setLixi(total_lixi);
		msg.setBenxi(investAmt + total_lixi);
		msg.setPeriod(1L);
		reDto.setTotal_lixi(total_lixi);
		reDto.setList(list);
		return reDto;
	}

	/**
	 * @descripses 收益计算工具：每月付息到期还本--->预期收益
	 * @param investAmt
	 *            投资金额
	 * @param investPeriod
	 *            投资期限（月）
	 * @param yearRate
	 *            年化利率
	 * @param yearDay
	 *            年换天标准（360 或365）
	 * @return 预期收益
	 */
	private RateCountUtilResponseDto getMonthCapPerPrincipal(Long investAmt, Long investPeriod, BigDecimal yearRate, String yearDay) {
		// 年化收益率
		BigDecimal expectEarnRate = yearRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 融资期限（月）
		BigDecimal termDate = new BigDecimal(investPeriod);
		// 融资期限（天）
		BigDecimal date = termDate.multiply(new BigDecimal(30));

		// 已融资到的资金规模
		BigDecimal scale = new BigDecimal(investAmt);

		/**
		 * 融资规模应还的利息(公式：投资规模*年化收益率*融资期限（月）*30/360或者365)
		 */
		String ydF = "365";
		if (yearDay != null && !"".equals(yearDay)) {
			ydF = yearDay;
		}

		// 投资时间转换
		BigDecimal tranferDateScale = date.divide(new BigDecimal(ydF), 15, RoundingMode.HALF_UP);
		// 投资总额对应的利息
		BigDecimal totalRate = scale.multiply(tranferDateScale).multiply(expectEarnRate);
		long totalRate_long = totalRate.setScale(0, RoundingMode.HALF_UP).longValue();
		// 每个月的利息
		BigDecimal perMonthRate = totalRate.divide(termDate, 15, RoundingMode.HALF_UP);
		long perMonthRate_long = perMonthRate.setScale(0, RoundingMode.HALF_UP).longValue();

		RateCountUtilResponseDto reDto = new RateCountUtilResponseDto();
		List<RateCountMsgDto> list = new ArrayList<RateCountMsgDto>();
		long lixi_total = 0l;
		// 如果是一个月，则本息一块还
		if (investPeriod == 1) {
			RateCountMsgDto msg = new RateCountMsgDto();
			msg.setBenjin(investAmt);
			msg.setLixi(perMonthRate_long);
			msg.setBenxi(investAmt + perMonthRate_long);
			msg.setPeriod(1L);
			list.add(msg);
		} else if (investPeriod >= 1) {
			for (int i = 1; i <= investPeriod; i++) {

				if (i < investPeriod) {
					lixi_total = lixi_total + perMonthRate_long;
					RateCountMsgDto msg = new RateCountMsgDto();
					msg.setBenjin(0);
					msg.setLixi(perMonthRate_long);
					msg.setBenxi(perMonthRate_long);
					msg.setPeriod(i);
					list.add(msg);
				} else {
					RateCountMsgDto msg = new RateCountMsgDto();
					msg.setBenjin(investAmt);
					msg.setLixi(totalRate_long - lixi_total);
					msg.setBenxi(investAmt + (totalRate_long - lixi_total));
					msg.setPeriod(i);
					list.add(msg);
				}
			}
		}

		reDto.setTotal_lixi(totalRate_long);
		reDto.setList(list);
		return reDto;
	}

	/**
	 * @Description: 收益计算工具：每月付息到期还本(按实际天数计算总息)--->预期收益
	 * @param @param
	 *            investAmt 投资总额
	 * @param @param
	 *            investPeriodDay 投资期限(天)
	 * @param @param
	 *            investPeriodMonth 投资期限(月)
	 * @param @param
	 *            yearRate 年化率
	 * @param @param
	 *            yearDay
	 * @param @return
	 * @return RateCountUtilResponseDto
	 */
	private RateCountUtilResponseDto getRealDayCapPerPrincipal(Long investAmt, Long investPeriodDay, Long investPeriodMonth, BigDecimal yearRate, String yearDay) {
		// 年化收益率
		BigDecimal expectEarnRate = yearRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 融资期限（月）
		// BigDecimal termDate = new BigDecimal(investPeriodMonth);
		// 融资期限（天）
		BigDecimal date = new BigDecimal(investPeriodDay);

		// 已融资到的资金规模
		BigDecimal scale = new BigDecimal(investAmt);

		// 起息日期
		Date establishDate = new Date();
		// 计算出结束日期（根据起息日期和投资天数）
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(establishDate);
		calendar.add(Calendar.DATE, investPeriodDay.intValue());
		Date endDate = calendar.getTime();
		calendar.setTime(establishDate);
		Date flagDate = establishDate;

		// 存储每一期的天数LIst
		List<RepayTerm> repayTermList = new ArrayList<RepayTerm>();
		// 计算从成立日期到结束日期的月数已经每月的天数，每月的结束日期
		int monthCount = 0;
		while (endDate.compareTo(flagDate) > 0) {
			++monthCount;
			calendar.setTime(establishDate);
			calendar.add(Calendar.MONTH, monthCount);
			// 记录上次计息结束日期
			Date preDate = flagDate;
			flagDate = calendar.getTime();
			if (flagDate.compareTo(endDate) > 0) {
				flagDate = endDate;
			}

			RepayTerm repayTerm = new RepayTerm();
			repayTerm.setTerm(monthCount);
			repayTerm.setPeriod(DateTimeUtils.getDistanceTime(preDate, flagDate));
			repayTerm.setRepayEndDate(new DateTimeUtils(flagDate).toDate8String());
			repayTermList.add(repayTerm);
		}

		// 融资期限（月）
		Long financeTerm = new BigDecimal(monthCount).longValue();
		int n = financeTerm.intValue();

		/**
		 * 融资规模应还的利息(公式：投资规模*年化收益率*融资期限（天）/360或者365)
		 */
		String ydF = "365";
		if (yearDay != null && !"".equals(yearDay)) {
			ydF = yearDay;
		}

		// 投资时间转换
		BigDecimal tranferDateScale = date.divide(new BigDecimal(ydF), 15, RoundingMode.HALF_UP);
		// 投资总额对应的利息
		BigDecimal totalRate = scale.multiply(tranferDateScale).multiply(expectEarnRate);
		long totalRate_long = totalRate.setScale(0, RoundingMode.HALF_UP).longValue();

		RateCountUtilResponseDto reDto = new RateCountUtilResponseDto();
		List<RateCountMsgDto> list = new ArrayList<RateCountMsgDto>();
		// 平均每一天的利息
		BigDecimal perDayRate = totalRate.divide(new BigDecimal(investPeriodDay), 15, RoundingMode.HALF_UP);

		long add_month_rate_long = 0L;
		long last_month_rate_long = 0L;
		for (int j = 1; j <= n; j++) {
			// 计算 本期利息=每天的利息*天数
			RepayTerm repayTerm = repayTermList.get(j - 1);
			BigDecimal periodDay = new BigDecimal(repayTerm.getPeriod());
			BigDecimal earning = periodDay.multiply(perDayRate);
			long earning_long = earning.setScale(0, RoundingMode.HALF_UP).longValue();
			// 记录还款结果
			RateCountMsgDto rDto = new RateCountMsgDto();
			if (j < n) {
				add_month_rate_long = add_month_rate_long + earning_long;
				// 投资者收入记录

				rDto.setBenjin(0l);
				rDto.setBenxi(earning_long);
				rDto.setLixi(earning_long);
				rDto.setPeriod(j);
				// // 本金
				// String benjin = "0";
				// // 本息
				// String benxi = earning_long + "";
				// // 利息
				// String lixi = earning_long + "";
				// // 期次
				// String qici = j + "";
				//
				// System.out.println("期次:" + qici);
				// System.out.println("本金:" + benjin);
				// System.out.println("本息:" + benxi);
				// System.out.println("利息:" + lixi);

			} else if (j == n) {
				last_month_rate_long = (totalRate_long - add_month_rate_long);
				// 投资者收入记录

				rDto.setBenjin(investAmt);
				rDto.setBenxi(last_month_rate_long + investAmt);
				rDto.setLixi(last_month_rate_long);
				rDto.setPeriod(j);
				// // 本金
				// String benjin = investAmt + "";
				// // 本息
				// long benxiL = last_month_rate_long + investAmt;
				// String benxi = benxiL + "";
				// // 利息
				// String lixi = last_month_rate_long + "";
				// // 期次
				// String qici = j + "";
				//
				// System.out.println("期次:" + qici);
				// System.out.println("本金:" + benjin);
				// System.out.println("本息:" + benxi);
				// System.out.println("利息:" + lixi);
			}
			list.add(rDto);
		}

		reDto.setTotal_lixi(totalRate_long);
		reDto.setList(list);
		return reDto;
	}

	/**
	 * @param product
	 * @return
	 * @description:根据不同产品类型获取产品要素信息
	 */
	private Map<String, String> getProductMessage(ProductTypeVo product) {

		Map<String, String> retMap = new HashMap<String, String>();

		ProductCommon pd = product.getProductCommonList().get(0);
		retMap.put("periondDay", pd.getHoldPeriod());
		retMap.put("earnRate", pd.getExpectEarnRate().toString());
		retMap.put("productId", pd.getProductId());
		retMap.put("productName", pd.getProductName());
		retMap.put("establishDate", pd.getEstablishDate());
		retMap.put("assetId", pd.getAssetId());// 资管人ID
		retMap.put("assetName", pd.getAssetName());// 资管人名称
		retMap.put("financeId", pd.getFinanceId());// 承租人ID
		retMap.put("financeName", pd.getFinanceName());// 承租人名称
		retMap.put("repayMode", "4");// 每月付息到期还本,按实际天数计算总息
		retMap.put("productType", pd.getProductType());

		return retMap;
	}

//	public static void main(String[] args) {
//		RateCountUtilServiceImpl rcu = new RateCountUtilServiceImpl();
//		System.out.println(rcu.getExpectEarning(new Long(100), new Long(360), null, new BigDecimal(5.57), "0", null).toString());
//	}
}
