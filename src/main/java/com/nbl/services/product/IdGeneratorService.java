
package com.nbl.services.product;


/**
 * @author Donald
 * @createdate 2016年4月13日
 * @version 1.0 
 * @description :sequence序列号生成器接口
 */
public interface IdGeneratorService {
	
	/**
	 * @param seqName
	 * @return
	 * @description:
	 * 1、长度生成规则为:8位日期+5位的序列号，比如客户号为：2016040500001
	 * 2、不同业务不同流水，比如产品编号、项目编号各自独立的流水。
	 * 3、根据seqName的不同来确定生成不同的流水号
	 */
	public String getNext_13Bit_Sequence(String seqName);

	/**
	 * @param seqName
	 * @return
	 * @description:
	 * 1、长度生成规则为:8位日期+7位的序列号，比如客户号为：201604050000001
	 * 2、此流水号可以用于一般的表主键等场景
	 */
	public String getNext_15Bit_Sequence();

	/**
	 * @param seqName
	 * @return
	 * @description:得到发往支付渠道的流水号
	 */
	public String getNext_CH_Sequence();
}
