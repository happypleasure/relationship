package com.hyf.core.cometd;

import org.cometd.annotation.Configure;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.authorizer.GrantAuthorizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.hyf.prince.domain.Message;


@org.cometd.annotation.Service("comtdSer")
@org.springframework.stereotype.Service("cometdSprSer")
public class MessageCometDServices {
	private static final Logger logger = LoggerFactory
			.getLogger(MessageCometDServices.class);
	@Session
	private ServerSession _session;

	// 无参构造函数必须存在

	/** 看是否做成同步的 */
	public MessageCometDServices() {

	}

	@Configure("/relationship/dynamic")
	public void configure(ConfigurableServerChannel channel) {
		channel.setLazy(true);
		channel.setPersistent(true);
		channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
	}

	public void sendMessage(Message message) {
		Gson gson = new Gson();
	    String messageJson=gson.toJson(message);
		// 发布消息
		_session.getLocalSession().getChannel("/relationship/dynamic").publish(messageJson);
	}
}


