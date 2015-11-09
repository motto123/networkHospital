package com.huiyuan.networkhospital.module.main.sport_coaching.adapter;

/**
 * 此处用的是复诊拿药里面选择医生界面的listview布局
 * 
 */
/**
 *@author 邓肇均
 *@date 2015年3月10日 18:08:21
 *@describe main1的适配器
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.SportSelect;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.SelectDoctorActivity;
import com.huiyuan.networkhospital.module.main.sport_coaching.activity.Sc_PaymentActivity_;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @ClassName:  SelectCAdapter   
 * @Description:TODO运动指导的选择教练适配器
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:06:10   
 *
 */
public class SelectCAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<SportSelect> persons;
	SportSelect person;
	ArrayList<String> imgList = new ArrayList<String>();
	public String path;

	public SelectCAdapter(Context context, List<SportSelect> persons) {
		this.persons = persons;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (persons == null) ? 0 : persons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		TextView name, address,price;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_selectsp, null);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_gm_ing_item1);
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.tv_gm_ing_item2);
			viewHolder.price = (TextView) convertView
					.findViewById(R.id.tv_gm_ing_item4);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.address.setText(person.getAddress());
		viewHolder.name.setText(person.getName());
		viewHolder.price.setText(person.getPrice()+"元");
		if (person.getPhoto() != null && !person.getPhoto().equals("")) {
			path = Url.imageupload + person.getPhoto();
			Tools.setimg(((CircularImage) convertView
					.findViewById(R.id.img_gm_ing_item)), path, context);
			// Log.e("path",path );
			// MYTask myTask = new MYTask(context,convertView);
			// myTask.execute(path);
		}
		/*
		 * 添加监听
		 */
		addListener(convertView, position, persons);

		return convertView;
	}

	private void addListener(View convertView, final int position,
			final List<SportSelect> persons2) {
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				RequestParams parmas = new RequestParams();
				parmas.put("act", "getmlist");
				parmas.put("cPhone", persons2.get(position).getPhone());
				parmas.put("uPhone", NApplication.phone);
				System.out.println(Url.userMovementGuidance + "?" + parmas);
				HttpClientUtils.post(Url.userMovementGuidance, parmas,
						new AsyncHttpResponseHandler() {
							@Override
							public void onFailure(Throwable arg0) {
								// TODO Auto-generated method stub
								Toast.makeText(context, "网络连接错误，请检查网络设置后重试。",
										Toast.LENGTH_SHORT).show();
								super.onFailure(arg0);
							}

							@Override
							public void onSuccess(String arg0) {
								// TODO Auto-generated method stub
								try {
									org.json.JSONObject jsonObject = new org.json.JSONObject(
											arg0);
									System.out.print(arg0);
									JSONArray js = new JSONArray(jsonObject
											.getString("Data"));
									System.out.println(js.length());
									if ((js.length() > 0)) {

										AlertDialog.Builder builder = new Builder(
												context);
										builder.setMessage("与该教练有订单正在进行中");
										builder.setTitle("提示");
										builder.setPositiveButton("确认",
												new OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														dialog.dismiss();
													}
												});
										builder.create().show();
									}else {
										Intent intent = new Intent(context,
												Sc_PaymentActivity_.class);
										Bundle bundle = new Bundle();
										bundle.putSerializable("bean",
												persons2.get(position));
										NApplication.coachname = persons2.get(
												position).getName();
										intent.putExtras(bundle);
										// intent.putExtra(GlobalConstant.KEY_DATA,
										// persons2.get(position));
										context.startActivity(intent);
									}
								} catch (Exception e) {
									// TODO: handle exception
									Intent intent = new Intent(context,
											Sc_PaymentActivity_.class);
									Bundle bundle = new Bundle();
									bundle.putSerializable("bean",
											persons2.get(position));
									NApplication.coachname = persons2.get(
											position).getName();
									intent.putExtras(bundle);
									// intent.putExtra(GlobalConstant.KEY_DATA,
									// persons2.get(position));
									context.startActivity(intent);
								}
								super.onSuccess(arg0);
							}

						});
			}
		});
	}

}
