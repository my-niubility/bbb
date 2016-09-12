package com.nbl.services.recharge;

import java.util.List;

import com.nbl.common.vo.PageVO;
import com.nbl.model.Recharge;

public interface NewRechargeService {
	/**
	 * @param pageVO
	 * @param Recharge
	 * @return
	 * @description:分页查询
	 */
	public List<Recharge> pageListQueryRecharge(PageVO<Recharge> pageVO,Recharge recharge);
	
	/**
	 * @param recharge
	 * @return
	 * @description:分页查询统计总数
	 */
	public int pageCountQueryRecharge(Recharge recharge);
	
	/**
	 * @param id
	 * @return obj
	 * @description: 根据交易订单ID来查询订单明细
	 */
	public Recharge rechargeDetail(String id);
}
