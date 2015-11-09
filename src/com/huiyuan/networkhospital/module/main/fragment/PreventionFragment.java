package com.huiyuan.networkhospital.module.main.fragment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.customview.MyImgScroll;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.BadgeView;
import com.huiyuan.networkhospital.entity.zlk.ReminderCount;
import com.huiyuan.networkhospital.module.main.MainActivity;
import com.huiyuan.networkhospital.module.main.case_history.activity.CaseHistoryActivity_;
import com.huiyuan.networkhospital.module.main.doctor_visit.activity.DoctorVisitActivity_;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.GetMedicineActivity_;
import com.huiyuan.networkhospital.module.main.hospital_introduce.activity.HosbitalIntroduceActivity_;
import com.huiyuan.networkhospital.module.main.registration.activity.RegistrationActivity_;
import com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity.RehabilitationTutorialActivity_;
import com.huiyuan.networkhospital.module.main.sport_coaching.activity.SportCoachingActivity_;
import com.huiyuan.networkhospital.module.user.IndentListActivity_;
import com.umeng.message.proguard.bd;

@EFragment
public class PreventionFragment extends Fragment {

	@ViewById
	MyImgScroll myPager; // 图片容器
	@ViewById
	LinearLayout ovalLayout; // 圆点容器

	// 运动指导
	@ViewById
	Button btGuide;
	// 复诊拿药
	@ViewById
	Button btMedication;
	// 康复教程
	@ViewById
	Button btCourse;
	// 快速挂号
	@ViewById
	Button btRegistered;
	// 医生上门
	@ViewById
	Button btVisit;
	// 医院LBS
	@ViewById
	Button btLBS;
	// 中心介绍
	@ViewById
	Button btCenter;
	// 消息
	@ViewById
	Button btMessage;
	// 我的病历
	@ViewById
	Button btMyRecord;
	/**
	 * 图片组
	 */
	private List<View> listViews;

	private Activity context;

	/**
	 * 来自医生的未读消息数量
	 */
	public static int doctorUnreadCount = 0;
	/**
	 * 来说教练的未读数量
	 */
	public static int CoachUnreadCount = 0;

	private BadgeView bdDoctor;

	private BadgeView bdCoach;

