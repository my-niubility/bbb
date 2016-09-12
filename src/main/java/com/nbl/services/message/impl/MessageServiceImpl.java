package com.nbl.services.message.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nbl.common.constants.ProjectConstants;
import com.nbl.common.constants.SeqenceConstant;
import com.nbl.common.vo.PageVO;
import com.nbl.dao.MessageDao;
import com.nbl.dao.UserMessageDao;
import com.nbl.model.Message;
import com.nbl.model.UserMessage;
import com.nbl.service.business.constant.MessageReadStatus;
import com.nbl.service.business.constant.MessageStatus;
import com.nbl.service.business.dto.req.MessageReqDto;
import com.nbl.service.business.dto.res.MessageResDto;
import com.nbl.services.message.MessageService;
import com.nbl.services.product.IdGeneratorService;
import com.nbl.util.DateTimeUtils;
import com.nbl.utils.BeanParseUtils;

/**
*@author:chenhongji
*@createdate：2016年8月22日 
*@version: 1.0 
*/
@Service("messageService")
public class MessageServiceImpl implements MessageService{
	
	private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Resource
	private IdGeneratorService idGeneratorAppService;
	@Resource
	private MessageDao messageDao;
	@Resource
	private UserMessageDao userMessageDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public String saveMessage(MessageReqDto reqDto) {
		logger.info("[enter insertMessage inparam is :]" + reqDto.toString());
		Message message = new Message();
		
		BeanParseUtils.copyProperties(reqDto, message);
		String messageId = idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
		Date date = new Date();
		message.setId(messageId);
		message.setCreateTime(date);
		message.setStatus(MessageStatus.INIT.getValue());
		logger.info("[message :]" + message.toString());
		messageDao.insert(message);
		
		List<String> userIds = reqDto.getUserIds();
		List<UserMessage> userMessages=new ArrayList<>();
		int userIdCounts = userIds.size();
		//拼装批量添加的UserMessge信息
		for(int i=0;i<userIdCounts;i++){
			UserMessage userMessage = new UserMessage();
			String id=idGeneratorAppService.getNext_13Bit_Sequence(SeqenceConstant.BI_PK_SEQUENCE);
			String userId=userIds.get(i);
			userMessage.setId(id);
			userMessage.setRecUserId(userId);
			userMessage.setMessageId(messageId);
			userMessage.setCreateTime(date);
			userMessage.setIsreadstatus(MessageReadStatus.NOT_READ.getValue());
			userMessage.setStatus(MessageStatus.INIT.getValue());
			//logger.info("[userMessage :]" + userMessage.toString());
			userMessages.add(userMessage);
			logger.info("[userMessages :]" + userMessages.toString());
		}
		
		userMessageDao.batchInsert(userMessages);
		
		return message.getId();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public MessageResDto getMessage(MessageReqDto reqDto) {
		String messageId = reqDto.getId();
		String recUserId=reqDto.getUserIds().get(0);
		logger.info("[enter getMessage inparam is :]" + messageId+" "+recUserId);
		//消息详情
		Message message = messageDao.selectByPrimaryKey(messageId);
		String isreadStatus =userMessageDao.getIsreadStatus(messageId,recUserId);
		//判断消息是否已读
		if(MessageReadStatus.NOT_READ.getValue().equals(isreadStatus)){
			//设置该消息为已读
			UserMessage userMessage = new UserMessage();
			userMessage.setMessageId(messageId);
			userMessage.setRecUserId(recUserId);
			userMessage.setIsreadstatus(MessageReadStatus.READ.getValue());
			userMessageDao.updateIsreadstatus(userMessage);
		}
		
		MessageResDto resDto = new MessageResDto();
		if (message==null) {
			return null;
		}
		BeanParseUtils.copyProperties(message, resDto);
		resDto.setCustId(message.getUserId());
		return resDto;
	}
	@Override
	public String getUnreadMessageCount(MessageReqDto reqDto){
		logger.info("[enter getMessage inparam is :]" + reqDto.toString());
		UserMessage userMessage = new UserMessage();
		String recUserId=reqDto.getUserIds().get(0);
		userMessage.setRecUserId(recUserId);
		userMessage.setIsreadstatus(MessageReadStatus.NOT_READ.getValue());
		userMessage.setStatus(MessageStatus.INIT.getValue());
		userMessage.setMessageType(reqDto.getMessageType());
		logger.info("[userMessage :]" + userMessage.toString());
		Integer messageCount = userMessageDao.getUnreadMessageCount(userMessage);
		String count = messageCount==null?null:messageCount.toString();
		return count ;
	}

	@Override
	public List<MessageResDto> getMessagePage(MessageReqDto reqDto) {
		logger.info("【enter getMessagePage input params is】:" + reqDto.toString());
		PageVO<MessageReqDto> pageVO = new PageVO<>();
		//设置分页每页显示条数
		int recordNum = reqDto.getRecordNum() == 0 ? ProjectConstants.RECORD_NUM
				: reqDto.getRecordNum();
		pageVO.setSize(recordNum);
		//设置每页开始下标
		pageVO.setStartSize(reqDto.getStartIndex());
		String recUserId = reqDto.getUserIds().get(0);
		Message message = new Message();
		BeanParseUtils.copyProperties(reqDto, message);
		//查询用户消息集合
		List<Message> messages = messageDao.findListPage(pageVO,message,recUserId);
		if (messages == null || messages.size() == 0) {
			logger.warn("user message is null");
			return null;
		} 
		List<MessageResDto> messageResDtos = new ArrayList<>();
		for (Message mess : messages) {
			MessageResDto messageResDto = new MessageResDto();
			messageResDto.setCreateTime(new DateTimeUtils((mess.getCreateTime())).toDateTimeString());
			messageResDto.setCustId(mess.getUserId());
			messageResDto.setContent(mess.getContent());
			messageResDto.setId(mess.getId());
			messageResDto.setIsreadstatus(mess.getIsreadstatus());
			messageResDto.setStatus(mess.getStatus());
			messageResDto.setTitle(mess.getTitle());
			messageResDto.setMessageType(mess.getMessageType());
			messageResDtos.add(messageResDto);
		}
		
		return messageResDtos;
	}

	@Override
	public String getMessagePageCount(MessageReqDto reqDto) {
		logger.info("【enter getMessagePageCount input params is】:" + reqDto.toString());
		//查询客户userId在T_user_message表中对应的messageId
		String recUserId = reqDto.getUserIds().get(0);
		Message message = new Message();
		message.setMessageType(reqDto.getMessageType());
		Integer messageCount=messageDao.findListPageCount(message,recUserId);
		String count = messageCount==null?null:messageCount.toString();
		return count;
	}

	@Override
	public String updateStatus(MessageReqDto reqDto) {
		logger.info("【enter updateStatus input params is】:" + reqDto.toString());
		UserMessage userMessage = new UserMessage();
		//此处可能抛异常，后续处理
		String recUserId = reqDto.getUserIds().get(0);
		List<String> messageIds = reqDto.getMessageIds();
		userMessage.setRecUserId(recUserId);
		userMessage.setStatus(MessageStatus.DELETE.getValue());
		Integer updateCount = userMessageDao.updateStatus(userMessage,messageIds);
		String count = updateCount==null?null:updateCount.toString();
		return count;
	}

	@Override
	public String updateReadStatus(MessageReqDto reqDto) {
		logger.info("【enter updateReadStatus input params is】:" + reqDto.toString());
		UserMessage userMessage = new UserMessage();
		String recUserId = reqDto.getUserIds().get(0);
		List<String> messageIds = reqDto.getMessageIds();
		userMessage.setRecUserId(recUserId);
		userMessage.setIsreadstatus(MessageReadStatus.READ.getValue());
		Integer updateCount = userMessageDao.updateReadStatus(userMessage,messageIds);
		String count = updateCount==null?null:updateCount.toString();
		return count;
	}
}


