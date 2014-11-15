package com.hyf.prince.resource.test;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.hyf.prince.domain.UserDetail;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class BaseResourceTest {

	protected static String urlBase = "http://127.0.0.1:8080/relationship/rest";
	protected static Client client=null;
	protected static ObjectMapper objectMapper;
	protected static String token;
	
	protected static void login() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
		//client = new Client();

		WebResource wr = client.resource(urlBase+"/user/login");
		
		UserDetail user = new UserDetail();
		user.setLoginCoordX(60.12d);
		user.setLoginCoordY(80.12d);
		user.setRegisterAccount("happy");
		user.setPassword("123456");
		String json = wr
				.accept(MediaType.APPLICATION_JSON)
				.entity(user, MediaType.APPLICATION_JSON)
				.post(String.class);
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(json);
			JSONObject data = jsonobject.getJSONObject("data");
			token = data.getString("token");
			System.out.println("token : "+token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(json);
	}
}
