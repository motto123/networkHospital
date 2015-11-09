package com.huiyuan.networkhospital.module.main.get_medicine.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
@EActivity(R.layout.activity_evaluate)
public class EvaluateActivity extends BaseActivity {
	
	private AsyncHttpResponseHandler vupdate_handler;
	private String evaluation,score;
	
	@ViewById
	TextView tv_evaluate_name;
	@ViewById
	EditText et_evaluate_conment;
	@ViewById
	RatingBar ratingBar1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		hanlder();
	}
	@Click({R.id.imbt_evaluate_back,R.id.bt_evaluate_submit})
	public void back(View v) {
		switch (v.getId()) {
		case R.id.imbt_evaluate_back:
			vupdate();
			break;
		case R.id.bt_evaluate_submit:
			vupdate();
			break;
		}
	}
	@AfterViews
	public void init(){
		//判断muserdate是否有值，有值表示从聊天界面进来的，没有值表示从登陆界面进来
		if (NApplication.muserdate!=null) {
			tv_evaluate_name.setText("姓名："+NApplication.muserdate.getUname()+"\n"+"医生："+NApplication.muserdate.getDname());
			NApplication.muserdate = null;
		}else {
			tv_evaluate_name.setText("姓名："+NApplication.username+"\n"+"医生："+NApplication.doctorname);
			NApplication.username = null;
			NApplication.doctorname = null;
		}
		if (et_evaluate_conment.getText().length()==0) {
			evaluation = "说的蛮仔细的，指导很到位";
		}else {
			evaluation = et_evaluate_conment.getText().toString();
		}
		//获取评价等级
		score = ratingBar1.getRating()+"";
		score=score.substring(0,score.indexOf("."));
		Log.e("score", score);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		vupdate();
	}
	/**
	 * 用户付款后评价
	 */
	public void vupdate(){
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "vupdate");
		params.put("ID", NApplication.Vid);
		params.put("score", score);
		params.put("evaluation",evaluation );
		client.post(Url.department, params, vupdate_handler);

	}
	
	private void hanlder() {
		/**
		 * 判断是否评价成功
		 */
		vupdate_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(EvaluateActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("arg0", arg0);
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					if (jsonObject.getString("Data").equals("true")) {
						NApplication.Vid = null;
//						Intent intent = new Intent(EvaluateActivity.this,GetMedicineActivity_.class);
//						startActivity(intent);
						finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.onSuccess(arg0);
			}
		};
	}
}
