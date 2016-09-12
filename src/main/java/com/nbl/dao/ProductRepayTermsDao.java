package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.ProductRepayTerms;
import com.nbl.model.vo.PrdRepTrmVo;
import com.nbl.service.manager.dto.PrdRepTrmDto;

@Repository
public interface ProductRepayTermsDao {
	int deleteByPrimaryKey(String id);

	int insert(ProductRepayTerms record);

	int insertSelective(ProductRepayTerms record);

	ProductRepayTerms selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ProductRepayTerms record);

	int updateByPrimaryKey(ProductRepayTerms record);

	List<ProductRepayTerms> selectByCond(@Param("pageVO")PageVO<PrdRepTrmDto> pageVO,@Param("prtb") PrdRepTrmVo prtb);

	int selectByCondCount(@Param("prtb")PrdRepTrmVo prtb);
}