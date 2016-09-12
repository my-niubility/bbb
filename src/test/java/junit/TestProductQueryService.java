package junit;

import java.util.ArrayList;
import java.util.List;
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
import com.nbl.dao.ProductCommonDao;
import com.nbl.model.vo.PrdBatchUpdVo;
import com.nbl.service.business.dto.req.MutiCndQryPrdsDto;
import com.nbl.service.business.dto.req.PrdDtlInfoQryDto;
import com.nbl.service.business.dto.req.PrdExhiInfoDto;
import com.nbl.services.product.ProductQueryService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestProductQueryService {

	private final static Logger logger = LoggerFactory.getLogger(TestProductQueryService.class);
	private static String PARAMS_URI = "src/test/java/junit/data";

	@Resource
	private ProductQueryService productQueryService;
	@Resource
	private ProductCommonDao productCommonDao;

	private static Map<String, String> inputMap;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

	@Ignore
	public void testProductExhibitionQuery() {
		logger.info("【enter testProductExhibitionQuery】");
		PrdExhiInfoDto inputParam = JSONObject.parseObject(inputMap.get("productExhibitionQuery001.js"), PrdExhiInfoDto.class);
		CommRespDto result = productQueryService.productExhibitionQuery(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

	@Test
	public void testProductsMutiCondQuery() {
		logger.info("【enter testProductsMutiCondQuery】");
		MutiCndQryPrdsDto inputParam = JSONObject.parseObject(inputMap.get("productsMutiCondQuery001.js"), MutiCndQryPrdsDto.class);
		CommRespDto result = productQueryService.productsMutiCondQuery(inputParam);
		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}

//		 String count =
//		 productQueryService.productsMutiCondCountQuery(inputParam);
//		 logger.info("【============== result】:" + count);
	}

	@Ignore
	public void testPrdDetailQuery() {
		logger.info("【enter testPrdDetailQuery】");
		PrdDtlInfoQryDto inputParam = JSONObject.parseObject(inputMap.get("productDetailsQuery001.js"), PrdDtlInfoQryDto.class);
		CommRespDto result = productQueryService.productDetailsQuery(inputParam);
		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

	@Ignore
	public void TestUpdateByPrdIdsAndPorts() {
		logger.info("【enter UpdateByPrdIdsAndPorts】");

		List<PrdBatchUpdVo> productIds = new ArrayList<PrdBatchUpdVo>();
		PrdBatchUpdVo vo1 = new PrdBatchUpdVo();
		vo1.setPrdId("pd_eng_000018");
		vo1.setPortion(new Long(1));
		PrdBatchUpdVo vo2 = new PrdBatchUpdVo();
		vo2.setPrdId("pd_eng_000019");
		vo2.setPortion(new Long(2));
		productIds.add(vo1);
		productIds.add(vo2);

		int i = productCommonDao.updateByPrdIdsAndPorts(productIds);
		logger.info("【SUCCESS result】:" + i);
	}

}
