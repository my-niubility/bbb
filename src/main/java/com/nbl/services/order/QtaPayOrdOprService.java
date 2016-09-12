package com.nbl.services.order;

import com.nbl.service.business.dto.req.PayAlyInfoDto;

/**
 * @author AlanMa
 *
 */
public interface QtaPayOrdOprService {
	
	/**
	 * 客户建仓
	 * @param payAlyInfoDto
	 * @param tradeTalAmt
	 * @param purchasePortion
	 */
	public void createCustPosAcc(PayAlyInfoDto payAlyInfoDto, Long tradeTalAmt, Long purchasePortion); 
	
}
