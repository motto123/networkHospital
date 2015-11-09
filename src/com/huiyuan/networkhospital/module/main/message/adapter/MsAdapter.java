//package com.huiyuan.networkhospital.module.main.message.adapter;
//
///**
// *@author 邓肇均
// *@date 2015年3月10日 18:08:21
// *@describe main1的适配器
// */
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ListAdapter;
//import android.widget.TextView;
//
//import com.huiyuan.networkhospital.R;
//import com.huiyuan.networkhospital.entity.MsBean;
//import com.huiyuan.networkhospital.module.main.message.activity.MessageDetailsActivity_;
//
//public class MsAdapter extends BaseAdapter {
//	Map<String, String> map = new HashMap<String, String>();
//	Context context;
//	ListAdapter adapter = null;
//	private List<MsBean> persons;
//	MsBean person;
//	ArrayList<String> imgList = new ArrayList<String>();
//	public MsAdapter(Context context, List<MsBean> persons) {
//		this.persons = persons;
//		this.context = context;
//	}
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return (persons == null) ? 0 : persons.size();
//	}
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return persons.get(position);
//	}
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//	public class ViewHolder {
//		TextView classs,time;
////		ImageButton imbt_message_item_detalis;
//	}
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		person = persons.get(position);
//		ViewHolder viewHolder = new ViewHolder();
//		 if(convertView==null){ 
//				convertView = LayoutInflater.from(context).inflate(
//						R.layout.item_message, null);
//				viewHolder.time = (TextView) convertView
//						.findViewById(R.id.tv_message_item_time);
//				viewHolder.classs = (TextView) convertView
//						.findViewById(R.id.tv_message_item_detalis);
////				viewHolder.imbt_message_item_detalis = (ImageButton) convertView
////						.findViewById(R.id.imbt_message_item_detalis);
//				
//				convertView.setTag(viewHolder);
//				 }else{
//				 viewHolder = (ViewHolder)convertView.getTag();
//				 }
//			viewHolder.classs.setText(person.getConment()+position);
//			viewHolder.time.setText(person.getTime());
//			/*
//			 * 参与监听
//			 */
////			addListener(convertView, position, persons);
//			
//		return convertView;
//	}
//	private void addListener(View convertView, final int position,
//			final List<MsBean> persons2) {
////		(
//				convertView
////				.findViewById(R.id.imbt_message_item_detalis))
//				.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Intent intent = new Intent(context,
//								MessageDetailsActivity_.class);
////						((Activity) context).startActivityForResult(intent,0);
////						Log.e("", position+"");
//						context.startActivity(intent);
//					}
//				});
//	}
//	
//}
