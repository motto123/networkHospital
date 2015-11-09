package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.LookOver;
import com.huiyuan.networkhospital.entity.zlk.Refund;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.doctor_visit.adapter.Dv_personAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 查看详情页面
 * 
 * @author lenovo
 *
o */
@EActivity(R.layout.activity_look_over2)
public class LookOverActivity extends BaseActivity {
	@ViewById
	ImageButton imbt_look_over_back;
	@ViewById
	Button bt_particpation_orderloading, bt_particpation_disjoin;
	@ViewById
	TextView tv_look_over_address;
	@ViewById
	TextView TextView01;
	@ViewById
	TextView TextView02;
	@ViewById
	TextView TextView03;
	@ViewById
	ListView listView;

	private ProgressDialog loadDialog;

	private ArrayList<LookOver> items = new ArrayList<LookOver>();
	private ArrayList<LookOver> list = new ArrayList<LookOver>();
	private String address = "";
	private String id = "";
	private String state = "";

	/**
	 * 退款信息列表
	 */
	private ArrayList<Refund> refunds;

	/**
	 * 展示在界面上的数据
	 * 该请求与detailesHandler是同一个请求，优化代码可以只使用一个请求
	 */
	private AsyncHttpResponseHandler LOhandler;
	/**
	 * 退款
	 * @param refunds
	 */
	private AsyncHttpResponseHandler refundHandler;
	/**
	 * 获得医生上门订单中所有参与人的id
	 * @param refunds
	 */
	private AsyncHttpResponseHandler detailesHandler;
	
	private Dv_personAdapter adapter;

