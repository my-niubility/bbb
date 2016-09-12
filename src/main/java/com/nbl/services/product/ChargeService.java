package com.nbl.services.product;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.service.business.dto.req.ChargeReqDto;
import com.nbl.service.business.dto.res.ChargeResDto;

/**
 * @author Donald
 * @createdate 2016年5月6日
 * @version 1.0
 * @description :账务流水内部服务接口
 */

public interface ChargeService {
	/**
	 * @param pageVO
	 * @param reqDto
	 * @return
	 * @description:账务流水分页查询
	 */
	public List<ChargeResDto> pageListQueryCharge(PageVO<ChargeReqDto> pageVO, ChargeReqDto reqDto);

	/**
	 * @param reqDto
	 * @return
	 * @description:分页查询总数
	 */
	public int pageCountQueryCharge(ChargeReqDto reqDto);

	/**
	 * @param chargeId
	 * @return
	 * @description:账务流水明细查询
	 */
	public ChargeResDto detailQueryCharge(String chargeId);

}
