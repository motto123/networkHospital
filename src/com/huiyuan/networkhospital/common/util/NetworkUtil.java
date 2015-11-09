package com.huiyuan.networkhospital.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class NetworkUtil {
	public static void checkNetworkState(final Activity activity) {
		// 判断有没有网
		ConnectivityManager manager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		// 没网显示一个dialog
		if (networkInfo == null) {
			AlertDialog.Builder dialog = new Builder(activity);
			dialog.setMessage("亲，你现在没网");
			// 打开网络
			dialog.setPositiveButton("打开网络", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 打开网络设置界面
					try {
						//用代码得手机厂商如samsung ,xiaomi，不同厂商的手机代码不一样。
						//用代码得手机串号，某些android手机得手机号
						//判断手机的android版本,不同版本不同代码
						
						int sdk_int=android.os.Build.VERSION.SDK_INT;
						if (sdk_int>10)
						{
						// 手机刚出厂时，有网络设置界面,有一个Activity
						Intent intent = new Intent(
								Settings.ACTION_WIRELESS_SETTINGS);
						activity.startActivity(intent);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
			// 取消
			dialog.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();

		}
	}

}
