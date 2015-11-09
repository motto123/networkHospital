package com.huiyuan.networkhospital.module.main.doctor_visit.fragment;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
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
import com.huiyuan.networkhospital.module.main.doctor_visit.adapter.DvAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
* 
* @ClassName:  Dv_fragment_on   
* @Description:TODO未参加的人员页面
* @author: 邓肇均
* @date:   2015年10月21日 下午4:55:07   
*
*/
@EFragment
public class Dv_fragment_un extends Fragment {

	@ViewById
	TextView xlistview_header_time;
	@ViewById(android.R.id.list)
	XListView listView;
	private Dialog loadingDialog;
	private DvAdapter adapter = null;
	private List<DvBean> persons = new ArrayList<DvBean>();
	private int PageSize = 10, PageIndex = 1;
	private AsyncHttpResponseHandler DV_handler;
	private ArrayList<DvBean> items = new ArrayList<DvBean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_dv_fragment_on,
				container, false);
		return view;
	}

	@AfterViews
	void initBookmarkList() {
		dohandler();
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
	}

	public void showByMyBaseAdapter() {
		adapter = new DvAdapter(getActivity(), persons);
		listView.setAdapter(adapter);
	}

	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "list");
		params.put("uid", NApplication.userid);
		params.put("Participate", "0");
		params.put("PageSize", "" + PageSize);
		params.put("PageIndex", "" + PageIndex);
		params.put("IsParticipate", "0");
		Log.e("", Url.Operation + "?" + params);
		client.post(Url.Operation, params, DV_handler);
	}

	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		DV_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(getActivity(), "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
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
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				super.onSuccess(arg0);
			}
		};

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		newListViewListener.onRefresh();
	}

	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
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
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
			PageIndex = PageIndex + 1;
			read();
			adapter.notifyDataSetChanged();
			listView.stopRefresh();
			listView.stopLoadMore();
		}

	};
}
