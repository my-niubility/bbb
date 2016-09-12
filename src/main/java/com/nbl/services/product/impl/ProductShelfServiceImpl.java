package com.nbl.services.product.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.nbl.dao.ProductCommonDao;
import com.nbl.dao.ProductShelfDao;
import com.nbl.model.ProductCommon;
import com.nbl.model.ProductShelf;
import com.nbl.model.vo.ProductShelfVo;
import com.nbl.service.business.constant.ProductShelfNumber;
import com.nbl.service.business.constant.ProductStatus;
import com.nbl.service.manager.dto.ProductShelfDto;
import com.nbl.services.product.ProductShelfService;

@Service("productShelfService")
public class ProductShelfServiceImpl implements ProductShelfService {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductShelfServiceImpl.class); 
	
	@Resource
	private ProductShelfDao productShelfDao;
	
	@Resource
	private ProductCommonDao productCommonDao;
	
	@Override
	public boolean setNewProductShelf(Map<String, List<String>> productMap) {
		
		Set<String> set = productMap.keySet();
		
		Iterator<String> it = set.iterator();
		
		while(it.hasNext()){
			
			String  productId = it.next();
			//依次处理产品对应的货架
			List<String> list = productMap.get(productId);
			
			List<ProductShelf> entityList = new ArrayList<ProductShelf>();
			
			for(String shelf:list){
				
				ProductShelf entity = new ProductShelf();
				if(ProductShelfNumber.PRODUCT_SHELF01.getValue().equals(shelf)){
					entity.setExhType("01");
				}else if(ProductShelfNumber.PRODUCT_SHELF02.getValue().equals(shelf)){
					entity.setExhType("02");
				}else if(ProductShelfNumber.PRODUCT_SHELF03.getValue().equals(shelf)){
					entity.setExhType("03");
				}else if(ProductShelfNumber.PRODUCT_SHELF04.getValue().equals(shelf)){
					entity.setExhType("04");
				}else if(ProductShelfNumber.PRODUCT_SHELF05.getValue().equals(shelf)){
					entity.setExhType("05");
				}else if(ProductShelfNumber.PRODUCT_SHELF06.getValue().equals(shelf)){
					entity.setExhType("06");
				}else if(ProductShelfNumber.PRODUCT_SHELF07.getValue().equals(shelf)){
					entity.setExhType("07");
				}
				entity.setProductId(productId);
				entity.setStatus("1");
				Date tm = new Date();
				entity.setCreateDate(tm);
				entity.setUpdateDate(tm);
				entityList.add(entity);
			}
			
			//入库处理
			productShelfDao.batchInsertShelf(entityList);
			//更新产品表状态为“募集中”
			ProductCommon record = new ProductCommon();
			record.setProductId(productId);
			record.setProductStatus(ProductStatus.PRODUCT_STATUS03.getValue());
			productCommonDao.updateByPrimaryKeySelective(record);
			
		}
		return true;
	}

	@Override
	public boolean offAndUpProductShelf(Map<String, List<String>> productMap,String[] productAll) {
		
		Set<String> set = productMap.keySet();
		
		Iterator<String> it = set.iterator();
		
		//遍历数据productAll 
		for (String productIds : productAll) {
			//如果set中不包含数据中的元素则全部为下架
			boolean falg = set.contains(productIds);
			if(!falg){
				ProductShelf ps = new ProductShelf();
				
				List<ProductShelfVo> oldList = productShelfDao.getShelfProductByProductId(productIds);
				List<String> oldShelfNumList = Lists.newArrayList();
				
				//对比传递过来的数据与原始版本的数据
				for(ProductShelfVo shelf : oldList){
					
					String shelfNum = shelf.getShelfNum();
					oldShelfNumList.add(shelfNum);
				}
				//正向对比找出需要下架的货架点
				for(String oldString:oldShelfNumList){
						ps.setProductId(productIds);
						//此货架点需要对此产品做下架处理
						ps.setExhType(oldString);
						//下架
					    productShelfDao.batchUpdateShelf(ps);
				}
				logger.info("<<<<<<<<<产品下架数据 productId"+ps+"-------"+productIds);
			}
			
		}
		
		while(it.hasNext()){
				
				String  productId = it.next();
				
				List<ProductShelfVo> oldList = productShelfDao.getShelfProductByProductId(productId);
				
				//依次处理产品对应的货架
				List<String> newList = productMap.get(productId);
				List<String> newShelfNumList = Lists.newArrayList();
				for(String shelf:newList){
					
					if(ProductShelfNumber.PRODUCT_SHELF01.getValue().equals(shelf)){
						newShelfNumList.add("01");
					}else if(ProductShelfNumber.PRODUCT_SHELF02.getValue().equals(shelf)){
						newShelfNumList.add("02");
					}else if(ProductShelfNumber.PRODUCT_SHELF03.getValue().equals(shelf)){
						newShelfNumList.add("03");
					}else if(ProductShelfNumber.PRODUCT_SHELF04.getValue().equals(shelf)){
						newShelfNumList.add("04");
					}else if(ProductShelfNumber.PRODUCT_SHELF05.getValue().equals(shelf)){
						newShelfNumList.add("05");
					}else if(ProductShelfNumber.PRODUCT_SHELF06.getValue().equals(shelf)){
						newShelfNumList.add("06");
					}else if(ProductShelfNumber.PRODUCT_SHELF07.getValue().equals(shelf)){
						newShelfNumList.add("07");
					}
				}

				List<String> oldShelfNumList = Lists.newArrayList();
				
				//对比传递过来的数据与原始版本的数据
				for(ProductShelfVo shelf : oldList){
					
					String shelfNum = shelf.getShelfNum();
					oldShelfNumList.add(shelfNum);
				}
				
				//正向对比找出需要下架的货架点
				for(String oldString:oldShelfNumList){
					
					if(!newShelfNumList.contains(oldString)){
						//此货架点需要对此产品做下架处理
						ProductShelf ps = new ProductShelf();
						ps.setProductId(productId);
						ps.setExhType(oldString);
						//下架
						productShelfDao.batchUpdateShelf(ps);
					}
					
				}
				
				
				List<ProductShelf> insertList = Lists.newArrayList();
				//方向对比找出需要上架的货架点
				for(String newString : newShelfNumList){
					
					if(!oldShelfNumList.contains(newString)){
						//此货架需要做上架处理
						ProductShelf ps = new ProductShelf();
						ps.setProductId(productId);
						ps.setExhType(newString);
						ps.setStatus("1");
						Date tm = new Date();
						ps.setCreateDate(tm);
						ps.setUpdateDate(tm);
						insertList.add(ps);
					}
				}
				//上架
				if(insertList.size()>0){
					productShelfDao.batchInsertShelf(insertList);
				}
				
			}
			
		return true;

	}

	@Override
	public List<ProductShelfDto> getAllUpShelfProduct() {
		
		List<ProductShelfDto> retList = new ArrayList<ProductShelfDto>();
		
		List<ProductShelfVo> list = productShelfDao.getAllUpShelfProduct();
		logger.info("-------产品货架数据数量：{}------",list.size());
		if(list !=null && list.size()>0){
			Iterator<ProductShelfVo> it = list.iterator();
			while(it.hasNext()){
				ProductShelfDto retDto = new ProductShelfDto();
				ProductShelfVo ps = it.next();
				BeanUtils.copyProperties(ps,retDto);
				retList.add(retDto);
				logger.info("-------产品货架数据：{}------",retDto.toString());
			}
		}
		
		return retList;
	}

}
