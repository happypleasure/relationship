package com.hyf.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPupload {
	
	
	public static void main(String[] args) throws SocketException, IOException {
		//192.168.1.101:21:
		FTPClient fc = getFtpClient("192.168.1.101", "admin", "123456", 21);
		uploadFileToBBK(new File("D:/sql.txt"), fc, "wangle");
		closeFtp(fc);
		System.out.println("it' Ok!");
	}
	
	public static FTPClient getFtpClient(String serverIP,String userName,String password,int port) throws SocketException, IOException{
        int reply;
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(serverIP, port);
            reply = ftpClient.getReplyCode();
           System.out.println("reply:"+reply);
           if(!FTPReply.isPositiveCompletion(reply)){
               ftpClient.disconnect();
               System.out.println("登陆FTP失败");
               return null;
           }
           ftpClient.login(userName, password);
           ftpClient.setDataTimeout(2000);
           ftpClient.enterLocalPassiveMode();
           ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
           System.out.println("ftpClient:"+ftpClient);
           System.out.println(ftpClient.getLocalAddress());
         return ftpClient;
   }

	
	/**
     * 上传文件到文件服务器中
     * @param file  需上传的文件
     * @param ftpClient  
     * @param workDirectory   FTP服务器的相对目录
     * @return
     * @throws IOException
     */
    public static String uploadFileToBBK(File file,FTPClient ftpClient,String workDirectory) throws IOException{
        
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.makeDirectory(workDirectory);
        ftpClient.changeWorkingDirectory(workDirectory);
        BufferedInputStream  fiStream =new BufferedInputStream(new FileInputStream(file));
        System.out.println("fiStream:"+fiStream);
        boolean  flag = ftpClient.storeFile(new String((file.getName()).getBytes("UTF-8"),"iso-8859-1"),fiStream);
        System.out.println("flag:"+flag);
        fiStream.close();
        if(flag){
            return "OK";
        }else{
            return "";
        }
    }
    
    public static void closeFtp(FTPClient ftpClient){
        if(ftpClient!=null && ftpClient.isConnected()){
            try {
                boolean isLogOut = ftpClient.logout();
                if(isLogOut){
                    System.out.println("成功关闭ftp连接");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("关闭FTP服务器异常");
            }finally{
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("关闭服务器连接异常");
                }
            }
            
        }
    }
}
