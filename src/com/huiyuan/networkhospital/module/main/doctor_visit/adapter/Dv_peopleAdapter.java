package com.huiyuan.networkhospital.module.main.doctor_visit.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
/**
 * 
 * @ClassName:  Dv_peopleAdapter   
 * @Description:TODO创建活动列表设置人员的适配器
 * @author: 邓肇均
 * @date:   2015年10月21日 下午4:52:48   
 *
 */
public class Dv_peopleAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<Dv_people> persons;
	Dv_people person;
	ArrayList<String> imgList = new ArrayList<String>();

	public Dv_peopleAdapter(Context context, List<Dv_people> persons) {
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
		TextView tv_item_dvpeople_name;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_dv_people, null);
			viewHolder.tv_item_dvpeople_name = (TextView) convertView
					.findViewById(R.id.tv_item_dvpeople_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.address.setText(person.getAddress());
		viewHolder.tv_item_dvpeople_name.setText(person.getName());
		return convertView;
	}

}
