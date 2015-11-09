package com.huiyuan.networkhospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;

import com.easemob.EMCallBack;
import com.huiyuan.networkhospital.common.util.CrashHandler;
import com.huiyuan.networkhospital.common.util.LogcatHelper;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.huiyuan.networkhospital.entity.hepeng.GM_pay;
import com.huiyuan.networkhospital.entity.hepeng.MD_pay;
import com.huiyuan.networkhospital.entity.hepeng.R_pay;
import com.huiyuan.networkhospital.entity.zlk.MUserData;
import com.huiyuan.networkhospital.entity.zlk.ReminderCount;
import com.huiyuan.networkhospital.entity.zlk.SUserData;
import com.huiyuan.networkhospital.module.huanxin.DemoHXSDKHelper;

public class NApplication extends Application {

	private List AllAcivity;
	/**
	 * 应用是否发布
	 */
	public static boolean isRelease = true;

	public static Context context; 

	public static String device_token;

	/**
	 * 当前帐号id
	 */
	public static String userid;

	/**
	 * 用户的手机号
	 */
	public static String phone;

	/**
	 * 用户身份证号
	 */
	public static String IC;

	public static String sex;

	public static String username;

	//用户头像名称
	public static String photo;

	//运动指导生成订单时用的CID
	public static String CID;
	//运动指导支付完成后需要的教练电话
	public static String cphone;

	//从复诊拿药列表进入支付取得的医生姓名。
	public static String doctorname;
	//用于评价的教练名字。
	public static String coachname;
	//用于上传的头像图片
	public static Bitmap headBitmap;
	//用于显示的头像图片
	public static Bitmap ImgheadBitmap;

	public static String Vid;
	//复诊拿药快递的地址,收货人姓名，电话号码
	public static GM_pay gm_pay;
	//从推送直接进来的vid 和医生，用户的电话和名字。
	public static MUserData muserdate;
	//从推送直接进来的vid 和教练，用户的电话和名字。
	public static SUserData suserdate;
	//判断是哪一个在使用支付的变量，对应的com.huiyuan.networkhospital.constant。
	public static int pay_style;
	//R_PaymentActivity 支付成功后所需传的值
	public static R_pay r_pay;
	//Dv_PaymentActivity 支付成功后所需传的值
	public static Dv_people dv_people;
	//MakeDetailsActivity 支付成功后所需传的值
	public static MD_pay md_pay;

	public static Context applicationContext;
	private static NApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";

	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();

	/**
	 * 通过环信发的命令消息
	 */
	public static String cmd;

	/**
	 * 聊天消息的发送者
	 */
	public static char role;
	/**
	 * 用户从通知栏进入聊天界面的标记  正常进入为null  通知栏进入为notifyBar
	 */
	public static String way;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

	}

	public void onCreate() {
		super.onCreate();
		LogcatHelper.getInstance(this).start();
		NApplication.context=getApplicationContext();
		AllAcivity = new ArrayList<Map<String, String>>();
		applicationContext = this;
		instance = this; 
		//开启以后程序在运行中报错不会崩溃,在logcat中过滤Sandy就可查看报的错.
		//此代码用于正常情况崩溃不报错时使用.
//		CrashHandler handler = CrashHandler.getInstance();
//		handler.init(getApplicationContext()); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器

		/**
		 * this function will initialize the HuanXin SDK
		 * 
		 * @return boolean true if caller can continue to call HuanXin related APIs after calling onInit, otherwise false.
		 * 
		 * 环信初始化SDK帮助函数
		 * 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
		 * 
		 * for example:
		 * 例子：
		 * 
		 * public class DemoHXSDKHelper extends HXSDKHelper
		 * 
		 * HXHelper = new DemoHXSDKHelper();
		 * if(HXHelper.onInit(context)){
		 *     // do HuanXin related work
		 * }
		 */
		hxSDKHelper.onInit(applicationContext);
	}

	public void addActivity(Activity activity) {
		try {
			AllAcivity.add(activity);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void destoryAllActivity() {
		try {
			for (int i = 0; i < AllAcivity.size(); i++) {
				//				Log.e("", AllAcivity.get(i).toString());
				((Activity) AllAcivity.get(i)).finish();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	//以下是环信方法

	public static NApplication getInstance() {
		return instance;
	}


	/**
	 * 获取当前登陆用户名
	 *
	 * @return
	 */
	public String getUserName() {
		return hxSDKHelper.getHXId();
	}

	/**
	 * 获取密码
	 *
	 * @return
	 */
	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	/**
	 * 设置用户名
	 *
	 * @param user
	 */
	public void setUserName(String username) {
		hxSDKHelper.setHXId(username);
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setPassword(String pwd) {
		hxSDKHelper.setPassword(pwd);
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout(final boolean isGCM,final EMCallBack emCallBack) {
		// 先调用sdk logout，在清理app中自己的数据
		hxSDKHelper.logout(isGCM,emCallBack);
	}


}