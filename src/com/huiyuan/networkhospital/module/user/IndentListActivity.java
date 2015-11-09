package com.huiyuan.networkhospital.module.user;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.user.fragment.Indent_fragment_DV_;
import com.huiyuan.networkhospital.module.user.fragment.Indent_fragment_R_;
import com.huiyuan.networkhospital.module.user.fragment.Indent_fragment_SC_;
@EActivity(R.layout.activity_indent_list)
public class IndentListActivity extends FragmentActivity {
	@ViewById(R.id.flytIndent)
	ViewPager viewPager;
	@ViewById
	ImageButton ibtnBack;
	@ViewById
	RelativeLayout rlytAll;
	@ViewById
	TextView tvAll;
	@ViewById
	ImageView ivAll;
	@ViewById
	RelativeLayout rlytNotSettled;
	@ViewById
	TextView tvNotSettled;
	@ViewById
	ImageView ivNotSettled;
	@ViewById
	RelativeLayout rlytPaid;
	@ViewById
	TextView tvPaid;
	@ViewById
	ImageView ivPaid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_indent_list);
	}

	@Click({R.id.ibtnBack,R.id.rlytAll,R.id.rlytNotSettled,R.id.rlytPaid})
	protected void skip(View v){

		switch (v.getId()) {
		case R.id.ibtnBack:
			Tools.startActivity(this, MainActivity_.class);
			finish();
			break;
		case R.id.rlytAll:
			viewPager.setCurrentItem(0);;
			updateFontColor(0);
			break;
		case R.id.rlytNotSettled:
			viewPager.setCurrentItem(1);;
			updateFontColor(1);
			break;
		case R.id.rlytPaid:
			viewPager.setCurrentItem(2);;
			updateFontColor(2);
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		Tools.startActivity(this, MainActivity_.class);
		super.onBackPressed();
	}
	
	@AfterViews
	protected void setupFragment() {
		new Thread(){
			@Override
			public void run() {
				final FragmentManager fm = getSupportFragmentManager();
				//构建Fragment的集合
				final List<Fragment> fs=new ArrayList<Fragment>();
				//快速挂号
				fs.add(new Indent_fragment_R_());
				//运动指导
				fs.add(new Indent_fragment_SC_());
				//医生上门
				fs.add(new Indent_fragment_DV_());
				MyPagerAdapter adapter=new MyPagerAdapter(fm, fs);
				viewPager.setAdapter(adapter);
			}
		}
		.start();
//		ZoomOutPageTransformer ZoomOutPageTransformer  = new ZoomOutPageTransformer();
//		viewPager.setPageTransformer(true, ZoomOutPageTransformer);
		//添加事件监听
		viewPager
		.setOnPageChangeListener
		(new OnPageChangeListener() {
			public void onPageSelected(int position) {
					viewPager.setCurrentItem(position);;
					updateFontColor(position);
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}
	/**
	 * 点击效果，根据点击位置修改按钮颜色和下划线的显示。
	 * @param i
	 */
	private void updateFontColor(int i){
		switch (i) {
		case 0:
			tvAll.setTextColor(0xffffa954);
			ivAll.setVisibility(View.VISIBLE);
			tvNotSettled.setTextColor(0xff3b3b3b);
			ivNotSettled.setVisibility(View.INVISIBLE);
			tvPaid.setTextColor(0xff3b3b3b);
			ivPaid.setVisibility(View.INVISIBLE);
			break;
		case 1:
			tvNotSettled.setTextColor(0xffffa954);
			ivNotSettled.setVisibility(View.VISIBLE);
			tvAll.setTextColor(0xff3b3b3b);
			ivAll.setVisibility(View.INVISIBLE);
			tvPaid.setTextColor(0xff3b3b3b);
			ivPaid.setVisibility(View.INVISIBLE);
			break;

		case 2:
			tvPaid.setTextColor(0xffffa954);
			ivPaid.setVisibility(View.VISIBLE);
			tvAll.setTextColor(0xff3b3b3b);
			ivAll.setVisibility(View.INVISIBLE);
			tvNotSettled.setTextColor(0xff3b3b3b);
			ivNotSettled.setVisibility(View.INVISIBLE);
			break;
		}
	}
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
