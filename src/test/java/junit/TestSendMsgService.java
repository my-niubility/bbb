package junit;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.common.constants.ComConst;
import com.nbl.service.business.constant.ParamKeys;
import com.nbl.service.business.constant.UserMessageType;
import com.nbl.utils.SendStaMsg;
import com.nbl.utils.threadpool.ThreadPoolProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestSendMsgService {

	private final static Logger logger = LoggerFactory.getLogger(TestSendMsgService.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testSendMsg() {
		logger.info("testSendMsg============");
		//
		// ThreadPoolProcessor tpProcessor =
		// ThreadPoolProcessor.getInstanceFixed(ComConst.TP_MAX);
		// SendStaMsg sendStaMsgService = new SendStaMsg("CP2016081500006",
		// UserMessageType.BUSINESS.getValue(), ParamKeys.SMT_BUY.getValue(),
		// ParamKeys.SMC_BUY.getValue());
		// tpProcessor.execute(sendStaMsgService);
		// try {
		// TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 30000));//
		// 1000毫秒以内的随机数，模拟业务逻辑处理
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

}
