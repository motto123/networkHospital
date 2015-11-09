package com.huiyuan.networkhospital.module.main.sport_coaching.activity;

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
import com.huiyuan.networkhospital.module.huanxin.activity.ChatMainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @ClassName:  EvaluateActivity   
 * @Description:TODO运动指导评价界面
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:04:00   
 *
 */
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
	@AfterViews
	public void change(){
			tv_evaluate_name.setText("姓名："+NApplication.suserdate.getUname()+"\n"+"教练："+NApplication.suserdate.getCname());
			
		if (et_evaluate_conment.getText().length()==0) {
			evaluation = "说的蛮仔细的，指导很到位";
		}else {
			evaluation = et_evaluate_conment.getText().toString();
		}
		score = ratingBar1.getRating()+"";
		score=score.substring(0,score.indexOf("."));
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
		params.put("act", "mupdate");
		params.put("ID", NApplication.suserdate.getId());
		params.put("score", score);
		params.put("evaluation", evaluation );
		client.post(Url.userMovementGuidance, params, vupdate_handler);

	}
	private void hanlder() {
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
						NApplication.suserdate = null;
						Intent intent = new Intent(EvaluateActivity.this,SportCoachingActivity_.class);
						startActivity(intent);
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
