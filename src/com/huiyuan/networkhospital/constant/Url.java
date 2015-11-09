package com.huiyuan.networkhospital.constant;

public class Url {
	private final static String URL = "http://183.221.242.75:8089/APPInterface/user/" ;
	private final static String ASHX = ".ashx" ;
	private static String getURL(String str) {
		String url = null;
		url =URL+str+ASHX;
		return url;
	}
	/**
	 * 用户登录模块
	 */
	public static String userLogin=getURL("Login");
	
	
	/**
	 * 用户获取
	 */
	public static String department=getURL("Visit");

	
	/**
	 * 用户获取教练列表模块
	 */
	public static String userMovementGuidance=getURL("MovementGuidance");

	/**
	 * 用户端口医生上门模块
	 */
	
	public static String Operation="http://183.221.242.75:8089/APPInterface/Appointment/Operation.ashx" ;
	/**
	 * 图片下载
	 */
	public static String imageupload="http://183.221.242.75:8089/upload/";
	/**
	 * 上传图片
	 */
	public static String imageup="http://183.221.242.75:8089/APPInterface/user/Login.ashx";
	/**
	 * 用户端口预约挂号模块
	 */
	public static String Registers="http://183.221.242.75:8089/APPInterface/user/Registers.ashx";
	/**
	 * 聊天记录模块
	 */
	public static String Chat="http://183.221.242.75:8089/APPInterface/user/Chat.ashx";
	/**
	 * 康复教程
	 */
	public static String kangfujiaocheng="http://183.221.242.75:8089/APPInterface/com/Course.ashx";
	/**
	 * 新增意见反馈
	 */
	public static String Opinion="http://183.221.242.75:8089/APPInterface/com/Opinion.ashx";
}
