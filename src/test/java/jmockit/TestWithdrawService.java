package jmockit;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.nbl.common.constants.ComConst;
import com.nbl.service.user.dto.req.WthdwAlyInfoDto;
import com.nbl.service.user.dto.res.WthdwAlyResultDto;
import com.nbl.services.account.WithdrawService;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;
import utils.FileUtil;

/**
 * 提现测试
 * 
 * @author AlanMa
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestWithdrawService {

	private final static Logger logger = LoggerFactory.getLogger(TestWithdrawService.class);

	@Resource
	private WithdrawService withdrawService;

	private static WthdwAlyInfoDto inputParam;
	private static String PARAMS_URI = "src/test/java/jmockit/data/withdraw001.js";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String inpParJson = FileUtil.readFile(PARAMS_URI);
		inputParam = JSONObject.parseObject(inpParJson, WthdwAlyInfoDto.class);
		logger.info(inputParam.toString());
	}

//	@Mocked
//	WithdrawZLImpl withdrawZLImpl;
//
//	/**
//	 * 方法名要以testExpectation开头
//	 */
//	@Test
//	public void testExpectationWithDraw() {
//		logger.info("【enter testExpectation】");
//		new Expectations() {
//			{
//				logger.info("【enter Expectations】");
//				withdrawZLImpl.withdrawApply((WthdwAlyInfoDto) any);
//				result = new Delegate<WthdwAlyResultDto>() {
//					// 方法名要以DelegateMethod结尾
//					public WthdwAlyResultDto executeDelegateMethod(WthdwAlyInfoDto wthdwAlyInfoDto) {
//
//						WthdwAlyResultDto result = null;
//
//						if (wthdwAlyInfoDto.getAmt().compareTo(new Long(100)) == -1) {
//							// 成功
//							result = new WthdwAlyResultDto(wthdwAlyInfoDto.getCustId(), "500.66",
//									wthdwAlyInfoDto.getWithdrawId());
//							result.setResultInfo("充值成功（第三方返回）");
//						}
//
//						if (wthdwAlyInfoDto.getAmt().compareTo(new Long(100)) == 1) {
//							// 失败
//							result = new WthdwAlyResultDto().fail("E0003", "用户余额不足");
//							result.setResultInfo("E0003-用户余额不足");
//							result.setWithdrawId(wthdwAlyInfoDto.getWithdrawId());
//						}
//
//						return result;
//					}
//				};
//			}
//		};
//
//		// 测试逻辑
//		logger.info("【Test content】");
//		WthdwAlyResultDto result = withdrawService.withDrawApply(inputParam);
//
//		if (ComConst.SUCCESS.equals(result.getReturnType())) {
//			logger.info("【SUCCESS result】:" + result.toString());
//		} else {
//			logger.info("【FAIL result】:" + result.toString());
//		}
//
//		// new Verifications() {
//		// {
//		// logger.info("Verifications============");
//		// rechargeImpl.rechargeApply((RechgAlyInfoDto) any);
//		// times = 100;
//		// }
//		// };
//	}

}
