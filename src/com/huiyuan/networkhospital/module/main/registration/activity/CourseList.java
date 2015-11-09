//package com.huiyuan.networkhospital.module.main.registration.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.format.Time;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.huiyuan.networkhospital.R;
//import com.huiyuan.networkhospital.common.widget.XListView;
//import com.huiyuan.networkhospital.entity.Course;
//import com.huiyuan.networkhospital.module.BaseActivity;
//import com.huiyuan.networkhospital.module.main.registration.adapter.CourseAdapter;
//
//@EActivity(R.layout.activity_select_person)
//public class CourseList extends BaseActivity{
//
//	@ViewById(android.R.id.list)
//	XListView lvCourse;
//
//	@ViewById
//	TextView xlistview_header_time;
//	
//	
//	@ViewById 
//	Spinner spiSelect;
//
//	@ViewById
//	TextView tvTitle1;
//
//	@ViewById
//	ImageView ivBack;
//
//	private CourseAdapter adapter;
//
//	private List<Course> list;
//
//	private ArrayAdapter<String> adapter1;
//
//
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//	}
//
//	@AfterViews
//	protected void init(){
//		//设置当前界面的标题
//		tvTitle1.setText("选择科室");
//		
//		lvCourse.setPullRefreshEnable(true);// 设置下拉刷新
//		lvCourse.setXListViewListener(newListViewListener);// 设置监听事件，重写两个方法
//		lvCourse.setPullLoadEnable(true);// 设置上拉刷新
//		
//		list=new ArrayList<Course>();
//		for(int i=0;i<5;i++){
//			Course c=new Course();
//			c.setCourse("外科"+i);
//			list.add(c);
//		}
//		adapter=new CourseAdapter(list, this);
//		lvCourse.setAdapter(adapter);
//		
//		//初始化spinner
//		String[] str=new String[]{"2015年6月1日","2015年7月1日","2015年8月1日","2015年9月1日"};
//		adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str);
//		spiSelect.setAdapter(adapter1);
//	}
//
//	/**
//	 * XListView上拉下拉监听事件
//	 */
//	private XListView.IXListViewListener newListViewListener = new XListView.IXListViewListener() {
//
//		@Override
//		public void onRefresh() {
//			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
//			// "正在加载中……",
//			// true, true);
//			Handler hander=new Handler();
//			hander.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					list.clear();
//					read();
//					adapter.notifyDataSetChanged();
//					lvCourse.stopRefresh();
//					lvCourse.stopLoadMore();
//					
//				}
//			}, 1500);
//			Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
//			t.setToNow(); // 取得系统时间。
//			xlistview_header_time.setText(t.month + "月" + t.monthDay + "日"
//					+ t.hour + "：" + t.minute);
//		}
//
//		@Override
//		public void onLoadMore() {
//			// loadingDialog = ProgressDialog.show(DoctorVisitActivity.this, "",
//			// "正在加载中……",
//			// true, true);
//			read();
//			adapter.notifyDataSetChanged();
//			lvCourse.stopRefresh();
//			lvCourse.stopLoadMore();
//		}
//
//	};
//	
//	protected void read() {
//		Course c=new Course();
//		c.setCourse("外科"+"-新增");
//		list.add(c);
//	}
//
////	@ItemClick(R.id.lvDoctor)
////	protected void skip(int position){
////		Tools.startActivity(this, R_PaymentActivity_.class);
////	}
//
//	@Click({R.id.ivBack})
//	protected void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.ivBack:
//			finish();
//			break;
//		}
//	}
//	
//测试git
//测试git 2
//}
