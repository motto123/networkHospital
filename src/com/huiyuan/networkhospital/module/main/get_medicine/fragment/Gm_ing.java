package com.huiyuan.networkhospital.module.main.get_medicine.fragment;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.widget.XListView;
import com.huiyuan.networkhospital.entity.Gm_ingBean;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.Gm_ingAdapter;

@EFragment
public class Gm_ing extends Fragment {
	@ViewById
	TextView xlistview_header_time;
	@ViewById(android.R.id.list)
	XListView listView;
	private Dialog loadingDialog;
	private Gm_ingAdapter adapter = null;
	private List<Gm_ingBean> persons = new ArrayList<Gm_ingBean>();
	private ArrayList<Gm_ingBean> items = new ArrayList<Gm_ingBean>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_gm_ing,
				container, false);
		return view;
	}
	@AfterViews
	void initBookmarkList() {
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
		newListViewListener.onRefresh();
	}
	public void showByMyBaseAdapter() {
		adapter = new Gm_ingAdapter(getActivity(), persons);
		listView.setAdapter(adapter);
	}

	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		items = new ArrayList<Gm_ingBean>();
		items.add(new Gm_ingBean());
		items.add(new Gm_ingBean());
		items.add(new Gm_ingBean());
		items.add(new Gm_ingBean());
		items.add(new Gm_ingBean());
		items.add(new Gm_ingBean());
		items.add(new Gm_ingBean());
		items.add(new Gm_ingBean());
		persons.addAll(items);
	}

	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
			Handler hander=new Handler();
			hander.postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
//					persons.clear();
					read();
					adapter.notifyDataSetChanged();
					listView.stopRefresh();
					listView.stopLoadMore();
					
				}
			}, 1500);
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
			read();
			adapter.notifyDataSetChanged();
			listView.stopRefresh();
			listView.stopLoadMore();
		}

	};
}
