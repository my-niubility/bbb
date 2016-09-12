package com.nbl.services.product.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.nbl.common.dto.CommRespDto;
import com.nbl.common.exception.MyBusinessCheckException;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.ProductShelfDao;
import com.nbl.model.ProductAudit;
import com.nbl.model.ProductCommon;
import com.nbl.model.vo.ProIdxVo;
import com.nbl.model.vo.ProductCommonVo;
import com.nbl.model.vo.ProductTypeVo;
import com.nbl.service.business.constant.OrderByFlag;
import com.nbl.service.business.constant.PrdIdxOrderByCol;
import com.nbl.service.business.constant.ProductStatus;
import com.nbl.service.business.dto.req.MutiCndQryPrdsDto;
import com.nbl.service.business.dto.req.PrdDtlInfoQryDto;
import com.nbl.service.business.dto.req.PrdExhiInfoDto;
import com.nbl.service.business.dto.req.StandardProductQueryDto;
import com.nbl.service.business.dto.res.PrdDetResultDto;
import com.nbl.service.business.dto.res.PrdExhResDto;
import com.nbl.service.business.dto.res.PrdExhResItemDto;
import com.nbl.services.product.ProductQueryService;
import com.nbl.services.product.feature.PrdFeatureService;
import com.nbl.utils.BeanParseUtils;

@Service("productQueryService")
public class ProductQueryServiceImpl implements ProductQueryService {

	private final static Logger logger = LoggerFactory.getLogger(ProductQueryServiceImpl.class);

	@Resource
	private ProductCommonDao productCommonDao;
	@Resource
	private ProductShelfDao productShelfDao;

	@Resource
	private PrdFeatureService prdFeatureService;

	@Override
	public List<ProductTypeVo> getProductList(ProductTypeVo productVo) {
		return null;
	}

	@Override
	public List<ProductTypeVo> findProductListByname(ProductTypeVo productVo) {
		return null;
	}

	@Override
	public int getProductStat(ProductTypeVo productVo) {
		return 0;
	}

	@Override
	public void productAudit(ProductTypeVo productVo, ProductAudit projectAudit) {

	}

	@Override
	public ProductTypeVo selectByPrimaryKey(String productId, String productType) {
		return null;
	}

	@Override
	public void updateByPrimaryKeySelective(ProductTypeVo productVo) {

	}

	@Override
	public CommRespDto productExhibitionQuery(PrdExhiInfoDto reqDto) {
		logger.info("[enter productExhibitionQuery inparam is :]" + reqDto.toString());
		PrdExhResDto result = new PrdExhResDto();
		List<ProductCommon> prdExhs = productCommonDao.selectByExhType(reqDto.getExhType());
		if (prdExhs == null || prdExhs.size() == 0) {
			logger.warn("【no product to exhibition】");
			return new CommRespDto().success();
		}

		List<PrdExhResItemDto> list = parseEntityToVo(prdExhs);
		result.addPrdExhResItemDtos(list);

		return new CommRespDto().success(result);
	}

	private List<PrdExhResItemDto> parseEntityToVo(List<ProductCommon> eleProducts) {
		List<PrdExhResItemDto> list = new ArrayList<PrdExhResItemDto>();
		for (ProductCommon eleProduct : eleProducts) {
			PrdExhResItemDto newRecharge = new PrdExhResItemDto();
			BeanParseUtils.copyPropertiesAmt(eleProduct, newRecharge, "unitCost");
			list.add(newRecharge);
		}
		return list;
	}

	@Override
	public CommRespDto productsMutiCondQuery(MutiCndQryPrdsDto mutiCndQryPrdsDto) {
		logger.info("[enter productsMutiCondQuery inparam is :]" + mutiCndQryPrdsDto.toString());
		PrdExhResDto result = null;
		ProIdxVo proIdxVo = new ProIdxVo();
		BeanUtils.copyProperties(mutiCndQryPrdsDto, proIdxVo);
		PageVO<ProIdxVo> pageVO = new PageVO<ProIdxVo>();

		if (StringUtils.isNotEmpty(mutiCndQryPrdsDto.getOrderColumn()) && StringUtils.isNotEmpty(mutiCndQryPrdsDto.getOrderFlag())) {
			StringBuffer orderBy = new StringBuffer();
			orderBy.append(PrdIdxOrderByCol.parseOf(mutiCndQryPrdsDto.getOrderColumn()));
			orderBy.append(" ");
			orderBy.append(OrderByFlag.parseOf(mutiCndQryPrdsDto.getOrderFlag()));
			pageVO.setOrderBy(orderBy.toString());
		}

		pageVO.setStartSize(mutiCndQryPrdsDto.getStartIndex());
		pageVO.setSize(mutiCndQryPrdsDto.getRecordNum());
		List<String> productStatus = new ArrayList<String>();
		productStatus.add(ProductStatus.PRODUCT_STATUS03.getValue());
		productStatus.add(ProductStatus.PRODUCT_STATUS04.getValue());
		productStatus.add(ProductStatus.PRODUCT_STATUS05.getValue());
		productStatus.add(ProductStatus.PRODUCT_STATUS09.getValue());
		proIdxVo.setProductStatus(productStatus);
		List<ProductCommon> prdInfoTrans = productCommonDao.selectByMutiCond(pageVO, proIdxVo);
		if (prdInfoTrans == null || prdInfoTrans.size() == 0) {
			logger.warn("【no product to exhibition】");
			return new CommRespDto().success();
		}
		List<PrdExhResItemDto> list = parseEntityToVo(prdInfoTrans);

		result = new PrdExhResDto(list);

		return new CommRespDto().success(result);
	}

