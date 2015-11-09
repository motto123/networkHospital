package com.huiyuan.networkhospital.module.main.sport_coaching.activity;

import java.util.ArrayList;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.SportSelect;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @ClassName:  Sc_PaySucceedActivity   
 * @Description:TODO运动指导之付款成功
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:05:00   
 *
 */
@EActivity(R.layout.activity_sc__pay_succeed)
public class Sc_PaySucceedActivity extends BaseActivity {

	private String CID;
	private String ID;
	
	private SportSelect ss;
	
	@ViewById
	Button btMyCoach;

	@ViewById
	Button btStartCaoch;

	@ViewById
	ImageButton imbt_evaluate_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_sc__pay_succeed);
		Intent getidIntent = getIntent();
		CID = getidIntent.getStringExtra("CID");
		ss=(SportSelect) getIntent().getSerializableExtra(GlobalConstant.KEY_DATA);
	}

	@Click({ R.id.btMyCoach, R.id.btStartCaoch, R.id.imbt_evaluate_back })
	protected void onClick(View v) {
		switch (v.getId()) {
		case R.id.btMyCoach:
			Tools.startActivity(Sc_PaySucceedActivity.this,
					SportCoachingActivity_.class);
			break;
		case R.id.btStartCaoch:
			Tools.comeinHXChat(this, "c"+ss.getPhone(), ss.getName());
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("act", "madd");
			params.put("UID", NApplication.userid);
			params.put("CID", CID);
			client.post(Url.userMovementGuidance, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFailure(Throwable arg0, String arg1) {
							Toast.makeText(Sc_PaySucceedActivity.this,
									"网络连接错误，请检查网络设置后重试。", Toast.LENGTH_SHORT)
									.show();
							super.onFailure(arg0, arg1);
						}

						@Override
						public void onSuccess(String arg0) {
							try {
								Log.e("", arg0 + "");
								JSONObject jsonObject = new JSONObject(arg0);
								Gson gson = new Gson();
								if (jsonObject.getString("Status").equals("0")) {

									ID = jsonObject.getString("Data");
									// items=
									// gson.fromJson(jsonObject.getString("Data"),
									// new TypeToken<ArrayList<SportSelect>>() {
									// }.getType());
									// persons.addAll(items);
								} else {
									Tools.showTextToast(
											Sc_PaySucceedActivity.this, "错误");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							super.onSuccess(arg0);
						}
					});

			break;
		case R.id.imbt_evaluate_back:
			finish();
			break;

		}
	}
}
