package com.huiyuan.networkhospital.module.user.adapter;

/**
 *@author 邓肇均
 *@date 2015年3月10日 18:08:21
 *@describe main1的适配器
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.DvAddressBean;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.Dv_ChoseAddressDetailesActivity_;

public class ChoseAddressAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<DvAddressBean> persons;
	DvAddressBean person;
	ArrayList<String> imgList = new ArrayList<String>();
	public ChoseAddressAdapter(Context context, List<DvAddressBean> persons) {
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
		TextView address;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		 if(convertView==null){ 
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_dv_choseaddress, null);
				viewHolder.address = (TextView) convertView
						.findViewById(R.id.item_dv_choseaddress);
				convertView.setTag(viewHolder);
				 }else{
				 viewHolder = (ViewHolder)convertView.getTag();
				 }
			viewHolder.address.setText(person.getAddress()+position);
			/*
			 * 参与监听
			 */
//			addListener(convertView, position, persons);
			
		return convertView;
	}
	private void addListener(View convertView, final int position,
			final List<DvAddressBean> persons2) {
		convertView
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								Dv_ChoseAddressDetailesActivity_.class);
//						((Activity) context).startActivityForResult(intent,0);
//						Log.e("", position+"");
						context.startActivity(intent);
					}
				});
	}
	
}