	@Override
	public CommRespDto productDetailsQuery(PrdDtlInfoQryDto prdDtlInfoQryDto) {
		Object result = null;
		CommRespDto commresp = null;
		ProductCommon prdCom = null;
		PrdExhResItemDto prdComDto = null;
		PrdDetResultDto prdDetial = null;
		try {
			prdCom = productCommonDao.selectByPrimaryKey(prdDtlInfoQryDto.getProductId());
			if (prdCom != null) {
				prdComDto = new PrdExhResItemDto();
				BeanParseUtils.copyPropertiesAmt(prdCom, prdComDto, "unitCost");
				result = prdFeatureService.getProductFeatureInfo(prdCom.getProductLittleType(), prdDtlInfoQryDto.getProductId());
				prdDetial = new PrdDetResultDto(prdComDto, result);
			}

		} catch (MyBusinessCheckException e) {
			logger.error("[getProductFeatureInfo exception]:", e);
			return new CommRespDto().fail(e.getErrorCode(), e.getErrMsgKey());
		}

		commresp = prdDetial == null ? new CommRespDto().success() : new CommRespDto().success(prdDetial);

		return commresp;
	}

	@Override
	public String productsMutiCondCountQuery(MutiCndQryPrdsDto mutiCndQryPrdsDto) {
		String countStr = null;
		ProIdxVo proIdxVo = new ProIdxVo();
		BeanUtils.copyProperties(mutiCndQryPrdsDto, proIdxVo);
		List<String> productStatus = new ArrayList<String>();
		productStatus.add(ProductStatus.PRODUCT_STATUS03.getValue());
		productStatus.add(ProductStatus.PRODUCT_STATUS04.getValue());
		productStatus.add(ProductStatus.PRODUCT_STATUS05.getValue());
		productStatus.add(ProductStatus.PRODUCT_STATUS09.getValue());
		proIdxVo.setProductStatus(productStatus);
		Integer count = productCommonDao.selectByMutiCondCount(proIdxVo);
		countStr = count == null ? null : Integer.toString(count);
		return countStr;
	}

	@Override
	public List<StandardProductQueryDto> pageListQueryStProduct(PageVO<StandardProductQueryDto> pageVO, StandardProductQueryDto reqDto) {

		PageVO<ProductCommonVo> pgVo = new PageVO<ProductCommonVo>();
		ProductCommonVo productCommonVo = new ProductCommonVo();

		BeanUtils.copyProperties(reqDto, productCommonVo);
		productCommonVo.setRepayMode(reqDto.getRentMode() == null ? null : new Short(reqDto.getRentMode()));
		productCommonVo.setCollectStartDt(reqDto.getStartdt());
		productCommonVo.setCollectEndDt(reqDto.getEnddt());

		BeanUtils.copyProperties(pageVO, pgVo);

		List<ProductCommon> list = productCommonDao.findListPage(pgVo, productCommonVo);

		if (list != null) {
			List<StandardProductQueryDto> retList = new ArrayList<StandardProductQueryDto>();
			Iterator<ProductCommon> it = list.iterator();
			while (it.hasNext()) {
				ProductCommon product = it.next();
				logger.info("--------查询产品ID:{}、STATUS:{}---------", product.getProductId(), product.getProductStatus());
				StandardProductQueryDto retDto = new StandardProductQueryDto();

				BeanUtils.copyProperties(product, retDto, "createTime");

				retDto.setCreateTime(product.getCreateTime());
				retDto.setEnddt(product.getCollectEndDt());
				retDto.setStartdt(product.getCollectStartDt());
				retDto.setScale(product.getFinanceScale().toString());
				retDto.setRate(product.getExpectEarnRate().toString());
				retDto.setUnitcost(product.getUnitCost().toString());
				if (product.getInvestMax() != null && !"".equals(product.getInvestMax())) {
					retDto.setScaleLimit(product.getInvestMax().toString());
				}
				retDto.setBackday(product.getRepayRentDt());// 每月返租日期
				retDto.setRentType(product.getRentType());
				retDto.setNonbackday(product.getNonmBackDt());
				retDto.setBlockday(product.getbLockPeriod());// 赎回锁定期
				retDto.setTlockday(product.gettLockPeriod());// 转让锁定期
				retDto.setHoldday(product.getHoldPeriod());
				retDto.setEstablish(product.getIsEstablish());// 是否成立
				retDto.setFinancePortion(product.getFinancePortion());//给募集总额传值
				if (product.getEstablishMinimum() != null && !"".equals(product.getEstablishMinimum())) {
					retDto.setXxEstabValue(product.getEstablishMinimum().toString());
				}
				if (product.getEstablishRatio() != null && !"".equals(product.getEstablishRatio())) {
					retDto.setBlEstabValue(product.getEstablishRatio().toString());
				}
				if (product.getEstablishRatio() != null && !"".equals(product.getEstablishRatio())) {
					retDto.setScaleLimit(product.getEstablishRatio().toString());
				}
				retDto.setRentMode(product.getRepayMode().toString());
				logger.info("------返回产品dto:------:{}", retDto.toString());
				retList.add(retDto);
			}
			return retList;
		} else {
			return null;
		}
	}

