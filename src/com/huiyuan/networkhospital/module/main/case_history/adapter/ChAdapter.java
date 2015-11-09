package com.huiyuan.networkhospital.module.main.case_history.adapter;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.ChBean;

public class ChAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<ChBean> persons;
	ChBean person;
	ArrayList<String> imgList = new ArrayList<String>();
	public ChAdapter(Context context, List<ChBean> persons) {
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
		TextView classs,doc,time;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		 if(convertView==null){ 
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_case_history, null);
				viewHolder.classs = (TextView) convertView
						.findViewById(R.id.tv_case_history_class);
				viewHolder.doc = (TextView) convertView
						.findViewById(R.id.tv_case_history_doc);
				viewHolder.time = (TextView) convertView
						.findViewById(R.id.tv_case_history_time);
				convertView.setTag(viewHolder);
				 }else{
				 viewHolder = (ViewHolder)convertView.getTag();
				 }
			viewHolder.classs.setText(person.getClasss()+position);
			viewHolder.doc.setText(person.getDoc());
			viewHolder.time.setText(person.getTime());
			/*
			 * 参与监听
			 */
//			addListener(convertView, position, persons);
			
		return convertView;
	}
//	private void addListener(View convertView, final int position,
//			final List<ChBean> persons2) {
//		convertView
//				.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Intent intent = new Intent(context,
//								CaseHistoryDetailsActivity_.class);
////						((Activity) context).startActivityForResult(intent,0);
////						Log.e("", position+"");
//						context.startActivity(intent);
//					}
//				});
//	}
	
}
