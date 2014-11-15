package com.hyf.prince.resource.test;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import com.hyf.prince.domain.UserBase;
import com.hyf.prince.domain.UserDetail;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class UserResourceTest extends BaseResourceTest {
	private Client client;
	private String url = "http://127.0.0.1:8080/relationship/rest/user";
	@Before
	public void init(){
		ClientConfig clientConfig = new DefaultClientConfig();
		//clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
		super.login();
	}
	/*
	 * 注册
	 */
	@Test
	public final void registerUser() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/register");
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		UserBase user = new UserBase();
		user.setRegisterAccount("dingdang");
		user.setPassword("123456");
		String json = builder.entity(user,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 登录
	 */
/*	@Test
	public final void login() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/login");
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		UserDetail user = new UserDetail();
		user.setLoginCoordX(30.12d);
		user.setLoginCoordY(40.12d);
		user.setRegisterAccount("wangle");
		user.setPassword("123456");
		String json = builder.entity(user,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}*/
	
	/*
	 * 更新用户信息  updateuserinfo
	 */
	
	@Test
	public final void updateuserinfo() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/updateuserinfo?token="+token);
		WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
		UserDetail user = new UserDetail();
		user.setUserId(1023002);
		user.setUsername("dingdang");
		user.setAge(28);
		user.setSignature("singer");
		user.setMailAddress("234567@qq.com");
		user.setPhone("3245678");
		user.setAddress("sz");
		user.setInterest("singer");
		user.setQqCount("234565");
		user.setJob("singer");
		user.setBirthday(new java.util.Date());
		String json = builder.entity(user,MediaType.APPLICATION_JSON).post(String.class);
		System.out.println(json);
	}
	
	/*
	 * 获取用户信息  getuserbase
	 */
		@Test
		public final void getuserbase() throws JsonParseException, JsonMappingException, IOException {
			WebResource wr = client.resource(url+"/getuserbase?token="+token);
			WebResource.Builder builder = wr.queryParam("userId", "1023002").accept(MediaType.APPLICATION_JSON);
			String json = builder.get(String.class);
			System.out.println(json);
		}
		
		/*
		 * 获取用户详细信息  getuserinfo
		 */
		@Test
		public final void getuserinfo() throws JsonParseException, JsonMappingException, IOException {
			WebResource wr = client.resource(url+"/getuserinfo?token="+token);
			WebResource.Builder builder = wr.queryParam("userId", "1023001").accept(MediaType.APPLICATION_JSON);
			String json = builder.get(String.class);
			System.out.println(json);
		}
		
		/*
		 * 退出系统  
		 */
		@Test
		public final void quitSystem() throws JsonParseException, JsonMappingException, IOException {
			WebResource wr = client.resource(url+"/quitSystem?token="+token);
			WebResource.Builder builder = wr.accept(MediaType.APPLICATION_JSON);
			UserDetail user = new UserDetail();
			user.setUserId(1023000);
			String json = builder.entity(user,MediaType.APPLICATION_JSON).post(String.class);
			System.out.println(json);
		}
		
		
		
		
	
	
	/*@Test
	public final void uploadPhoto() throws JsonParseException, JsonMappingException, IOException {
		WebResource resource = client.resource(url+"/uploadphoto");
		FormDataMultiPart form = new FormDataMultiPart();
		File file = new File("G:/she.jpg");
		form.bodyPart(new FileDataBodyPart("file", file,MediaType.MULTIPART_FORM_DATA_TYPE));
		System.out.println(form.getBodyParts());
		WebResource.Builder builder = resource.type
				(MediaType.MULTIPART_FORM_DATA);
		String json = builder.post(String.class,form);
		System.out.println("resposne:"+json);
	}
	
	
	public static void main(String[] args) throws IOException {
		File file = new File("G:/she.jpg");
		System.out.println(file.length());
	}*/
}
