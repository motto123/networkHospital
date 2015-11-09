package com.huiyuan.networkhospital.module.main.registration.activity;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.OrderDoctor;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.registration.adapter.OrderDoctorAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @ClassName: DoctorDetail
 * @Description:TODO医生信息列表
 * @author: 邓肇均
 * @date: 2015年10月21日 下午5:06:17
 *
 */
@EActivity(R.layout.activity_select_person)
public class DoctorList extends BaseActivity {

	@ViewById
	ListView lvDoctor;

	@ViewById
	TextView xlistview_header_time;

	@ViewById
	Spinner spiSelect;

	@ViewById
	TextView tvTitle1;

	@ViewById
	ImageView ivBack;

	private ArrayList<OrderDoctor> doctors = new ArrayList<OrderDoctor>();
	private ArrayList<OrderDoctor> items = new ArrayList<OrderDoctor>();

	private OrderDoctorAdapter dAdapter;
	static Context context;
	private AsyncHttpResponseHandler DLHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	protected void init() {
		// 隐藏多余的控件
		dohandler();
		spiSelect.setVisibility(View.GONE);
		// 设置当前界面的标题
		tvTitle1.setText("选择医生");
		dAdapter = new OrderDoctorAdapter(doctors, this);
		lvDoctor.setAdapter(dAdapter);
		read();
		context = DoctorList.this;
	}

	protected void read() {
		RequestParams parmas = new RequestParams();
		parmas.put("act", "rdlist");
		System.out.println(Url.department + "?" + parmas.toString());
		HttpClientUtils.post(Url.Registers, parmas, DLHandler);

	}

	@ItemClick(R.id.lvDoctor)
	protected void selectDoctor(int position) {
		Intent intent = new Intent(this, DoctorDetail_.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("person", doctors.get(position));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click({ R.id.ivBack })
	protected void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		}
	}

	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		DLHandler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(DoctorList.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					Gson gson = new Gson();
					if (jsonObject.getJSONArray("Data").length() > 0) {
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<OrderDoctor>>() {
								}.getType());
						doctors.addAll(items);
					} else {
						// Tools.showTextToast(getActivity(), "没有数据可以显示了");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					// Tools.showTextToast(getActivity(), "没有数据可以显示了");
					e.printStackTrace();
				}
				dAdapter.notifyDataSetChanged();
				super.onSuccess(arg0);
			}
		};

	}

	public static void Destroy() {
		((Activity) context).finish();
	}

}
