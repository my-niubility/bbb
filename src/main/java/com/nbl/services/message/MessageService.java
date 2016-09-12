package com.nbl.services.message;

import java.util.List;

import com.nbl.service.business.dto.req.MessageReqDto;
import com.nbl.service.business.dto.res.MessageResDto;

/**
*@author:chenhongji
*@createdate：2016年8月22日 
*@version: 1.0 
*站内信接口
*/

public interface MessageService {
	/**
	 * 添加信息
	 * @param record
	 * @return
	 */
	String saveMessage(MessageReqDto reqDto);
	
	/**
	 * 查看消息详情
	 * @param messageId
	 * @return
	 */
	MessageResDto getMessage(MessageReqDto reqDto);
	
	/**
	 * 获取用户未读消息个数
	 * @param reqDto
	 * @return
	 */
	String getUnreadMessageCount(MessageReqDto reqDto);
	/**
	 * 分页查询用户信息
	 * @param reqDto
	 * @return
	 */
	List<MessageResDto> getMessagePage(MessageReqDto reqDto);
	
	/**
	 * 分页查询时获取指定条件下的消息总数
	 * @param reqDto
	 * @return
	 */
	String getMessagePageCount(MessageReqDto reqDto);
	/**
	 * 将用户信息置为删除状态
	 * @param reqDto
	 * @return
	 */
	String updateStatus(MessageReqDto reqDto);
	/**
	 * 将用户信息置为已读状态
	 * @param reqDto
	 * @return
	 */
	String updateReadStatus(MessageReqDto reqDto);
	
}


