package com.huiyuan.networkhospital.module.main.case_history.activity;

import java.util.ArrayList;
import java.util.Collection;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
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
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.hepeng.GM_visitlist;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.Gm_visitAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_gm_end)
public class CaseHistoryActivity extends FragmentActivity {

	@ViewById
	TextView xlistview_header_time;
	
	@ViewById(android.R.id.list)
	XListView listView;
	
	@ViewById
	ImageButton imbt_gm_end;

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
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * 设置监听
	 */
	@Click({ R.id.imbt_gm_end, R.id.imbt_getmedicine_add })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbt_gm_end:
			Tools.startActivity(this, MainActivity_.class);
			finish();
			break;
		}
	}
	
	@ItemClick(android.R.id.list)
	protected void skip(int position){
		Intent intent = new Intent(this, CaseHistoryDetailsActivity_.class);
		intent.putExtra(GlobalConstant.KEY_DATA, doctors.get(position-1));
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		Tools.startActivity(this, MainActivity_.class);
		super.onBackPressed();
	}

	/**
	 * 初始化
	 */
	@AfterViews
	void initBookmarkList() {
		hanlder();
		context = CaseHistoryActivity.this;
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		adapter = new Gm_visitAdapter(this, doctors,"CaseHistoryActivity");
		listView.setAdapter(adapter);
		newListViewListener.onRefresh();
		// newListViewListener.onRefresh();
	}

	/**
	 * 用户获取自己复诊拿药列表listview刷新和加载更多方法
	 */
	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
			// TODO Auto-generated method stub
			doctors.clear();
			PageIndex = 1;
			read();
			listView.stopRefresh();
			listView.stopLoadMore();
			Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
			t.setToNow(); // 取得系统时间。
			xlistview_header_time.setText((t.month+1) + "月" + t.monthDay + "日"
					+ t.hour + "：" + t.minute);
		}

		@Override
		public void onLoadMore() {
			Log.e("", "加载更多");
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
			PageIndex = PageIndex + 1;
			read();
			listView.stopRefresh();
			listView.stopLoadMore();
		}

	};

	/**
	 * 用户获取自己复诊拿药列表
	 */
	public void read() {
		RequestParams parmas = new RequestParams();
		parmas.put("act", "getvisitlist");
		parmas.put("uid", NApplication.userid);
		parmas.put("PageSize", "10");
		parmas.put("PageIndex", PageIndex + "");
		parmas.put("type", "0");//过滤掉聊天中和进行中
		HttpClientUtils.post(Url.department, parmas, getvisitHandler);
	}

	/**
	 * 用户获取自己复诊拿药列表，返回回来的数据并且解析
	 */
	private void hanlder() {
		getvisitHandler = new AsyncHttpResponseHandler() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(String arg0) {
				try {
					JSONObject jsObject = new JSONObject(arg0);
					Log.e("arg0", arg0);
					if (jsObject.getString("Status").equals("1")) {
						doctors.addAll((Collection<? extends GM_visitlist>) gson.fromJson(
								jsObject.getString("Data"),
								new TypeToken<ArrayList<GM_visitlist>>() {
								}.getType()));
						adapter.notifyDataSetChanged();
						if (doctors.size() < PageIndex) {
							Toast.makeText(CaseHistoryActivity.this, "没有更多了。",
									Toast.LENGTH_SHORT).show();
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				super.onSuccess(arg0);
			}

			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(CaseHistoryActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0);
			}

		};

	}

	public static void Destroy() {
		((Activity) context).finish();
	}
}
