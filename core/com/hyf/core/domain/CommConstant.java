package com.hyf.core.domain;

public class CommConstant {
	/*********************** 通用返回错误码 *********************************/
	/** 正常返回的时候 */
	public static final String ERRC_SUCCESS = "0000000";
	/** 参数错误，少传，或者类型错误的时候返回0000001 */
	public static final String ERRC_PARAMERR = "0000001";
	/** 当因为服务器或者数据库等非配置或者操作流程不当而引起的错误,用户统一接受到得错误代码是0000002 */
	public static final String ERRC_SERVERERR = "0000002";
	/** 服务正常调用，没产生异常，但是没有执行成功，譬如数据已经更新，再更新数据则返回的更新条数为0000003 */
	public static final String ERRC_FAILURE = "0000003";
	/** 服务正常调用，当没有权限访问一定的资源时返回提示代码0000004*/
	public static final String ERRC_NOAUTHORITY = "0000004";
	/** 当文件、笔记、任务被删除之后，再次访问的错误代码*/
	public static final String ERRC_NOTEXISTS = "0000005";
	/*********************** 返回值类型end *********************************/
	/********用户模块错误 ********/
	/****账号已存在******/
	public static final String USEREXIST = "账号已存在";
	/****请输入账号******/
	public static final String ACCOUNTERROR = "请输入账号";
	/****请输入长度大于6的密码******/
	public static final String PWDERROR = "请输入长度大于6的密码";
	
	
	public static final Integer SPONSOR = 1;
	
	public static final Integer MEMBER = 2;
}