	/**
	 * 医生和教练的未读消息
	 */
	public static ArrayList<ReminderCount> rcs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_prevention, container,
				false);
		return view;
	}

	@AfterViews
	protected void init() {
		context = PreventionFragment.this.getActivity();
		InitViewPager();// 初始化图片
		// 开始滚动
		myPager.start(context, listViews, 4000, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
		bdDoctor = new BadgeView(this.getActivity());
		bdCoach = new BadgeView(this.getActivity());
	}

	/**
	 * 初始化图片
	 */
	private void InitViewPager() {
		listViews = new ArrayList<View>();
		int[] imageResId = new int[] { R.drawable.test_01banner,
				R.drawable.test_bg_my_portrait, R.drawable.test_ic_find_banner,
				R.drawable.test_ic_information };
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(context);
			imageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {// 设置图片点击事件
					//					Toast.makeText(context, "点击了:" + myPager.getCurIndex(),
					//							Toast.LENGTH_SHORT).show();
				}
			});
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			listViews.add(imageView);
		}
	}

	@Click({ R.id.btCenter, R.id.btCourse, R.id.btGuide, R.id.btLBS,
		R.id.btMedication, R.id.btMessage, R.id.btMyRecord,
		R.id.btRegistered, R.id.btVisit })
	protected void skip(View v) {
		switch (v.getId()) {
		case R.id.btCenter://中心介绍
			Tools.startActivity(context, HosbitalIntroduceActivity_.class);
			break;
		case R.id.btVisit://医生上门
			Tools.startActivity(context, DoctorVisitActivity_.class);
			break;
		case R.id.btGuide://运动指导
			Tools.startActivity(context, SportCoachingActivity_.class);
			break;
		case R.id.btLBS://地图
			// TTSController ttsManager =
			// TTSController.getInstance(getActivity());// 初始化语音模块
			// ttsManager.init();
			// AMapNavi.getInstance(getActivity()).setAMapNaviListener(ttsManager);//
			// 设置语音模块播报
			// Intent naviStartIntent = new Intent(getActivity(),
			// NaviStartActivity.class);
			// naviStartIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			// startActivity(naviStartIntent);
			// Intent intent = new Intent("android.intent.action.VIEW",
			// android.net.Uri.parse("androidamap://showTraffic?sourceApplication=softname&poiid=BGVIS1&lat=36.2&lon=116.1&level=10&dev=0"));
			// intent.setPackage("com.autonavi.minimap");
			// startActivity(intent);
			// Tools.startActivity(context, HospitalLBSActivity.class);
			try {
				// "http://f.amap.com/084K4P"
				//
				Intent intent = new Intent("android.intent.action.VIEW",
						// android.net.Uri
						// .parse("androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=30.657718&lon=104.047106&dev=1&style=2"));
						android.net.Uri
						.parse("androidamap://viewMap?sourceApplication=appname&poiname=四川省治未病中心&lat=30.6515533019&lon=104.0399785749&dev=0"));
				intent.setPackage("com.autonavi.minimap");
				startActivity(intent);

			} catch (Exception e) {
				// TODO: handle exception
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://f.amap.com/062Kfg"));
				startActivity(browserIntent);
			}
			// Tools.startActivity(context, gaode.class);
			break;//复诊拿药
		case R.id.btMedication:
			Tools.startActivity(context, GetMedicineActivity_.class);
			break;
		case R.id.btMessage://我的消息
			Tools.startActivity(context, IndentListActivity_.class);
			// Tools.startActivity(context, MessageListActivity_.class);
			//				Tools.startActivity(context, R_Myorder_.class);

			break;
		case R.id.btMyRecord://我的病历
			Tools.startActivity(context, CaseHistoryActivity_.class);
			break;
		case R.id.btRegistered://快速挂号
			Tools.startActivity(context, RegistrationActivity_.class);
			break;
		case R.id.btCourse:// 康复教程
			Tools.startActivity(context,
					RehabilitationTutorialActivity_.class);
			break;
		}
	}


	/**
	 * 获取所有会话
	 * 
	 * @param context
	 * @return
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        +	 */
	public static List<EMConversation> loadConversationsWithRecentChat() {
		// 获取所有会话，包括陌生人
		Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
		// 过滤掉messages size为0的conversation
		/**
		 * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
		 * 影响排序过程，Collection.sort会产生异常
		 * 保证Conversation在Sort过程中最后一条消息的时间不变 
		 * 避免并发问题
		 */
		List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
		rcs = new ArrayList<ReminderCount>();
		synchronized (conversations) {
			int d = 0;
			int c = 0;
			for (EMConversation conversation : conversations.values()) {
				if (conversation.getAllMessages().size() != 0) {
					//if(conversation.getType() != EMConversationType.ChatRoom){
					sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
					//}
					String who = conversation.getMessage(0).getFrom();
					if("d".equals(who.subSequence(0, 1))){
						ReminderCount rc = new ReminderCount();
						d= d+conversation.getUnreadMsgCount();
						rc.setDoctor(who);
						rc.setdCount(conversation.getUnreadMsgCount());
						rcs.add(rc);
					}else {
						ReminderCount rc = new ReminderCount();
						c= c+conversation.getUnreadMsgCount();
						rc.setCoach(who);
						rc.setcCount(conversation.getUnreadMsgCount());
						rcs.add(rc);
					}
				}
			}
			doctorUnreadCount = d;
			CoachUnreadCount = c;
		}
		//		try {
		//			// Internal is TimSort algorithm, has bug
		//			sortConversationByLastChatTime(sortList);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		List<EMConversation> list = new ArrayList<EMConversation>();
		for (Pair<Long, EMConversation> sortItem : sortList) {
			list.add(sortItem.second);
		}
		return list;
	}

	/**
	 * 显示来自医生和教练的未读消息
	 */
	private void showUnreadCount(){
		if(doctorUnreadCount>=0){
			bdDoctor.setBadgeCount(doctorUnreadCount);
			bdDoctor.setTargetView(btMedication);
		}
		if(CoachUnreadCount>=0){
			bdCoach.setBadgeCount(CoachUnreadCount);
			bdCoach.setTargetView(btGuide);
		}
	}

	@Override
	public void onResume() {
		myPager.startTimer();
		loadConversationsWithRecentChat();
		showUnreadCount();
		//		totalUnreadCount = ((MainActivity) this.getActivity()).getUnreadMsgCountTotal();
		super.onResume();
	}

	@Override
	public void onStop() {
		myPager.stopTimer();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
