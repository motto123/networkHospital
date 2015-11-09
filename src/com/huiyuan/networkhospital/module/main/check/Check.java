package com.huiyuan.networkhospital.module.main.check;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.CheckBean;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.hospital_introduce.activity.DoctorDetailsActivity_;
import com.huiyuan.networkhospital.module.main.registration.adapter.OrderDoctorAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 聊天界面
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_check)
public class Check extends BaseActivity {

	@ViewById
	ListView lv_check;

	private List<CheckBean> peoples = new ArrayList<CheckBean>();
	private ArrayList<CheckBean> items = new ArrayList<CheckBean>();

	private Checkadapter adapter;
	private AsyncHttpResponseHandler Handler;
	private String mvid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化
	 * 
	 * @param vid
	 */
	@AfterViews
	protected void init() {
		dohandler();
		adapter = new Checkadapter(peoples, this);
		lv_check.setAdapter(adapter);
		mvid = getIntent().getStringExtra("Vid");
		read(mvid);
	}

	/**
	 * 获取聊天信息列表
	 * 
	 * @param vid
	 */
	protected void read(String vid) {
		RequestParams parmas = new RequestParams();
		if ("d".equals(getIntent().getStringExtra("flag"))) {
			parmas.put("act", "vchatlist");
			parmas.put("vid",
					getIntent().getStringExtra(GlobalConstant.KEY_DATA));
		} else {
			parmas.put("act", "mchatlist");
			parmas.put("mid",
					getIntent().getStringExtra(GlobalConstant.KEY_DATA));
		}
		HttpClientUtils.post(Url.Chat, parmas, Handler);
	}

	/**
	 * 设置监听
	 */
	@Click({ R.id.imgb_check })
	protected void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgb_check:
			finish();
			break;
		}
	}

	/**
	 * 聊天信息列表连接网络后跳转的handler处理数据
	 */
	private void dohandler() {
		Handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(Check.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				read(mvid);
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					Gson gson = new Gson();
					if (jsonObject.getJSONArray("Data").length() > 0) {
						Tools.LogE(arg0);
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<CheckBean>>() {
								}.getType());
						peoples.addAll(items);
					} else {
						// Tools.showTextToast(getActivity(), "没有数据可以显示了");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					// Tools.showTextToast(getActivity(), "没有数据可以显示了");
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				super.onSuccess(arg0);
			}
		};

	}
}
