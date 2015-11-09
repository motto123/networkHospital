package com.huiyuan.networkhospital.module.main.hospital_introduce.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.main.hospital_introduce.fragment.CenterIntroducedFragment_;
import com.huiyuan.networkhospital.module.main.hospital_introduce.fragment.CourseIntroducedFragment_;
import com.huiyuan.networkhospital.module.main.hospital_introduce.fragment.DoctorIntroducedFragment_;
/**
 * 
 * @ClassName:  DepartmentsDetailsActivity   
 * @Description:TODO医院信息展示
 * @author: 邓肇均
 * @date:   2015年10月21日 下午4:58:33   
 *
 */
@EActivity(R.layout.activity_hosbital_introduce)
public class HosbitalIntroduceActivity extends FragmentActivity {

	@ViewById
	ViewPager viewPager;
	
	@ViewById
	TextView tvTitle;

	@ViewById
	TextView tvCenter;

	@ViewById
	TextView tvDoctor;

	@ViewById
	TextView tvCourse;

	@ViewById
	TextView tvIntroduceContent;

	@ViewById
	ImageView ivPicture;

	@ViewById
	ImageView ivBack;

	@ViewById
	ImageView ivLine1;

	@ViewById
	ImageView ivLine2;

	@ViewById
	ImageView ivLine3;

//	//手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)  
//	private float x1 = 0;  
//	private float x2 = 0;  
//	private float y1 = 0;  
//	private float y2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_hosbital_introduce);
	}

	@Click({R.id.ivBack,R.id.tvCenter,R.id.tvDoctor,R.id.tvCourse})
	protected void onClick(View v){
		switch (v.getId()) {
		case R.id.ivBack:
			Tools.startActivity(this, MainActivity_.class);
			finish();
			break;
		case R.id.tvCenter:
			alterColor(0);
			viewPager.setCurrentItem(0);
			break;
		case R.id.tvDoctor:
			alterColor(1);
			viewPager.setCurrentItem(1);
			break;
		case R.id.tvCourse:
			alterColor(2);
			viewPager.setCurrentItem(2);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		Tools.startActivity(this, MainActivity_.class);
		super.onBackPressed();
	}
	
//	/**
//	 * 隐藏和显示相对应的布局
//	 * @param i
//	 */
//	private void show(int i){
//		switch (i) {
//		case 1:
//			rlCenter.setVisibility(View.VISIBLE);
//			rlDoctor.setVisibility(View.GONE);
//			rlCourse.setVisibility(View.GONE);
//			mark=1;
//			break;
//		case 2:
//			rlCenter.setVisibility(View.GONE);
//			rlDoctor.setVisibility(View.VISIBLE);
//			rlCourse.setVisibility(View.GONE);
//			mark=2;
//			break;
//		case 3:
//			rlCenter.setVisibility(View.GONE);
//			rlDoctor.setVisibility(View.GONE);
//			rlCourse.setVisibility(View.VISIBLE);
//			mark=3;
//			break;
//		}
//	}

	/**
	 * 修改字体的颜色
	 * @param i
	 */
	private void alterColor(int i) {
		switch (i) {//212121
		case 0:
			tvCenter.setTextColor(Color.rgb(116, 191, 230));
			tvDoctor.setTextColor(Color.rgb(59, 59, 59));
			tvCourse.setTextColor(Color.rgb(59, 59, 59));
			ivLine1.setBackgroundColor(Color.rgb(116, 191, 230));
			ivLine2.setBackgroundColor(Color.rgb(212, 212, 212));
			ivLine3.setBackgroundColor(Color.rgb(212, 212, 212));
			break;
		case 1:
			tvCenter.setTextColor(Color.rgb(59, 59, 59));
			tvDoctor.setTextColor(Color.rgb(116, 191, 230));
			tvCourse.setTextColor(Color.rgb(59, 59, 59));
			ivLine1.setBackgroundColor(Color.rgb(212, 212, 212));
			ivLine2.setBackgroundColor(Color.rgb(116, 191, 230));
			ivLine3.setBackgroundColor(Color.rgb(212, 212, 212));
			break;
		case 2:
			tvCenter.setTextColor(Color.rgb(59, 59, 59));
			tvDoctor.setTextColor(Color.rgb(59, 59, 59));
			tvCourse.setTextColor(Color.rgb(116, 191, 230));
			ivLine1.setBackgroundColor(Color.rgb(212, 212, 212));
			ivLine2.setBackgroundColor(Color.rgb(212, 212, 212));
			ivLine3.setBackgroundColor(Color.rgb(116, 191, 230));
			break;
		}
	}

	@AfterViews
	protected void setupFragment() {
		new Thread(){
			@Override
			public void run() {
				final FragmentManager fm = getSupportFragmentManager();
				//构建Fragment的集合
				final List<Fragment> fs=new ArrayList<Fragment>();
				fs.add(new CenterIntroducedFragment_());
				fs.add(new DoctorIntroducedFragment_());
				fs.add(new CourseIntroducedFragment_());
				MyPagerAdapter adapter=new MyPagerAdapter(fm, fs);
				viewPager.setAdapter(adapter);
			}
		}
		.start();
		//添加切换效果
//		viewPager.setPageTransformer(true, new DepthPageTransformer());
		//添加事件监听
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					alterColor(0);
					viewPager.setCurrentItem(0);
					break;
				case 1:
					alterColor(1);
					viewPager.setCurrentItem(1);
					break;
				case 2:
					alterColor(2);
					viewPager.setCurrentItem(2);
					break;
				}
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	/**
	 * 更新Fragment
	 * @author Administrator
	 *
	 */
	class MyPagerAdapter extends FragmentPagerAdapter{
		private List<Fragment> fs;

		public MyPagerAdapter(FragmentManager fm, List<Fragment> fs) {
			super(fm);
			this.fs=fs;
		}
		public Fragment getItem(int arg0) {
			return fs.get(arg0);
		}

		public int getCount() {
			return fs.size();
		}

	}
	
	
	
}
