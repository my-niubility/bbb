package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nbl.common.vo.PageVO;
import com.nbl.model.Message;
import com.nbl.service.business.dto.req.MessageReqDto;
import com.nbl.service.business.dto.res.MessageResDto;

public interface MessageDao {
    int deleteByPrimaryKey(String id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    
    /**
     * 分页查询用户消息
     * @param pageVO
     * @param messageIds 
     * @param reqDto
     * @return
     */
	List<Message> findListPage(@Param("pageVO")PageVO<MessageReqDto> pageVO, @Param("message")Message message, @Param("messageIds") List<String> messageIds);
	/**
	 * 分页查询用户信息的总条数
	 * @param message
	 * @param recUserId
	 * @return
	 */
	int findListPageCount(@Param("message") Message message, @Param("recUserId") String recUserId);

	List<Message> findListPage(@Param("pageVO") PageVO<MessageReqDto> pageVO, @Param("message")Message message,@Param("recUserId")String recUserId);
}