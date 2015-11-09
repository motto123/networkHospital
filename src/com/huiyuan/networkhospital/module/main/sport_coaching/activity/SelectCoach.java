package com.huiyuan.networkhospital.module.main.sport_coaching.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.XListView;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.SportSelect;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.GetMedicineActivity;
import com.huiyuan.networkhospital.module.main.sport_coaching.adapter.SelectCAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @ClassName:  SelectCoach   
 * @Description:TODO运动指导之选择教练
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:05:14   
 *
 */
@EActivity(R.layout.activity_select_coach)
public class SelectCoach extends BaseActivity {
	@ViewById
	TextView xlistview_header_time;
	private AsyncHttpResponseHandler SC_handler;
	@ViewById
	Button ll_sport_chose1;
	@ViewById
	Button ll_sport_chose2;
	@ViewById
	LinearLayout ll_sport_person;
	@ViewById
	ImageButton imbt_sport_person_back;
	@ViewById(android.R.id.list)
	XListView listView;
	@ViewById(R.id.sp_sport_person1)
	Spinner provinceSpinner;
	@ViewById(R.id.sp_sport_person2)
	Spinner citySpinner;
	@ViewById(R.id.sp_sport_person3)
	Spinner countySpinner;
	private Dialog loadingDialog;
	static Context context;
	private SelectCAdapter adapter = null;
	private List<SportSelect> persons = new ArrayList<SportSelect>();
	private ArrayList<SportSelect> items = new ArrayList<SportSelect>();
	ArrayAdapter<String> provinceAdapter = null; // 省级适配器
	ArrayAdapter<String> cityAdapter = null; // 地级适配器
	ArrayAdapter<String> countyAdapter = null; // 县级适配器
	static int provincePosition = 0;
	static String Area = "510101";
	static String Top = "0";
	boolean key = true;
	int onrekey = 1;
	private int PageSize = 10, PageIndex = 1;
	// 省级选项值
	private String[] province = new String[] { "四川" };// ,"重庆","黑龙江","江苏","山东","浙江","香港","澳门"};
	// 地级选项值
	private String[][] city = new String[][] { { "成都市" } };
	// , "自贡市", "攀枝花市", "泸州市", "德阳市", "绵阳市", "广元市", "遂宁市",
	// "内江市", "乐山市", "南充市", "眉山市", "宜宾市", "广安市", "达州市", "雅安市", "巴中市",
	// "资阳市", "阿坝藏族羌族自治州", "甘孜藏族自治州", "凉山彝族自治州" } };
	// 县级选项值
	private String[][][] county = new String[][][] { { // 北京
	{ "市辖区", "锦江区", "青羊区", "金牛区", "武侯区", "成华区", "龙泉驿区", "青白江区", "新都区", "温江县",
			"金堂县", "双流县", "郫　县", "大邑县", "蒲江县", "新津县", "都江堰市", "彭州市", "邛崃市",
			"崇州市" } }, };
	private String countyid[] = new String[] { "510101", "510104", "510105",
			"510106", "510107", "510108", "510112", "510113", "510114",
			"510115", "510121", "510122", "510124", "510129", "510131",
			"510132", "510181", "510182", "510183", "510184" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Click({ R.id.imbt_sport_person_back, R.id.ll_sport_chose1,
			R.id.ll_sport_chose2 })
	public void back(View v) {
		switch (v.getId()) {
		case R.id.imbt_sport_person_back:
			finish();

			break;
		case R.id.ll_sport_chose1:
			if (ll_sport_person.getVisibility() == View.VISIBLE) {
				ll_sport_person.setVisibility(View.GONE);
			} else {
				ll_sport_person.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.ll_sport_chose2:
			if (Top.endsWith("0")) {
				Top = "1";
				ll_sport_chose2.setText(R.string.ll_sport_chose3);
			} else {
				Top = "0";
				ll_sport_chose2.setText(R.string.ll_sport_chose2);
			}
			newListViewListener.onRefresh();
			// Intent intent2 = new Intent(Intent.ACTION_SEND);
			// intent2.setType("text/plain");
			// intent2.putExtra(Intent.EXTRA_SUBJECT, "运动助理");
			// intent2.putExtra(
			// Intent.EXTRA_TEXT,
			// "我正在用《运动助理》"
			// + "发表的："
			// + "http://118.112.183.197:9111/imgserver/APK/SportAssistant.apk"
			// + "");
			// startActivity(Intent.createChooser(intent2, "运动助理"));
			break;

		default:
			break;
		}

	}

	@AfterViews
	void initBookmarkList() {
		context=SelectCoach.this;
		dohandler();
		listView.setPullRefreshEnable(true);// 设置下拉刷新
		listView.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
		listView.setPullLoadEnable(true);// 设置上拉刷新
		showByMyBaseAdapter();
		// newListViewListener.onRefresh();
		// 绑定适配器和值
		provinceAdapter = new ArrayAdapter<String>(SelectCoach.this,
				android.R.layout.simple_spinner_item, province);
		provinceSpinner.setAdapter(provinceAdapter);
		// provinceSpinner.setSelection(0, true); // 设置默认选中项，此处为默认选中第4个值

		cityAdapter = new ArrayAdapter<String>(SelectCoach.this,
				android.R.layout.simple_spinner_item, city[0]);
		citySpinner.setAdapter(cityAdapter);
		// citySpinner.setSelection(0, true); // 默认选中第0个
		countyAdapter = new ArrayAdapter<String>(SelectCoach.this,
				android.R.layout.simple_spinner_item, county[0][0]);
		countySpinner.setAdapter(countyAdapter);
		ll_sport_person.setVisibility(View.VISIBLE);
		// if (key) {
		// Log.e("", "!!!!!!!!!!!!!");
		// key = false;
		// countySpinner.setSelection(0, true);
		// }
	}

	@ItemSelect(R.id.sp_sport_person1)
	public void spinner(boolean selected, int position) {
		// 省级下拉框监听
		cityAdapter = new ArrayAdapter<String>(SelectCoach.this,
				android.R.layout.simple_spinner_item, city[position]);
		citySpinner.setAdapter(cityAdapter);
		provincePosition = position; // 记录当前省级序号，留给下面修改县级适配器时用
	}

	@ItemSelect(R.id.sp_sport_person2)
	public void spinnerWithArgument(boolean selected, int position) {
		countyAdapter = new ArrayAdapter<String>(SelectCoach.this,
				android.R.layout.simple_spinner_item,
				county[provincePosition][position]);
		countySpinner.setAdapter(countyAdapter);
	}

	@ItemSelect(R.id.sp_sport_person3)
	public void fat(boolean selected, int position) {
		Area = countyid[position];
		newListViewListener.onRefresh();
	}

	public void showByMyBaseAdapter() {
		adapter = new SelectCAdapter(SelectCoach.this, persons);
		listView.setAdapter(adapter);
	}

	private void read() {
		// TODO Auto-generated method stub
		// 从网络获取数据并且调用Handler来添加到persons里面
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "clist");
		// params.put("Province", "0");
		// params.put("City", "0");
		if (Area.endsWith("510101")) {
			params.put("City", "510100");
		} else {
			params.put("Area", Area);
		}
		params.put("Type", "0");
		params.put("PageSize",""+ PageSize);
		params.put("PageIndex", ""+PageIndex);
		params.put("Top", Top);
		Log.e("", Url.userMovementGuidance + "?" + params);
		client.post(Url.userMovementGuidance, params, SC_handler);
	}

	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {
			Log.e("", "上拉刷新");
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
			if (onrekey<3) {
				onrekey++;
				return;
			}
			System.out.println("mytwfsdgfdsgfdsgfds");
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
			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
			// "正在加载中……",
			// true, true);
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
		SC_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(SelectCoach.this, "网络连接错误，请检查网络设置后重试。",
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
					if (jsonObject.getString("Status").equals("1")) {
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<SportSelect>>() {
								}.getType());
						persons.addAll(items);
					} else {
						Tools.showTextToast(SelectCoach.this, "没有数据可以显示了");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				listView.stopRefresh();
				listView.stopLoadMore();
				super.onSuccess(arg0);
			}
		};

	}
	public static void Destroy(){
		((Activity) context).finish();
	}
}
