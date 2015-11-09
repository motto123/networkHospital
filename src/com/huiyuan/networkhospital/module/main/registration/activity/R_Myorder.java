package com.huiyuan.networkhospital.module.main.registration.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.R_PBean;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.registration.adapter.R_PAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @ClassName:  R_Myorder   
 * @Description:TODO我的预约信息
 * @author: 邓肇均
 * @date:   2015年10月21日 下午5:07:07   
 *
 */
@EActivity(R.layout.activity_r__myorder)
public class R_Myorder extends BaseActivity {

	@ViewById
	ListView lv_r_myorder;
	private List<R_PBean> doctors = new ArrayList<R_PBean>();
	private ArrayList<R_PBean> items = new ArrayList<R_PBean>();
	private R_PAdapter adapter;
	private AsyncHttpResponseHandler R_MHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Click(R.id.imgb_r_myorder)
	protected void back() {
		finish();
	}

	@ItemClick(R.id.lv_r_myorder)
	protected void skipDetail(int position){
		Intent intent =new Intent(this,RegisterDetailActivity_.class);
		intent.putExtra(GlobalConstant.KEY_DATA, doctors.get(position));
		startActivity(intent);
	}
	
	@AfterViews
	protected void init() {
		dohandler();
		adapter = new R_PAdapter(doctors, this);
		lv_r_myorder.setAdapter(adapter);
		read();
	}
/**
 * 获取我的预约信息
 */
	private void read() {
		// TODO Auto-generated method stub
		RequestParams parmas = new RequestParams();
		parmas.put("act", "list");
		parmas.put("Uid", NApplication.userid);
		System.out.println(Url.Registers + "?" + parmas.toString());
		HttpClientUtils.post(Url.Registers, parmas, R_MHandler);

	}
/**
 * 获取我的预约信息，根据返回值显示
 */
	private void dohandler() {
		// TODO Auto-generated method stub

		R_MHandler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(R_Myorder.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				Tools.LogE("我的预约记录："+arg0);
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					Gson gson = new Gson();
					if (jsonObject.getJSONArray("Data").length() > 0) {
						items = gson.fromJson(jsonObject.getString("Data"),
								new TypeToken<ArrayList<R_PBean>>() {
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
				adapter.notifyDataSetChanged();
				super.onSuccess(arg0);
			}
		};
	}

}
