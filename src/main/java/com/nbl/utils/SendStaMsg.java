package com.nbl.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nbl.service.business.constant.ParamKeys;
import com.nbl.service.business.dto.req.MessageReqDto;
import com.nbl.services.context.AppContextService;
import com.nbl.services.message.MessageService;
import com.nbl.services.parameter.GeneralParameterService;

/**
 * 发送站内信业务逻辑实现类
 * 
 * @author AlanMa
 *
 */
public class SendStaMsg implements Runnable {
	private final static Logger logger = LoggerFactory.getLogger(SendStaMsg.class);

	private GeneralParameterService generalParameterService = AppContextService.getBean("generalParameterService");
	private MessageService messageService;
	/**
	 * 用户编号
	 */
	private String custId;
	/**
	 * 信息类型（枚举类UserMessageType）
	 */
	private String msgType;
	/**
	 * 信息标题KEY（枚举类ParamKeys）
	 */
	private String msgTitle;
	/**
	 * 信息内容KEY（枚举类ParamKeys）
	 */
	private String msgCont;
	/**
	 * 信息标题变量值
	 */
	private String[] titlesVariables;
	/**
	 * 信息内容变量值
	 */
	private String[] contentVariables;

	public void setTitlesVariables(String... titlesVariables) {
		this.titlesVariables = titlesVariables;
	}

	public void setContentVariables(String... contentVariables) {
		this.contentVariables = contentVariables;
	}

	public SendStaMsg() {
		super();
	}

	public SendStaMsg(String custId, String msgType, String msgTitle, String msgCont) {
		super();
		this.custId = custId;
		this.msgType = msgType;
		this.msgTitle = msgTitle;
		this.msgCont = msgCont;
	}

	/**
	 * 替换消息内容配置中的通配符%s
	 * 
	 * @param variables
	 */
	public String fillContent() {
		msgCont = generalParameterService.getValueByCode(msgCont);
		if (contentVariables != null) {
			for (int index = 0; index < contentVariables.length; index++) {
				if (msgCont.indexOf("%s") != -1) {
					msgCont = msgCont.replaceFirst("%s", contentVariables[index]);
				}
			}
		}
		return msgCont;
	}

	/**
	 * 替换消息标题配置中的通配符%s
	 * 
	 * @param variables
	 */
	public String fillTitle() {
		msgTitle = generalParameterService.getValueByCode(msgTitle);
		if (titlesVariables != null) {
			for (int index = 0; index < titlesVariables.length; index++) {
				if (msgTitle.indexOf("%s") != -1) {
					msgTitle = msgCont.replaceFirst("%s", titlesVariables[index]);
				}
			}
		}
		return msgTitle;
	}

	@Override
	public void run() {
		logger.info("【send station message begin】");
		messageService = AppContextService.getBean("messageService");
		MessageReqDto reqDto = new MessageReqDto();
		reqDto.setUserId(generalParameterService.getValueByCode(ParamKeys.CUST_ID_ZGPT.getValue()));
		List<String> receptUserIds = new ArrayList<String>();
		receptUserIds.add(custId);
		reqDto.setUserIds(receptUserIds);
		reqDto.setMessageType(msgType);
		reqDto.setTitle(fillTitle());
		reqDto.setContent(fillContent());
		messageService.saveMessage(reqDto);
		logger.info("【send station message finish】");
	}

}