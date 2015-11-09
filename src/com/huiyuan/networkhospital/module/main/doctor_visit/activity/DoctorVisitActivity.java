package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.customview.NoScrollViewPager;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.main.doctor_visit.fragment.Dv_fragment_on_;
import com.huiyuan.networkhospital.module.main.doctor_visit.fragment.Dv_fragment_un_;
/**
 * 医生上门主页面
 * @author lenovo
 *
 */
@SuppressLint("ShowToast")
@EActivity(R.layout.activity_doctor_visit)
public class DoctorVisitActivity extends FragmentActivity {

	@ViewById(R.id.DvviewPager)
	NoScrollViewPager viewPager;

	@ViewById
	Button bt_doctor_visit_chose1;
	@ViewById
	ImageView img_doctor_visit_chose1;
	@ViewById
	ImageView img_doctor_visit_chose2;
	@ViewById
	Button bt_doctor_visit_chose2;
	@ViewById
	ImageButton imbt_doctor_visit_back;
	@ViewById
	ImageButton imbt_doctor_visit_add;
	private boolean isExit;
	public static String StringE="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

	}

	/**
	 * 设置监听
	 */
	@Click({ R.id.imbt_doctor_visit_back, R.id.imbt_doctor_visit_add })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imbt_doctor_visit_add:
			Intent intent = new Intent(DoctorVisitActivity.this,
					MakeActivity_.class);
			startActivity(intent);
			break;
		case R.id.imbt_doctor_visit_back:
			Tools.startActivity(this, MainActivity_.class);
			finish();
			break;
		default:
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
		new Thread() {
			@Override
			public void run() {
				final FragmentManager fm = getSupportFragmentManager();
				// 构建Fragment的集合
				final List<Fragment> fs = new ArrayList<Fragment>();
				fs.add(new Dv_fragment_un_());
				fs.add(new Dv_fragment_on_());
				DvPagerAdapter adapter = new DvPagerAdapter(fm, fs);
				viewPager.setAdapter(adapter);
				// viewPager.setScrollble(false);
			}
		}.start();
		DoctorVisitActivity.StringE="1";
		img_doctor_visit_chose1.setVisibility(View.VISIBLE);
		img_doctor_visit_chose2.setVisibility(View.INVISIBLE);
		// //添加切换效果
		// viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					img_doctor_visit_chose1.setVisibility(View.VISIBLE);
					img_doctor_visit_chose2.setVisibility(View.INVISIBLE);
					break;
				case 1:
					img_doctor_visit_chose1.setVisibility(View.INVISIBLE);
					img_doctor_visit_chose2.setVisibility(View.VISIBLE);
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
	 * 点击radioButton切换fragment
	 */
	@Click({ R.id.bt_doctor_visit_chose1, R.id.bt_doctor_visit_chose2 })
	protected void conversion(View v) {
		switch (v.getId()) {
		case R.id.bt_doctor_visit_chose1:
			viewPager.setCurrentItem(0);
			DoctorVisitActivity.StringE="1";
			img_doctor_visit_chose1.setVisibility(View.VISIBLE);
			img_doctor_visit_chose2.setVisibility(View.INVISIBLE);
			break;
		case R.id.bt_doctor_visit_chose2:
			viewPager.setCurrentItem(1);
			DoctorVisitActivity.StringE="2";
			img_doctor_visit_chose1.setVisibility(View.INVISIBLE);
			img_doctor_visit_chose2.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		try {
			if (StringE.equals("2")) {
				viewPager.setCurrentItem(1);
				img_doctor_visit_chose1.setVisibility(View.INVISIBLE);
				img_doctor_visit_chose2.setVisibility(View.VISIBLE);
			} 
			else {
				viewPager.setCurrentItem(0);
				img_doctor_visit_chose1.setVisibility(View.VISIBLE);
				img_doctor_visit_chose2.setVisibility(View.INVISIBLE);
			}
		} catch (Exception e) {
			// TODO: handle exception
			viewPager.setCurrentItem(0);
			img_doctor_visit_chose1.setVisibility(View.VISIBLE);
			img_doctor_visit_chose2.setVisibility(View.INVISIBLE);
		}
		super.onResume();
		
	}

	/**
	 * 更新Fragment
	 * 
	 * @author Administrator
	 *
	 */
	class DvPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fs;

		public DvPagerAdapter(FragmentManager fm, List<Fragment> fs) {
			super(fm);
			this.fs = fs;
		}

		public Fragment getItem(int arg0) {
			return fs.get(arg0);
		}

		public int getCount() {
			return fs.size();
		}

	}

}
