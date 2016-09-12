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
import com.nbl.service.business.dto.req.HisTrdOrdDto;
import com.nbl.service.business.dto.req.InvHisDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.req.TrdOrdDtlQryDto;
import com.nbl.service.user.dto.req.TrdOrdQryInfoDto;
import com.nbl.services.order.TrdOrdDtlQryService;
import com.nbl.services.order.TrdOrdQryService;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestTrdOrdQryService {

	private final static Logger logger = LoggerFactory.getLogger(TestTrdOrdQryService.class);
	private static String PARAMS_URI = "src/test/java/junit/data";

	@Resource
	private TrdOrdQryService trdOrdQryService;
	@Resource
	private TrdOrdDtlQryService trdOrdDtlSolQry;

	private static Map<String, String> inputMap;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

	@Test
	public void testTrdOrdQryService() {
		logger.info("【enter testTrdOrdQryService】");
		TrdOrdQryInfoDto inputParam = JSONObject.parseObject(inputMap.get("trdOrdQryService001.js"), TrdOrdQryInfoDto.class);
		CommRespDto result = trdOrdQryService.queryTradeOrder(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}

		// String count = trdOrdQryService.queryTradeOrderCount(inputParam);
		// logger.info("【===============result】:" + count);
	}

	@Ignore
	public void testTrdOrdDtlQryService() {
		logger.info("【enter testTrdOrdDtlQryService】");
		TrdOrdDtlQryDto inputParam = JSONObject.parseObject(inputMap.get("trdOrdDtlQryService001.js"), TrdOrdDtlQryDto.class);
		CommRespDto result = trdOrdDtlSolQry.queryTrdOrdDtl(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

	@Ignore
	public void testQueryTradeOrderInSerFund() {
		logger.info("【enter testQueryTradeOrderInSerFund】");
		//SerFundQryDto inputParam = JSONObject.parseObject(inputMap.get("queryTradeOrderInSerFund001.js"), SerFundQryDto.class);
		// CommRespDto result = trdOrdQryService.querySerialFund(inputParam);
		//
		// if
		// (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType()))
		// {
		// logger.info("【SUCCESS result】:" + result.toString());
		// } else {
		// logger.info("【FAIL result】:" + result.toString());
		// }
		/*String result = trdOrdQryService.querySerialFundCount(inputParam);
		logger.info("【SUCCESS result】:" + result);*/
		SerFundQryDto serFundQryDto = new SerFundQryDto();
		serFundQryDto.setCustId("CP001201608310000009");
		serFundQryDto.setStartIndex(0);
		serFundQryDto.setRecordNum(2);
		serFundQryDto.setType("02");
		CommRespDto respDto = trdOrdQryService.queryTradeOrderInSerFund(serFundQryDto);
		logger.info("【SUCCESS result】:" + respDto.getData().toString());
	}

	@Ignore
	public void testQueryWthDrwInSerFund() {
		logger.info("【enter testQueryWthDrwInSerFund】");
		SerFundQryDto inputParam = JSONObject.parseObject(inputMap.get("queryBenifitInSerFund.js"), SerFundQryDto.class);
		CommRespDto result = trdOrdQryService.queryTradeOrderInSerFund(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}
	
	@Test
	public void testBenifitInSerFund() {
		logger.info("【enter testBenifitInSerFund】");
		SerFundQryDto inputParam = JSONObject.parseObject(inputMap.get("queryBenifitInSerFund.js"), SerFundQryDto.class);
		CommRespDto result = trdOrdQryService.querySerialFund(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

	@Ignore
	public void testQueryHisTrdOrd() {
		logger.info("【enter testQueryHisTrdOrd】");
		HisTrdOrdDto inputParam = JSONObject.parseObject(inputMap.get("queryHisTrdOrd001.js"), HisTrdOrdDto.class);
		CommRespDto result = trdOrdQryService.queryHisTrdOrd(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

	@Ignore
	public void testQueryHisPrdTrd() {
		logger.info("【enter testQueryHisPrdTrd】");

		CommRespDto result = trdOrdQryService.queryHisPrdTrd();

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

	@Test
	public void testQryCustInvestHistory() {
		logger.info("【enter testQryCustInvestHistory】");
		InvHisDto inputParam = JSONObject.parseObject(inputMap.get("qryCustInvestHistory001.js"), InvHisDto.class);
		CommRespDto result = trdOrdQryService.qryCustInvestHistory(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}

}
