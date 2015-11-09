package com.huiyuan.networkhospital.module.main.sport_coaching.activity;

/**
 * 此处用的是复诊拿药里面选择医生界面的listview布局
 * 
 */
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.huanxin.activity.ChatMainActivity;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.main.check.Check_;
import com.huiyuan.networkhospital.module.main.fragment.PreventionFragment;
import com.huiyuan.networkhospital.module.main.sport_coaching.adapter.ScAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * .
 * @ClassName:  SportCoachingActivity   
 * @Description:TODO运动指导之主界面
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:05:27   
 *
 */
@EActivity(R.layout.activity_sport_coaching)
public class SportCoachingActivity extends BaseActivity {
	@ViewById
	TextView xlistview_header_time;
	@ViewById
	ImageButton imbt_sport_coaching_back;
	@ViewById
	ImageButton imbt_sport_coaching_add;
	@ViewById(android.R.id.list)
	XListView listView;
	private Dialog loadingDialog;
	private ScAdapter adapter = null;
	private List<ScBean> persons = new ArrayList<ScBean>();
	private ArrayList<ScBean> items = new ArrayList<ScBean>();// 临时
	private AsyncHttpResponseHandler SC_handler;
	private int PageSize = 10, PageIndex = 1;
	private boolean isExit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Click({ R.id.imbt_sport_coaching_back, R.id.imbt_sport_coaching_add })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbt_sport_coaching_add:
			Intent intent = new Intent(SportCoachingActivity.this,
					SelectCoach_.class);
			startActivity(intent);
			break;
		case R.id.imbt_sport_coaching_back:
			outgo();
			break;
		default:
			break;
		}
	}

	@AfterViews
	void initBookmarkList() {
		dohandler();
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
		//		newListViewListener.onRefresh();
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
		adapter = new ScAdapter(SportCoachingActivity.this, persons);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		newListViewListener.onRefresh();
		super.onResume();
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
		Intent intent = new Intent(SportCoachingActivity.this,MainActivity_.class);
		this.startActivity(intent);
		finish();
	}
	
	/**
	 * 从服务器获取数据
	 */
	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "mlist");
		params.put("UID", NApplication.userid);
		params.put("PageSize", "" + PageSize);
		params.put("PageIndex", "" + PageIndex);
		client.post(Url.userMovementGuidance, params, SC_handler);
	}

	//
	@ItemClick(android.R.id.list)
	public void itemclick(int position) {
		// intent.putExtra(GlobalConstant.KEY_DATA,
		// persons.get(position).getId());
		// intent.putExtra("flag", "c");
		// context.startActivity(intent);
		ScBean ss = (ScBean) adapter.getItem(position - 1);
		if (persons.get(position - 1).getState().equals("2")) {
			// 聊天记录
			Tools.LogI(persons.get(position - 1).getState() + "  聊天记录页面");
			if (Tools.matching(persons.get(position - 1).getCreateTime())) {
				SportCoachingActivity.this.startActivity(new Intent(
						SportCoachingActivity.this, ChatMainActivity.class)
						.putExtra("userId", "c" + ss.getcPhone())
						.putExtra("toChatName", ss.getCName())
						.putExtra("self",
								NApplication.getInstance().getUserName())
						.putExtra("record", "record"));
			} else {
				Intent intent = new Intent(SportCoachingActivity.this,
						Check_.class);
				intent.putExtra(GlobalConstant.KEY_DATA,
						persons.get(position - 1).getId());
				startActivity(intent);
			}
		} else {
			// 进入聊天界面
			Tools.LogI(persons.get(position - 1).getState() + "  聊天页面");
			Tools.comeinHXChat(this, "c" + ss.getcPhone(), ss.getCName());
		}
	}

	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			PreventionFragment.loadConversationsWithRecentChat();
			loadingDialog = ProgressDialog.show(SportCoachingActivity.this, "",
					"正在加载中……", true, true);
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
			loadingDialog = ProgressDialog.show(SportCoachingActivity.this, "",
					"正在加载中……", true, true);
			PageIndex = 1 + PageIndex;
			read();
		}

	};

	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		SC_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(SportCoachingActivity.this,
						"网络连接错误，请检查网络设置后重试。", Toast.LENGTH_SHORT).show();
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				loadingDialog.dismiss();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					Gson gson = new Gson();
					if (jsonObject.getString("Status").equals("1")) {
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<ScBean>>() {
						}.getType());
						persons.addAll(items);
					} else {
						Tools.showTextToast(SportCoachingActivity.this,
								"没有数据可以显示了");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				loadingDialog.dismiss();
				super.onSuccess(arg0);
			}
		};

	}
}
