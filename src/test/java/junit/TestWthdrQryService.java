package junit;

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
import com.nbl.common.constants.ComConst;
import com.nbl.common.dto.CommRespDto;
import com.nbl.service.user.dto.req.SerFundQryDto;
import com.nbl.service.user.dto.req.WthdrQryInfoDto;
import com.nbl.service.user.dto.res.SerFundQryRsltItemDto;
import com.nbl.services.account.WthdrQryService;
import com.nbl.services.trade.IncomeService;
import com.sun.source.tree.WhileLoopTree;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml",
		"classpath*:/spring/applicationContext-dataSource.xml" })
public class TestWthdrQryService {

	private final static Logger logger = LoggerFactory.getLogger(TestWthdrQryService.class);
	private static String PARAMS_URI = "src/test/java/junit/data/withdrawquery001.js";

	@Resource
	private WthdrQryService wthdrQryService;
	@Resource
	private IncomeService incomeService;
	private static WthdrQryInfoDto inputParam;
	private List<SerFundQryRsltItemDto> qryIncomeInFundFlow;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String inpParJson = FileUtil.readFile(PARAMS_URI);
		inputParam = JSONObject.parseObject(inpParJson, WthdrQryInfoDto.class);
		logger.info("【input param is:】" + inputParam.toString());
	}

	@Test
	public void testQueryRecharge() {
		logger.info("testExpectation============");

		CommRespDto result = wthdrQryService.queryWthdrOrder(inputParam);

		if (ComConst.SUCCESS.equals(result.getResIdentifier().getReturnType())) {
			logger.info("【SUCCESS result】:" + result.toString());
		} else {
			logger.info("【FAIL result】:" + result.toString());
		}
	}
	
	/**
	 * 交易订单取现记录数查询测试
	 */
	@Test
	public void testQueryWthdrQryCount() {
		logger.info("testExpectation============");
		WthdrQryInfoDto wthdrQryInfoDto =new WthdrQryInfoDto();
		wthdrQryInfoDto.setCustId("CP001201608310000009");
		String count = wthdrQryService.queryWthdrOrderCount(wthdrQryInfoDto);

		System.out.println("count"+count);
	}
	/**
	 * 资金流水中提现查询
	 */
	@Test
	public void testQueryWthdrQry() {
		logger.info("testExpectation============");
		WthdrQryInfoDto wthdrQryInfoDto =new WthdrQryInfoDto();
		wthdrQryInfoDto.setCustId("CP001201609010000008");
		wthdrQryInfoDto.setRecordNum(2);
		wthdrQryInfoDto.setStartIndex(0);
		wthdrQryInfoDto.setStatus("02");
		CommRespDto resDto = wthdrQryService.queryWthdrOrder(wthdrQryInfoDto);

		System.out.println(resDto.toString());
	}
	
	/**
	 * 资金流水中收益查询
	 */
	@Test
	public void testQueryBenfitQry() {
		logger.info("testExpectation============");
		SerFundQryDto serFundQryDto = new SerFundQryDto();
		serFundQryDto.setCustId("CP001201609010000008");
		serFundQryDto.setRecordNum(2);
		serFundQryDto.setStartIndex(0);
		
		List<SerFundQryRsltItemDto> list = incomeService.qryIncomeInFundFlow(serFundQryDto);

		System.out.println(list.toString());
	}

}
