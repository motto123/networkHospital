package com.huiyuan.networkhospital.module.main.registration.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.module.main.MainActivity_;

/**
 * 
 * @ClassName: R_PaymentActivity
 * @Description:TODO预约挂号主界面
 * @author: 邓肇均
 * @date: 2015年10月21日 下午5:08:34
 *
 */
@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends Activity {
	@ViewById
	ImageButton ibtnBack;

	@ViewById
	Button btDepartment;

	@ViewById
	Button btDoctor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_registration);
	}

	@Click({ R.id.ibtnBack, R.id.btDepartment, R.id.btDoctor, })
	protected void skip(View v) {
		switch (v.getId()) {
		case R.id.ibtnBack:
			// Tools.startActivity(AlterPWActivity.this, MyActivity_.class);
			Tools.startActivity(this, MainActivity_.class);
			finish();
			break;
		case R.id.btDepartment:
			Tools.startActivity(this, R_Myorder_.class);
			break;
		case R.id.btDoctor:
			Tools.startActivity(this, DoctorList_.class);
			break;

		}
	}
	@Override
	public void onBackPressed() {
		Tools.startActivity(this, MainActivity_.class);
		super.onBackPressed();
	}
}
