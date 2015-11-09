package com.huiyuan.networkhospital.module.user;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.RollDelete;
import com.huiyuan.networkhospital.common.widget.XListView;
import com.huiyuan.networkhospital.entity.DvAddressBean;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.Dv_ChoseAddressDetailesActivity_;
import com.huiyuan.networkhospital.module.user.adapter.ChoseAddressAdapter;

@EActivity(R.layout.activity_chose_address)
public class ChoseAddress extends BaseActivity {
	@ViewById
	ImageButton imbt_chose_address_user_back;
	@ViewById
	TextView xlistview_header_time;
	@ViewById
	Button bt_chose_address_user_deta_sum;
	@ViewById(android.R.id.list)
	XListView listView;
	private ChoseAddressAdapter adapter = null;
	private List<DvAddressBean> persons = new ArrayList<DvAddressBean>();
	private ArrayList<DvAddressBean> items = new ArrayList<DvAddressBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	void initBookmarkList() {
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
		newListViewListener.onRefresh();
		RollDelete rl = new RollDelete(ChoseAddress.this, persons, adapter,
				listView) {
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
				Toast.makeText(ChoseAddress.this, "Removed positions: ",
						Toast.LENGTH_SHORT).show();
			}
		};
	}
	@ItemClick(android.R.id.list)
	public void itemclick(int position) {
		Intent intent = new Intent(ChoseAddress.this, 
				Dv_ChoseAddressDetailesActivity_.class);
		startActivity(intent);
	}

	@Click({ R.id.imbt_chose_address_user_back,
			R.id.bt_chose_address_user_deta_sum })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_chose_address_user_deta_sum:
			Intent intent = new Intent(ChoseAddress.this,
					Dv_ChoseAddressDetailesActivity_.class);
			startActivity(intent);
			break;
		case R.id.imbt_chose_address_user_back:
			finish();
			break;
		default:
			break;
		}
	}

	public void showByMyBaseAdapter() {
		adapter = new ChoseAddressAdapter(ChoseAddress.this, persons);
		listView.setAdapter(adapter);
	}

	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		items = new ArrayList<DvAddressBean>();
		items.add(new DvAddressBean());
		items.add(new DvAddressBean());
		items.add(new DvAddressBean());
		items.add(new DvAddressBean());
		items.add(new DvAddressBean());
		items.add(new DvAddressBean());
		items.add(new DvAddressBean());
		items.add(new DvAddressBean());
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
					persons.clear();
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
