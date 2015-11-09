package com.huiyuan.networkhospital.module.main.get_medicine.activity;

import java.util.ArrayList;
import java.util.Collection;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.XListView;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.hepeng.GM_visitlist;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.DoctorVisitActivity;
import com.huiyuan.networkhospital.module.main.fragment.PreventionFragment;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.Gm_visitAdapter;
import com.huiyuan.networkhospital.module.main.sport_coaching.activity.SportCoachingActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_get_medicine)
public class GetMedicineActivity extends FragmentActivity {

	private Dialog loadingDialog;
	@ViewById
	TextView xlistview_header_time;
	@ViewById(android.R.id.list)
	XListView listView;
	@ViewById
	ImageButton imbt_getmedicine_back;
	@ViewById
	ImageButton imbt_getmedicine_add;

	private ArrayList<GM_visitlist> doctors = new ArrayList<GM_visitlist>();

	private Gm_visitAdapter adapter = null;

	private int PageIndex = 1;

	private AsyncHttpResponseHandler getvisitHandler;

	private Gson gson = new Gson();
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		hanlder();
	}

	@Override
	protected void onResume() {
		newListViewListener.onRefresh();
		super.onResume();
	}

	@Click({ R.id.imbt_getmedicine_back, R.id.imbt_getmedicine_add })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbt_getmedicine_add:
			Intent intent = new Intent(GetMedicineActivity.this,
					SelectDoctorActivity_.class);
			startActivity(intent);
			break;
		case R.id.imbt_getmedicine_back:
			outgo();
			break;
		default:
			break;
		}
	}

	@AfterViews
	void initBookmarkList() {
		context = GetMedicineActivity.this;
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
		// newListViewListener.onRefresh();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		outgo();
	}
	/**
	 * 返回处理方式
	 */
	public void outgo(){
		if ("notifyBar".equals(NApplication.way)) {
			//从通知栏进来的返回处理方式
			NApplication.way = null;
		}
		Intent intent = new Intent(GetMedicineActivity.this,MainActivity_.class);
		this.startActivity(intent);
		finish();
	}

	public void showByMyBaseAdapter() {
		adapter = new Gm_visitAdapter(this, doctors);
		listView.setAdapter(adapter);
	}

	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			PreventionFragment.loadConversationsWithRecentChat();
			loadingDialog = ProgressDialog.show(GetMedicineActivity.this, "",
					"正在加载中……", true, true);
			// TODO Auto-generated method stub
			doctors.clear();
			PageIndex = 1;
			read();
			Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
			t.setToNow(); // 取得系统时间。
			xlistview_header_time.setText((t.month + 1) + "月" + t.monthDay
					+ "日" + t.hour + "：" + t.minute);
		}

		@Override
		public void onLoadMore() {
			Log.e("", "加载更多");
			loadingDialog = ProgressDialog.show(GetMedicineActivity.this, "",
					"正在加载中……", true, true);
			PageIndex = PageIndex + 1;
			read();
		}

	};

	/**
	 * 用户获取自己的复诊拿药信息
	 */
	public void read() {
		RequestParams parmas = new RequestParams();
		parmas.put("act", "getvisitlist");
		parmas.put("uid", NApplication.userid);
		parmas.put("PageSize", "10");
		parmas.put("PageIndex", PageIndex + "");
		HttpClientUtils.post(Url.department, parmas, getvisitHandler);
	}

	private void hanlder() {
		/**
		 * 显示用户的复诊拿药信息
		 */
		getvisitHandler = new AsyncHttpResponseHandler() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(String arg0) {
				try {
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("1")) {
						//						doctors.clear();
						doctors.addAll((Collection<? extends GM_visitlist>) gson.fromJson(
								jsObject.getString("Data"),
								new TypeToken<ArrayList<GM_visitlist>>() {
								}.getType()));

						if (doctors.size() < PageIndex) {
							Toast.makeText(GetMedicineActivity.this, "没有更多了。",
									Toast.LENGTH_SHORT).show();
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				loadingDialog.dismiss();
				super.onSuccess(arg0);
			}

			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(GetMedicineActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				loadingDialog.dismiss();
				super.onFailure(arg0);
			}

		};

	}
	//销毁activity
	public static void Destroy() {
		((Activity) context).finish();
	}
}
