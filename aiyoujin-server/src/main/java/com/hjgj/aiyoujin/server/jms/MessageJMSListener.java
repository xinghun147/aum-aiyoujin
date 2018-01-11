package com.hjgj.aiyoujin.server.jms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class MessageJMSListener  {

	private static final Logger logger = LoggerFactory.getLogger(MessageJMSListener.class);
	
	
	public void handleMessage(String msg) {
    	 logger.info(String.format("receive message: %s", msg));
	}
	
//	@RabbitListener(queues = Constants.TRADE_TO_ACCOUNT_QUEUE)
//	public void process(User user) {
//		System.out.println(JSON.toJSONString(user));
//	}

}
