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
import com.nbl.service.business.dto.req.CanlTrdOrdDto;
import com.nbl.services.order.TrdOrdMgeService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestTrdOrdMgeService {

	private final static Logger logger = LoggerFactory.getLogger(TestTrdOrdMgeService.class);
	private static String PARAMS_URI = "src/test/java/junit/data";

	@Resource
	private TrdOrdMgeService trdOrdMgeService;

	private static Map<String, String> inputMap;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

@Test
	public void testCancelTradeOrder() {
		logger.info("【enter testCancelTradeOrder】");
		CanlTrdOrdDto inputParam = JSONObject.parseObject(inputMap.get("cancelTradeOrder.js"), CanlTrdOrdDto.class);
		CommRespDto result = trdOrdMgeService.cancelTradeOrder(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

@Ignore
	public void testDisabledTradeOrder() {
		logger.info("【enter disabledTradeOrder】");
		CommRespDto result = trdOrdMgeService.disabledTradeOrder();

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

}
