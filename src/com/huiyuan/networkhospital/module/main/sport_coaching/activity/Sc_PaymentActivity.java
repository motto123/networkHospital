package com.huiyuan.networkhospital.module.main.sport_coaching.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Constant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.SportSelect;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.registration.activity.R_PaymentActivity;
import com.huiyuan.networkhospital.weixinpay.PayActivity;
/**
 * 
 * @ClassName:  Sc_PaymentActivity   
 * @Description:TODO运动指导付款页面
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:04:31   
 *
 */
@EActivity(R.layout.activity_sc__payment)
public class Sc_PaymentActivity extends BaseActivity {
	private SportSelect bean;
	@ViewById
	TextView textView4;
	@ViewById
	TextView tvTime;
	@ViewById
	TextView tvScTotal;
	@ViewById
	ImageView ivMore1;
	
	public String path;
	static Context context;
	String name = "教练指导费";
	String total = "1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@AfterViews
	protected void init(){

		context=Sc_PaymentActivity.this;
		Intent getidIntent =getIntent();
		bean = (SportSelect) getidIntent.getSerializableExtra("bean");	

		textView4.setText(bean.getName());
		tvTime.setText(bean.getPrice()+"元");
		tvScTotal.setText(bean.getPrice()+"元");
		if (bean.getPhoto()!=null&&!bean.getPhoto().equals("")) {
			path = Url.imageupload+bean.getPhoto();
			Tools.setimg(ivMore1, path, context);
		}

	}

	@Click({ R.id.ivBack, R.id.btScPayment })
	protected void onClick(View v) {

		switch (v.getId()) {
		case R.id.btScPayment:
			NApplication.cphone = bean.getPhone();
			NApplication.pay_style = Constant.SC_PAY;
				NApplication.CID = bean.getId();
				Log.e("CID", NApplication.CID);
				ConnectivityManager manager = (ConnectivityManager) (Sc_PaymentActivity.this)
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = manager.getActiveNetworkInfo();
				if (networkInfo == null) {
					Toast.makeText(Sc_PaymentActivity.this, "网络连接错误，请检查网络设置后重试。",
							Toast.LENGTH_SHORT).show();
				}else {
					
					PayActivity payActivity = new PayActivity(this,name,total);
					payActivity.name();
				}
				
			
			//			Intent intent=getIntent();
			//			intent.setClass(this,
			//					Sc_PaySucceedActivity_.class);
			//			startActivity(intent);
			break;
		case R.id.ivBack:
			finish();
			break;

		}
	}



	public static void Destroy(){
		((Activity) context).finish();
	}
}
