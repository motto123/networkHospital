package com.huiyuan.networkhospital.module.main.doctor_visit.adapter;

/**
 */
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
import com.huiyuan.networkhospital.R.string;
import com.huiyuan.networkhospital.entity.DvBean;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.LookOverActivity_;
/**
 * 
 * @ClassName:  Dv_onAdapter   
 * @Description:TODO主界面已参加的适配器  
 * @author: Android_Robot  
 * @date:   2015年10月21日 下午4:51:29   
 *
 */
public class Dv_onAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<DvBean> persons;
	DvBean person;
	ArrayList<String> imgList = new ArrayList<String>();
	public Dv_onAdapter(Context context, List<DvBean> persons) {
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
		TextView address,num,time,state,reason;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		 if(convertView==null){ 
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_dv_list_on, null);
				viewHolder.address = (TextView) convertView
						.findViewById(R.id.Platform_View_item_on_tv1);
				viewHolder.num = (TextView) convertView
						.findViewById(R.id.Platform_View_item_on_tv2);
				viewHolder.time = (TextView) convertView
						.findViewById(R.id.Platform_View_item_on_tv3);
				viewHolder.state = (TextView) convertView
						.findViewById(R.id.Platform_View_item_on_tv4);
				viewHolder.reason = (TextView) convertView
						.findViewById(R.id.tvReason);
				
				convertView.setTag(viewHolder);
				 }else{
				 viewHolder = (ViewHolder)convertView.getTag();
				 }
			viewHolder.address.setText("地址:" 
					+ person.getAddress());
			viewHolder.num.setText("参与人数:" + person.getNum() + "人");
			viewHolder.time.setText(person.getCreateTime());
			
			System.out.println(person.getState());
			
			if (person.getState().equals("0")) {
				viewHolder.state.setText("进行中");
				viewHolder.state.setTextColor(Color.BLACK);
			} else if (person.getState().equals("1")) {
				viewHolder.state.setText("成功");
				viewHolder.state.setTextColor(Color.BLACK);
			} else if (person.getState().equals("2")) {
				viewHolder.state.setText("失败");
				viewHolder.state.setTextColor(Color.RED);
				viewHolder.reason.setText(context.getResources().getString(string.reason)+
						person.getReason());
				viewHolder.reason.setVisibility(View.VISIBLE);
			}
			/*
			 * 参与监听
			 */
			addListener(convertView, position, persons);
			
		return convertView;
	}
	private void addListener(View convertView, final int position,
			final List<DvBean> persons2) {
		convertView
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,
								LookOverActivity_.class);
//						((Activity) context).startActivityForResult(intent,0);
//						Log.e("", position+"");
						intent.putExtra("address", 
								persons2.get(position).getAddress()+"");
						intent.putExtra("id", 
								persons2.get(position).getId()+"");
						intent.putExtra("state", 
								persons2.get(position).getState()+"");
						intent.putExtra("name", 
								persons2.get(position).getDname()+"");
						intent.putExtra("phone", 
								persons2.get(position).getDPhone()+"");
						intent.putExtra("atime", 
								persons2.get(position).getATime()+"");
						context.startActivity(intent);
					}
				});
	}
	
}
