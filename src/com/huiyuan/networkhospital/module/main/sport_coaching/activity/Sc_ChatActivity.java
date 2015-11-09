//package com.huiyuan.networkhospital.module.main.sport_coaching.activity;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.database.Cursor;
//import android.media.MediaPlayer;
//import android.media.MediaRecorder;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.huiyuan.networkhospital.R;
//import com.huiyuan.networkhospital.common.util.SoundRecord;
//import com.huiyuan.networkhospital.common.util.Tools;
//import com.huiyuan.networkhospital.constant.GlobalConstant;
//import com.huiyuan.networkhospital.entity.Chat;
//import com.huiyuan.networkhospital.module.main.VideoRecordActivity_;
//import com.huiyuan.networkhospital.module.main.adapter.ChatAdapter;
//@EActivity(R.layout.activity__chat)
//public class Sc_ChatActivity extends Activity {
//	/** log标记 */
//	private static final String LOG_TAG = "AudioRecordTest";
//	/** 语音文件保存路径 */
//	private String mFileName = null;
//	/** 按住说话按钮 */
//	@ViewById(R.id.chat_record_btn)
//	Button mBtnVoice;
//
//	/** 按住说话动画 */
//	@ViewById(R.id.voice_record_anim)
//	ImageView recordAnim;
//
//	@ViewById(R.id.voice_record_anim_container)
//	RelativeLayout recordAnimContainer;
//
//	/** 用于完成录音 */
//	private MediaRecorder mRecorder = null;
//	// /** 显示语音列表 */
//	// private ListView mVoidListView;
//	// /** 语音列表适配器 */
//	// private MyListAdapter mAdapter;
//	// /** 语音列表 */
//	// private List<String> mVoicesList;
//	// /** 录音存储路径 */
//	private static final String PATH = "/sdcard/networkhospital_user/VOICE/";
//
//	@ViewById
//	TextView tvTitle1;
//
//	@ViewById
//	TextView tvTime;
//
//	@ViewById
//	ImageView ivBack;
//
//	@ViewById
//	ImageView ivRecord;
//
//	@ViewById
//	ImageView ivMore1;
//
//	@ViewById
//	ListView lvChat;
//
//	@ViewById
//	EditText etInput;
//
//	@ViewById
//	Button btSend;
//	@ViewById
//	RelativeLayout rlMore;
//	@ViewById
//	RelativeLayout rlInputBox;
//
//	private InputMethodManager manager;
//	@ViewById
//	ImageView ivImage;
//
//	@ViewById
//	ImageView ivVideo;
//
//	private List<Chat> list = new ArrayList<Chat>();;
//
//	private ChatAdapter adapter;
//
//    private SoundRecord mRecord = null;
//	protected int recordState = RECORD_OFF; // 录音状态
//	protected float recodeTime = 0.0f; // 录音时长
//	protected double voiceValue = 0.0; // 录音的音量值
//	protected static final int RECORD_OFF = 0; // 不在录音
//	protected static final int RECORD_ON = 1; // 正在录音
//    protected Thread mRecordThread;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		// 注：android.os.Build.VERSION.RELEASE获取版本号
//		// android.os.Build.MODEL 获取手机型号
//        mRecord = new SoundRecord(this);
//	}
//
//	@AfterViews
//	protected void init() {
//		// 去掉Item的分割线
//		lvChat.setDividerHeight(0);
//		lvChat.setDivider(null);
//		// 隐藏聊天功能栏
//		rlMore.setVisibility(View.GONE);
//
//		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		adapter = new ChatAdapter(list, this);
//		lvChat.setAdapter(adapter);
//		mBtnVoice.setOnTouchListener(new OnTouchListener() {// 发送语音
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						switch (event.getAction()) {
//						case MotionEvent.ACTION_DOWN:
//							startVoice();
//							break;
//						case MotionEvent.ACTION_UP:
//							try {
//								stopVoice();
//								sendVoice(mFileName);
//							} catch (Exception e) {
//								// TODO: handle exception
//								Toast.makeText(Sc_ChatActivity.this, "录音时间太短了", 0).show();
//							}
//							break;
//						default:
//							break;
//						}
//						return false;
//					}
//				});
//	}
//
//	@Click({ R.id.ivBack, R.id.ivRecord, R.id.ivMore1, R.id.btSend,
//			R.id.ivImage, R.id.ivVideo, R.id.voice_record_anim })
//	protected void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.ivBack:
//			finish();
//			break;
//		case R.id.ivRecord:
//			if (etInput.getVisibility() == View.GONE) {
//				etInput.setVisibility(View.VISIBLE);
//				ivMore1.setVisibility(View.VISIBLE);
//				btSend.setVisibility(View.VISIBLE);
//				mBtnVoice.setVisibility(View.GONE);
//			} else {
//				etInput.setVisibility(View.GONE);
//				btSend.setVisibility(View.GONE);
//				ivMore1.setVisibility(View.GONE);
//				mBtnVoice.setVisibility(View.VISIBLE);
//				etInput.setBottom(RelativeLayout.ALIGN_BOTTOM);
//			}
//
//			break;
//
//		// 隐藏或显示聊天功能栏
//		case R.id.ivMore1:
//			if (rlMore.getVisibility() == View.GONE) {
//				rlMore.setVisibility(View.VISIBLE);
//			} else {
//				rlMore.setVisibility(View.GONE);
//				rlInputBox.setBottom(RelativeLayout.ALIGN_BOTTOM);
//			}
//			break;
//		case R.id.btSend:
//			sendText(etInput.getText().toString());
//			break;
//		case R.id.ivImage:
//			getImage();
//			break;
//		case R.id.ivVideo:// 发送视频
//			Intent intent = new Intent(this, VideoRecordActivity_.class);
//			startActivityForResult(intent, GlobalConstant.VIDEO_RECORD);
//			break;
//
//		}
//	}
//
//	/**
//	 * 进入系统图库
//	 */
//	public void getImage() {
//		/*
//		 * 4.4以下版本可以使用 开启Pictures画面Type设定为image intent.setType("image/*");
//		 * 使用Intent.ACTION_GET_CONTENT这个Action
//		 * 
//		 * 在Activity Action里面有一个“ACTION_GET_CONTENT”字符串常量，
//		 * 该常量让用户选择特定类型的数据，并返回该数据的URI.
//		 * 
//		 * intent.setAction(Intent.ACTION_GET_CONTENT); 取得相片后返回本画面
//		 * startActivityForResult(intent, GlobalConstant.PHOTO_ALBUM);
//		 */
//
//		/*
//		 * 此方法 4.4及以上版本使用,兼容低版本 如果4.4及以上不使用此方法,获得的Uri无法从中取出图片的路径
//		 */
//		Intent intent1 = new Intent(Intent.ACTION_PICK,
//				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//		intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//				"image/*");
//		startActivityForResult(intent1, GlobalConstant.PHOTO_ALBUM);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (RESULT_OK == resultCode && data != null) {
//			String version = Tools.getCurrentVersionName(this);
//			switch (requestCode) {
//			case GlobalConstant.VIDEO_RECORD:
//				// String path = data.getData().getPath();
//				String[] paths = data
//						.getStringArrayExtra(GlobalConstant.KEY_DATA);
//				Toast.makeText(this, "录制成功:" + paths[0].toString(), 0).show();
//				sendVideo(paths);
//				break;
//			case GlobalConstant.PHOTO_ALBUM:
//				Uri uri = data.getData();
//				Log.e("uri", uri.toString());
//				String[] proj = { MediaStore.Images.Media.DATA };
//
//				// 好像是android多媒体数据库的封装接口，具体的看Android文档
//				Cursor cursor = managedQuery(uri, proj, null, null, null);
//				// 按我个人理解 这个是获得用户选择的图片的索引值
//				int column_index = cursor
//						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
//				cursor.moveToFirst();
//				// 最后根据索引值获取图片路径
//				String path = cursor.getString(column_index);
//				sendPicture(path);
//				Toast.makeText(this, "成功:" + path, 0).show();
//				break;
//			}
//		}
//
//	}
//
//	/**
//	 * 发送文字消息
//	 * 
//	 * @param text
//	 */
//	private void sendText(String text) {
//		if (TextUtils.isEmpty(text)) {
//			Toast.makeText(this, "输入的内容不能为空", 0).show();
//			return;
//		}
//		etInput.setText("");// 清空输入框内容
//		Chat c = new Chat(0, 1, "msg_text", text, "", "", "", "");
//		list.add(c);
//		adapter.notifyDataSetChanged();
//	}
//
//	/**
//	 * 发送图片消息
//	 * 
//	 * @param path
//	 */
//	private void sendPicture(String path) {
//		if (TextUtils.isEmpty(path)) {
//			return;
//		}
//		Chat c = new Chat(0, 1, "msg_picture", "", path, "", "", "");
//		list.add(c);
//		adapter.notifyDataSetChanged();
//	}
//
//	/**
//	 * 发送语音消息
//	 * 
//	 * @param path
//	 */
//	private void sendVoice(String path) {
//		Chat c = new Chat(0, 1, "msg_voice", "", "", "", path, "");
//		list.add(c);
//		adapter.notifyDataSetChanged();
//	}
//
//	/**
//	 * 发送视频消息
//	 * 
//	 * @param str
//	 */
//	private void sendVideo(String[] str) {
//		Chat c = new Chat(0, 1, "msg_video", "", str[1], str[0], "", "");
//		list.add(c);
//		adapter.notifyDataSetChanged();
//	}
//
//	// @ItemClick(R.id.lvChat)
//	// public void spinner(int position) {
//	// if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
//	// manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//	// InputMethodManager.HIDE_NOT_ALWAYS); }
//	// }
//
//	 /**
//	 * 录音线程
//	 */
//	 private Runnable recordThread = new Runnable() {
//	
//	 @Override
//	 public void run() {
//	 while (recordState == RECORD_ON) {
//	 try {
//	 Thread.sleep(200);
//	 // 获取音量，更新dialog
//	  voiceValue = mRecord.getAmplitude();
////	 Tools.LogD("ChatActivity voiceValue = "+voiceValue);
//	 // mHandler.sendEmptyMessage(3);
//	 } catch (InterruptedException e) {
//	 e.printStackTrace();
//	 }
//	 }
//	
//	
//	 }
//	 };
//	/** 开始录音 */
//	private void startVoice() {
//		// 设置录音保存路径
//		mFileName = PATH + UUID.randomUUID().toString() + ".amr";
//		String state = android.os.Environment.getExternalStorageState();
//		if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
//			Log.i(LOG_TAG, "SD Card is not mounted,It is  " + state + ".");
//		}
//		File directory = new File(mFileName).getParentFile();
//		if (!directory.exists() && !directory.mkdirs()) {
//			Log.i(LOG_TAG, "Path to file could not be created");
//		}
////		Toast.makeText(Sc_ChatActivity.this, "开始录音", 0).show();
//        if (recordState == RECORD_ON) {
//            return;
//        }
//        recordState = RECORD_ON;
//        recordAnimContainer.setVisibility(View.VISIBLE);
//        mRecordThread = new Thread(recordThread);
//        mRecordThread.start();
//		mRecorder = new MediaRecorder();
//		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//		mRecorder.setOutputFile(mFileName);
//		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//		try {
//			mRecorder.prepare();
//		} catch (IOException e) {
//			Log.e(LOG_TAG, "prepare() failed");
//		}
//		mRecorder.start();
//	}
//
//	/** 停止录音 */
//	private void stopVoice() {
//	        if (recordState != RECORD_ON) {
//	            return;
//	        }
//	        recordState = RECORD_OFF;
//	        recordAnimContainer.setVisibility(View.INVISIBLE);
//			mRecorder.stop();
//			mRecorder.reset();
//			mRecorder.release();
//			mRecorder = null;
////			 mVoicesList.add(mFileName);
////			 mAdapter = new MyListAdapter(RecordActivity.this);
////			 mVoidListView.setAdapter(mAdapter);
////			Toast.makeText(Sc_ChatActivity.this, "录音完成", 0).show();
//			// TODO: handle exception
//	}
//
//	/**
//	 * 录音图片随声音大小切换
//	 */
//	protected void setRecordAnimImage() {
//		if (voiceValue < 800.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_01);
//		} else if (voiceValue > 800.0 && voiceValue < 1200.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_02);
//		} else if (voiceValue > 1200.0 && voiceValue < 1400.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_03);
//		} else if (voiceValue > 1400.0 && voiceValue < 1600.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_04);
//		} else if (voiceValue > 1600.0 && voiceValue < 1800.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_05);
//		} else if (voiceValue > 1800.0 && voiceValue < 2000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_06);
//		} else if (voiceValue > 2000.0 && voiceValue < 3000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_07);
//		} else if (voiceValue > 3000.0 && voiceValue < 4000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_08);
//		} else if (voiceValue > 4000.0 && voiceValue < 5000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_09);
//		} else if (voiceValue > 5000.0 && voiceValue < 6000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_10);
//		} else if (voiceValue > 6000.0 && voiceValue < 8000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_11);
//		} else if (voiceValue > 8000.0 && voiceValue < 10000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_12);
//		} else if (voiceValue > 10000.0 && voiceValue < 12000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_13);
//		} else if (voiceValue > 12000.0) {
//			recordAnim.setImageResource(R.drawable.record_animate_14);
//		}
//	}
//
//}
