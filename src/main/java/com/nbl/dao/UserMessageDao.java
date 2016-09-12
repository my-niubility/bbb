package com.nbl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nbl.model.UserMessage;

public interface UserMessageDao {
    int deleteByPrimaryKey(String id);

    int insert(UserMessage record);

    int insertSelective(UserMessage record);

    UserMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserMessage record);

    int updateByPrimaryKey(UserMessage record);
    
    UserMessage selectByMessageId(String messageId);
    
    /**
     * 批量添加userMessage
     * @param userMessages
     * @return
     */
	int batchInsert(List<UserMessage> userMessages);
	/**
	 * 根据messageId、recUserId更新消息的是否已读状态
	 * @param userMessage
	 * @return
	 */
	int updateIsreadstatus(UserMessage userMessage);
	/**
	 * 查询用户未读消息个数
	 * @param userMessage
	 * @return
	 */
	Integer getUnreadMessageCount(UserMessage userMessage);
	/**
	 * 根据recUserId查询出用户的messageId的集合
	 * @param recUserId
	 * @return
	 */
	List<String> getMessIdByrecUserId(String recUserId);
	/**
	 * 将用户信息置为删除状态
	 * @param userMessage
	 * @param messageIds
	 * @return
	 */
	int updateStatus(@Param("userMessage")UserMessage userMessage,@Param("messageIds")List<String> messageIds);
	/**
	 * 判断信息是否已读
	 * @param messageId
	 * @param recUserId
	 * @return
	 */
	String getIsreadStatus(@Param("messageId") String messageId, @Param("recUserId")String recUserId);
	/**
	 * 设置信息状态为已读
	 * @param userMessage
	 * @param messageIds
	 * @return
	 */
	int  updateReadStatus(@Param("userMessage") UserMessage userMessage, @Param("messageIds") List<String> messageIds);
}