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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.Medicine;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.MedicineIndentsAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
@EActivity(R.layout.activity_gm_checkprescription)
public class Gm_checkprescriptionActivity extends Activity {
	@ViewById
	ListView lvlist;
	@ViewById
	TextView tvTotal;
	
	private String Vid = null;
	private MedicineIndentsAdapter adapter;
	private AsyncHttpResponseHandler getprescriptionHandler;
	private List<Medicine> medicins = new ArrayList<Medicine>();
	private Gson gson = new Gson();
	private float price;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_gm_checkprescription);
		hanlder();
		
	}
	
	@AfterViews
	protected void init(){
		Intent it = getIntent();
		Vid = it.getStringExtra("Vid");
		adapter=new MedicineIndentsAdapter(medicins, this);
		lvlist.setAdapter(adapter);
		read();
	}

	@Click({R.id.ivBack})
	protected void back(View v){
		switch (v.getId()) {
		//返回上一个页面
		case R.id.ivBack:
			this.finish();
			break;
		}
	}
	
	
	/**
	 * 用户获取医生开药信息列表
	 */
	 public void read() {
		 RequestParams parmas=new RequestParams();
			parmas.put("act", "getprescription");
			parmas.put("Vid", Vid);
			HttpClientUtils.post(Url.department, parmas, getprescriptionHandler);
	}
	
	 private void hanlder() {
		 /**
		  * 显示医生开药信息列表
		  */
		 getprescriptionHandler=new AsyncHttpResponseHandler(){
				@SuppressWarnings("unchecked")
				@Override
			
				public void onSuccess(String arg0) {
					try {
						BigDecimal result = new BigDecimal("0");
						JSONObject jsObject = new JSONObject(arg0);
						Log.e("arg1", arg0);
						if (jsObject.getString("Status").equals("1")) {
							medicins.addAll((Collection<? extends Medicine>) gson.fromJson(jsObject.getString("Data"),
									new TypeToken<ArrayList<Medicine>>() {
									}.getType()));
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
					Toast.makeText(Gm_checkprescriptionActivity.this, "网络连接错误，请检查网络设置后重试。",
							Toast.LENGTH_SHORT).show();
					super.onFailure(arg0);
				}

			};
			
		}
}
