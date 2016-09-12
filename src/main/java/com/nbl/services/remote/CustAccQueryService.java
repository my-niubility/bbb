package com.nbl.services.remote;

public interface CustAccQueryService {
	/**
	 * 获取网销平台第三方系统账户ID
	 * @param custId
	 * @return
	 */
	public String getAccId(String custId);
	
	public String getCustName(String custId);
}
