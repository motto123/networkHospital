//package com.huiyuan.networkhospital.module.main.registration.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.huiyuan.networkhospital.R;
//import com.huiyuan.networkhospital.common.util.Tools;
//import com.huiyuan.networkhospital.entity.Course;
//import com.huiyuan.networkhospital.module.main.registration.activity.R_PaymentActivity_;
//
//public class CourseAdapter extends BaseAdapter {
//
//	/**
//	 * 医生集合
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
//			holder=new ViewHolder();
//			convertView=View.inflate(context,R.layout.item_course, null);
//			holder.course=(TextView)convertView.findViewById(R.id.tv_item_chose);
//			convertView.setTag(holder);
//		}
//		holder=(ViewHolder) convertView.getTag();
//		//更新所需的数据
//		Course c=list.get(position);
//		holder.course.setText(c.getCourse());
//		addListener(holder);
//		return convertView;
//		}
//
//	private void addListener(ViewHolder holder){
//		holder.course.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Tools.startActivity(context, R_PaymentActivity_.class);				
//			}
//		});
//	}
//	
//	class ViewHolder {
//		TextView course;
//	}
//
//}
