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
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
	public String phone,pass; 
	private AsyncHttpResponseHandler register_handler;

	@ViewById
	Button btRegistration;
	@ViewById
	Button btBack;
	@ViewById
	EditText etAffirmance;
	@ViewById
	EditText etSet;

	private InputMethodManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_register);
		dohandler();
	}

	@AfterViews
	protected void init(){
		Intent it = getIntent();
		phone= it.getStringExtra("phone");
		manager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	public void Registration(){


	}
	@Click({R.id.btBack,R.id.btRegistration})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.btBack:
			Tools.startActivity(RegisterActivity.this, LoginActivity_.class);
			finish();
			break;
		case R.id.btRegistration:
			btRegistrationeclick();
			break;

		}
	}

	//点此空白的地方,自动收回虚拟键盘
	@Override 
	public boolean onTouchEvent(MotionEvent event) {  
		// TODO Auto-generated method stub   
		if(event.getAction() == MotionEvent.ACTION_DOWN){    
			if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);     }   }  
		return super.onTouchEvent(event);  
	}
	/**
	 * btRegistration 的按键点击处理，修改密码
	 */
	public void btRegistrationeclick(){
		if (etSet.getText().length()==0) {
			Toast.makeText(RegisterActivity.this,"密码不能为空",
					Toast.LENGTH_SHORT).show();
		}else if (etAffirmance.getText().length()==0) {
			Toast.makeText(RegisterActivity.this, "确认密码不能为空",
					Toast.LENGTH_SHORT).show();
		}else if (etSet.getText().length()<6) {
			Toast.makeText(RegisterActivity.this, "密码不能少于6位",
					Toast.LENGTH_SHORT).show();
		}else if (!(etAffirmance.getText()+"").equals(etSet.getText()+"")) {
			Toast.makeText(RegisterActivity.this, "密码和确认密码不一致",
					Toast.LENGTH_SHORT).show();
		}else {
			setPass();
		}
	}




	/**
	 * 用户注册密码添加 
	 */
	private void setPass() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act","userregistration");
		params.put("phone", phone);
		Log.e("phone", phone);
		params.put("pass", etSet.getText()+"");
		client.post(Url.userLogin, params, register_handler);
	}
	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 用户注册密码添加  接口的处理方法，添加密码
		 */
		register_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(RegisterActivity.this, "网络连接错误，请检查网络设置后重试。",
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
						//把输入的密码和账号传递到登陆界面
						Intent intent = new Intent(RegisterActivity.this, LoginActivity_.class);
						intent.putExtra("password",etAffirmance.getText()+"");
						intent.putExtra("phone", phone);
						intent.putExtra("isLogin", "true");
						startActivity(intent);
						finish();
					}else {
						Toast.makeText(RegisterActivity.this, "此手机已经注册过账户",
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
