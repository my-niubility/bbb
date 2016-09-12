package com.nbl.utils.rocketmq;

import java.util.Map;

import com.alibaba.rocketmq.common.message.Message;
/**
 * @author Donald
 * @createdate 2016年4月8日
 * @version 1.0 
 * @description :
 */
public class ConsumerUserDispatch {

	public boolean dispatchAndHandle(Message mes){
		
		//parse sequenceId(keys),serviceName(tags)
		String tags = mes.getProperty("TAGS");
		String sequenceId = mes.getProperty("KEYS");
		//parse body
		byte[] body = mes.getBody();
		String jsonString = body.toString();
		
		return true;
	}
	
}
