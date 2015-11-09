package com.huiyuan.networkhospital.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 次Toast不会出现多次,当多次显示Toast时,会在把上一个Toast给覆盖掉,只显示最后一个Toast
 * @author Administrator
 *
 */
@SuppressLint("ShowToast")
public class MyToast{
	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	public static void showToast(Context mContext, String text, int duration) {

		mHandler.removeCallbacks(r);
		if (mToast != null)
			mToast.setText(text);
		else
			mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
		mHandler.postDelayed(r, duration);

		mToast.show();
	}
}