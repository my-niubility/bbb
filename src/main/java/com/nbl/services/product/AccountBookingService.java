package com.nbl.services.product;

import java.util.List;

import com.nbl.model.vo.AccountBookingReturnVo;
import com.nbl.model.vo.AccountBookingVo;
import com.nbl.model.vo.AccountReverseReturnVo;
import com.nbl.model.vo.AccountReverseVo;

/**
 * @author Donald
 * @createdate 2016年5月9日
 * @version 1.0 
 * @description :记账服务接口
 */
public interface AccountBookingService {
	

	/**
	 * @param accountBookingVoList
	 * @return
	 * @description:记账接口（记账流水为未对账）
	 */
	public List<AccountBookingReturnVo> doAccountBooking(List<AccountBookingVo> accountBookingVoList) ;
	
	
	/**
	 * @param accountReverseVoList
	 * @return
	 * @description:冲正接口
	 */
	public List<AccountReverseReturnVo> doAccountReverse(List<AccountReverseVo> accountReverseVoList);


}
