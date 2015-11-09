package com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.XListView;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.HealthBean;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.adapter.RehabilitationAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @ClassName:  RehabilitationTutorialActivity   
 * @Description:TODO康复教程的listview效果
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:00:27   
 *
 */
@EActivity(R.layout.activity_rehabilitation_tutorial)
public class RehabilitationTutorialActivity extends BaseActivity {
	public RehabilitationAdapter adapter = null;
	public boolean isVideo = true;
	@ViewById
	ImageButton ibtnRehabilitationTutorialTitleBack;
	@ViewById(android.R.id.list)
	XListView lvrehabilitationTutorial;
	@ViewById
	TextView xlistview_header_time;
	int PageIndex = 0;
	private List<HealthBean> persons = new ArrayList<HealthBean>();
	private ArrayList<HealthBean> items = new ArrayList<HealthBean>();// 临时

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Click({ R.id.ibtnRehabilitationTutorialTitleBack })
	protected void skip(View v) {
		int i = v.getId();
		switch (v.getId()) {
		case R.id.ibtnRehabilitationTutorialTitleBack:
			// Tools.startActivity(AlterSucceedActivity.this,
			// AlterPWActivity_.class);
			Tools.startActivity(this, MainActivity_.class);
			finish();
			break;

		}
	}

	@Override
	public void onBackPressed() {
		Tools.startActivity(this, MainActivity_.class);
		super.onBackPressed();
	}
	
	@AfterViews
	public void showAdapter() {
		lvrehabilitationTutorial.setPullRefreshEnable(true);// 设置下拉刷新
		lvrehabilitationTutorial.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		lvrehabilitationTutorial.setPullLoadEnable(true);// 设置上拉刷新
		adapter = new RehabilitationAdapter(
				RehabilitationTutorialActivity.this, persons, isVideo);
		lvrehabilitationTutorial.setAdapter(adapter);
		newListViewListener.onRefresh();
	}

	private void read() {
		// TODO Auto-generated method stub
		RequestParams parmas = new RequestParams();
		parmas.put("act", "list");
		parmas.put("PageSize", "10");
		parmas.put("PageIndex", PageIndex + "");
		System.out.println(Url.Registers + "?" + parmas.toString());
		HttpClientUtils.post(Url.kangfujiaocheng, parmas,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
					}

					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(arg0);
							Gson gson = new Gson();
							if (jsonObject.getString("Status").equals("1")) {
								items = gson.fromJson(
										jsonObject.getString("Data"),
										new TypeToken<ArrayList<HealthBean>>() {
										}.getType());
								persons.addAll(items);
							} else {
								Tools.showTextToast(
										RehabilitationTutorialActivity.this,
										"没有数据可以显示了");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						adapter.notifyDataSetChanged();
						lvrehabilitationTutorial.stopRefresh();
						lvrehabilitationTutorial.stopLoadMore();
						super.onSuccess(arg0);
					}

				});
	}

	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
			// TODO Auto-generated method stub
			persons.clear();
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
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
			PageIndex += 1;
			read();
			adapter.notifyDataSetChanged();
			lvrehabilitationTutorial.stopRefresh();
			lvrehabilitationTutorial.stopLoadMore();
		}

	};

}
