//package com.huiyuan.networkhospital.module.main.hospital_introduce.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.huiyuan.networkhospital.entity.Course;
//
//public class CourseAdapter extends BaseAdapter {
//
//	/**
//	 * Course集合
//	 */
//	private List<Course> list;
//
//	private Context context;
//
//
//	public CourseAdapter(List<Course> list, Context context) {
//		super();
//		this.list = list;
//		this.context = context;
//	}
//
//	@Override
//	public int getCount() {
//		return list.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {	
//		ViewHolder holder=null;
//		if(convertView==null){
//			convertView=View.inflate(context,android.R.layout.simple_list_item_1, null);
//			holder=new ViewHolder();
//			holder.course=(TextView)convertView.findViewById(android.R.id.text1);
//			convertView.setTag(holder);
//		}
//		holder=(ViewHolder) convertView.getTag();
//		//更新所需的数据
//		Course c=list.get(position);
//		holder.course.setText(c.getCourse());
//
//		return convertView;}
//
//	class ViewHolder {
//		TextView course;
//	}
//
//}
