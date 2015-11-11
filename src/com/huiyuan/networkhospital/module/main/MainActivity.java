package com.huiyuan.networkhospital.module.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.fragmentanimation.DepthPageTransformer;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.BadgeView;
import com.huiyuan.networkhospital.module.main.fragment.PreventionFragment_;
import com.huiyuan.networkhospital.module.main.fragment.UserFragment_;
import com.huiyuan.networkhospital.module.main.fragment.testFragment_;
import com.huiyuan.networkhospital.module.more.MoreActivity_;
import com.huiyuan.networkhospital.module.user.UserInfoActivity_;
import com.pgyersdk.update.PgyUpdateManager;

@SuppressLint("ShowToast")
@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	@ViewById
	ViewPager viewPager;

	@ViewById
	RadioButton radio0;

	@ViewById
	RadioButton radio1;

	@ViewById
	RadioButton radio2;

	@ViewById
	ImageView ivMore;

	private boolean isExit;
	private boolean key1=true;
	private boolean key2=true;

	private BadgeView bd;
	
	public static Activity activityInstance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		((NApplication)this.getApplication()).addActivity(this);
		//		setContentView(R.layout.activity_main);
		activityInstance = this;
		PgyUpdateManager.register(this);//蒲公英版本更新
	}
	
	/**
	 * 显示未读消息数量
	 */
	public void showUnreadMsg(){
		int count = getUnreadMsgCountTotal();
		bd.setBadgeCount(count);
		bd.setTargetView(radio0);
	}
	
	@AfterViews
	protected void setupFragment() {
		radio0.setChecked(true);
		bd = new BadgeView(this);
		new Thread(){
			@Override
			public void run() {
				final FragmentManager fm = getSupportFragmentManager();
				//构建Fragment的集合
				final List<Fragment> fs=new ArrayList<Fragment>();
				fs.add(new PreventionFragment_());
//				fs.add(new testFragment_());
//				fs.add(new FindFragment_());
				fs.add(new testFragment_());
				fs.add(new UserFragment_());
				MyPagerAdapter adapter=new MyPagerAdapter(fm, fs);
				viewPager.setAdapter(adapter);
			}
		}
		.start();
		//添加切换效果
		viewPager.setPageTransformer(true, new DepthPageTransformer());
		//添加事件监听
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					key1=true;
					radio0.setChecked(true);
					updateFontColor(0);
					break;
				case 1:
					if (key1) {
						viewPager.setCurrentItem(2);
						updateFontColor(4);
//						viewPager.setCurrentItem(2);
//						updateFontColor(2);
					}else {
						viewPager.setCurrentItem(0);
						updateFontColor(0);
					}
					break;
				case 2:
					radio2.setChecked(true);
					updateFontColor(4);
					key1=false;
					
//					radio1.setChecked(true);
//					key1=false;
//					key2=true;
//					updateFontColor(2);

					break;
				case 3:
					if (key2) {
						viewPager.setCurrentItem(4);
						updateFontColor(4);
					}else {
						viewPager.setCurrentItem(2);
						updateFontColor(2);
					}
					break;
				case 4:
					key2=false;
					radio2.setChecked(true);
					updateFontColor(4);
					viewPager.setCurrentItem(4);
					updateFontColor(4);
					break;
				}
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	@Override
	public void onBackPressed() {
		//			 Tools.count=0;
		if(isExit){
			super.onBackPressed();
			//结束进程，下次用户再从程序列表中启动程序，创建新的进程，再次执行Eapplication
			((NApplication) this.getApplication()).destoryAllActivity();
			finish();
			System.exit(0);
		}else{
			isExit=true;//准备退出
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					isExit=false;//取消退出
				}
			}, 2000);
		}
		Toast.makeText(MainActivity.this, "再按一次退出程序", 0).show();
	}


	/**
	 * 点击radioButton切换fragment
	 */
	@Click({R.id.radio0,R.id.radio1,R.id.radio2,R.id.ivMore})
	protected void conversion(View v) {
		switch (v.getId()) {
		case R.id.radio0:
			viewPager.setCurrentItem(0);
			updateFontColor(0);
//			 bd.decrementBadgeCount(1);
			break;
		case R.id.radio1:
			viewPager.setCurrentItem(2);
			updateFontColor(2);
			break;
		case R.id.radio2:
			viewPager.setCurrentItem(4);
			updateFontColor(4);
			break;
		case R.id.ivMore:
			Tools.startActivity(this, MoreActivity_.class);
			break;
		}
	}
	/**
	 * 根据传入的参数，修改对应的控件的字体颜色
	 * @param i
	 */
	private void updateFontColor(int i){
		switch (i) {
		case 0:
			radio0.setTextColor(Color.rgb(116, 191, 230));
			radio1.setTextColor(Color.rgb(114, 114, 114));
			radio2.setTextColor(Color.rgb(114, 114, 114));
			break;
		case 2:
			radio0.setTextColor(Color.rgb(114, 114, 114));
			radio1.setTextColor(Color.rgb(116, 191, 230));
			radio2.setTextColor(Color.rgb(114, 114, 114));
			break;

		case 4:
			radio0.setTextColor(Color.rgb(114, 114, 114));
			radio1.setTextColor(Color.rgb(114, 114, 114));
			radio2.setTextColor(Color.rgb(116, 191, 230));
			break;
		}
	}

	@Override
	protected void onResume() {
		hintCommCompleteInfo();
//		showUnreadMsg();
		super.onResume();
	}

	private void hintCommCompleteInfo(){
		if(isNull()){
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
					this);
			builder.setTitle("温馨提示");
			builder.setMessage("为了让我们能够为您提供完整顿服务,请去完善个资料!");
			builder.setIcon(R.drawable.ic_app_user);
			builder.setCancelable(false);
			builder.setPositiveButton("前往",
					new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Tools.startActivity(MainActivity.this, UserInfoActivity_.class);
				}
			});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			builder.create().show();
		}

	}

	/*
	 *   "Data": {
        "ID": 6,
        "LoginName": "18180545321",
        "LoginPsd": "123456",
        "name": "何鹏",
        "Sex": false,
        "Phone": "18180545321",
        "Address": "",
        "Type": 0,
        "State": 0,
        "Equipment": "Ajwuvu0P5DFcE4hO_L2JOCMKX-1wBljYYNLuibqcT_nm",
        "Photo": "635805068949654500.jpg",
        "IC": "111111111111111111",
        "Flag": true,
        "ENum": null
    },
	 */
	private boolean isNull(){
		if("".equals(NApplication.IC)){
			return true;
		}else if("".equals(NApplication.sex)){
			return true;
		}else if("".equals(NApplication.username)){
			return true;
		}
		return false;
	}

	
	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		int chatroomUnreadMsgCount = 0;
		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		for(EMConversation conversation:EMChatManager.getInstance().getAllConversations().values()){
			if(conversation.getType() == EMConversationType.ChatRoom)
			chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
		}
		return unreadMsgCountTotal-chatroomUnreadMsgCount;
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
