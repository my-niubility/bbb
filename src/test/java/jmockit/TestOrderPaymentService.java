package jmockit;

import java.util.Map;

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
import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.service.business.dto.req.PayAlyInfoDto;
import com.nbl.service.business.dto.res.PayAlyResultDto;
import com.nbl.service.user.dto.req.SerialRefDto;
import com.nbl.service.user.dto.res.SerialRefResultDto;
import com.nbl.services.order.OrderPaymentService;
import com.nbl.services.remote.impl.CustAccQueryImpl;
import com.nbl.services.remote.impl.PaymentThdServiceImpl;
import com.nbl.services.remote.impl.SerialRefServiceImpl;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;
import utils.FileUtil;

/**
 * 支付测试
 * 
 * @author AlanMa
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestOrderPaymentService {

	private final static Logger logger = LoggerFactory.getLogger(TestOrderPaymentService.class);
	private static String PARAMS_URI = "src/test/java/jmockit/data";
	private static Map<String, String> inputMap;

	@Resource
	private OrderPaymentService orderPaymentService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

	@Mocked
	PaymentThdServiceImpl paymentThdService;
	@Mocked
	CustAccQueryImpl custAccQueryImpl;
	@Mocked
	SerialRefServiceImpl serialRefServiceImpl;

	/**
	 * 方法名要以testExpectation开头
	 */
	@Test
	public void testExpectationPayment() {
		logger.info("【enter testExpectationPayment】");
		try {
			new Expectations() {
				{
					logger.info("【enter Expectations】");
					paymentThdService.paymentApply((PayAlyInfoDto) any);
					result = new Delegate<CommRespDto>() {
						// 方法名要以DelegateMethod结尾
						public CommRespDto executeDelegateMethod(PayAlyInfoDto payAlyInfoDto) {

							CommRespDto resp = null;
							PayAlyResultDto result = null;

							if (payAlyInfoDto.getTradeTalAmt().compareTo(new Long(1000000)) == -1) {
								// 成功
								result = new PayAlyResultDto(payAlyInfoDto.getPaymentSerialNum(), payAlyInfoDto.getPayAccount());
								result.setResultInfo("支付成功（第三方返回）");
								resp = new CommRespDto().success(result);
							}

							if (payAlyInfoDto.getTradeTalAmt().compareTo(new Long(1000000)) == 1) {
								// 失败
								// result = new PayAlyResultDto().fail("E0003",
								// "用户余额不足");
								// result.setResultInfo("E0003-用户余额不足");
								// result.setPaymentId(payAlyInfoDto.getPaymentId());
								result = new PayAlyResultDto(payAlyInfoDto.getPaymentSerialNum(), payAlyInfoDto.getPayAccount());
								resp = new CommRespDto().fail("E0003", "E0003-用户余额不足");
								resp.setData(result);
							}

							return resp;
						}
					};

					custAccQueryImpl.getAccId((String) any);
					result = new Delegate<String>() {
						// 方法名要以DelegateMethod结尾
						public String executeDelegateMethod(String str) {
							return "accid001";
						}
					};

					custAccQueryImpl.getCustName((String) any);
					result = new Delegate<String>() {
						// 方法名要以DelegateMethod结尾
						public String executeDelegateMethod(String str) {
							return "张三";
						}
					};

					serialRefServiceImpl.updateSerialRef((SerialRefDto) any);
					result = new Delegate<SerialRefResultDto>() {
						// 方法名要以DelegateMethod结尾
						public SerialRefResultDto executeDelegateMethod(SerialRefDto serialRefDto) {
							SerialRefResultDto resp = null;
							return resp;
						}
					};

				}
			};
		} catch (MyBusinessCheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 测试逻辑
		logger.info("【Test content】");
		PayAlyInfoDto inputParam = JSONObject.parseObject(inputMap.get("orderPaymentService002.js"), PayAlyInfoDto.class);
		CommRespDto result = orderPaymentService.paymentApplyQuick(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}

		// new Verifications() {
		// {
		// logger.info("Verifications============");
		// rechargeImpl.rechargeApply((RechgAlyInfoDto) any);
		// times = 100;
		// }
		// };
	}

}
