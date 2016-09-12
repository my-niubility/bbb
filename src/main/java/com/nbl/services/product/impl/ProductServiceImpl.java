package com.nbl.services.product.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.stream.events.EndDocument;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ErrorCodeMessage;
import com.nbl.common.constants.ProjectConstants;
import com.nbl.common.constants.SeqenceConstant;
import com.nbl.common.exception.MyBusinessRuntimeException;
import com.nbl.dao.PositionDao;
import com.nbl.dao.ProductAuditDao;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.ProductNoticeDao;
import com.nbl.model.Position;
import com.nbl.model.ProductAudit;
import com.nbl.model.ProductCommon;
import com.nbl.model.vo.ProductTypeVo;
import com.nbl.service.business.constant.CustSubject;
import com.nbl.service.business.constant.OtherAccount;
import com.nbl.service.business.constant.PositionCustType;
import com.nbl.service.business.constant.PositionHoldStatus;
import com.nbl.service.business.constant.ProductStatus;
import com.nbl.service.business.constant.ProductType;
import com.nbl.service.business.constant.SubjectType;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.services.product.ProductService;
import com.nbl.services.product.RateCountUtilService;
import com.nbl.utils.DateTimeUtils;

import javassist.bytecode.LineNumberAttribute.Pc;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	// 日志
	private final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Resource
	private PositionDao positionDao;

	@Resource
	private ProductCommonDao productCommonDao;

	@Resource
	private ProductAuditDao productAuditDao;

	@Resource
	private IdGeneratorService idGeneratorService;

	@Resource
	private ProductNoticeDao productNoticeDao;
	
	//利率计算服务
	@Resource
	private RateCountUtilService rateCountUtilService;	

	@Override
	public List<ProductTypeVo> getProductList(ProductTypeVo projectVo) {

		List<ProductTypeVo> retList = new ArrayList<ProductTypeVo>();

		return retList;
	}

	// 主键查询项目信息
	public ProductTypeVo selectByPrimaryKey(String id) {

		ProductTypeVo productVo = new ProductTypeVo();

		return productVo;
	}

	@Transactional
	public void productAudit(String productId, ProductAudit productAudit) {

		if (productAudit.getAuditUserId() == null || "".equals(productAudit.getAuditUserId())) {
			productAudit.setAuditUserId("Donald");
		}
		
		productAudit.setId(idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE));
		productAudit.setAuditTime(new Date());
		productAudit.setCreatedTime(new Date());
		productAudit.setProductId(productId);
		productAudit.setIsModify(Short.valueOf("0"));
		
		logger.info("---------保存审核信息：{}---------",productAudit.toString());
		// 保存审核信息
		productAuditDao.insert(productAudit);

		ProductCommon pd = productCommonDao.selectByPrimaryKey(productId);

		if (pd != null) {
			if (!ProductStatus.PRODUCT_STATUS01.getValue().equals(pd.getProductStatus())) {
				logger.error(
						"产品ID:" + productId + "," + ErrorCodeMessage.BUSINESS_PROJECTSTATUS_ERROR.getDisplayName());
				throw new MyBusinessRuntimeException(ErrorCodeMessage.BUSINESS_PROJECTSTATUS_ERROR.getValue(),
						ErrorCodeMessage.BUSINESS_PROJECTSTATUS_ERROR.getDisplayName());
			}
			if ("1".equals(productAudit.getPassed())) {
				logger.info("---------发布审核通过---------");
				pd.setProductStatus(ProductStatus.PRODUCT_STATUS02.getValue());// 设置为审核通过
			} else if ("0".equals(productAudit.getPassed())) {
				logger.info("---------发布审核失败，原因：{}---------",productAudit.getDescription());
				pd.setProductStatus(ProductStatus.PRODUCT_STATUS08.getValue());// 设置为审核失败
				pd.setRemark1(productAudit.getDescription());//审核失败的原因
			}

			String collectEndDate = pd.getCollectEndDt();
			// 当前日期
			String curDate = DateTimeUtils.now().toDate8String();
			// 如果是基金类型的  就判断募集截止日期
			if(ProductType.FUND.getValue().equals(pd.getProductType())){
				
			}else {
				if (curDate.compareTo(collectEndDate) > 0){// 当前日期大于募集截止日期
					pd.setProductStatus(ProductStatus.PRODUCT_STATUS08.getValue());// 设置为审核失败
					pd.setRemark1("审核已经过期，审核失败");//审核失败的原因
				}
			}
			
			
			// 判断审核状态
			String status = pd.getProductStatus();
			// 审核成功
			if (ProductStatus.PRODUCT_STATUS02.getValue().equals(status)) {

				productCommonDao.updateAuditStatus(pd);
				// 新增资管人持仓信息
				Position assetCustTPosition = this.getCustTPosition(pd, PositionCustType.ASSET);
				positionDao.insert(assetCustTPosition);
				// 新增中间持仓信息
				Position middleCustTPosition = this.getCustTPosition(pd, PositionCustType.MIDDLE);
				positionDao.insert(middleCustTPosition);

				// 审核失败
			} else if (ProductStatus.PRODUCT_STATUS08.getValue().equals(status)) {
				productCommonDao.updateAuditStatus(pd);
			} 

		} else {
			logger.error("根据产品ID查询光伏系列产品表为空,id=" + productId);
			throw new MyBusinessRuntimeException("根据产品ID查询光伏系列产品表为空");
		}

	}

	/**
	 * @param pd
	 * @param positionCustType
	 * @return
	 * @description:得到资管人持仓信息
	 */
	private Position getCustTPosition(ProductCommon pd, PositionCustType positionCustType) {
		Position position = new Position();
		position.setProductId(pd.getProductId());
		position.setProductNane(pd.getProductName());
		position.setPositionCustType(positionCustType.getValue());
		position.setHoldStatus(PositionHoldStatus.ONTHEWAY.getValue());
		position.setCreateTime(new Date());
		position.setUpdateTime(new Date());
		position.setInvenstAmt(0l);
		position.setUnitCost(pd.getUnitCost());
		if(StringUtils.isEmpty(pd.getHoldPeriod())){
			position.setRemainTerms(0l);//可持有年限
		}else {
			position.setRemainTerms(Long.parseLong(pd.getHoldPeriod()));
		}
		position.setTransferOutAmt(0l);
		position.setExpectEarning(0l);
		String productType = pd.getProductType();
		if (ProductType.STANDARD.getValue().equals(productType)) {//如果是标准产品  有到期日期    如果不是 则为空
			logger.info("判断处理逻辑产品类型是否对  "+ productType);
			try {
				position.setExpireDate(DateTimeUtils.parseDateEight(pd.getContractDt()).toDate());
			} catch (ParseException e) {
				logger.error("产品到期日转换异常");
				e.printStackTrace();
			} // 日期转化
		} else {
			
				position.setExpireDate(null);
				logger.info("到期日期为空 "+ productType);
		}
		
		position.setPossessPortion(0l);
		position.setInitialPortion(0l);
		position.setRemainTerms(0l);// 剩余期数
		if (PositionCustType.ASSET.equals(positionCustType)) {
			// 资管人
			position.setCustId(pd.getAssetId());
			position.setCustName(pd.getAssetName());
			position.setSubjectNo(CustSubject.CCY_DEP.getValue());
			position.setSubjectType(SubjectType.ASSETS.getValue());
			position.setId(CustSubject.CCY_DEP.getValue() + idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.BI_CA_SEQUENCE));
			logger.info("");
		} else if (PositionCustType.MIDDLE.equals(positionCustType)) {
			// 中间
			position.setCustId(OtherAccount.OTHER_CUST_ID_001.getValue());
			position.setCustName(OtherAccount.OTHER_CUST_ID_001.getDisplayName());
			position.setSubjectNo(CustSubject.PAYABLES.getValue());
			position.setSubjectType(SubjectType.DEBT.getValue());
			position.setId(CustSubject.PAYABLES.getValue() + idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.BI_CA_SEQUENCE));
		}
		return position;
	}

	// /**
	// * @description 得到项目当前的状态(待售、在售、售罄)
	// * @param project
	// * @param responseDto
	// */
	// private void getProjectCurrStatus(Project project ,ProjectResponseDTO
	// responseDto ){
	// String satus = project.getStatus();
	// String currStatus ="";
	// String remainingTime ="";
	// //当前的状态为募集中
	// if(satus.equals(ProductStatus.PROJECT_STATUS1.getValue())){
	//
	// Date day = DateUtils.getDate(DateUtil.getCurrentDateTime(),"yyyy-MM-dd
	// HH:mm:ss");//当前时间
	// //募集结束时间如果小于今天则状态置为已结束
	// Date endDate = project.getCollectEndDate();
	// boolean flg1 = endDate.before(day);
	// if(flg1){
	// Project projectUpdate = new Project();
	// projectUpdate.setId(project.getId());
	// projectUpdate.setStatus(ProductStatus.PROJECT_STATUS2.getValue());
	// projectDao.updateAuditStatus(projectUpdate);
	// currStatus = "0" ; //售罄
	// responseDto.setCurrStatus(currStatus);
	// remainingTime = DateUtils.getDateStr(endDate,"yyyy-M-d H:m:s");
	// responseDto.setRemainingTime(remainingTime);
	// return ;
	// }
	//
	// //募集开始时间如果大于今天则状态为待售中,小于今天则为在售中
	// Date date = project.getCollectStartDate();
	//
	// boolean flag = day.before(date);
	// if(flag){
	// currStatus = "2" ; //待售
	// if(project.getCollectStartDate() != null)
	// remainingTime =
	// DateUtils.getDateStr(project.getCollectStartDate(),"yyyy-M-d H:m:s");
	// }else{
	// currStatus = "1" ; //在售
	// if(project.getCollectEndDate() != null)
	// remainingTime =
	// DateUtils.getDateStr(project.getCollectEndDate(),"yyyy-M-d H:m:s");
	// }
	// }else {
	// //if(satus.equals(ProjectStatus.PROJECT_STATUS2.getValue())||"01".equals(project.getIsEstablish())){
	// //当前的状态为募集结束
	// currStatus = "0" ; //售罄
	// remainingTime =
	// DateUtils.getDateStr(project.getCollectEndDate(),"yyyy-M-d H:m:s");
	// }
	// responseDto.setCurrStatus(currStatus);
	// responseDto.setRemainingTime(remainingTime);
	// }

	public List<ProductTypeVo> findProjectListByname(ProductTypeVo productVo) {

		List<ProductTypeVo> ret = new ArrayList<ProductTypeVo>();

		return ret;
	}

	@Override
	public List<ProductTypeVo> findProductListByname(ProductTypeVo productVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProductStat(ProductTypeVo productVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ProductTypeVo selectByPrimaryKey(String productId, String productType) {
		ProductTypeVo productTypeVo = new ProductTypeVo();
		productTypeVo.setProductId(productId);
		productTypeVo.setProductType(productType);
		ProductCommon product = productCommonDao.selectByPrimaryKey(productId);
		List<ProductCommon> products = new ArrayList<ProductCommon>();
		products.add(product);
		productTypeVo.setProductCommonList(products);

		return productTypeVo;
	}

	@Override
	public void updateByPrimaryKeySelective(ProductTypeVo productVo) {
		// TODO Auto-generated method stub
	}
	/**
	 * @param productId
	 * @param productAudit 产品审核信息
	 * 判断募集完结审核是否通过
	 * */
	@Override
	@Transactional
	public void projectFound(String productId, ProductAudit productAudit) {
		if (productAudit.getAuditUserId() == null || "".equals(productAudit.getAuditUserId())) {
			productAudit.setAuditUserId("Donald");
		}
		
		productAudit.setId(idGeneratorService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE));
		productAudit.setAuditTime(new Date());
		productAudit.setCreatedTime(new Date());
		productAudit.setProductId(productId);
		productAudit.setIsModify(Short.valueOf("0"));
		
		logger.info("---------保存审核信息：{}---------",productAudit.toString());
		// 保存审核信息
		productAuditDao.insert(productAudit);
		
		//得到产品公共信息  0.不成立，1.成立
		ProductCommon pd = productCommonDao.selectByPrimaryKey(productId);
		
		//得到持仓人信息
		List<Position> allcPositions=positionDao.selectByProductId(productId);
		List<Position> cPositions=new ArrayList<Position>();
		for (Position custTPosition : allcPositions) {
			if (PositionCustType.INVENST.getValue().equals(custTPosition.getPositionCustType())&&
				PositionHoldStatus.HOLD.getValue().equals(custTPosition.getHoldStatus())) {//募集结束 持仓状态为 00持有
				cPositions.add(custTPosition);
			}
		}
		//下面第一个if  是判断以后前端传的用户是不是所属的资管人
		//if (pd.getAssetId().equals(dto.getCustId())) {
			if ("0".equals(productAudit.getPassed())) {
				if (pd.getDayFinanceAmt()==0) {
					pd.setProductStatus(ProductStatus.PRODUCT_STATUS08.getValue());
				}else {
					pd.setProductStatus(ProductStatus.PRODUCT_STATUS06.getValue());
				}	
				pd.setIsEstablish("00");
				//修改持仓人持有状态
				for (Position custTPosition : cPositions) {
					custTPosition.setHoldStatus(PositionHoldStatus.WAITINGREFUND.getValue());
					//成立后计算投资金额字段值
					Long investAmt = custTPosition.getPossessPortion()*custTPosition.getUnitCost();
					custTPosition.setInvenstAmt(investAmt);
					positionDao.updateByPrimaryKey(custTPosition);
				}				
			}
			if ("1".equals(productAudit.getPassed())) {
				pd.setProductStatus(ProductStatus.PRODUCT_STATUS05.getValue());
				String endDt = pd.getCollectEndDt();
				if (StringUtils.isNotEmpty(endDt)) {
					pd.setEstablishDate(DateTimeUtils.dateAfter(pd.getCollectEndDt(),1));//获取募集结束日期  第二天开始计息
				} 
				pd.setIsEstablish("01");
				repayModeType(pd, cPositions);
				for (Position custTPosition : cPositions) {
					custTPosition.setHoldStatus(PositionHoldStatus.HOLD.getValue());
					//成立后计算投资金额字段值
					Long investAmt = custTPosition.getPossessPortion()*custTPosition.getUnitCost();
					custTPosition.setInvenstAmt(investAmt);
					positionDao.updateByPrimaryKey(custTPosition);
				}
			}
		//}
		productCommonDao.updateByPrimaryKeySelective(pd);
		
		//计算该项目所有客户的利息补贴
//		subsidyResourceService.calculate(new InterestSubsidyVo(dto.getProjectId(),DateUtil.getCurrentDate()));
		//更新项目补贴总额
//		subsidyResourceService.updateProjectSubsidySumAmt(new InterestSubsidyVo(dto.getProjectId()));
		
		if (pd != null) {
			//如果产品状态不是05 抛出错误信息
			if (!ProductStatus.PRODUCT_STATUS05.getValue().equals(pd.getProductStatus())) {
				logger.error(
						"产品ID:" + productId + "," + ErrorCodeMessage.BUSINESS_PENDINGREPAY_ERROR.getDisplayName());
				throw new MyBusinessRuntimeException(ErrorCodeMessage.BUSINESS_PENDINGREPAY_ERROR.getValue(),
						ErrorCodeMessage.BUSINESS_PENDINGREPAY_ERROR.getDisplayName());
			}
			if ("1".equals(productAudit.getPassed())) {
				logger.info("---------募集审核通过---------");
				pd.setProductStatus(ProductStatus.PRODUCT_STATUS05.getValue());// 设置为待还款
			} else if ("0".equals(productAudit.getPassed())) {
				logger.info("---------募集审核失败，原因：{}---------",productAudit.getDescription());
				pd.setProductStatus(ProductStatus.PRODUCT_STATUS06.getValue());// 设置为待退款
				pd.setRemark1(productAudit.getDescription());//审核失败的原因
			}

		} else {
			logger.error("根据产品ID查询光伏系列产品表为空,id=" + productId);
			throw new MyBusinessRuntimeException("根据产品ID查询光伏系列产品表为空");
		}
	}

	private void repayModeType(ProductCommon pd, List<Position> cpList) {

		/**
		 * 根据项目信息：项目id、持仓类型、持有状态查询持仓表得出各投资人的投资信息。
		 */
		// PositionVo positionVo = new PositionVo();
		// positionVo.setProjectId(project.getId());
		// positionVo.setPositionCustType(ProjectConstants.POSITION_TYPE03);
		// List<String> list = new ArrayList<String>();
		// list.add(ProjectConstants.HOLD_STATUS06);
		// positionVo.setHoldStatus(list);
		// List<CustTPosition> cpList =
		// custTPositionDao.findPositionList(positionVo);
		if (cpList == null || cpList.size() == 0) {
			throw new MyBusinessRuntimeException("根据项目id查询不到持仓数据");
		}
		// 不同利息偿还方式存在不同处理
		if (ProjectConstants.REPAYMODE_EQUALINTEREST.equals(pd.getRepayMode().toString())) {
			// 利率计算
			rateCountUtilService.averageCapital(this.commonRepay(pd), cpList);

		} else if (ProjectConstants.REPAYMODE_ONCE.equals(pd.getRepayMode().toString())) {
			// 利率计算
			rateCountUtilService.periodRepayCapital(this.commonRepay(pd), cpList);

		} else if (ProjectConstants.REPAYMODE_EQUAL_PRINCIPAL.equals(pd.getRepayMode().toString())) {
			// 利率计算
			rateCountUtilService.averagePrincipal(this.commonRepay(pd), cpList);

		} else if (ProjectConstants.REPAYMODE_PERIOD_REPAY_CAPITAL.equals(pd.getRepayMode().toString())) {
			// 利率计算
			rateCountUtilService.monthCapPerPrincipal(this.commonRepay(pd), cpList);
		} else if (ProjectConstants.REPAYMODE_PERIOD_REPAY_REALDAY.equals(pd.getRepayMode().toString())) {
			// 利率计算
			rateCountUtilService.realDayCapPerPrincipal(this.commonRepay(pd), cpList);
		}

	}
	/**
	 * @param pd
	 * @return ProductTypeVo
	 * 利率计算公共部分
	 * */
	private ProductTypeVo commonRepay(ProductCommon pd){
		ProductTypeVo pt = new ProductTypeVo();
		pt.setProductId(pd.getProductId());
		pt.setProductType(pd.getProductType());
		List<ProductCommon> productCommonList = new ArrayList<ProductCommon>();
		productCommonList.add(pd);
		pt.setProductCommonList(productCommonList);
		return pt;
	}

}
