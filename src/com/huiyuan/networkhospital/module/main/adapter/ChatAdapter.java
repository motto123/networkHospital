package com.huiyuan.networkhospital.module.main.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.entity.Chat;

public class ChatAdapter extends BaseAdapter {

	private List<Chat> list;

	private Context context;

	private ViewHolder holder;

	private static int TYPE = 0;

	private static final int FROM = 0;

	private static final int TO = 1;

	/** 用于语音播放 */
	private MediaPlayer mMediaPlayer = null;
	private boolean isPlaying = true;
	private static final int TYPE_COUNT = 2;

	private static final String MSG_TEXT = "msg_text";

	private static final String MSG_VOICE = "msg_voice";

	private static final String MSG_VIDEO = "msg_video";

	private static final String MSG_PICTURE = "msg_picture";

	public ChatAdapter(List<Chat> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		mMediaPlayer = new MediaPlayer();
	}

	@Override
	public int getCount() {
		if (null == list) {
			list = new ArrayList<Chat>();
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		int type = 0;
		switch (list.get(position).getType()) {
		case FROM:
			type = 0;
			break;
		case TO:
			type = 1;
			break;
		}
		return type;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		holder = null;
		final Chat c = list.get(position);
		int type = getItemViewType(position);
		TYPE = type;
		if (holder == null) {
			holder = new ViewHolder();
			int i = c.getType();
			switch (type) {// 判断显示在左还是右
			case FROM:
				convertView = View.inflate(context, R.layout.item_left, null);
				break;
			case TO:
				convertView = View.inflate(context, R.layout.item_right, null);
				break;
			}
			holder.content = (TextView) convertView.findViewById(R.id.tv1);
			// holder.ivPic = (ImageView) convertView.findViewById(R.id.iv1);
			// holder.pic = (ImageView) convertView.findViewById(R.id.ivPic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final String msgType = c.getMsgType();
		if (MSG_TEXT.equals(msgType)) {// 文本消息
			holder.content.setText(c.getText());
		}else if(MSG_PICTURE.equals(msgType)){//图片消息
			holder.content.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            SpannableString msp = new SpannableString("image");
          //特性, 开始位置, 结束位置 , 用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果EXCLUSIVE_EXCLUSIVE(前后都不包括)
            ///mnt/sdcard/Download/ba88331dfec758b39c3f428034583292.jpg
//            BitmapDrawable bd = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ic_launcher);
            Bitmap bitmap =  PhotoUtil.getThumbnail(context, BitmapFactory.decodeFile(c.getPic()));
//            Bitmap bitmap =  PhotoUtil.getThumbnail(context, bd.getBitmap());
			msp.setSpan(new ImageSpan(context, bitmap), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.content.setText(msp);

		} else if (MSG_VIDEO.equals(msgType)) {// 视频消息
			holder.content.setCompoundDrawablesWithIntrinsicBounds(null, null,
					null, null);
			SpannableString msp = new SpannableString("image");
			msp.setSpan(
					new ImageSpan(this.context, BitmapFactory.decodeFile(c
							.getPic())), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.content.setText(msp);

		} else if (MSG_VOICE.equals(msgType)) {// 声音消息
			/**
			 * 根据消息的来源来确定音量图标的方向
			 */
			if (type == FROM) {
				holder.voicePlayAnim = (AnimationDrawable) context
						.getResources().getDrawable(
								R.drawable.voice_from_play_anim);
				holder.content.setCompoundDrawablesWithIntrinsicBounds(
						holder.voicePlayAnim, null, null, null);

			} else {
				holder.voicePlayAnim = (AnimationDrawable) context
						.getResources().getDrawable(
								R.drawable.voice_to_play_anim);
				holder.content.setCompoundDrawablesWithIntrinsicBounds(null,
						null, holder.voicePlayAnim, null);

			}

		}
		// 内容监听器
		holder.content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (MSG_TEXT.equals(msgType)) {// 文本消息

				} else if (MSG_PICTURE.equals(msgType)) {// 图片消息

				} else if (MSG_VIDEO.equals(msgType)) {// 视频消息

				} else if (MSG_VOICE.equals(msgType)) {// 声音消息
					/**
					 * 播放完成后停止动画
					 */
					mMediaPlayer
							.setOnCompletionListener(new OnCompletionListener() {
								@Override
								public void onCompletion(MediaPlayer mp) {
									// TODO Auto-generated method stub
									isPlaying = true;
									if (TYPE == FROM) {
										holder.voicePlayAnim = (AnimationDrawable) context
												.getResources()
												.getDrawable(
														R.drawable.voice_from_play_anim);
										holder.content
												.setCompoundDrawablesWithIntrinsicBounds(
														holder.voicePlayAnim,
														null, null, null);

									} else {
										holder.voicePlayAnim = (AnimationDrawable) context
												.getResources()
												.getDrawable(
														R.drawable.voice_to_play_anim);
										holder.content
												.setCompoundDrawablesWithIntrinsicBounds(
														null, null,
														holder.voicePlayAnim,
														null);
									}
								}
							});
					playVoice(c.getVoice(), holder);

				}
			}
		});

		return convertView;
	}

	/**
	 * 
	 * @author lenovo 音频播放
	 */

	private void playVoice(String name, ViewHolder holder) {
		try {
			if (isPlaying) {
				holder.voicePlayAnim.start();
				playVoice(name);
				isPlaying = false;
			} else {
				stopVoice();
				isPlaying = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void playVoice(String name) {
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(name);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopVoice() {
		isPlaying = true;
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
			holder.voicePlayAnim.stop();
			if (TYPE == FROM) {
				holder.voicePlayAnim = (AnimationDrawable) context
						.getResources().getDrawable(
								R.drawable.voice_from_play_anim);
				holder.content.setCompoundDrawablesWithIntrinsicBounds(
						holder.voicePlayAnim, null, null, null);

			} else {
				holder.voicePlayAnim = (AnimationDrawable) context
						.getResources().getDrawable(
								R.drawable.voice_to_play_anim);
				holder.content.setCompoundDrawablesWithIntrinsicBounds(null,
						null, holder.voicePlayAnim, null);
			}
		}
	}

	class ViewHolder {
		ImageView pic;
		TextView content;
		ImageView ivPic;
		public AnimationDrawable voicePlayAnim;
	}
}
