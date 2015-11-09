package com.huiyuan.networkhospital.module.usermanager;

import java.util.Timer;
import java.util.TimerTask;

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

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
@EActivity(R.layout.activity_match_sms)
public class MatchSMSActivity extends BaseActivity {
	private String isForget;
	private AsyncHttpResponseHandler sms_handler;
	private String smsString = "null";

	@ViewById
	Button btNext;
	@ViewById
	Button btSMSCode;
	@ViewById
	EditText etTel;
	@ViewById
	EditText etSMSCode;
	private InputMethodManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_match_sms);
		dohandler();
	}
	@AfterViews
	protected void init(){
		Intent intent1 = getIntent();
		isForget = intent1.getStringExtra("isForget");
		Log.e(isForget+"",isForget+"");
		manager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//弹出虚拟键盘
		Tools.popupKeyboard(etTel);
		//		setListener();
	}

	//	private void setListener() {
	//		btSMSCode.setOnClickListener(new OnClickListener() {
	//			
	//			@Override
	//			public void onClick(View v) {
	//				countDown();
	//			}
	//		});
	//	}
	@Click({R.id.btNext,R.id.btSMSCode})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.btNext:
			btNextclick();
			break;
		case R.id.btSMSCode:
			btSMSCodeclick();
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
	private int time=0;
	private Timer timer = new Timer();
	private TimerTask task;
	/**
	 * 倒计时
	 */
	private void countDown(){
		task = new TimerTask() {
			@Override
			public void run() {

				runOnUiThread(new Runnable() { // UI thread
					@Override
					public void run() {
						if (time <= 0) {
							btSMSCode.setEnabled(true);
							task.cancel();
							btSMSCode.setText("获取验证码");
						} else {
							btSMSCode.setText("" + time+"秒");
							btSMSCode.setEnabled(false);
						}
						time--;
					}
				});
			}
		};
		time = 120;
		timer.schedule(task, 0, 1000);
	}

	/**
	 * btNext 的按键点击处理，判断验证码是否正确，根据isforget判断跳转的界面。
	 */
	public void btNextclick(){
		if((etTel.getText()+"").length() == 0){
			Toast.makeText(MatchSMSActivity.this,"电话号码为空，请填写电话号码",
					Toast.LENGTH_SHORT).show();
		}else if ((etTel.getText()+"").length()!=11) {
			Toast.makeText(MatchSMSActivity.this, "电话号码位数不对",
					Toast.LENGTH_SHORT).show();
		}else if ((etSMSCode.getText()+"").length() == 0) {
			Toast.makeText(MatchSMSActivity.this, "验证码为空，请填写验证码",
					Toast.LENGTH_SHORT).show();
		}else if (!smsString.equals(etSMSCode.getText()+"")) {
			Log.e("dagadg", smsString);
			Log.e("dagadg", (etSMSCode.getText()+""));
			Toast.makeText(MatchSMSActivity.this, "验证码错误",
					Toast.LENGTH_SHORT).show();
		}else if(smsString.equals(etSMSCode.getText()+"")&&isForget==null){
			//把phone值传递到ForgetActivity
			Log.e("ForgetActivity_", "ForgetActivity_");
			Intent intent = new Intent(MatchSMSActivity.this, ForgetActivity_.class);
			intent.putExtra("phone", etTel.getText()+"");
			startActivity(intent);
			finish();
		}else if(smsString.equals(etSMSCode.getText()+"")){
			//把phone值传递到RegisterActivity
			Log.e("RegisterActivity_", "RegisterActivity_");
			Intent intent = new Intent(MatchSMSActivity.this, RegisterActivity_.class);
			intent.putExtra("phone", etTel.getText()+"");
			startActivity(intent);
			finish();
		}
	}

	/**
	 * btSMSCode 的按键点击处理，获取验证码
	 */
	public void btSMSCodeclick(){
		if((etTel.getText()+"").length() == 0){
			Toast.makeText(MatchSMSActivity.this, "电话号码为空，请填写验证码",
					Toast.LENGTH_SHORT).show(); 
		}else if ((etTel.getText()+"").length()!=11) {
			Toast.makeText(MatchSMSActivity.this, "电话号码位数不对",
					Toast.LENGTH_SHORT).show();
		}else {
			getSMS();
			countDown();
		}
	}



	/**
	 * 取得sms 连接服务器，获取验证码
	 */
	private void getSMS() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act","add_phone");
		params.put("phone", etTel.getText()+"");
		params.put("equipment", NApplication.device_token);
		client.post(Url.userLogin, params, sms_handler);
	}
	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * add_phone 接口的处理方法，获取验证码
		 */
		sms_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(MatchSMSActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				Log.e("登陆——login_handler", "login_handler");
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				JSONObject jsObject;
				try {
					jsObject = new JSONObject(arg0);
					if(jsObject.getString("Status").equals("0")){
						smsString = jsObject.getString("Data");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("登录", arg0);

				super.onSuccess(arg0);
			}

		};

	}
}
