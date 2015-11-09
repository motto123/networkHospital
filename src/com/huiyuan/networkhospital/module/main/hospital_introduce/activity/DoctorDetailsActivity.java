package com.huiyuan.networkhospital.module.main.hospital_introduce.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.ExceptionUtil;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.registration.activity.R_PaymentActivity_;
/**
 * 
 * @ClassName:  DepartmentsDetailsActivity   
 * @Description:TODO医院信息展示之查看医生详情
 * @author: 邓肇均
 * @date:   2015年10月21日 下午4:58:33   
 *
 */
@EActivity(R.layout.activity_doctor_details)
public class DoctorDetailsActivity extends BaseActivity {

	@ViewById
	TextView tvCourse2;

	@ViewById
	TextView tvLevel2;

	@ViewById
	TextView tvDuty;

	@ViewById
	TextView tvName2;

	@ViewById
	TextView tvIntroduce2;

	@ViewById
	TextView tvSchedule;

	@ViewById
	ImageView ivBack;
	@ViewById
	ImageView ivMore1;

	@ViewById
	Button btPay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_doctor_details);
	}

	@SuppressWarnings("deprecation")
	@AfterViews
	protected void init() {
		ivMore1.setBackgroundDrawable(DoctorDetailsActivity.this.getResources().getDrawable(
				R.drawable.head_7190));

	}

	@Click({ R.id.ivBack, R.id.btPay })
	protected void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.btPay:
			// 联网请求
			Intent intent = new Intent(this, R_PaymentActivity_.class);
			intent.putExtra(GlobalConstant.KEY_DATA, "预约医生");
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			String mark = getIntent().getStringExtra(GlobalConstant.KEY_DATA);
			if (mark.equals(GlobalConstant.MAKE)) {
				btPay.setVisibility(View.VISIBLE);
			} else {
				btPay.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			ExceptionUtil.exceptionHandle(e);
		}
	}
}
