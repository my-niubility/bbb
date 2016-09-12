package com.nbl.services.sendmsg;

/**
 * 站内信发送器
 * 
 * @author AlanMa
 *
 */
public interface StaMsgSender {
	public void sendStationMessage(Object... msgInfo);
}
