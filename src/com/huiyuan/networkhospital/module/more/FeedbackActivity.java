package com.huiyuan.networkhospital.module.more;

import java.io.ByteArrayInputStream;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.module.user.DataEditeActivity;
import com.huiyuan.networkhospital.module.user.UserInfoActivity_;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
@EActivity(R.layout.activity_feedback)
public class FeedbackActivity extends Activity {
	@ViewById
	ImageButton ibtnBack;
	@ViewById
	EditText etCentext;
	@ViewById
	EditText etEmail;
	
	private AsyncHttpResponseHandler add_handler;
	private InputMethodManager manager;
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//		setContentView(R.layout.activity_feedback);
		dohandler();
	}
	
	@Override 
	public boolean onTouchEvent(MotionEvent event) {  
		// TODO Auto-generated method stub   
		if(event.getAction() == MotionEvent.ACTION_DOWN){    
			if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);     }   }  
		return super.onTouchEvent(event);  
	}
	@Click({R.id.ibtnBack,R.id.btCancel,R.id.btsend})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
			finish();
			break;
		case R.id.btCancel:
			finish();
			break;
		case R.id.btsend:
			if (etCentext.getText().length()>0) {
				dialog = ProgressDialog.show(FeedbackActivity.this, "提示", "正在加载中");
				read();
			}else {
				Toast.makeText(FeedbackActivity.this, "请填写内容。",
						Toast.LENGTH_SHORT).show();
			}
			break;

		}
	}
	
	/**
	 * 新增意见反馈
	 */
	public void read(){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "add");
		params.put("Title","android");
		params.put("Contents",etCentext.getText()+"");
		client.post(Url.Opinion, params, add_handler);
	}
	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 新增意见反馈，判断时候新增完成
		 */
		add_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(FeedbackActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("add", arg0);
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("0")) {
						if (jsObject.getInt("Data")>0) {
								Toast.makeText(FeedbackActivity.this.getApplicationContext(), "反馈成功",
										Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(FeedbackActivity.this, "反馈失败",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(FeedbackActivity.this, "反馈失败",
								Toast.LENGTH_SHORT).show();
					}
					if (dialog != null) {
						dialog.dismiss();
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
