package com.hyf.prince.resource.test;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import com.hyf.prince.domain.Message;
import com.hyf.prince.domain.MessageBox;
import com.hyf.prince.domain.MessageLine;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class MessageResourceTest extends BaseResourceTest{
	private Client client;
	private String url = "http://127.0.0.1:8080/relationship/rest/message";
	@Before
	public void init(){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
		super.login();
	}
	
	/*
	 * 发送消息
	 */
	@Test
	public final void sendMessage() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/sendmessage?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		Message message = new Message();
		message.setReceiveUserId(1023002);
		message.setContent("cometd message!");
		String json = builder.entity(message,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 改变是否阅读的状态
	 */
	@Test
	public final void updatereadflag() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/updatereadflag?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		MessageBox mb = new MessageBox();
		mb.setConsistId(-1086534344);
		String json = builder.entity(mb,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 删除某一条消息
	 */
	@Test
	public final void deleteone() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/deleteone?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		MessageBox message = new MessageBox();
		message.setMessageId(1025004);
		String json = builder.entity(message,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 删除和某个人的消息会话
	 */
	@Test
	public final void deletesession() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/deletesession?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		MessageLine messageLine = new MessageLine();
		messageLine.setConsistId(1604446764);
		String json = builder.entity(messageLine,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 获取未读消息数目
	 */
	@Test
	public final void totalunreadnum() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/totalunreadnum?token="+token);
		WebResource.Builder builder = wr
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	/*
	 * 查询与所有人的会话列表以及未读消息数目
	 */
	@Test
	public final void sessionlist() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/sessionlist?token="+token);
		WebResource.Builder builder = wr
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	/*
	 * 查询与所有人的会话列表以及未读消息数目
	 */
	@Test
	public final void onesession() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/onesession?token="+token);
		WebResource.Builder builder = wr.queryParam("userId", "1023000")
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
}
