package junit;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.common.constants.ComConst;
import com.nbl.common.dto.CommRespDto;
import com.nbl.services.parameter.GeneralParameterService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestParamValueService {

	private final static Logger logger = LoggerFactory.getLogger(TestParamValueService.class);
	private static String PARAMS_URI = "src/test/java/junit/data";

	@Resource
	private GeneralParameterService generalParameterService;

	private static Map<String, String> inputMap;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

	@Test
	public void testQueryHisPrdTrd() {
		logger.info("【enter testQueryHisPrdTrd】");

		String result = generalParameterService.getValueByCode("custIdZgpt");

		logger.info("【SUCCESS result】:" + result);
	}

}
