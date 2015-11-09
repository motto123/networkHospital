package com.huiyuan.networkhospital.module.main.registration.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.Doctor;
import com.huiyuan.networkhospital.entity.dengzhaojun.OrderDoctor;
/**
 * 
 * @ClassName:  OrderDoctorAdapter   
 * @Description:TODO 医生列表适配器
 * @author: 邓肇均
 * @date:   2015年10月21日 下午5:10:03   
 *
 */
public class OrderDoctorAdapter extends BaseAdapter {

	/**
	 * 预约医生集合
	 */
	private List<OrderDoctor> list;

	private Context context;

	public OrderDoctorAdapter(List<OrderDoctor> list, Context context) {
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
		String time = new String();
		OrderDoctor d = list.get(position);
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_oderdoctor, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_item_oderdoctor);
			holder.time = (TextView) convertView
					.findViewById(R.id.tv3_item_oderdoctor);
			holder.level = (TextView) convertView
					.findViewById(R.id.tv2_item_oderdoctor);
			holder.jiage = (TextView) convertView
					.findViewById(R.id.tv_jiage);
			holder.state = (TextView) convertView
					.findViewById(R.id.tvState);
			
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.name.setText(d.getName());
		holder.level.setText(d.getOccupation());
		holder.jiage.setText((Integer.parseInt(d.getPrice())+5)+"元");
		if("1".equals(d.getType())){
			holder.state.setTextColor(Color.RED);
			holder.state.setText("人数已满");
		}else{
			holder.state.setTextColor(Color.BLUE);
			holder.state.setText("可预约");
		}
		try {
			Gson gson = new Gson();
			ArrayList<ordertime> items = new ArrayList<OrderDoctorAdapter.ordertime>();
			JSONObject jsonObject = new JSONObject("{\"Data\":"
					+ d.getOutpatientTime() + "}");
			items = gson.fromJson(jsonObject.getString("Data"),
					new TypeToken<ArrayList<ordertime>>() {
					}.getType());
			for (ordertime t : items) {
				switch (t.getDay().toString()) {
				case "1":
					t.setDay("星期一");
					break;
				case "2":
					t.setDay("星期二");
					break;
				case "3":
					t.setDay("星期三");
					break;
				case "4":
					t.setDay("星期四");
					break;
				case "5":
					t.setDay("星期五");
					break;
				case "6":
					t.setDay("星期六");
					break;
				case "7":
					t.setDay("星期天");
					break;
				default:
					break;
				}
				switch (t.getTime()) {
				case "am":
					t.setTime("上午");
					break;
				case "pm":
					t.setTime("下午");
					break;
				default:
					break;
				}
				time = time + t.getDay() + "" + t.getTime() + "   ";
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.time.setText(time);
		return convertView;
	}


	class ViewHolder {
		TextView name;
		TextView level;
		TextView time;
		TextView jiage;
		TextView state;
	}

	class ordertime {
		String day, time;

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}

}
