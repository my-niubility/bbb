package jmockit;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.nbl.service.user.dto.req.RechgAlyInfoDto;
import com.nbl.services.account.RechargeService;

import utils.FileUtil;

/**
 * 充值测试
 * @author AlanMa
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestRechargeService {

	private final static Logger logger = LoggerFactory.getLogger(TestRechargeService.class);

	@Resource
	private RechargeService rechargeService;

	private static RechgAlyInfoDto inputParam;
	private static String PARAMS_URI = "src/test/java/jmockit/data/recharge001.js";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String inpParJson = FileUtil.readFile(PARAMS_URI);
		inputParam = JSONObject.parseObject(inpParJson, RechgAlyInfoDto.class);
		logger.info(inputParam.toString());
	}

//	@Mocked
//	RechargeZLImpl rechargeImpl;
//
//	/**
//	 * 方法名要以testExpectation开头
//	 */
//	@Test
//	public void testExpectationRecharge() {
//		logger.info("【enter testExpectation】");
//		new Expectations() {
//			{
//				logger.info("【enter Expectations】");
//				rechargeImpl.rechargeApply((RechgAlyInfoDto) any);
//				result = new Delegate<RechgAlyResultDto>() {
//					// 方法名要以DelegateMethod结尾
//					public RechgAlyResultDto executeDelegateMethod(RechgAlyInfoDto rechgAlyInfoDto) {
//
//						RechgAlyResultDto result = null;
//
//						if (rechgAlyInfoDto.getAmt().compareTo(new Long(100)) == -1) {
//							// 成功
//							result = new RechgAlyResultDto(rechgAlyInfoDto.getCustId(), "500.66",
//									rechgAlyInfoDto.getRechargeThdId());
//							result.setResultInfo("充值成功（第三方返回）");
//						}
//
//						if (rechgAlyInfoDto.getAmt().compareTo(new Long(100)) == 1) {
//							// 失败
//							result = new RechgAlyResultDto().fail("E0003", "用户余额不足");
//							result.setResultInfo("E0003-用户余额不足");
//							result.setRechargeId(rechgAlyInfoDto.getRechargeThdId());
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
//		RechgAlyResultDto result = rechargeService.rechargeApply(inputParam);
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
