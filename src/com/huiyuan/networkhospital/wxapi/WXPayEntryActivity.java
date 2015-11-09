package com.huiyuan.networkhospital.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.constant.Constant;
import com.huiyuan.networkhospital.module.huanxin.activity.ChatMainActivity;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.DoctorVisitActivity_;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.Dv_PaymentActivity;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.MakeDetailsActivity;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.EvaluateActivity_;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.GetMedicineActivity;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.Gm_PaymentActivity;
import com.huiyuan.networkhospital.module.main.registration.activity.R_PaymentActivity;
import com.huiyuan.networkhospital.module.main.sport_coaching.activity.Sc_PaymentActivity;
import com.huiyuan.networkhospital.module.main.sport_coaching.activity.SelectCoach;
import com.huiyuan.networkhospital.weixinpay.Constants;
import com.huiyuan.networkhospital.weixinpay.PayActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

	TextView tv_gm_submit_succeed;
	Button bt_gm_submit_submit;
	ImageButton imbt_gm_submit_succeed_back;
	private String code;

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gm__submit_succeed);
		init();
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}
	public void init(){
		tv_gm_submit_succeed = (TextView)findViewById(R.id.tv_gm_submit_succeed);
		bt_gm_submit_submit = (Button)findViewById(R.id.bt_gm_submit_submit);
		imbt_gm_submit_succeed_back = (ImageButton)findViewById(R.id.imbt_gm_submit_succeed_back);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}
	
	@Override
	public void onReq(BaseReq req) {
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (code.equals("-2")) {
			off();
		}else if (code.equals("0")) {
			ok();
		}
	}
	/**
	 * 回调方法，每次支付都会掉这个方法
	 */
	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			code = String.valueOf(resp.errCode);
			click();

		}
	}
	/**
	 * code -1表示Constants里面的值有问题
	 * -2 表示用户放弃支付
	 * 0表示支付成功
	 */
	public void click(){
		if (code.equals("-2")) {
			//提示显示
			tv_gm_submit_succeed.setText("付款已取消");
			imbt_gm_submit_succeed_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					off();
				}
			});
			bt_gm_submit_submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					off();
				}
			});
		}else if (code.equals("0")) {
			if (NApplication.pay_style == Constant.SC_PAY) {
				tv_gm_submit_succeed.setText("恭喜您已付款成功！");
			}else {
				tv_gm_submit_succeed.setText("恭喜您已付款成功！若15:00前完成本订单，医院将于本日15:00后受理；若否，受理时间将推迟于次日15:00后，敬请登录查看。");
			}
			imbt_gm_submit_succeed_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ok();
				}
			});
			bt_gm_submit_submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ok();
				}
			});
			//这是一个类
			PayActivity payActivity = new PayActivity(this);
			payActivity.startTransactionId();
		} 
	}
	/**
	 * 返回码表示付款成功的处理
	 */
	public void ok(){
		switch (NApplication.pay_style) {
		case Constant.GM_PAY:
			//
			//			try {
			//				GetMedicineActivity.Destroy();
			//			} catch (Exception e) {
			//				// TODO: handle exception
			//				e.printStackTrace();
			//			}
			Gm_PaymentActivity.Destroy();
			Intent intent = new Intent(WXPayEntryActivity.this,EvaluateActivity_.class);
			startActivity(intent);
			finish();
			break;
		case Constant.R_PAY:
			R_PaymentActivity.Destroy();
			finish();
			break;
		case Constant.DV_PAY:
			Intent intent1 = new Intent(WXPayEntryActivity.this,
					DoctorVisitActivity_.class);
			Dv_PaymentActivity.Destroy();
			intent1.putExtra("key", "2");
			startActivity(intent1);
			finish();
			break;
		case Constant.MD_PAY:
			Intent intent2 = new Intent(WXPayEntryActivity.this,
					DoctorVisitActivity_.class);
			MakeDetailsActivity.Destroy();
			intent2.putExtra("key", "2");
			startActivity(intent2);
			finish();
			break;
		case Constant.SC_PAY:
			startActivity(new Intent(WXPayEntryActivity.this,
					ChatMainActivity.class).putExtra("userId", "c"+NApplication.cphone)
					.putExtra("toChatName", "c"+NApplication.cphone)
					.putExtra("self",NApplication.phone));
			Sc_PaymentActivity.Destroy();
			SelectCoach.Destroy();
			finish();

			break;

		}

	}
	/**
	 * 返回码表示付款失败的处理
	 */
	public void off(){
		NApplication.dv_people = null;
		finish();
	}
}
