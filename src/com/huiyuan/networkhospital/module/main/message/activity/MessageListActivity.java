//package com.huiyuan.networkhospital.module.main.message.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;
//
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//
//import com.huiyuan.networkhospital.R;
//import com.huiyuan.networkhospital.module.main.message.fragment.MsHospital_;
//import com.huiyuan.networkhospital.module.main.message.fragment.MsSystem_;
//
//@EActivity(R.layout.activity_message_list)
//public class MessageListActivity extends FragmentActivity {
//
//	@ViewById(R.id.ms_viewPager)
//	ViewPager viewPager;
//
//
//	@ViewById
//	Button bt_message_list1;
//	@ViewById
//	ImageView img_message_list1;
//	@ViewById
//	ImageView img_message_list2;
//	@ViewById
//	Button bt_message_list2;
//	@ViewById
//	ImageButton imbt_message_list;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		super.onCreate(savedInstanceState);
//		// setContentView(R.layout.activity_main);
//	}
//
//	@Click( R.id.imbt_message_list)
//	public void back() {
//			finish();
//	}
//
//	@AfterViews
//	protected void setupFragment() {
//		new Thread() {
//			@Override
//			public void run() {
//				final FragmentManager fm = getSupportFragmentManager();
//				// 构建Fragment的集合
//				final List<Fragment> fs = new ArrayList<Fragment>();
//
//				fs.add(new MsHospital_());
//
//				fs.add(new MsSystem_());
//
//				DvPagerAdapter adapter = new DvPagerAdapter(fm, fs);
//				viewPager.setAdapter(adapter);
//				img_message_list1.setVisibility(View.VISIBLE);
//				img_message_list2.setVisibility(View.INVISIBLE);
//			}
//		}.start();
//		// //添加切换效果
//		// viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
//			public void onPageSelected(int position) {
//				switch (position) {
//				case 0:
//					img_message_list1.setVisibility(View.VISIBLE);
//					img_message_list2.setVisibility(View.INVISIBLE);
//					break;
//				case 1:
//					img_message_list1.setVisibility(View.INVISIBLE);
//					img_message_list2.setVisibility(View.VISIBLE);
//					break;
//				}
//			}
//
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//			}
//
//			public void onPageScrollStateChanged(int arg0) {
//			}
//		});
//	}
//
//	/**
//	 * 点击radioButton切换fragment
//	 */
//	@Click({ R.id.bt_message_list1, R.id.bt_message_list2 })
//	protected void conversion(View v) {
//		switch (v.getId()) {
//		case R.id.bt_message_list1:
//			viewPager.setCurrentItem(0);
//			img_message_list1.setVisibility(View.VISIBLE);
//			img_message_list2.setVisibility(View.INVISIBLE);
//			break;
//		case R.id.bt_message_list2:
//			viewPager.setCurrentItem(1);
//			img_message_list1.setVisibility(View.INVISIBLE);
//			img_message_list2.setVisibility(View.VISIBLE);
//			break;
//		}
//	}
//
//	/**
//	 * 更新Fragment
//	 * 
//	 * @author Administrator
//	 *
//	 */
//	class DvPagerAdapter extends FragmentPagerAdapter {
//		private List<Fragment> fs;
//
//		public DvPagerAdapter(FragmentManager fm, List<Fragment> fs) {
//			super(fm);
//			this.fs = fs;
//		}
//
//		public Fragment getItem(int arg0) {
//			return fs.get(arg0);
//		}
//
//		public int getCount() {
//			return fs.size();
//		}
//
//	}
//
//}
