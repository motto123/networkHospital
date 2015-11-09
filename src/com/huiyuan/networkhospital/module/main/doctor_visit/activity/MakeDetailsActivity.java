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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.constant.Constant;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.huiyuan.networkhospital.entity.hepeng.MD_pay;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.weixinpay.PayActivity;
/**
 * 单人参加的付款页面
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_make_details)
public class MakeDetailsActivity extends BaseActivity {

	@ViewById
	Button bt_macke_details_pay;
	@ViewById
	ImageButton imbt_macke_details_back;
	@ViewById
	TextView tv_macke_details_phone;
	@ViewById
	TextView tv_macke_details_name;
	@ViewById
	TextView tv_macke_details_address;
	@ViewById
	TextView tv_macke_details_money;
	private int all = 0;
	private ArrayList<Dv_people> listObj;
	String is = "0";
	String Area = "";
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
	@SuppressWarnings("unchecked")
	@AfterViews
	public void init() {
		context = MakeDetailsActivity.this;
		Intent getintent = getIntent();
		listObj = (ArrayList<Dv_people>) getintent
				.getSerializableExtra("persons");
		tv_macke_details_address.setText(getintent.getStringExtra("address"));
		tv_macke_details_phone.setText(tv_macke_details_phone.getText()
				+ listObj.get(0).getPhone());

		for (Dv_people get : listObj) {
			tv_macke_details_name.setText(tv_macke_details_name.getText()
					+ get.getName() + "\n");
			all = all + 50;
		}
		is = getintent.getStringExtra("IsParticipate");
		Area = getintent.getStringExtra("Area");
		if (listObj.size() < 3 && is.equals("1")) {
			tv_macke_details_money.setText("150" + "元");

		} else {
			tv_macke_details_money.setText("" + all + "元");
		}
		MakeActivity.listObj.clear();
		System.out.println(listObj.get(0).getPhone());

	}

	/**
	 * 设置监听
	 */
	@Click(R.id.bt_macke_details_pay)
	public void check() {
		/**
		 * 完成付款发送信息 先创建活动再添加人员
		 */
		MD_pay md_pay = new MD_pay();
		md_pay.setProvince("510000");
		md_pay.setCity("510100");
		md_pay.setArea(Area);
		md_pay.setExplain("医生上门");
		md_pay.setTitle("医生上门");
		md_pay.setIsParticipate(is);
		md_pay.setAddress(tv_macke_details_address.getText().toString());
		md_pay.setListObj(listObj);
		NApplication.pay_style = Constant.MD_PAY;
		NApplication.md_pay = md_pay;
		ConnectivityManager manager = (ConnectivityManager) (MakeDetailsActivity.this)
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo == null) {
			Toast.makeText(MakeDetailsActivity.this, "网络连接错误，请检查网络设置后重试。",
					Toast.LENGTH_SHORT).show();
		} else {
			PayActivity payActivity = new PayActivity(this, name, total);
			payActivity.name();
		}

	}

	/**
	 * 设置返回监听
	 */
	@Click(R.id.imbt_macke_details_back)
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
