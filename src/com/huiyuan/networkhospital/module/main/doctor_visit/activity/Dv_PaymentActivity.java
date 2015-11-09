package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.NetworkUtil;
import com.huiyuan.networkhospital.constant.Constant;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.Gm_PaymentActivity;
import com.huiyuan.networkhospital.weixinpay.PayActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * 发起活动的支付页面
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_dv_payment)
public class Dv_PaymentActivity extends BaseActivity {
	// 付款
	@ViewById
	ImageButton imbt_dv_payment_back;
	@ViewById
	Button bt_dv_payment_pay;
	private Dv_people bean = new Dv_people();
	private ArrayList<Dv_people> listObj;
	private String time = "";
	private AsyncHttpResponseHandler DV_P_handler;
	static Context context;
	String name = "医生上门";
	String total = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化
	 */
	@AfterViews
	public void init() {
		context = Dv_PaymentActivity.this;
		Intent intent = getIntent();
		bean.setName(intent.getStringExtra("name"));
		bean.setSex(intent.getStringExtra("sex"));
		bean.setPhone(intent.getStringExtra("phone"));
		bean.setBrief((intent.getStringExtra("deta")));
		bean.setAID(intent.getStringExtra("AID"));
		bean.setAge(intent.getStringExtra("Age"));
		Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		time = t.year + "-" + t.month + "-" + t.monthDay;
		bean.setCreateTime(time);
	}

	/**
	 * 设置监听
	 */
	@Click(R.id.bt_dv_payment_pay)
	public void check() {
		NApplication.pay_style = Constant.DV_PAY;
		NApplication.dv_people = bean;
		ConnectivityManager manager = (ConnectivityManager) (Dv_PaymentActivity.this)
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo == null) {
			Toast.makeText(Dv_PaymentActivity.this, "网络连接错误，请检查网络设置后重试。",
					Toast.LENGTH_SHORT).show();
		} else {
			PayActivity payActivity = new PayActivity(this, name, total);
			payActivity.name();
		}

	}

	/**
	 * 设置返回监听
	 */
	@Click(R.id.imbt_dv_payment_back)
	public void back() {
		finish();
	}

	/**
	 * 自我销毁方法
	 */
	public static void Destroy() {
		((Activity) context).finish();
	}
}
