package com.hyf.prince.resource.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class FileResourceTest extends BaseResourceTest{

	private Client client;
	private String url = "http://127.0.0.1:8080/relationship/rest/file";
	@Before
	public void init(){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
		super.login();
	}
	
	@Test
	public final void uploadphoto() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		WebResource wr = client.resource(url+"/uploadphoto?token="+token);
		FormDataMultiPart form = new FormDataMultiPart();
		URI uri = new URI("http://e.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=2b38c8820a46f21fdd395601974d0005/18d8bc3eb13533fa7e791557a8d3fd1f40345bf8.jpg");
		File file = new File(uri);
		form.field("filename", file.getName());
		System.out.println("filename:"+file.getName());
		form.bodyPart(new FileDataBodyPart("file",file,MediaType.MULTIPART_FORM_DATA_TYPE));
		WebResource.Builder builder = wr.type
				(MediaType.MULTIPART_FORM_DATA);
		String json = builder.post(String.class,form);
		System.out.println("resposne:"+json);
	}
	
	@Test
	public final void uploadattach() throws JsonParseException, JsonMappingException, IOException {
		WebResource wr = client.resource(url+"/uploadattach?token="+token);
		FormDataMultiPart form = new FormDataMultiPart();
		File file = new File("E:/attach.txt");
		form.field("activityId", "1024000");
		System.out.println("filename:"+file.getName());
		form.bodyPart(new FileDataBodyPart("file",file,MediaType.MULTIPART_FORM_DATA_TYPE));
		WebResource.Builder builder = wr.type
				(MediaType.MULTIPART_FORM_DATA);
		String json = builder.post(String.class,form);
		System.out.println("resposne:"+json);
	}
	
}
