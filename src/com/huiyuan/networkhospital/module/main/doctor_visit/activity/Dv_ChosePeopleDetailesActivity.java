package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.huiyuan.networkhospital.module.BaseActivity;
/**
 * 发起活动设置人员详情
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_dv__chose_people_detailes_all)
public class Dv_ChosePeopleDetailesActivity extends BaseActivity {
	@ViewById
	ImageButton imbt_chose_people_detailes_back;

	private InputMethodManager manager;
	@ViewById
	Button bt_chose_people_detailes_deta_sum;
	@ViewById
	EditText ed_chose_people_detailes_name;
	@ViewById
	EditText ed_chose_people_detailes_age;
	@ViewById
	EditText ed_chose_people_detailes_phone;
	@ViewById
	EditText ed_chose_people_detailes_deta;
	@ViewById
	RadioGroup rbtnGroup;
	@ViewById
	RadioButton radioMale;
	@ViewById
	RadioButton radioFemale;
	String sex = "true";

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
		rbtnGroup.setOnCheckedChangeListener(mChangeRadio);

	}
	/**
	 * 设置监听
	 */
	private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if (checkedId == radioMale.getId()) {
				// 把mRadio1的内容传到mTextView1
				sex = "true";
			} else if (checkedId == radioFemale.getId()) {
				// 把mRadio2的内容传到mTextView1
				sex = "false";
			}
		}
	};
	/**
	 * 设置监听传递一些信息到上个页面
	 */
	@Click(R.id.bt_chose_people_detailes_deta_sum)
	public void sub() {
		// 传递一些信息到上个页面

		if (ed_chose_people_detailes_name.getText().toString().equals("")) {
			Tools.showTextToast(Dv_ChosePeopleDetailesActivity.this, "请输入姓名");
		} else if (ed_chose_people_detailes_age.getText().toString().equals("")) {
			Tools.showTextToast(Dv_ChosePeopleDetailesActivity.this, "请输入年龄");
		} else if (ed_chose_people_detailes_phone.getText().toString()
				.equals("")) {
			Tools.showTextToast(Dv_ChosePeopleDetailesActivity.this, "请输入电话号码");
		} else if (ed_chose_people_detailes_phone.length() != 11) {
			Tools.showTextToast(Dv_ChosePeopleDetailesActivity.this,
					"请输入11位电话号码");
		} else

		if (ed_chose_people_detailes_deta.getText().toString().equals("")) {
			Tools.showTextToast(Dv_ChosePeopleDetailesActivity.this, "请输入症状描述");
		} else {
			final Intent intent = getIntent();
			// Dv_people a=new Dv_people();
			intent.putExtra("name", ed_chose_people_detailes_name.getText()
					.toString());
			intent.putExtra("sex", sex);
			intent.putExtra("age", ed_chose_people_detailes_age.getText()
					.toString());
			intent.putExtra("phone", ed_chose_people_detailes_phone.getText()
					.toString());
			intent.putExtra("deta", ed_chose_people_detailes_deta.getText()
					.toString());
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("people", a);
			// intent.putExtras(bundle);
			Log.e("", "" + intent);
			setResult(1, intent);
			finish();
		}
	}
	/**
	 * 设置监听取消软键盘
	 */
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
	 * 设置监听
	 */
	@Click(R.id.imbt_chose_people_detailes_back)
	public void back() {
		finish();
	}
}
