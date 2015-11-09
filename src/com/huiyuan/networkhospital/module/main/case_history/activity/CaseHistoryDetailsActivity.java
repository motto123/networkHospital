package com.huiyuan.networkhospital.module.main.case_history.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.Medicine;
import com.huiyuan.networkhospital.entity.hepeng.GM_visitlist;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_indent_detail)
public class CaseHistoryDetailsActivity extends BaseActivity {
	@ViewById
	TextView tvCreateTime;
	@ViewById
	TextView tvIndent;
	@ViewById
	TextView tvName;
	@ViewById
	TextView tvTel;
	@ViewById
	TextView tvId;
	@ViewById
	TextView tvDepartment;
	@ViewById
	TextView tvCost;
	@ViewById
	TextView Sex123;
	@ViewById
	TextView tvTime;
	/**
	 * 显示医生开药的价格
	 */
	private AsyncHttpResponseHandler getprescriptionHandler;
	
	private Gson gson = new Gson();
	
	private GM_visitlist indent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hanlder();
	}

	@AfterViews
	protected void init() {
		indent = (GM_visitlist) getIntent().getSerializableExtra(GlobalConstant.KEY_DATA);
		read();
		tvCreateTime.setText(indent.getCreateTime());
		tvIndent.setText(indent.getTitle());
		tvName.setText(NApplication.username);
		tvTel.setText(NApplication.phone);
		tvId.setText(NApplication.IC);
		tvDepartment.setText("医生姓名："+indent.getName());
		if (NApplication.sex.equals("true")) {
			Sex123.setText("男");
		} else {
			Sex123.setText("女");
		}
		tvTime.setText("医生电话："+indent.getdPhone());//医生电话
	}

	@Click(R.id.ibtnBack)
	protected void skip(){
		finish();
	}
	
	/**
	 * 用户获取医生开药信息列表
	 */
	public void read() {
		RequestParams parmas = new RequestParams();
		parmas.put("act", "getprescription");
		parmas.put("Vid", indent.getId());
		HttpClientUtils.post(Url.department, parmas, getprescriptionHandler);
	}
	
	private void hanlder() {
		
		getprescriptionHandler = new AsyncHttpResponseHandler() {

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(String arg0) {
				try {
					BigDecimal result = new BigDecimal("0");  
					JSONObject jsObject = new JSONObject(arg0);
					Log.e("arg1", arg0);
					List<Medicine> medicins = new ArrayList<Medicine>();
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
						tvCost.setText("订单价格："+result + "元");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				super.onSuccess(arg0);
			}

			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(CaseHistoryDetailsActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0);
			}

		};
	}
	
}
