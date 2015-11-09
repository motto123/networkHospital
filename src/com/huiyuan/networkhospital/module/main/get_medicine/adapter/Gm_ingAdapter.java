package com.huiyuan.networkhospital.module.main.get_medicine.adapter;

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
import com.huiyuan.networkhospital.entity.Gm_ingBean;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.Gm_ChatActivity_;

public class Gm_ingAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<Gm_ingBean> persons;
	Gm_ingBean person;
	ArrayList<String> imgList = new ArrayList<String>();
	public Gm_ingAdapter(Context context, List<Gm_ingBean> persons) {
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
		TextView name,address;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		 if(convertView==null){ 
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_gm_ing, null);
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.tv_gm_ing_item1);
				viewHolder.address = (TextView) convertView
						.findViewById(R.id.tv_gm_ing_item2);
				convertView.setTag(viewHolder);
				 }else{
				 viewHolder = (ViewHolder)convertView.getTag();
				 }
			viewHolder.address.setText(person.getAddress());
			viewHolder.name.setText(person.getName()+position);
			/*
			 * 参与监听
			 */
			addListener(convertView, position, persons);
			
		return convertView;
	}
	private void addListener(View convertView, final int position,
			final List<Gm_ingBean> persons2) {
		convertView
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								Gm_ChatActivity_.class);
//						((Activity) context).startActivityForResult(intent,0);
//						Log.e("", position+"");
						context.startActivity(intent);
					}
				});
	}
	
}
