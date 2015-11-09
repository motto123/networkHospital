package com.huiyuan.networkhospital.module.user.fragment;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.ScBean;
import com.huiyuan.networkhospital.module.main.sport_coaching.adapter.ScAdapter;
import com.huiyuan.networkhospital.module.user.activity.SCIndentActivity_;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EFragment
public class Indent_fragment_SC extends Fragment {
	@ViewById
	TextView xlistview_header_time;
	@ViewById(android.R.id.list)
	XListView listView;
	
	private ProgressDialog dialog;
	private ScAdapter adapter = null;
	private List<ScBean> persons = new ArrayList<ScBean>();
	private ArrayList<ScBean> items = new ArrayList<ScBean>();//临时
	private AsyncHttpResponseHandler SC_handler;
	private int PageSize = 10, PageIndex = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_indent_sc, container, false); 
	}
	@AfterViews
	void initBookmarkList() {
		dohandler();
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
		newListViewListener.onRefresh();
		//		RollDelete rl = new RollDelete(SportCoachingActivity.this, persons,
		//				adapter, listView) {
		//			@Override
		//			public void deleteItem(int position) {
		//				// TODO Auto-generated method stub
		//				persons.remove(position);
		//				adapter.notifyDataSetChanged();
		//			}
		//
		//			@Override
		//			public void onDismiss(AbsListView listView,
		//					int[] reverseSortedPositions) {
		//				// TODO Auto-generated method stub
		//				for (int position : reverseSortedPositions) {
		//					persons.remove(position);
		//				}
		//				Toast.makeText(SportCoachingActivity.this,
		//						"Removed positions: ", Toast.LENGTH_SHORT).show();
		//			}
		//		};
	}
	public void showByMyBaseAdapter() {
		adapter = new ScAdapter(getActivity(), persons);
		listView.setAdapter(adapter);
	}
	
	
	/**
	 * 从服务器获取数据,获取运动指导订单
	 */
	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "mlist");
		params.put("UID", NApplication.userid);
		params.put("type", "0");
		params.put("PageSize", "" + PageSize);
		params.put("PageIndex", "" + PageIndex);
		client.post(Url.userMovementGuidance, params, SC_handler);
	}

	//list点击事件，跳转界面
	@ItemClick(android.R.id.list)
	public void itemclick(int position) {
			Intent intent = new Intent(getActivity(),SCIndentActivity_.class);
			intent.putExtra(GlobalConstant.KEY_DATA, persons.get(position-1));
			startActivity(intent);
	}

	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中",true,true);
			persons.clear();
			PageIndex = 1;
			read();
			Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
			t.setToNow(); // 取得系统时间。
			xlistview_header_time.setText((t.month+1) + "月" + t.monthDay + "日"
					+ t.hour + "：" + t.minute);
		}

		@Override
		public void onLoadMore() {
			Log.e("", "加载更多");
			dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中");
			PageIndex = 1 + PageIndex;
			read();
			adapter.notifyDataSetChanged();
			listView.stopRefresh();
			listView.stopLoadMore();
		}

	};

	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 显示运动指导订单信息
		 */
		SC_handler = new AsyncHttpResponseHandler() {
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
				Tools.LogE("运动指导订单："+arg0);
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					Gson gson = new Gson();
					if (jsonObject.getString("Status").equals("1")) {
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<ScBean>>() {
						}.getType());
						//						for (ScBean a:persons) {
						//							if (a.getCID().equals(items.get(0).getCID())) {
						//								isExit = true;
						//								break;
						//							}
						//						}
						//						if (!isExit) {
						//							persons.addAll(items);
						//						}else {
						//							isExit = false;
						//						}
						persons.addAll(items);
					} else {
						Tools.showTextToast(getActivity(),
								"没有数据可以显示了");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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

}
