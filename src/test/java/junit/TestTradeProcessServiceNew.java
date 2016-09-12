package junit;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.PositionDao;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.ProductShelfDao;
import com.nbl.dao.TIncomeDao;
import com.nbl.model.ProductCommon;
import com.nbl.model.vo.IncomeVo;
import com.nbl.model.vo.PositionVo;
import com.nbl.model.vo.PrdRefundVo;

import utils.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestTradeProcessServiceNew {

	private final static Logger logger = LoggerFactory.getLogger(TestTradeProcessServiceNew.class);
	private static String PARAMS_URI = "src/test/java/junit/data";

	@Resource
	private TIncomeDao tIncomeDao;
	@Resource
	private ProductCommonDao productCommonDao;
	@Resource
	private PositionDao positionDao;
	@Resource
	private ProductShelfDao productShelfDao;

	private static Map<String, String> inputMap;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputMap = FileUtil.getInputParams(PARAMS_URI, null);
		logger.info("【input param is:】" + inputMap.toString());
	}

	@Ignore
	public void testIncomeQuery() {
		logger.info("【enter testIncomeQuery】");
		IncomeVo inputParam = JSONObject.parseObject(inputMap.get("incomequery001.js"), IncomeVo.class);

		PageVO<IncomeVo> pageVo = new PageVO<IncomeVo>();
		pageVo.setSize(8);
		pageVo.setStartSize(0);
		pageVo.setEndSize(0);
		pageVo.setAllSize(0);

		// List<TIncome> result = tIncomeDao.findIncomes(pageVo, inputParam);
		int count = tIncomeDao.findIncomeCount(inputParam);
		// logger.info("【SUCCESS result】:" + result.toString());
		logger.info("======coutn:" + count);
	}

	@Ignore
	public void testRefund() {
		logger.info("【enter testIncomeQuery】");
		PrdRefundVo inputParam = JSONObject.parseObject(inputMap.get("refundquery001.js"), PrdRefundVo.class);

		PageVO<ProductCommon> pageVo = new PageVO<ProductCommon>();
		pageVo.setSize(8);
		pageVo.setStartSize(0);
		pageVo.setEndSize(0);
		pageVo.setAllSize(0);

		// List<ProductCommon> result =
		// productCommonDao.selectByRefundCond(pageVo, inputParam);
		// logger.info("【SUCCESS result】:" + result.toString());

		int count = productCommonDao.selectByRefundCondCount(inputParam);
		logger.info("【SUCCESS result】:" + count);
	}

	@Ignore
	public void testPosition() {
		logger.info("【enter testIncomeQuery】");
		PositionVo inputParam = JSONObject.parseObject(inputMap.get("refundqueryposition001.js"), PositionVo.class);

		PageVO<PositionVo> pageVo = new PageVO<PositionVo>();
		pageVo.setSize(8);
		pageVo.setStartSize(0);
		pageVo.setEndSize(0);
		pageVo.setAllSize(0);

		// List<Position> result = positionDao.findListPage(pageVo, inputParam);
		// logger.info("【SUCCESS result】:" + result.toString());
		int count = positionDao.getPositionCount(inputParam);
		logger.info("【SUCCESS result】:" + count);
	}

//	@Test
//	public void testProductShelf() {
//		logger.info("【enter testProductShelf】");
//
//		ProductShelfExample example = new ProductShelfExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andExhTypeEqualTo("01");
//
//		List<ProductShelf> result = productShelfDao.selectByExample(example);
//		logger.info("【SUCCESS result】:" + result.toString());
//	}

}
