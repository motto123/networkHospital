package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.module.BaseActivity;
/**
 * 单人添加上传信息页面
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_dv__chose_people_detailes)
public class ParticipationActivity extends BaseActivity {
	@ViewById
	ImageButton imbt_chose_people_detailes_back;

	private InputMethodManager manager;
	@ViewById
	Button bt_chose_people_detailes_deta_sum;
	@ViewById
	TextView ed_chose_people_detailes_name;
	@ViewById
	TextView ed_chose_people_detailes_age;
	@ViewById
	TextView ed_chose_people_detailes_phone;
	@ViewById
	EditText ed_chose_people_detailes_deta;
	@ViewById
	TextView sex123;
	private String sex = "true";
	private String AID = "";
	private String Age = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

	}
	/**
	 * 初始化
	 */
	@AfterViews
	public void init() {
		if (sex.equals("true")) {
			sex123.setText("男");
		}else {
			sex123.setText("女");
		}
		ed_chose_people_detailes_name.setText(NApplication.username);
		Time time12 = new Time();
		time12.setToNow();
		Age = time12.year
				- Integer.parseInt(NApplication.IC.toString()
						.substring(6, 10)) + "";
		ed_chose_people_detailes_age.setText(""+Age);
		ed_chose_people_detailes_phone.setText(NApplication.phone);
		Intent get = getIntent();
		AID = get.getStringExtra("AID");
	}
	/**
	 * 设置监听
	 */
	@Click(R.id.bt_chose_people_detailes_deta_sum)
	public void sub() {
		// 传递一些信息到付款页面

		if (ed_chose_people_detailes_name.getText().toString().equals("")) {
			Tools.showTextToast(ParticipationActivity.this, "请输入姓名");
		} else if (ed_chose_people_detailes_age.getText().toString().equals("")) {
			Tools.showTextToast(ParticipationActivity.this, "请输入年龄");
		} else if (ed_chose_people_detailes_phone.getText().toString()
				.equals("")) {
			Tools.showTextToast(ParticipationActivity.this, "请输入电话");
		} else if (ed_chose_people_detailes_phone.length() != 11) {
			Tools.showTextToast(ParticipationActivity.this, "请输入11位电话号码");
		} else if (ed_chose_people_detailes_deta.getText().toString()
				.equals("")) {
			Tools.showTextToast(ParticipationActivity.this, "请输入症状描述");
		} else {
			Intent intent = new Intent();
			intent.setClass(ParticipationActivity.this,
					Dv_PaymentActivity_.class);
			intent.putExtra("name", ed_chose_people_detailes_name.getText()
					.toString());
			intent.putExtra("sex", sex);
			// intent.putExtra("age", ed_chose_people_detailes_age.getText()
			// .toString());
			intent.putExtra("phone", ed_chose_people_detailes_phone.getText()
					.toString());
			intent.putExtra("deta", ed_chose_people_detailes_deta.getText()
					.toString());
			intent.putExtra("AID", AID);
			intent.putExtra("Age", Age);
			
			startActivity(intent);
			finish();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	/**
	 * 设置返回监听
	 */
	@Click(R.id.imbt_chose_people_detailes_back)
	public void back() {
		finish();
	}
}
