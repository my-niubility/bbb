package com.nbl.services.product.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.InterfaceConstants;
import com.nbl.common.constants.ProjectConstants;
import com.nbl.common.constants.SeqenceConstant;
import com.nbl.dao.FinanceExtendsDao;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.RentRegisterDao;
import com.nbl.model.ProductCommon;
import com.nbl.service.business.constant.ProductStatus;
import com.nbl.service.business.constant.ProductType;
import com.nbl.service.business.dto.req.PublishSolarFinanceDto;
import com.nbl.service.business.dto.req.PublishSolarProductDto;
import com.nbl.service.business.dto.req.PublishSolarProjectDto;
import com.nbl.service.business.dto.req.PublishSolarRentDto;
import com.nbl.service.business.dto.req.PublishStandardProductDto;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.PublishProductService;

/**
 * @author Donald
 * @createdate 2016年4月13日
 * @version 1.0
 * @description :产品发布相应功能实现类
 */
@Service("publishProductService")
public class PublishProductServiceImpl implements PublishProductService {

	private final static Logger logger = LoggerFactory.getLogger(PublishProductServiceImpl.class);
	private final static String PRODUCT_ID = "PD";

	@Resource
	private ProductCommonDao productCommonDao;
	@Resource
	private FinanceExtendsDao financeDao;
	@Resource
	private RentRegisterDao rentDao;
	@Resource
	private IdGeneratorService idGeneratorService;

	// 发布光伏项目入口操作
	@Override
	@Transactional
	public boolean saveSolarProduct(PublishSolarProductDto productDto, PublishSolarProjectDto projectDto,
			PublishSolarFinanceDto financeDto, PublishSolarRentDto rentDto) {

		return true;
	}

	
	/**
	 * @param standardDto
	 * @return
	 * @description:标准产品
	 */
	@Override
	public boolean saveStandardProduct(PublishStandardProductDto standardDto) {
		
		logger.info("------标准产品发布开始---------");
		// 获取产品序列号
		String pdSeq = idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.BI_NP_SEQUENCE);
		String productId = PRODUCT_ID + pdSeq;
		ProductCommon product = new ProductCommon();
		product.setProductId(productId);
		//赋值
		this.setProDuctValues(product, standardDto);
		//入库
		productCommonDao.insert(product);
		logger.info("------标准产品发布结束---------");
		return true;
	}

	/**
	 * @param product
	 * @param productDto
	 * @description:标准产品要素赋值
	 */
	private void setProDuctValues(ProductCommon product, PublishStandardProductDto productDto) {
		product.setAssetId(InterfaceConstants.ASSET_ID);//资管人
		product.setAssetName(InterfaceConstants.ASSET_NAME);//资管人名称
		product.setBreBackCheck(null);//提前回购条款
		product.setBreBackDt(productDto.getBackday());//提前回购日
		product.setConfFinanceAmt(new Long(0));//确定融资份额
		product.setRealFinanceAmt(new Long(0));//结算融资份额
		product.setDayFinanceAmt(new Long(0));//日间融资份额
		product.setCreateTime(new Date());//创建时间
		product.setCollectStartTime(productDto.getStartTime());//募集开始时间
		product.setCollectEndTime(productDto.getEndTime());//募集结束时间
		product.setExpectEarnRate(new BigDecimal(productDto.getRate()));//预期收益率
		product.setFinanceId(productDto.getFinanceId());//融资人ID
		product.setFinanceName(productDto.getFinanceName());//融资人名称
		product.setFinancePortion(productDto.getFinancePortion());//融资总额
		product.setFinanceScale(new Long(productDto.getScale()));//融资规模
		product.setUnitCost(new Long(productDto.getUnitcost())*100);//产品单价
		product.setRepayRentType(productDto.getRentType());//返租方式
		product.setRepayRentDt(productDto.getBackday());//每月返租日期
		product.setRentType(productDto.getRentType());//计租方式 0 次日记租 1 成立记租
		product.setRepayMode(new Short(productDto.getRentMode()));//
		//product.setProductType(ProductType.STANDARD.getValue());//产品类型 000-光伏001-电能002-基金003-信托 004-众筹 005-标准
		product.setProductType(productDto.getProductType());//产品类型 000-光伏001-电能002-基金003-信托 004-众筹 005-标准
		product.setProductStatus(ProductStatus.PRODUCT_STATUS01.getValue());//产品状态--发布待审核
		product.setProductName(productDto.getProductName());//产品名称
		product.setNonmBackDt(productDto.getNonbackday());//正常回购日期
		product.setbLockPeriod(productDto.getBlockday());//赎回锁定期
		product.settLockPeriod(productDto.getTlockday());//转让锁定期
		product.setLockScale(new Long(0));//锁定份额
		product.setHoldPeriod(productDto.getHoldday());//持有期
		product.setCollectEndDt(productDto.getEnddt());//
		product.setCollectStartDt(productDto.getStartdt());//
		product.setEstablishCondition(productDto.getEstablish());//
		product.setContractDt(productDto.getContrday());
		product.setInvestMin(productDto.getInvestMin());//投资下限
		product.setProductLittleType(productDto.getProductTittleType());//产品小类类型
		product.setRedType(productDto.getRentType());//红包类型
		product.setRemainTerms(productDto.getRemainTerms());//剩余期数
		if(productDto.getXxEstabValue()!=null && !"".equals(productDto.getXxEstabValue())){
			product.setEstablishMinimum(Long.valueOf(productDto.getXxEstabValue()));//下线
		}
		if(productDto.getBlEstabValue()!=null && !"".equals(productDto.getBlEstabValue())){
			product.setEstablishRatio(Long.valueOf(productDto.getBlEstabValue()));//比例
		}
		if(productDto.getSubsidyRate()!=null && !"".equals(productDto.getSubsidyRate())){
			product.setSubsidyRate(new BigDecimal(productDto.getSubsidyRate()));//补贴利率
		}
		if(productDto.getScaleLimit() !=null && !"".equals(productDto.getScaleLimit())){
			product.setInvestMax(Long.valueOf(productDto.getScaleLimit()));//
		}
		if(productDto.getSubsidyDay()==null || "".equals(productDto.getSubsidyDay())){
			product.setSubsidyDay("0");
		}else{
			product.setSubsidyDay(productDto.getSubsidyDay());
		}
		product.setTransferFlag(productDto.getTransferFlag());//
		product.setSellRate(new BigDecimal("0"));//
		product.setSellType("00");//
		
	}

}
