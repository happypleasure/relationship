package com.hyf.prince.resource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.hyf.prince.service.IFileService;


@Path("/file")
@Controller
public class FileResource  extends BaseResource{

	@Resource
	private IFileService fileService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);
	
	@POST
	@Path("/uploadphoto")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadUserPhoto(){
		String photoUrl = null;
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> items = sfu.parseRequest(request);
			for (FileItem fileItem : items) {
				if(fileItem.isFormField()){
				}else{
					String photoRootPath = scx.getRealPath("attach");
					String photoUrlPart = "http://" + request.getLocalName() + ':' + request.getLocalPort() +request.getContextPath();
					photoUrl = fileService.uploadUserPhoto(fileItem,photoRootPath,photoUrlPart,getCurrUserId());
				}
			}
			
		}catch(Exception e){
			logger.error("cause by:",e);
		}
		return photoUrl;
	}
	
	@POST
	@Path("/uploadattach")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadActivityAttach(){
		String attachUrl = null;
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			Integer activityId = null;
			@SuppressWarnings("unchecked")
			List<FileItem> items = sfu.parseRequest(request);
			for (FileItem fileItem : items) {
				if(fileItem.isFormField()){
					if("activityId".equals(fileItem.getFieldName())){
						activityId = Integer.valueOf(fileItem.getString());
					}
					System.out.println();
				}else{
					String attachRootPath = scx.getRealPath("attach");
					String attachUrlPart = "http://" + request.getLocalName() + ':' + request.getLocalPort() + request.getContextPath();
					attachUrl = fileService.uploadActivityAttach(fileItem,attachRootPath,attachUrlPart,getCurrUserId(),activityId);
				}
			}
		}catch(Exception e){
			logger.error("cause by:",e);
		}
		return attachUrl;
	}
	
	@GET
	@Path("/download")
	public void download(@QueryParam("url") String url){
		 //E:\workspace\relationship\WebRoot\attach
		//	http://127.0.0.1:8080/relationship/attach/activity/1024000/HsjuYu1jJc4.txt
		try{
			String[] urlArr = url.split("/");
			String attachRootPath = scx.getRealPath("attach");
			String path = attachRootPath + "\\" + urlArr[urlArr.length - 3] + "\\" + urlArr[urlArr.length - 2] + '\\' + urlArr[urlArr.length - 1] ;
			File file = new File(path);// path是根据日志路径和文件名拼接出来的
			if(!file.exists()){
				logger.error("the download file is not exists!");
				throw new RuntimeException();
			}
		    String filename = file.getName();// 获取日志文件名称
		    InputStream fis = new BufferedInputStream(new FileInputStream(path));
		    byte[] buffer = new byte[fis.available()];
		    fis.read(buffer);
		    fis.close();
		    response.reset();
		    // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
		    response.addHeader("Content-Length", "" + file.length());
		    OutputStream os = new BufferedOutputStream(response.getOutputStream());
		    response.setContentType("application/octet-stream");
		    os.write(buffer);// 输出文件
		    os.flush();
		    os.close();
		}catch(Exception e){
			logger.error("cause by:",e);
		}
	}
}
