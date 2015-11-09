package com.huiyuan.networkhospital.module.usermanager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.google.gson.JsonSyntaxException;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.constant.User;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
@EActivity(R.layout.activity_forget)
public class ForgetActivity extends BaseActivity {
	private String phone;
	private AsyncHttpResponseHandler forget_handler;

	@ViewById
	ImageButton ibtnBack;

	@ViewById
	Button btConfirmChange;

	@ViewById
	EditText etNewPassword;

	@ViewById
	EditText etConfirmPassword;

	private InputMethodManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_alter_pw);
		dohandler();
	}


	@AfterViews
	protected void init() {
		Intent it = getIntent();
		phone= it.getStringExtra("phone");
		manager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//弹出虚拟键盘
		Tools.popupKeyboard(etNewPassword);
	}


	@Click({R.id.ibtnBack,R.id.btConfirmChange})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
			finish();
			break;
		case R.id.btConfirmChange:
			btConfirmChangeclick();
			break;

		}
	}

	//点此空白的地方,自动收回虚拟键盘
	@Override 
	public boolean onTouchEvent(MotionEvent event) {  
		// TODO Auto-generated method stub   
		if(event.getAction() == MotionEvent.ACTION_DOWN){  
			if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){  
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
			}  
		}  
		return super.onTouchEvent(event);  
	}
	/**
	 * 修改密码
	 */
	public void btConfirmChangeclick(){
		if (etNewPassword.getText().length()==0) {
			Toast.makeText(ForgetActivity.this,"码不能为空",
					Toast.LENGTH_SHORT).show();
		}else if (etConfirmPassword.getText().length()==0) {
			Toast.makeText(ForgetActivity.this, "确认密码不能为空",
					Toast.LENGTH_SHORT).show();
		}else if (etNewPassword.getText().length()<6) {
			Toast.makeText(ForgetActivity.this, "密码不能少于6位",
					Toast.LENGTH_SHORT).show();
		}else if (!(etNewPassword.getText()+"").equals(etConfirmPassword.getText()+"")) {
			Toast.makeText(ForgetActivity.this, "密码和确认密码不一致",
					Toast.LENGTH_SHORT).show();
		}else {
			changePass();
		}
	}



	/**
	 * 忘记密码密码更改
	 */
	private void changePass() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act","update");
		params.put("phone", phone);
		Log.e("phone", phone);
		params.put("pass", etNewPassword.getText()+"");
		client.post(Url.userLogin, params, forget_handler);
	}
	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 忘记密码密码更改 接口的处理方法，修改密码
		 */
		forget_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(ForgetActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				Log.e("登陆——login_handler", "login_handler");
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("登录", arg0);
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("0")) {
						if (jsObject.getString("Data").equals("true")) {
							//把输入的密码和账号传递到登陆界面
							final Intent intent = new Intent(ForgetActivity.this, LoginActivity_.class);
							intent.putExtra("password",etConfirmPassword.getText()+"");
							intent.putExtra("phone", phone);
							intent.putExtra("isLogin", "true");
							User user = new User(ForgetActivity.this); 
							user.removePass();
							EMChatManager.getInstance().logout(new EMCallBack() {

								@Override
								public void onSuccess() {
									Tools.LogI("退出app和环信");
									startActivity(intent);
									finish();
								}

								@Override
								public void onProgress(int progress, String status) {

								}

								@Override
								public void onError(int code, String message) {
									Tools.LogI("退出环信 error/"+code+":"+message);
								}
							});//退出环信聊天服务器  
						}else {
							Toast.makeText(ForgetActivity.this, "密码修改失败",
									Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(ForgetActivity.this, "密码修改失败",
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

}
