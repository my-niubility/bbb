package com.nbl.utils;

import com.alibaba.rocketmq.client.producer.SendStatus;

public class TestUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SendStatus ss = SendStatus.FLUSH_DISK_TIMEOUT;
		
		if("FLUSH_DISK_TIMEOUT".equals(ss.toString())){
			System.out.println("ss.same:"+ss);

		}

		String nn = String.format("%03d", 455);
		
		System.out.println("----"+nn);
	}

}
