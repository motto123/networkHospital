package com.huiyuan.networkhospital.module;

import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.google.gson.Gson;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.ExceptionUtil;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.constant.User;
import com.huiyuan.networkhospital.entity.UserInfo;
import com.huiyuan.networkhospital.module.huanxin.DemoHXSDKHelper;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.usermanager.LoginActivity_;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;


@EActivity(R.layout.activity_loading)
public class LoadingActivity extends BaseActivity {
	private AsyncHttpResponseHandler login_handler;
	private boolean succeed=false;

	private long time=30;
	private String phone,pass;
	private UserInfo userInfo = null;
	private User user = new User(LoadingActivity.this);

	private boolean isFirst;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_loading);
		dohandler();
	}
	@AfterViews
	public void waiting(){
		judgeFist();
		initUmeng();
		isLogin();
		final Timer timer=new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				time--;
				if(time<=15&&succeed==true){//最少执行1500，最大执行3000，登录成功则跳转
					Tools.startActivity(LoadingActivity.this, MainActivity_.class);
					NApplication.phone = userInfo.getPhone();
					NApplication.userid=userInfo.getID();
					NApplication.username = userInfo.getName();
					NApplication.photo = userInfo.getPhoto();
					NApplication.sex = userInfo.getSex();
					NApplication.IC = userInfo.getIC();

					timer.cancel();
					LoadingActivity.this.finish();
				}
				if(time<=0&&succeed==false){
					Intent intent=new Intent(LoadingActivity.this, LoginActivity_.class);
					intent.putExtra(GlobalConstant.KEY_DATA, isFirst);
					/**
					 * 用于表示自动登录失败，isLogin 为false表示自动登录失败
					 */
					if(pass!= null&&!pass.equals("")){
						intent.putExtra("isLogin", "false");
					}else {
						intent.putExtra("isLogin", "");
					}
					LoadingActivity.this.startActivity(intent);
					timer.cancel();
					LoadingActivity.this.finish();
				}
			}

		};

		timer.schedule(task,0, 100);

	}

	/**
	 * 判断是否是第一次登陆
	 */
	private void judgeFist() {
		SharedPreferences sp=getSharedPreferences("baseinfo", Activity.MODE_PRIVATE);
		isFirst=sp.getBoolean("first", true);
		if(isFirst){
			//第一次登陆
			Editor editor=sp.edit();
			editor.putBoolean("first", false);
			editor.commit();
		}
	}
	/**
	 * 初始化友盟相关数据
	 */
	private void initUmeng() {
		PushAgent mPushAgent = PushAgent.getInstance(NApplication.context);
		mPushAgent.enable();//开启推送
		PushAgent.getInstance(NApplication.context).onAppStart();//开启统计
		String device_token = UmengRegistrar.getRegistrationId(NApplication.context);
		if(null!=device_token){
			NApplication.device_token=device_token;
		}

	}
	@Override
	public void onBackPressed() {
		System.exit(0);
		super.onBackPressed();
	}
	/**
	 * 判断是不是自动登录，如果是自动登录就登录。
	 */
	public void isLogin(){
		pass = user.getPass(); 
		phone = user.getPhone();
		if (!pass.equals("")&&!phone.equals("")&&pass!=null) {
			Log.e("pass", pass);
			Log.e("phone", phone);
			loginlnfo();
		}
	}



	/**
	 * 登录接口，取得用户信息
	 */
	private void loginlnfo() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act","login");
		params.put("phone", phone);
		params.put("pass", pass);
		params.put("equipment", NApplication.device_token);
		params.put("enum", "android");
		client.post(Url.userLogin, params, login_handler);
	}
	/**
	 * 网络请求完成是的处理方法。
	 */
	private void dohandler() {
		/**
		 * 登录接口的处理方式，取得用户信息
		 */
		login_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				//				Toast.makeText(LoadingActivity.this(), "网络连接错误，请检查网络设置后重试。",
				//						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("登录", arg0);
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("1")) {
						Gson gson = new Gson();
						userInfo = gson.fromJson(jsObject.getString("Data"), UserInfo.class);
						//						if (userInfo.getFlag().equals("false")) {
						try {
							autoHX(phone, pass);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					}
				} catch (Exception e) {
					ExceptionUtil.exceptionHandle(e);
				}
				super.onSuccess(arg0);
			}

		};

	}
	
	
	
	private void  autoHX(String userName,String password) {
		if (DemoHXSDKHelper.getInstance().isLogined()) {
			// ** 免登陆情况 加载所有本地群和会话
			//不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
			//加上的话保证进了主页面会话和群组都已经load完毕
			EMGroupManager.getInstance().loadAllGroups();
			EMChatManager.getInstance().loadAllConversations();
			//进入主页面
			succeed=true;
			Tools.LogI("环信免登录");
		}else {
			loginHX(userName, password);
		}
	}
	
	//环信
	
			private void loginHX(final String userName,final String password){
				EMChatManager.getInstance().login("u"+userName,password,new EMCallBack() {//回调
					@Override
					public void onSuccess() {
						// 登陆成功，保存用户名密码
						NApplication.getInstance().setUserName(userName);
						NApplication.getInstance().setPassword(password);

						try {
							// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
							// ** manually load all local groups and
						    EMGroupManager.getInstance().loadAllGroups();
							EMChatManager.getInstance().loadAllConversations();
							// 处理好友和群组
//							initializeContacts();
						} catch (Exception e) {
							e.printStackTrace();
							// 取好友或者群聊失败，不让进入主页面
							runOnUiThread(new Runnable() {
								public void run() {
									DemoHXSDKHelper.getInstance().logout(true,null);
									Toast.makeText(LoadingActivity.this, R.string.login_failure_failed, 1).show();
								}
							});
							return;
						}
						// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
						boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
								NApplication.currentUserNick.trim());
						if (!updatenick) {
							Log.e("LoginActivity", "update current user nick fail");
						}
						Tools.LogI("自动登陆聊天服务器成功！");
						succeed=true;
					}
				 
					@Override
					public void onProgress(int progress, String status) {
				 
					}
				 
					@Override
					public void onError(int code, String message) {
//						Toast.makeText(LoadingActivity.this.LoadingActivity.thisContext(), "服务器异常，程序即将退出", 0).show();
						Tools.LogI("自动登陆聊天服务器失败！");
					}
				});
			}

}
