package com.huiyuan.networkhospital.module.usermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.UserInfo;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.huanxin.DemoHXSDKHelper;
import com.huiyuan.networkhospital.module.huanxin.db.UserDao;
import com.huiyuan.networkhospital.module.huanxin.domain.User;
import com.huiyuan.networkhospital.module.huanxin.utils.CommonUtils;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
	private AsyncHttpResponseHandler login_handler;
	private String phone,pass,isLogin;
	private UserInfo userInfo = null;
	@ViewById
	TextView tvRegistration;
	@ViewById
	TextView tvForget;
	@ViewById
	Button btNext;
	@ViewById
	EditText etTel;
	@ViewById
	EditText etPassword;
	
	private String currentUsername;
	private String currentPassword;
	private boolean progressShow;
	//
	// @ViewById
	// ScrollView sc_login;
	//
	private InputMethodManager manager;
	private AsyncHttpResponseHandler test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((NApplication)this.getApplication()).addActivity(this); 
		dohandler();
		// setContentView(R.layout.activity_login);
	}

	@AfterViews
	protected void init(){
		boolean isFirst=getIntent().getBooleanExtra(GlobalConstant.KEY_DATA, true);
//		if(isFirst) tvForget.setVisibility(View.GONE);
	}
	
	public void getPP() {
		/**
		 * 当修改密码或者注册成功后，把账号和密码自动填写在里面。
		 */
		Intent it = getIntent();
		phone= it.getStringExtra("phone");
		pass = it.getStringExtra("password");
		isLogin = it.getStringExtra("isLogin");
		Log.e("dsfadfasd", "fasdfasdfa");
		/**
		 * 判断是否自动登录成功
		 */
		if (isLogin.equals("false")) {
			Toast.makeText(LoginActivity.this,"自动登录失败，请检查网络是否通畅或密码是否正确！",
					Toast.LENGTH_SHORT).show();
			it.putExtra("isLogin", "");
		}
		if (phone != null) {
			etTel.setText(phone);
			Log.e("phone", phone);
			etPassword.setText(pass);
		}
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Click({ R.id.tvRegistration, R.id.btNext, R.id.tvForget })
	protected void skip(View v) {
		switch (v.getId()) {
		case R.id.tvRegistration:
			tvRegistrationclick();
			break;
		case R.id.btNext:
			btNextclick();
			break;
		case R.id.tvForget:
			tvForgetclick();
			break;
			// 筛选
		}
	}
	@Override
	protected void onResume() {
		try {
			getPP();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onResume();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		((NApplication) this.getApplication()).destoryAllActivity();
		System.exit(0);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	/**
	 * btNext 的按键点击处理，取得账号和密码，用于登录接口，判断是否能够都登录
	 */
	public void btNextclick(){
		if((etTel.getText()+"").length() == 0){
			Toast.makeText(LoginActivity.this, "电话号码为空，请填写电话号码",
					Toast.LENGTH_SHORT).show();
		}else if ((etPassword.getText()+"").length()==0) {
			Toast.makeText(LoginActivity.this, "密码为空",
					Toast.LENGTH_SHORT).show();
		}else if((etTel.getText()+"").length() != 11){
			Toast.makeText(LoginActivity.this, "电话号码位数不对",
					Toast.LENGTH_SHORT).show();
		}else{
			phone = etTel.getText()+"";
			pass = etPassword.getText()+"";
			loginlnfo();
		}
	}
	/**
	 * tvRegistration 的按键点击处理,跳转到获取验证码界面，isforget为false表示是注册。
	 */
	public void tvRegistrationclick(){
		Intent intent1 = new Intent(LoginActivity.this,
				MatchSMSActivity_.class);
		intent1.putExtra("isForget", "false");
		startActivity(intent1);
	}
	/**
	 * tvForget 的按键点击处理，跳转到获取验证码界面，isforget为true表示是忘记密码。
	 */
	public void tvForgetclick(){
		Intent intent2 = new Intent(LoginActivity.this,
				MatchSMSActivity_.class);
		startActivity(intent2);
		intent2.putExtra("isForget", "true");
	}





	/**
	 * 登录接口，用于取得用户信息
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
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 登录网络请求后，用于取得用户信息
		 */
		login_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(LoginActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
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
						 com.huiyuan.networkhospital.constant.User user = new  com.huiyuan.networkhospital.constant.User(LoginActivity.this);
						user.savePhone(phone);
						user.savePass(pass);
						//						if (userInfo.getFlag().equals("false")) {
						NApplication.phone = userInfo.getPhone();
						NApplication.photo = userInfo.getPhoto();
						NApplication.username = userInfo.getName();
						NApplication.userid=userInfo.getID();
						NApplication.sex = userInfo.getSex();
						NApplication.IC = userInfo.getIC();
						try {
							loginHX();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//						}
					}else {
						Toast.makeText(LoginActivity.this, "电话号码或者密码错误",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				super.onSuccess(arg0);
			}

		};

	}
	
	///以下是环信登录   
	/**
	 * 登录
	 * 
	 */
	public void loginHX() {
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
			return;
		}
		currentUsername = etTel.getText().toString().trim();
		currentPassword = etPassword.getText().toString().trim();

		progressShow = true;
		final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
		pd.setMessage(getString(R.string.Is_landing));
		pd.show();

		final long start = System.currentTimeMillis();
		// 调用sdk登陆方法登陆聊天服务器
		EMChatManager.getInstance().login("u"+currentUsername, currentPassword, new EMCallBack() {

			@Override
			public void onSuccess() {
				if (!progressShow) {
					return;
				}
				// 登陆成功，保存用户名密码
				NApplication.getInstance().setUserName(currentUsername);
				NApplication.getInstance().setPassword(currentPassword);

				try {
					// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
					// ** manually load all local groups and
				    EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					// 处理好友和群组
					initializeContacts();
				} catch (Exception e) {
					e.printStackTrace();
					// 取好友或者群聊失败，不让进入主页面
					runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							DemoHXSDKHelper.getInstance().logout(true,null);
							Toast.makeText(LoginActivity.this, R.string.login_failure_failed, 1).show();
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
				if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
					pd.dismiss();
				}
				// 进入主页面
				
				Intent intent = new Intent(LoginActivity.this,
						MainActivity_.class);
				startActivity(intent);
				
				finish();
				Tools.LogI("环信帐号登录成功");
			}

			@Override
			public void onProgress(int progress, String status) {
				Tools.LogI("处理中:"+progress,"\\status:"+status);
			}

			@Override
			public void onError(final int code, final String message) {
				if (!progressShow) {
					return;
				}
				runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						Toast.makeText(LoginActivity.this, getString(R.string.Login_failed) + message,
								Toast.LENGTH_SHORT).show();
						Tools.LogI("环信帐号登录失败");
					}
				});
			}
		});
	}

	
	private void initializeContacts() {
		Map<String, User> userlist = new HashMap<String, User>();
		// 添加user"申请与通知"
		User newFriends = new User();
		newFriends.setUsername(GlobalConstant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setNick(strChat);

		userlist.put(GlobalConstant.NEW_FRIENDS_USERNAME, newFriends);
		// 添加"群聊"
		User groupUser = new User();
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setUsername(GlobalConstant.GROUP_USERNAME);
		groupUser.setNick(strGroup);
		groupUser.setHeader("");
		userlist.put(GlobalConstant.GROUP_USERNAME, groupUser);
		
		// 添加"Robot"
		User robotUser = new User();
		String strRobot = getResources().getString(R.string.robot_chat);
		robotUser.setUsername(GlobalConstant.CHAT_ROBOT);
		robotUser.setNick(strRobot);
		robotUser.setHeader("");
		userlist.put(GlobalConstant.CHAT_ROBOT, robotUser);
		
		// 存入内存
		((DemoHXSDKHelper)HXSDKHelper.getInstance()).setContactList(userlist);
		// 存入db
		UserDao dao = new UserDao(LoginActivity.this);
		List<User> users = new ArrayList<User>(userlist.values());
		dao.saveContactList(users);
	}


}
