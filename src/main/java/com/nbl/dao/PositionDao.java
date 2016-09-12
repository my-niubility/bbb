package com.nbl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nbl.common.vo.PageVO;
import com.nbl.model.Position;
import com.nbl.model.vo.PositionVo;

@Repository
public interface PositionDao {
	int deleteByPrimaryKey(String id);

	int insert(Position record);

	int insertSelective(Position record);

	Position selectByPrimaryKey(String id);

	Position selectByBusUniqCond(Map<String, String> map);

	int updateByPrimaryKeySelective(Position record);

	int updateByPrimaryKey(Position record);

	/**
	 * @param productId
	 * @return
	 * @description:查询募集期间 已卖出的融资金额（不包括冻结到平台名义下的）
	 */
	public long selectInvenstAmtSum(@Param("productId") String productId);

	/**
	 * @param productId
	 * @return
	 * @description:查询募集期间 已卖出的融资金额（包括冻结到平台名义下的）
	 */
	public long selectFinanceAmtSum(@Param("productId") String productId);

	/**
	 * @param productId
	 * @return
	 * @description:查询募集成功后各投资人的预期收益
	 */
	public List<Float> selectExpectEarning(@Param("productId") String productId);

	/**
	 * @param productId
	 * @param holdStatus
	 * @return
	 * @description:募集截止日更新持仓信息状态
	 */
	public int updateHoldStatusByProductId(@Param("productId") String productId,
			@Param("holdStatus") String holdStatus);

	/**
	 * @param map
	 * @return
	 * @description:根据项目ID和持有状态修改其持有状态
	 */
	public int updateHoldStatus(Map<String, Object> map);

	/**
	 * @param positionVo
	 * @return
	 * @description:根据查询条件动态查询对应的持仓数据
	 */
	public List<Position> findPositionList(@Param("positionVo") PositionVo positionVo);

	/**
	 * @param positionVo
	 * @return
	 * @description:根据查询条件动态查询持仓总数
	 */
	public int getPositionCount(@Param("positionVo") PositionVo positionVo);

	/**
	 * @param positionVo
	 * @return
	 * @description:根据查询条件动态查询总收益
	 */
	public Long getTotalEarnings(@Param("positionVo") PositionVo positionVo);

	/**
	 * @param id
	 * @return
	 * @description:根据ID查询持仓数据，同时对该条数据加锁
	 */
	public Position selectByPrimaryKeyForUpdate(String id);

	/**
	 * @param pageVO
	 * @param positionVo
	 * @return
	 * @description:分页查询项目信息
	 */
	public List<Position> findListPage(@Param("pageVO") PageVO<PositionVo> pageVO,
			@Param("positionVo") PositionVo positionVo);

	/**
	 * @param record
	 * @description:根据项目ID,持仓人类型修改持有状态及份额
	 */
	public void updateHoldStatusAndPossPortion(Position record);

	/**
	 * @param record
	 * @description:修改资管人当前持有份额
	 */
	public void updateAssetPossPortion(Position record);

	/**
	 * @param record
	 * @description:更新账户份额信息
	 */
	public void updateAccountAmt(Position record);

	/**
	 * @return
	 * @description:锁定持仓表
	 */
	public List<Integer> lockPositionTable();

	/**
	 * @description:释放持仓表
	 */
	public void releasePositionTable();

	/**
	 * @param positionVo
	 * @return
	 * @description:根据查询条件动态统计总投资
	 */
	public Long getTotaInvest(@Param("positionVo") PositionVo positionVo);

	/**
	 * @param productId
	 * @return
	 * @description:根据项目 ID查询持仓记录
	 */
	public List<Position> selectByProductId(String productId);

	/**
	 * @param positionVo
	 * @return
	 * @description:根据查询条件动态查询购买人数
	 */
	public int getInvestRsCount(@Param("positionVo") PositionVo positionVo);
	
	/**
	 * @param id
	 * @return
	 * @description:根据身份证号查询个人详情
	 */
    Position selectById(String id);
    
    /**
	 * @param pageVO
	 * @param position
	 * @return
	 * @description:分页查询
	 */
	public List<Position> findListPageTable(@Param("pageVO")PageVO<Position> pageVO,@Param("position")Position position);
	
	/**
	 * @param position
	 * @return
	 * @description:分页查询统计总数
	 */
	public int findListPageCountTable(@Param("position")Position position);
	
	/**
	 * @param productId 
	 * @param positionCustType
	 * @description:查询该产品 所有持仓人类型为 01投资
	 * 
	 * */
	public List<Position> selectByProductIdAndHold(String productId,String positionCustType);
}