package com.huiyuan.networkhospital.module.main.check;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.dengzhaojun.CheckBean;

public class Checkadapter extends BaseAdapter {

	private List<CheckBean> list;

	private Context context;

	public Checkadapter(List<CheckBean> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		RelativeLayout rl;
		TextView name;
		TextView content;
		TextView time;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		String time = new String();
		CheckBean d = list.get(position);
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_check, null);
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.time);
			if (d.getSendType().equals("0")) {
				convertView.findViewById(R.id.tv_2).setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.tv_4).setVisibility(View.GONE);
				holder.name = (TextView) convertView.findViewById(R.id.tv_1);
				holder.content = (TextView) convertView.findViewById(R.id.tv_2);
				holder.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_1);
				holder.name.setText(d.getUname());
			} else {
				convertView.findViewById(R.id.tv_2).setVisibility(View.GONE);
				convertView.findViewById(R.id.tv_4).setVisibility(View.VISIBLE);
				holder.name = (TextView) convertView.findViewById(R.id.tv_3);
				holder.content = (TextView) convertView.findViewById(R.id.tv_4);
				holder.rl = (RelativeLayout) convertView
						.findViewById(R.id.rl_2);
				holder.name.setText(d.getDname());

			}
			holder.rl.setVisibility(View.VISIBLE);
			holder.time.setText(d.getSendTime());
			holder.content.setText(d.getContents());
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
//		holder.time.setText(time);
		return convertView;
	}

}
