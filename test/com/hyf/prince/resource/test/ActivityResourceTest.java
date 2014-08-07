package com.hyf.prince.resource.test;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import com.hyf.prince.domain.Activity;
import com.hyf.prince.domain.ActivityComment;
import com.hyf.prince.domain.ActivityMember;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ActivityResourceTest extends BaseResourceTest{
	private Client client;
	private String url = "http://127.0.0.1:8080/relationship/rest/activity";
	@Before
	public void init(){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
		super.login();
	}
	
	/*
	 * 发布活动
	 */
	@Test
	public final void publish() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/publish?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		Activity activity = new Activity();
		activity.setTitle("qwertyuio");
		activity.setType(1);
		activity.setAddress("宝安中心2");
		activity.setDescription("play...football........");
		activity.setCoordX(30.23d);
		activity.setCoordY(40.23d);
		activity.setMaxUserNum(10);
		activity.setPlanStartTime(new Date());
		String json = builder.entity(activity,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 修改活动
	 */
	@Test
	public final void update() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/update?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		Activity activity = new Activity();
		activity.setActivityId(1024000);
		activity.setTitle("xxxxxxxxxx !>>>>>>>>>>>>>>>>");
		activity.setType(1);
		activity.setAddress("xxxxxxxxxxxxx");
		activity.setDescription("xxxxxxxxxx..............................");
		activity.setCoordX(60.23d);
		activity.setCoordY(80.23d);
		activity.setMaxUserNum(10);
		activity.setPlanStartTime(new Date());
		String json = builder.entity(activity,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 参加活动
	 */
	@Test
	public final void join() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/join?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		ActivityMember am = new ActivityMember();
		am.setActivityId(1024000);
		String json = builder.entity(am,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 喜欢活动
	 */
	@Test
	public final void like() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/like?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		Activity activity = new Activity();
		activity.setActivityId(1024000);
		String json = builder.entity(activity,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	
	/*
	 * 评论活动
	 */
	@Test
	public final void comment() throws JsonParseException, JsonMappingException, IOException {
		
		WebResource wr = client.resource(url+"/comment?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		ActivityComment ac = new ActivityComment();
		ac.setActivityId(1024000);
		for(int i = 1;i<10;i++){
			ac.setContent("太棒了"+(i+1));
			String json = builder.entity(ac,MediaType.APPLICATION_JSON).post(String.class);
			System.out.println(json);
		}
	}
	
	/*
	 * 获取活动详情
	 */
	@Test
	public final void detail() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/detail");
		WebResource.Builder builder = wr.queryParam("activityId", "1024000")
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	/*
	 * 获取活动详情
	 */
	@Test
	public final void getActivityMembers() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/members");
		WebResource.Builder builder = wr.queryParam("activityId", "1024000")
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	/*
	 * 获取活动的评论
	 */
	@Test
	public final void getActivityComments() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/commentlist");
		WebResource.Builder builder = wr.queryParam("activityId", "1024000")
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	
	/*
	 * 获取活动的附件图片
	 */
	@Test
	public final void getActivityAttachs() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/attachs");
		WebResource.Builder builder = wr.queryParam("activityId", "1024000")
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	
	/*
	 *获取附近的活动列表
	 */
	@Test
	public final void getSurrondActivitys() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/arroundlist");
		WebResource.Builder builder = wr.queryParam("coorX", "30")
				.queryParam("coorY", "40")
				.queryParam("nearRange", "1")
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	/*
	 * 获取我发布的活动列表
	 */
	@Test
	public final void sponsorlist() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/sponsorlist?token="+token);
		WebResource.Builder builder = wr
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
	
	/*
	 * 获取我发布的活动列表
	 */
	@Test
	public final void memberlist() throws JsonParseException, JsonMappingException, IOException{
		WebResource wr = client.resource(url + "/memberlist?token="+token);
		WebResource.Builder builder = wr
				.getRequestBuilder();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		String json = builder.get(String.class);
		System.out.println(json);
	}
}
