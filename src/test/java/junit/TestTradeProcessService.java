package junit;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.nbl.common.vo.PageVO;
import com.nbl.service.manager.dto.PrdRepTrmDto;
import com.nbl.services.trade.impl.TradeProcService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestTradeProcessService {

	private final static Logger logger = LoggerFactory.getLogger(TestTradeProcessService.class);
	private static String PARAMS_URI = "src/test/java/junit/data/repayquery001.js";

	@Resource
	private TradeProcService tradeProcService;

	private static PrdRepTrmDto inputParam;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String inpParJson = FileUtil.readFile(PARAMS_URI);
		inputParam = JSONObject.parseObject(inpParJson, PrdRepTrmDto.class);
		logger.info("【input param is:】" + inputParam.toString());
	}

	@Test
	public void testQueryPrdRepTerm() {
		logger.info("testExpectation============");

		PageVO<PrdRepTrmDto> pageVo = new PageVO<PrdRepTrmDto>();
		pageVo.setSize(8);
		pageVo.setStartSize(0);
		pageVo.setEndSize(0);
		pageVo.setAllSize(0);
		// List<PrdRepTrmDto> result = tradeProcService.queryPrdRepTrm(pageVo,
		// inputParam);
		// logger.info("【SUCCESS result】:" + result.toString());

		int count = tradeProcService.queryPrdRepTrmCount(inputParam);
		logger.info("【SUCCESS result count】:" + count);

	}
	
//	public static void main(String[] args) {
//		List testList = new ArrayList();
//		testList.add("str001");
//		testList.add("str002");
//		testList.add("str003");
//		System.out.println(JSONObject.toJSONString(testList));
//	}

}
