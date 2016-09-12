package junit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbl.service.business.dto.res.RateCountMsgDto;
import com.nbl.service.business.dto.res.RateCountUtilResponseDto;

/**
 * @author gcs
 * @createdate 2016年8月23日	
 * @version 1.0
 * 测试审核产品问题
 */
public class TestWhatService {
	
	private final static Logger logger = LoggerFactory.getLogger(TestProductQueryService.class);
	
	public static void main(String[] args) {
		RateCountUtilResponseDto sd = new RateCountUtilResponseDto();
		long a1 = 200000l;
		long a2 = 180l;
		long a3 = 0l;
		BigDecimal a4 = new BigDecimal(8.20);
		String a5 = "1";
		String a6 = "365";
		sd = getPeriodRepayCapital(a1, a2, a4, a6);
		System.out.println("一次还体付息 ==" + sd.getTotal_lixi()+"       "+sd.getList());
	}
		
	private static RateCountUtilResponseDto getPeriodRepayCapital(Long investAmt, Long investPeriod, BigDecimal yearRate,
			String yearDay) {
		// 年化收益率
		BigDecimal expectEarnRate = yearRate.divide(new BigDecimal(100), 15, RoundingMode.HALF_UP);
		// 融资期限（天数）
		BigDecimal termDate = new BigDecimal(investPeriod);
		// 已融资到的资金规模
		BigDecimal scale = new BigDecimal(investAmt);

		/**
		 * 融资规模应还的利息(公式：投资规模*年化收益率*融资期限（月）*30/360 或者 365)
		 */
		String ydF = "365";
		if (yearDay != null && !"".equals(yearDay)) {
			ydF = yearDay;
		}
		// 投资时间转换
		BigDecimal tranferDateScale = termDate.divide(new BigDecimal(ydF), 15, RoundingMode.HALF_UP);
		// 投资总额对应的利息
		BigDecimal totalRate = scale.multiply(tranferDateScale).multiply(expectEarnRate);
		// 总利息精度处理，金额精度保留小数点后两位
		BigDecimal scaleTotalRate = totalRate.divide(new BigDecimal(1), 15, RoundingMode.HALF_UP);

		long total_lixi = scaleTotalRate.setScale(0, RoundingMode.HALF_UP).longValue();

		RateCountUtilResponseDto reDto = new RateCountUtilResponseDto();
		List<RateCountMsgDto> list = new ArrayList<RateCountMsgDto>();
		RateCountMsgDto msg = new RateCountMsgDto();
		msg.setBenjin(investAmt);
		msg.setLixi(total_lixi);
		msg.setBenxi(investAmt + total_lixi);
		msg.setPeriod(1L);
		reDto.setTotal_lixi(total_lixi);
		reDto.setList(list);
		return reDto;
	}
	
}
