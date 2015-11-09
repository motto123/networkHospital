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
import com.huiyuan.networkhospital.entity.Gm_endBean;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.Gm_PaymentActivity_;

public class Gm_endAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<Gm_endBean> persons;
	Gm_endBean person;
	ArrayList<String> imgList = new ArrayList<String>();

	public Gm_endAdapter(Context context, List<Gm_endBean> persons) {
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
		TextView tv_gm_end_ite_moeny, tv__gm_end_item_all;
		TextView tv_gm_end_item_top;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_gm_end, null);
			viewHolder.tv_gm_end_ite_moeny = (TextView) convertView
					.findViewById(R.id.tv_gm_end_ite_moeny);
			viewHolder.tv__gm_end_item_all = (TextView) convertView
					.findViewById(R.id.tv__gm_end_item_all);
			viewHolder.tv_gm_end_item_top = (TextView) convertView
					.findViewById(R.id.tv_gm_end_item_top);
			// viewHolder.time = (TextView) convertView
			// .findViewById(R.id.Platform_View_item_on_tv3);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.address.setText(person.getAddress());
		viewHolder.tv_gm_end_ite_moeny.setText("单价/数量:3" + position + "*1件");
		viewHolder.tv__gm_end_item_all.setText("小计：3" + position + "元");
		/*
		 * 参与监听
		 */
		if (position%3==1) {
			viewHolder.tv_gm_end_item_top.setText("未付款");
		}
		if (viewHolder.tv_gm_end_item_top.getText().equals("未付款")) {
			addListener(convertView, position, persons);
		}

		return convertView;
	}

	private void addListener(View convertView, final int position,
			final List<Gm_endBean> persons2) {
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, Gm_PaymentActivity_.class);
				// ((Activity) context).startActivityForResult(intent,0);
				// Log.e("", position+"");
				context.startActivity(intent);
			}
		});
	}

}
