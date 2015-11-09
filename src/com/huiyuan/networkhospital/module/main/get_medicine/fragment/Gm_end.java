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
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.RollDelete;
import com.huiyuan.networkhospital.common.widget.XListView;
import com.huiyuan.networkhospital.entity.Gm_endBean;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.Gm_endAdapter;

@EFragment
public class Gm_end extends Fragment {
	@ViewById
	TextView xlistview_header_time;
	@ViewById(android.R.id.list)
	XListView listView;
	private Dialog loadingDialog;
	private Gm_endAdapter adapter = null;
	private List<Gm_endBean> persons = new ArrayList<Gm_endBean>();
	private ArrayList<Gm_endBean> items = new ArrayList<Gm_endBean>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_gm_end,
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
		RollDelete rl=new RollDelete(getActivity(), persons, adapter, listView){
			@Override
			public void deleteItem(int position) {
				// TODO Auto-generated method stub
				persons.remove(position);
				adapter.notifyDataSetChanged();
			}
			@Override
			public void onDismiss(AbsListView listView,
					int[] reverseSortedPositions) {
				// TODO Auto-generated method stub
				for (int position : reverseSortedPositions) {
					persons.remove(position);
				}
				Toast.makeText(getActivity(), "Removed positions: ",
						Toast.LENGTH_SHORT).show();
			}
		};
	}
	public void showByMyBaseAdapter() {
		adapter = new Gm_endAdapter(getActivity(), persons);
		listView.setAdapter(adapter);
	}
//
//	@ItemClick(android.R.id.list)
//	public void itemclick(int position) {
//		Intent intent = new Intent(getActivity(), Gm_PaymentActivity_.class);
//		startActivity(intent);
//	}
	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		items = new ArrayList<Gm_endBean>();
		items.add(new Gm_endBean());
		items.add(new Gm_endBean());
		items.add(new Gm_endBean());
		items.add(new Gm_endBean());
		items.add(new Gm_endBean());
		items.add(new Gm_endBean());
		items.add(new Gm_endBean());
		items.add(new Gm_endBean());
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
