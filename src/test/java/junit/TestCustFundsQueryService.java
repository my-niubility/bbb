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
import com.nbl.services.account.CustFundsQryService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestCustFundsQueryService {

	private final static Logger logger = LoggerFactory.getLogger(TestCustFundsQueryService.class);
	private static String PARAMS_URI = "src/test/java/junit/data";

	@Resource
	private CustFundsQryService custFundsQryService;

	private static Map<String, String> inputMap;

	//@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

	@Test
	public void testPrdDetailQuery() {
		logger.info("【enter testPrdDetailQuery】");
		CommRespDto result = custFundsQryService.queryCustFunds("CP001201609010000008");
		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}
	@Test
	public void testQueryCustFunds(){
		String custId = "CP001201609070000023";
		CommRespDto respDto = custFundsQryService.queryCustFunds(custId);
	}

}
