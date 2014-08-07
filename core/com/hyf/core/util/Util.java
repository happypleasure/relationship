package com.hyf.core.util;

import java.util.Random;

public class Util {
	
	private static final String str = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Integer number = 0;
	public static Integer getConsistId(Integer num1,Integer num2){
		MurmurHash mh = new MurmurHash();
		return mh.getConsistId(num1, num2);
	}
	
	public static String getSecreatPwd(String password){
		MurmurHash mh = new MurmurHash();
		return mh.hash(password);
	}
	
	public static String getRandomString(int length) {
		if(length < 1) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int len = str.length();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(r.nextInt(len)));
		}
		return sb.toString() + ++number;
	}
}