	// 查看
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
		dohandler();
		final Intent intent = getIntent();
		// 从Intent当中根据key取得value,还要取得id
		address = intent.getStringExtra("address");
		id = intent.getStringExtra("id");
		state = intent.getStringExtra("state");
		tv_look_over_address.setText(address);
		if (state.equals("0")) {
			bt_particpation_disjoin.setVisibility(View.VISIBLE);
		} else if (state.equals("1")) {
			TextView01.setVisibility(View.VISIBLE);
			TextView01.setText("医生姓名:" + intent.getStringExtra("name"));
			TextView02.setVisibility(View.VISIBLE);
			TextView02.setText("医生电话:" + intent.getStringExtra("phone"));
			TextView03.setVisibility(View.VISIBLE);
			TextView03.setText("预约时间:" + intent.getStringExtra("atime"));
		}
		adapter = new Dv_personAdapter(list, this);
		listView.setAdapter(adapter);	
		read();
	}

	/**
	 * 对界面数据发送请求
	 */
	private void read() {
		loadDialog = ProgressDialog.show(LookOverActivity.this, "提示", "正在加载中",true,true);
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "personnellist");
		params.put("AID", id);
		Log.e("", Url.Operation + "?" + params);
		client.post(Url.Operation, params, LOhandler);
	}

	/**
	 * 设置监听
	 */
	@Click({ R.id.bt_particpation_orderloading, R.id.bt_particpation_disjoin })
	public void check(View v) {
		switch (v.getId()) {
		case R.id.bt_particpation_orderloading:
			finish();
			break;
		case R.id.bt_particpation_disjoin:

			Builder dialog=new AlertDialog.Builder(this);
			dialog.setTitle("温馨提示")
			.setMessage("是否退款！")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(refunds.isEmpty()){
						Toast.makeText(LookOverActivity.this, "网络波动，请稍候再试", 0).show();
					}else{
						loadDialog = ProgressDialog.show(LookOverActivity.this, "提示", "正在加载中",true,true);
						refund(refunds);
					}
					dialog.cancel();
				}
			});
			dialog.create().show();
			break;
		}
	}

	/**
	 * 设置返回监听
	 */
	@Click(R.id.imbt_look_over_back)
	public void back() {
		//		Intent intent = new Intent(LookOverActivity.this,
		//				DoctorVisitActivity_.class);
		DoctorVisitActivity.StringE = "2";
		//		startActivity(intent);
		finish();
	}


	private void dohandler() {
		LOhandler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(LookOverActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				if(null != loadDialog){
					loadDialog.dismiss();
				}
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					/**
					 * 显示数据
					 */
					String isRefund = null;
					JSONObject jsonObject = new JSONObject(arg0);
					Gson gson = new Gson();
					if (jsonObject.getJSONArray("Data").length() > 0) {
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<LookOver>>() {
						}.getType());
						list.addAll(items);
						adapter.notifyDataSetChanged();
						System.out.println(items.size());
						for (LookOver a : items) {
							if(NApplication.userid.equals(a.getUID())){
								isRefund = a.getIsRefund();
							}
						}
						if("0".equals(isRefund)){//未退款

						}else if("1".equals(isRefund)){//已退款
							bt_particpation_disjoin.setText("退款成功");
							bt_particpation_disjoin.setEnabled(false);
						}else if("2".equals(isRefund)){//退款失败
							bt_particpation_disjoin.setText("退款失败");
							bt_particpation_disjoin.setEnabled(false);
						}else if("3".equals(isRefund)){//退款中
							bt_particpation_disjoin.setText("退款中");
							bt_particpation_disjoin.setEnabled(false);
						}
						refunds =	gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<Refund>>() {
						}.getType());
						if(null != loadDialog){
							loadDialog.dismiss();
						}
					} else {
						if(null != loadDialog){
							loadDialog.dismiss();
						}
						Tools.showTextToast(LookOverActivity.this, "没有数据可以显示了");
					}

				} catch (Exception e) {
					Tools.showTextToast(LookOverActivity.this, "没有数据可以显示了");
					e.printStackTrace();
					if(null != loadDialog){
						loadDialog.dismiss();
					}
				}
				super.onSuccess(arg0);
			}
		};

		refundHandler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				Tools.showTextToast(LookOverActivity.this, "网络连接异常，请求失败!");
				loadDialog.dismiss();
				super.onFailure(arg0);
			}

			@Override
			public void onSuccess(String arg0) {
				JSONObject jsobj;
				try {
					jsobj = new JSONObject(arg0);
					if(Integer.valueOf(jsobj.getString("Data"))>0){
						Intent intent = new Intent(LookOverActivity.this,
								DoctorVisitActivity_.class);
						DoctorVisitActivity.StringE = "2";
						Toast.makeText(LookOverActivity.this.getApplicationContext(), "取消成功，退款正在处理中！",0).show();
						startActivity(intent);
						loadDialog.dismiss();
						finish();
					}
				} catch (Exception e) {
					loadDialog.dismiss();
					e.printStackTrace();
				}
				super.onSuccess(arg0);
			}
		};


	}

	/**
	 * 退款
	 * @param refunds
	 */
	private void refund(ArrayList<Refund> refunds){
		ArrayList<Refund> r = new ArrayList<Refund>();
		for(int i = 0;i<refunds.size();i++){
			if(NApplication.userid.equals(refunds.get(i).getUID())){
				r.add(refunds.get(i));
			}
		}
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Refund>>() {
		}.getType();
		/*
		 * [{"AID":"11","Age":"11","Brief":"帮助","IsRefund":"1","Number":"1002200679201510261337573297",
		 * "Phone":"11111111111","UID":"2","createTime":"2015-09-26 00:00:00","id":"19","name":"请求","sex":"false"}]
		 */
		Gson gson = new Gson();
		params.put("act", "updateai");
		params.put("list", gson.toJson(r, type));
		Log.e("", Url.Operation + "?" + params);
		client.post(Url.Operation, params,refundHandler );
	}
	/**
	 * 获得医生上门订单中所有参与人的id
	 */
	private void getDetails(){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "personnellist");
		params.put("AID", id);
		Log.e("", Url.Operation + "?" + params);
		client.post(Url.Operation, params,detailesHandler );
	}



}
