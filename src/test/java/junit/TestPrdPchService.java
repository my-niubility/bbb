package junit;

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
import com.nbl.service.business.dto.req.PrdPchInfoDto;
import com.nbl.services.order.PrdPchService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestPrdPchService {

	private final static Logger logger = LoggerFactory.getLogger(PrdPchService.class);
	private static String PARAMS_URI = "src/test/java/junit/data/prdpchservice001.js";

	@Resource
	private PrdPchService prdPchService;
	private static PrdPchInfoDto inputParam;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String inpParJson = FileUtil.readFile(PARAMS_URI);
		inputParam = JSONObject.parseObject(inpParJson, PrdPchInfoDto.class);
		logger.info("【input param is:】" + inputParam.toString());
	}

	@Test
	public void testPurchaseProduct() {
		logger.info("testExpectation============");

		CommRespDto result = prdPchService.buyNow(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

}
