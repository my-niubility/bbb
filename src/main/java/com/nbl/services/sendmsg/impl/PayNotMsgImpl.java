package com.nbl.services.sendmsg.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nbl.common.constants.ComConst;
import com.nbl.common.dto.CommRespDto;
import com.nbl.model.TradeOrder;
import com.nbl.service.business.constant.ParamKeys;
import com.nbl.service.business.constant.UserMessageType;
import com.nbl.service.business.dto.req.MessageReqDto;
import com.nbl.services.message.MessageService;
import com.nbl.services.parameter.GeneralParameterService;
import com.nbl.services.sendmsg.StaMsgSender;
import com.nbl.util.AmtParseUtil;
import com.nbl.util.HideSensInfoUtil;
import com.nbl.utils.DateTimeUtils;
import com.nbl.utils.threadpool.ThreadPoolProcessor;

/**
 * 发送提現通知
 * 
 * @author AlanMa
 *
 */
@Service("payNotMsg")
public class PayNotMsgImpl implements StaMsgSender {
	private final static Logger logger = LoggerFactory.getLogger(PayNotMsgImpl.class);

	private TradeOrder tradeOrder = null;
	private CommRespDto commRespDto = null;
	/**
	 * 用户编号
	 */
	private String custId;
	@Resource
	private GeneralParameterService generalParameterService;
	@Resource
	private MessageService messageService;

	@Override
	public void sendStationMessage(Object... msgInfo) {
		custId = (String) msgInfo[0];
		tradeOrder = (TradeOrder) msgInfo[1];
		commRespDto = (CommRespDto) msgInfo[2];
		SendStaMsgPay msg = new SendStaMsgPay(UserMessageType.BUSINESS.getValue());
		ThreadPoolProcessor tpProcessor = ThreadPoolProcessor.getInstanceFixed(ComConst.TP_MAX);
		tpProcessor.execute(msg);
	}

	class SendStaMsgPay implements Runnable {

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

		public SendStaMsgPay() {
			super();
		}

		public SendStaMsgPay(String msgType) {
			super();
			this.msgType = msgType;
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

			if (ComConst.SUCCESS.equals(commRespDto.getResIdentifier().getReturnType())) {
				// 信息内容KEY
				msgTitle = ParamKeys.SMTS_BUY.getValue();
				msgCont = ParamKeys.SMCS_BUY.getValue();
				// 内容变量值: 0-购买时间，1-商品名称，2-购买总金额
				contentVariables = new String[3];
			} else {
				// 信息内容KEY
				msgTitle = ParamKeys.SMTF_BUY.getValue();
				msgCont = ParamKeys.SMCF_BUY.getValue();
				// 内容变变量值: 0-购买时间，1-商品名称，2-购买总金额，3-失败原因
				contentVariables = new String[4];
				contentVariables[3] = commRespDto.getResIdentifier().getReturnMsg();
			}
			contentVariables[0] = new DateTimeUtils(tradeOrder.getCreateTime()).toDateTimeString();
			contentVariables[1] = HideSensInfoUtil.hideCardNum(tradeOrder.getProductNane());
			contentVariables[2] = AmtParseUtil.longToStrAmt(tradeOrder.getTradeTalAmt());
			// 发送信息
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

}
