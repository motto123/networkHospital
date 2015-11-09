package com.huiyuan.networkhospital.module.main.registration.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.entity.dengzhaojun.R_PBean;

@EActivity(R.layout.activity_register_detail)
public class RegisterDetailActivity extends Activity {
	@ViewById
	TextView tvCreateTime;
	@ViewById
	TextView tvIndent;
	@ViewById
	TextView tvName;
	@ViewById
	TextView tvTel;
	@ViewById
	TextView tvId;
	@ViewById
	TextView tvDepartment;
	@ViewById
	TextView tvCost;
	@ViewById
	TextView Sex123;
	@ViewById
	TextView tvTime;
	@ViewById
	TextView tvReason;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	protected void init() {
		R_PBean indent = (R_PBean) getIntent().getSerializableExtra(GlobalConstant.KEY_DATA);
		tvCreateTime.setText(indent.getCreateTime());
		tvIndent.setText(indent.getNumber());
		tvName.setText(indent.getName());
		tvTel.setText(indent.getPhone());
		tvId.setText(indent.getIC());
		tvDepartment.setText(indent.getDname());
		if (NApplication.sex.equals("true")) {
			Sex123.setText("男");
		} else {
			Sex123.setText("女");
		}
		tvTime.setText(indent.getRTime());
		tvCost.setText(indent.getPrice());
		if(null != indent.getReason()){
			tvReason.setVisibility(View.VISIBLE);
			tvReason.setText(indent.getReason());
		}
	}

	@Click(R.id.ibtnBack)
	protected void skip(){
		finish();
	}
	
}
