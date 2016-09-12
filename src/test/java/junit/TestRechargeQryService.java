package junit;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.nbl.common.constants.ComConst;
import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.RchgQryInfoDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.services.account.RechargeQryService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestRechargeQryService {

	private final static Logger logger = LoggerFactory.getLogger(TestRechargeQryService.class);
	private static String PARAMS_URI = "src/test/java/junit/data";

	@Resource
	private RechargeQryService rechargeQryService;

	private static Map<String, String> inputMap;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

	@Ignore
	public void testQueryRecharge() {
		logger.info("【enter testQueryRecharge】");
		RchgQryInfoDto inputParam = JSONObject.parseObject(inputMap.get("rechargequery001.js"), RchgQryInfoDto.class);
		CommRespDto result = rechargeQryService.queryRechgOrder(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

	@Test
	public void testQueryRechargeInSerFund() {
		logger.info("【enter testQueryRechargeInSerFund】");
		SerFundQryDto inputParam = JSONObject.parseObject(inputMap.get("rechargequery002.js"), SerFundQryDto.class);
		CommRespDto result = rechargeQryService.queryRechgOrderInSerFund(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}
}
