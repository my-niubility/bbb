package com.nbl.app.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbl.service.business.app.UserMessageApp;
import com.nbl.service.business.dto.req.MessageReqDto;
import com.nbl.service.business.dto.res.MessageResDto;
import com.nbl.services.message.MessageService;

/**
*@author:chenhongji
*@createdate：2016年8月24日 
*@version: 1.0 
*用户站内信添加、删除、查询
*/
@Service("userMessageApp")
public class UserMessageAppImpl implements UserMessageApp {
	
	@Resource
	MessageService messageService;

	@Override
	public String addMessage(MessageReqDto reqDto) {
		return messageService.saveMessage(reqDto);
	}

	@Override
	public String deleteMessage(MessageReqDto reqDto) {
		return messageService.updateStatus(reqDto);
	}

	@Override
	public List<MessageResDto> getMessagePage(MessageReqDto reqDto) {
		return messageService.getMessagePage(reqDto);
	}

	@Override
	public String getMessagePageCount(MessageReqDto reqDto) {
		return messageService.getMessagePageCount(reqDto);
	}

	@Override
	public MessageResDto getMessage(MessageReqDto reqDto) {
		return messageService.getMessage(reqDto);
	}

	@Override
	public String getUnreadMessageCount(MessageReqDto reqDto) {
		return messageService.getUnreadMessageCount(reqDto);
	}

	@Override
	public String setMessageReadStatus(MessageReqDto reqDto) {
		return messageService.updateReadStatus(reqDto);
	}

}


