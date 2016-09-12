package com.nbl.services.product.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.SeqenceConstant;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.dao.BICaSequenceDao;
import com.nbl.dao.BICtSequenceDao;
import com.nbl.dao.BINpSequenceDao;
import com.nbl.dao.BIPkSequenceDao;
import com.nbl.model.BICaSequenceKey;
import com.nbl.model.BICtSequenceKey;
import com.nbl.model.BINpSequenceKey;
import com.nbl.model.BIPkSequenceKey;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.utils.DateTimeUtils;

/**
 * @author Donald
 * @createdate 2016年4月13日
 * @version 1.0 
 * @description :
 */
@Service("idGeneratorService")
public class IdGeneratorServiceImpl implements IdGeneratorService {

	private final static Logger logger = LoggerFactory.getLogger(IdGeneratorServiceImpl.class); 
	@Resource
	private BINpSequenceDao npDao;
	@Resource	
	private BICaSequenceDao caDao;
	@Resource	
	private BICtSequenceDao ctDao;
	@Resource	
	private BIPkSequenceDao pkDao;
	
	
	/**
	 * @param seqName
	 * @return
	 * @description:
	 * 1、长度生成规则为:8位日期+5位的序列号，比如客户号为：2016040500001
	 * 2、不同业务不同流水，比如产品编号、项目编号各自独立的流水。
	 * 3、根据seqName的不同来确定生成不同的流水号,可提供的seqName:
	 *    BI_CA_SEQUENCE(持仓)、BI_CT_SEQUENCE(项目)、BI_NP_SEQUENCE(产品)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getNext_13Bit_Sequence(String seqName) {
		
		logger.info("开始生成序列号，序列号的名称是："+seqName);
		
		if(seqName == null || "".equals(seqName)){
			logger.error("序列号名称为空，请输入");
			throw new MyBusinessRuntimeException("seqName is empty,please input it");
		}
		String date =DateTimeUtils.now().toDate8String();		
		
		String retSeq = null;
		if(SeqenceConstant.BI_NP_SEQUENCE.equals(seqName)){
			BINpSequenceKey np = new BINpSequenceKey();
			np.setCurdate(date);
			npDao.insert(np);
			String seq = String.format("%05d", np.getId());
			retSeq = date+seq;
		} else if(SeqenceConstant.BI_CT_SEQUENCE.equals(seqName)){
			BICtSequenceKey ct = new BICtSequenceKey();
			ct.setCurdate(date);
			ctDao.insert(ct);
			String seq = String.format("%05d", ct.getId());
			retSeq = date+seq;
		} else if(SeqenceConstant.BI_CA_SEQUENCE.equals(seqName)){			
			BICaSequenceKey ca = new BICaSequenceKey();
			ca.setCurdate(date);
			caDao.insert(ca);
			String seq = String.format("%05d", ca.getId());
			retSeq = date+seq;
		} else if(SeqenceConstant.BI_PK_SEQUENCE.equals(seqName)){			
			BIPkSequenceKey pk = new BIPkSequenceKey();
			pk.setCurdate(date);
			pkDao.insert(pk);
			String seq = String.format("%08d", pk.getId());
			retSeq = date+seq;
		} 
		
		logger.info("生成序列号结束，序列号值为："+retSeq);
		return retSeq;
	}

	/**
	 * @param seqName
	 * @return
	 * @description:
	 * 1、长度生成规则为:8位日期+7位的序列号，比如客户号为：201604050000001
	 * 2、此流水号可以用于一般的表主键等场景:BI_PK_SEQUENCE
	 */
	@Override
	public String getNext_15Bit_Sequence() {
		logger.info("生成15位序列号开始");
		String date =DateTimeUtils.now().toDate8String();		
		String retSeq = null;
		BIPkSequenceKey pk = new BIPkSequenceKey();
		pk.setCurdate(date);
		pkDao.insert(pk);
		String seq = String.format("%07d", pk.getId());
		retSeq = date+seq;
		logger.info("生成15位序列号结束，序列号值为："+retSeq);
		
		return retSeq;
	}

	/**
	 * @param seqName
	 * @return
	 * @description:得到发往支付渠道的流水号
	 */
	@Override
	public String getNext_CH_Sequence() {
		
		
		return null;
	}

}
