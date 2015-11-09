package com.huiyuan.networkhospital.module.main.get_medicine.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.constant.Constant;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.Medicine;
import com.huiyuan.networkhospital.entity.hepeng.GM_pay;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.MedicineIndentAdapter;
import com.huiyuan.networkhospital.weixinpay.PayActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_gm__payment)
public class Gm_PaymentActivity extends BaseActivity {

	@ViewById
	ImageView ivBack;

	@ViewById
	ListView lvMedicine;

	@ViewById
	RadioButton radio0;

	@ViewById
	RadioButton radio1;

	@ViewById
	EditText etReceivingAddress;
	@ViewById
	EditText etNameContent;
	@ViewById
	EditText etTelContent;

	@ViewById
	TextView tvTelContent;
	@ViewById
	TextView tvNameContent;
	@ViewById
	TextView tvReceivingAddress;

	@ViewById
	TextView tvName;
	@ViewById
	TextView tvTel;
	@ViewById
	TextView tv1;


	@ViewById
	TextView tvTotal;

	@ViewById
	TextView textView3;

	@ViewById
	Button btPayment;


	private InputMethodManager manager;

	private MedicineIndentAdapter adapter;
	private AsyncHttpResponseHandler getprescriptionHandler, vupdate_handler;
	private List<Medicine> medicins = new ArrayList<Medicine>();
	private Gson gson = new Gson();
	static Context context;
	private float price;
	//显示支付货物名
	String name = "药品清单";
	//钱数（分,只能是整数）
	String total = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_gm__payment);
		hanlder();

	}

	@AfterViews
	protected void init() {
		context = Gm_PaymentActivity.this;
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		Intent it = getIntent();
		NApplication.Vid = it.getStringExtra(GlobalConstant.KEY_DATA);
		adapter = new MedicineIndentAdapter(medicins, this);
		lvMedicine.setAdapter(adapter);
		read();
	}

	@Click({ R.id.ivBack, R.id.radio0, R.id.radio1, R.id.btPayment })
	protected void skip(View v) {
		switch (v.getId()) {
		// 返回上一个页面
		case R.id.ivBack:
			this.finish();
			break;
			// 选择自提
		case R.id.radio0:
			textView3.setText("医院地址");
			tvReceivingAddress.setText("四川省成都市一环路西一段132号");
			tvName.setText("医院联系人：");
			tv1.setText("医院地址：");


			tvReceivingAddress.setVisibility(View.VISIBLE);
			tvTelContent.setVisibility(View.VISIBLE);
			tvNameContent.setVisibility(View.VISIBLE);

			etTelContent.setVisibility(View.GONE);
			etNameContent.setVisibility(View.GONE);
			etReceivingAddress.setVisibility(View.GONE);
			break;
			// 选择快递
		case R.id.radio1:
			textView3.setText("收货地址");
			tvName.setText("收货联系人：");
			tv1.setText("收货地址：");

			tvReceivingAddress.setVisibility(View.GONE);
			tvTelContent.setVisibility(View.GONE);
			tvNameContent.setVisibility(View.GONE);

			etTelContent.setVisibility(View.VISIBLE);
			etNameContent.setVisibility(View.VISIBLE);
			etReceivingAddress.setVisibility(View.VISIBLE);
			break;
			// 付款
		case R.id.btPayment:
			//支付位置，这里是复诊拿药支付。
			NApplication.pay_style = Constant.GM_PAY;
			if (radio0.isEnabled()) {
				//复诊拿药支付后需要的上传的送货地址，自提
				GM_pay gm_pay = new GM_pay();
				gm_pay.setAddress("四川省成都市一环路西一段132号");
				gm_pay.setName("张芳");
				gm_pay.setPhone("028-66892586");
				gm_pay.setType("1");
				NApplication.gm_pay=gm_pay;
				//判断是否有网
				ConnectivityManager manager = (ConnectivityManager) (Gm_PaymentActivity.this)
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = manager.getActiveNetworkInfo();
				if (networkInfo == null) {
					Toast.makeText(Gm_PaymentActivity.this, "网络连接错误，请检查网络设置后重试。",
							Toast.LENGTH_SHORT).show();
				}else {
					//微信支付
					PayActivity payActivity = new PayActivity(this, name, total);
					payActivity.name();
				}

			} else if (radio1.isEnabled()) {
				if (etReceivingAddress.getText().length() == 0) {
					Toast.makeText(Gm_PaymentActivity.this, "请输入地址",
							Toast.LENGTH_SHORT).show();
				} else if(etNameContent.getText().length() == 0){
					Toast.makeText(Gm_PaymentActivity.this, "请输入收货人姓名",
							Toast.LENGTH_SHORT).show();
				}else if (etTelContent.getText().length() != 11) {
					Toast.makeText(Gm_PaymentActivity.this, "请输入正确的电话号码",
							Toast.LENGTH_SHORT).show();
				}else {
					//复诊拿药支付后需要的上传的送货地址，快递
					GM_pay gm_pay = new GM_pay();
					gm_pay.setAddress(etReceivingAddress.getText().toString());
					gm_pay.setName(etNameContent.getText().toString());
					gm_pay.setPhone(etTelContent.getText().toString());
					gm_pay.setType("0");
					NApplication.gm_pay = gm_pay;
					//判断是否有网
					ConnectivityManager manager = (ConnectivityManager) (Gm_PaymentActivity.this)
							.getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = manager.getActiveNetworkInfo();
					if (networkInfo == null) {
						Toast.makeText(Gm_PaymentActivity.this, "网络连接错误，请检查网络设置后重试。",
								Toast.LENGTH_SHORT).show();
					}else {
						//微信支付
						PayActivity payActivity = new PayActivity(this, name, total);
						payActivity.name();
					}
				}
			}

			break;

		}
	}

	/**
	 * 用户获取医生开药信息列表
	 */
	public void read() {
		RequestParams parmas = new RequestParams();
		parmas.put("act", "getprescription");
		parmas.put("Vid", NApplication.Vid);
		HttpClientUtils.post(Url.department, parmas, getprescriptionHandler);
	}

	private void hanlder() {
		/**
		 * 显示医生开药的信息列表信息
		 */
		getprescriptionHandler = new AsyncHttpResponseHandler() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(String arg0) {
				try {
					BigDecimal result = new BigDecimal("0");  
					JSONObject jsObject = new JSONObject(arg0);
					Log.e("arg1", arg0);
					if (jsObject.getString("Status").equals("1")) {
						medicins.addAll((Collection<? extends Medicine>) gson
								.fromJson(jsObject.getString("Data"),
										new TypeToken<ArrayList<Medicine>>() {
								}.getType()));
						//计算总额
						for (Medicine medicine:medicins) {
							BigDecimal bignum = new BigDecimal(medicine.getPrice());  
							result = result.add(bignum);
						}
						tvTotal.setText(result+"元");
						adapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				super.onSuccess(arg0);
			}

			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(Gm_PaymentActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0);
			}

		};

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	//销毁activity
	public static void Destroy() {
		((Activity) context).finish();
	}
}
