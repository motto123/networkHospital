package com.huiyuan.networkhospital.module.main.registration.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.R.string;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.entity.dengzhaojun.R_PBean;

/**
 * 
 * @ClassName: R_PAdapter
 * @Description:TODO我的预约信息页面
 * @author: 邓肇均
 * @date: 2015年10月21日 下午5:12:11
 *
 */
public class R_PAdapter extends BaseAdapter {

	/**
	 * 医生集合
	 */
	private List<R_PBean> list;

	private Context context;

	public R_PAdapter(List<R_PBean> list, Context context) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		R_PBean d = list.get(position);
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_r_myorder, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_item_r_myorder1);
			holder.time = (TextView) convertView
					.findViewById(R.id.tv_item_r_myorder2);
			holder.level = (TextView) convertView
					.findViewById(R.id.tv_item_r_myorder3);
			holder.state = (TextView) convertView
					.findViewById(R.id.tv_item_r_myorder4);
			holder.reason = (TextView) convertView
					.findViewById(R.id.tvReason);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		holder.name.setText(context.getString(R.string.tv_item_r_myorder1)
				+ d.getName());
		holder.level.setText(context.getString(R.string.tv_item_r_myorder3)
				+ d.getDname());
		switch (d.getState()) {
		case "0":
			holder.state.setText("审核中");
			break;
		case "1":
			holder.state.setText("审核成功");
			break;
		case "2": 
			holder.state.setText("审核失败");
			holder.reason.setVisibility(View.VISIBLE);
			holder.reason.setText(context.getResources().getString(string.reason)+d.getReason());
			break;
		case "3":
			holder.state.setText("退款中");
			break;
		case "4":
			holder.state.setText("退款成功");
			break;
		default:
			break;
		}
		try {
			switch (d.getRTime().substring(11, 13)) {
			case "09":

				holder.time.setText(context.getString(R.string.tv_item_r_myorder2)
						+ d.getRTime().substring(0, 11) + "上午");

				break;
			case "14":

				holder.time.setText(context.getString(R.string.tv_item_r_myorder2)
						+ d.getRTime().substring(0, 11) + "下午");

				break;

			default:
				break;
			}
		} catch (Exception e) {
			Tools.LogE("服务器返回的时间不符合时间格式！");
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView level;
		TextView time;
		TextView state;
		TextView reason;
	}

}
