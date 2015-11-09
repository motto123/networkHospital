package com.huiyuan.networkhospital.module.main.doctor_visit.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.DvBean;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.ParticipationActivity_;
/**
 * 
 * @ClassName:  DvAdapter   
 * @Description:TODO主界面未参加的适配器  
 * @author: 邓肇均
 * @date:   2015年10月21日 下午4:54:03   
 *
 */
public class DvAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<DvBean> persons;
	DvBean person;
	ArrayList<String> imgList = new ArrayList<String>();

	public DvAdapter(Context context, List<DvBean> persons) {
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
		TextView address, num, time;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_dv_list, null);
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.Platform_View_item_tv1);
			viewHolder.num = (TextView) convertView
					.findViewById(R.id.Platform_View_item_tv2);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.Platform_View_item_tv3);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.address.setText(person.getAddress());
		viewHolder.num.setText("参与人数:" + person.getNum() + "人");
		viewHolder.time.setText(person.getCreateTime());
		/*
		 * 参与监听
		 */
		addListener(convertView, position, persons);

		return convertView;
	}

	private void addListener(View convertView, final int position,
			final List<DvBean> persons2) {
		(convertView.findViewById(R.id.Platform_View_item_bt))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								ParticipationActivity_.class);
						intent.putExtra("AID", persons2.get(position).getId());
						// ((Activity)
						// context).startActivityForResult(intent,0);
						// Log.e("", position+"");
						context.startActivity(intent);
					}
				});
	}
	// public void remove(int position) {
	// // TODO Auto-generated method stub
	// persons.remove(position);
	// notifyDataSetChanged();
	//
	// }

}