	@Override
	public int pageCountQueryStProduct(StandardProductQueryDto reqDto) {

		ProductCommonVo productCommonVo = new ProductCommonVo();
		BeanUtils.copyProperties(reqDto, productCommonVo);
		productCommonVo.setRepayMode(reqDto.getRentMode() == null ? null : new Short(reqDto.getRentMode()));
		productCommonVo.setCollectStartDt(reqDto.getStartdt());
		productCommonVo.setCollectEndDt(reqDto.getEnddt());

		return productCommonDao.findListPageCount(productCommonVo);
	}

	@Override
	public StandardProductQueryDto detailQuery(String productId) {

		StandardProductQueryDto retDto = new StandardProductQueryDto();
		ProductCommon product = productCommonDao.selectByPrimaryKey(productId);
		BeanUtils.copyProperties(product, retDto, "createTime");
		retDto.setCreateTime(product.getCreateTime());
		retDto.setEnddt(product.getCollectEndDt());// 募集结束日期
		retDto.setEndTime(product.getCollectEndTime());// 募集结束时间
		retDto.setStartTime(product.getCollectStartTime());// 募集开始时间
		retDto.setProductTittleType(product.getProductLittleType());
		retDto.setFinancePortion(product.getFinancePortion());//募集总额
		retDto.setContrday(product.getContractDt());// 合同到期日
		retDto.setInvestMin(product.getInvestMin());
		retDto.setRedType(product.getRedType());
		retDto.setStartdt(product.getCollectStartDt());
		retDto.setScale(product.getFinanceScale().toString());
		retDto.setRate(product.getExpectEarnRate().toString());
		retDto.setUnitcost(product.getUnitCost().toString());
		if (product.getInvestMax() != null && !"".equals(product.getInvestMax())) {
			retDto.setScaleLimit(product.getInvestMax().toString());
		}
		retDto.setBackday(product.getRepayRentDt());// 每月返租日期
		retDto.setRentType(product.getRentType());
		retDto.setNonbackday(product.getNonmBackDt());
		retDto.setBlockday(product.getbLockPeriod());// 赎回锁定期
		retDto.setTlockday(product.gettLockPeriod());// 转让锁定期
		retDto.setHoldday(product.getHoldPeriod());
		if (product.getSubsidyRate()!=null && !"".equals(product.getSubsidyRate())) {
			retDto.setSubsidyRate(product.getSubsidyRate().toString());// 补贴年化利率
		}
		retDto.setEstablish(product.getIsEstablish());// 是否成立
		if (product.getEstablishMinimum() != null && !"".equals(product.getEstablishMinimum())) {
			retDto.setXxEstabValue(product.getEstablishMinimum().toString());
		}
		if (product.getEstablishRatio() != null && !"".equals(product.getEstablishRatio())) {
			retDto.setBlEstabValue(product.getEstablishRatio().toString());
		}
		if (product.getEstablishRatio() != null && !"".equals(product.getEstablishRatio())) {
			retDto.setScaleLimit(product.getEstablishRatio().toString());
		}
		retDto.setRentMode(product.getRepayMode().toString());
		return retDto;
	}

}
