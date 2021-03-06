package com.huiyuan.networkhospital.module.user.fragment;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.XListView;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.DvBean;
import com.huiyuan.networkhospital.module.main.doctor_visit.adapter.Dv_onAdapter;
import com.huiyuan.networkhospital.module.user.DataEditeActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EFragment
public class Indent_fragment_DV extends Fragment {
	@ViewById
	TextView xlistview_header_time;
	private Dialog loadingDialog;
	@ViewById(android.R.id.list)
	XListView listView;

	private Dv_onAdapter adapter = null;
	private List<DvBean> persons = new ArrayList<DvBean>();
	private int PageSize = 10, PageIndex = 1;
	private AsyncHttpResponseHandler DV_handler;
	private ArrayList<DvBean> items = new ArrayList<DvBean>();
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_indent_dv, container, false); 
	}
	@AfterViews
	void initBookmarkList() {
		dohandler();
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
		newListViewListener.onRefresh();
	}
	/**
	 * 设置adapter
	 */
	public void showByMyBaseAdapter() {
		adapter = new Dv_onAdapter(getActivity(), persons);
		listView.setAdapter(adapter);
	}
	/**
	 * 医生上门订单
	 */
	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "list");
		params.put("uid", NApplication.userid);
		params.put("type", "0");
		params.put("Participate", "1");
		params.put("PageSize", "" + PageSize);
		params.put("PageIndex", "" + PageIndex);
		//		params.put("IsParticipate", "0");
		Log.e("", Url.Operation + "?" + params);
		client.post(Url.Operation, params, DV_handler);
	}

	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 显示获取到的医生上门订单
		 */
		DV_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(getActivity(), "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				if (dialog != null) {
					dialog.dismiss();
				}
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				Tools.LogE("医生上门订单："+arg0);
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					Gson gson = new Gson();
					if (jsonObject.getJSONArray("Data").length() > 0) {
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<DvBean>>() {
						}.getType());
						persons.clear();
						persons.addAll(items);
					} else {
						// Tools.showTextToast(getActivity(), "没有数据可以显示了");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					// Tools.showTextToast(getActivity(), "没有数据可以显示了");
					e.printStackTrace();
				}
				if (dialog != null) {
					dialog.dismiss();
				}
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				super.onSuccess(arg0);
			}
		};

	}
	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中",true,true);
			// TODO Auto-generated method stub
			PageIndex = 1;
			read();
			adapter.notifyDataSetChanged();
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
			dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中");
			PageIndex = PageIndex + 1;
			read();
			adapter.notifyDataSetChanged();
			listView.stopRefresh();
			listView.stopLoadMore();
		}

	};

}
