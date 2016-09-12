package com.nbl.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nbl.service.business.dto.req.MessageReqDto;
import com.nbl.service.business.dto.res.MessageResDto;
import com.nbl.services.message.MessageService;
import com.nbl.services.message.impl.MessageServiceImpl;


/**
*@author:chenhongji
*@createdate：2016年8月23日 
*@version: 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext.xml", "classpath*:/spring/applicationContext-dataSource.xml" })
public class TestMessage {
	
	private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Resource
	MessageService messageService ;
	
	@Test
	public void testSaveMessage(){
		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		for(int i =0;i<10;i++){
			MessageReqDto reqDto = new MessageReqDto();
			//reqDto.setId("0001");
			reqDto.setUserId("admin");
			reqDto.setMessageType ("0");
			reqDto.setTitle("测试消息"+i);
			reqDto.setContent("测试消息体"+i);
			reqDto.setStatus("1");
			reqDto.setCreateTime(new Date());
			List<String> userIds = new ArrayList<>();
			userIds.add("user01");
			userIds.add("user02");
			reqDto.setUserIds(userIds);
			List<String> custIds = new ArrayList<>();
			custIds.add("cust01");
			custIds.add("cust02");
			//reqDto.setCustIds(custIds);
			System.out.println(reqDto.toString());
			String messId = messageService.saveMessage(reqDto);
			System.out.println(messId);
		}
		
		System.out.println("~~~~~~~~~~~~end~~~~~~~~~~~~~~~~");
		
	}
	
	@Test
	public void testSelectMessage(){
		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		MessageReqDto reqDto = new MessageReqDto();
		reqDto.setId("2016082300000515");
		List<String> userIds = new ArrayList<>();
		userIds.add("CP2016072500002");
		reqDto.setUserIds(userIds);
		MessageResDto resDto = messageService.getMessage(reqDto);
		logger.info("[resDto:]"+resDto.toString());
		System.out.println("~~~~~~~~~~~~end~~~~~~~~~~~~~~~~");
	}
	
	@Test
	public void testGetUnreadMessageCount(){
		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		MessageReqDto reqDto = new MessageReqDto();
		List<String> userIds = new ArrayList<>();
		userIds.add("CP001201609020000012");
		reqDto.setUserIds(userIds);
		reqDto.setMessageType("0");
		String count = messageService.getUnreadMessageCount(reqDto);
		System.out.println("count:"+count);
		System.out.println("~~~~~~~~~~~~end~~~~~~~~~~~~~~~~");
	}
	
	@Test
	public void testPageMessage(){
		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		MessageReqDto reqDto = new MessageReqDto();
		List<String> userIds = new ArrayList<>();
		userIds.add("CP2016072500002");
		reqDto.setUserIds(userIds);
		//reqDto.setMessageType("1");
		reqDto.setRecordNum(-1);
		//reqDto.setStartIndex(4);
		List<MessageResDto> list = messageService.getMessagePage(reqDto);
		System.out.println("listSize:"+list.size());
		System.out.println("list:"+list.toString());
		System.out.println("~~~~~~~~~~~~end~~~~~~~~~~~~~~~~");
	}
	
	@Test
	public void tetsPageMessageCount(){
		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		MessageReqDto reqDto = new MessageReqDto();
		List<String> userIds = new ArrayList<>();
		userIds.add("CP2016072500002");
		reqDto.setUserIds(userIds);
		//reqDto.setMessageType("1");
		String count = messageService.getMessagePageCount(reqDto);
		System.out.println("【message count:】"+count);
		System.out.println("~~~~~~~~~~~~end~~~~~~~~~~~~~~~~");
	}
	
	@Test
	public void testUpdateStatus(){
		System.out.println("~~~~~~~~~~~~start~~~~~~~~~~~~~~~~");
		MessageReqDto reqDto = new MessageReqDto();
		List<String> userIds = new ArrayList<>();
		userIds.add("user02");
		reqDto.setUserIds(userIds);
		List<String> messageIds = new ArrayList<>();
		messageIds.add("2016082300000458");
		messageIds.add("2016082300000461");
		reqDto.setMessageIds(messageIds);
		/*uIds.add("2016082300000404");
		uIds.add("2016082300000453");
		reqDto.setUIds(uIds);*/
		String count = messageService.updateStatus(reqDto);
		System.out.println("【updateStatus count:】"+count);
		System.out.println("~~~~~~~~~~~~end~~~~~~~~~~~~~~~~");
	}
}


