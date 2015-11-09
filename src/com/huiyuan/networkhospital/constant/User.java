/**
 *@author 何鹏
 *@date 2014-9-9上午8:54:21
 *@describe 用户手机号码的本地信息的存储和读取
 */

package com.huiyuan.networkhospital.constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class User {

	private Context context;

	// 构造方法中传入上下文对象
	public User(Context context) {
		super();
		this.context = context;
	}

	public void savePhone(String userPhone) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"UserPhone", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("UserPhone", userPhone); // 目前是保存在内存中，还没有保存到文件中
		editor.commit(); // 数据提交到xml文件中
	}

	public void savePass(String pass) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"savePass", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("savePass", pass); // 目前是保存在内存中，还没有保存到文件中
		editor.commit(); // 数据提交到xml文件中
	}

	public String getPhone() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"UserPhone", Context.MODE_PRIVATE);
		return sharedPreferences.getString("UserPhone", "");
	}

	public String getPass() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"savePass", Context.MODE_PRIVATE);
		return sharedPreferences.getString("savePass", "");
	}

	public void removePass() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"savePass", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.remove("savePass");
		editor.commit();
	}

}
