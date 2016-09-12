package com.nbl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.TIncome;
import com.nbl.model.vo.IncomeVo;
import com.nbl.model.vo.TIncOrdVo;

@Repository
public interface TIncomeDao {
	int deleteByPrimaryKey(String id);

	int insert(TIncome record);

	int insertSelective(TIncome record);

	TIncome selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(TIncome record);

	int updateByPrimaryKey(TIncome record);

	public Map<String, Object> selectSumAmtAndCapital(@Param("projectId") String projectId, @Param("proIncomeTerm") String proIncomeTerm, @Param("repayMode") String repayMode);

	/**
	 * @param incomeVo
	 * @return
	 * @description:根据查询条件查询投资收入信息
	 */
	public List<TIncome> getTIncomes(@Param("incomeVo") IncomeVo incomeVo);

	/**
	 * @param pageVO
	 * @param incomeVo
	 * @return
	 * @description:分页查询
	 */
	public List<TIncome> findIncomes(@Param("pageVO") PageVO<IncomeVo> pageVO, @Param("incomeVo") IncomeVo incomeVo);

	/**
	 * @param incomeVo
	 * @return
	 * @description:分页查询总数
	 */
	public Integer findIncomeCount(@Param("incomeVo") IncomeVo incomeVo);

	/**
	 * @param id
	 * @return
	 * @description:根据流水ID查询详情
	 */
	TIncome selectById(String id);

	/**
	 * @param pageVO
	 * @param income
	 * @return
	 * @description:标准分页查询
	 */
	public List<TIncome> findListPage(@Param("pageVO") PageVO<TIncome> pageVO, @Param("income") TIncome income);

	/**
	 * @param TIncome
	 * @return
	 * @description:标准分页查询统计总数
	 */
	public int findListPageCount(@Param("income") TIncome income);

	/**
	 * @param incomeVo
	 * @return
	 */
	public Integer selectIncomeInFundFlowCount(@Param("incomeVo") IncomeVo incomeVo);

	/**
	 * @param incomeVo
	 * @return
	 */
	public List<TIncOrdVo> selectIncomeInFundFlow(@Param("pageVO") PageVO<IncomeVo> pageVO, @Param("incomeVo") IncomeVo incomeVo);

}