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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.R_PBean;
import com.huiyuan.networkhospital.module.main.registration.activity.RegisterDetailActivity_;
import com.huiyuan.networkhospital.module.main.registration.adapter.R_PAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EFragment
public class Indent_fragment_R extends Fragment{
	@ViewById
	TextView xlistview_header_time;
	@ViewById
	ListView lvR;

	private List<R_PBean> doctors = new ArrayList<R_PBean>();
	private ArrayList<R_PBean> items = new ArrayList<R_PBean>();
	private R_PAdapter adapter;
	private AsyncHttpResponseHandler R_MHandler;
	private ProgressDialog dialog;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_indent_r, container, false); 
	}
	@AfterViews
	protected void init() {
		dohandler();
		adapter = new R_PAdapter(doctors, getActivity());
		lvR.setAdapter(adapter);
		doctors.clear();
		dialog = ProgressDialog.show(getActivity(), "提示", "正在加载中",true,true);
		read();
	}
	
	@ItemClick(R.id.lvR)
	protected void skipDetail(int position){
		Intent intent =new Intent(this.getActivity(),RegisterDetailActivity_.class);
		intent.putExtra(GlobalConstant.KEY_DATA, doctors.get(position));
		startActivity(intent);
	}
	
	/**
	 * 获得快速挂号订单
	 */
	private void read() {
		// TODO Auto-generated method stub
		RequestParams parmas = new RequestParams();
		parmas.put("act", "list");
		parmas.put("Uid", NApplication.userid);
		parmas.put("type", "0");
		System.out.println(Url.Registers + "?" + parmas.toString());
		HttpClientUtils.post(Url.Registers, parmas, R_MHandler);

	}

	private void dohandler() {
		// TODO Auto-generated method stub
		/**
		 * 显示快速挂号接口获取的信息
		 */
		R_MHandler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(getActivity(), "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("list", arg0);
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
				if (dialog != null) {
					dialog.dismiss();
				}
				adapter.notifyDataSetChanged();
				super.onSuccess(arg0);
			}
		};
	}

}
