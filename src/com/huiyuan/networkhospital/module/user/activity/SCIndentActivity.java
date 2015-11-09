package com.huiyuan.networkhospital.module.user.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.widget.TextView;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.entity.dengzhaojun.ScBean;
import com.huiyuan.networkhospital.module.BaseActivity;

@EActivity(R.layout.activity_indent_detail)
public class SCIndentActivity extends BaseActivity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	protected void init() {
		ScBean indent = (ScBean) getIntent().getSerializableExtra(GlobalConstant.KEY_DATA);
		tvCreateTime.setText(indent.getCreateTime());
		tvIndent.setText(indent.getTitle());
		tvName.setText(NApplication.username);
		tvTel.setText(NApplication.phone);
		tvId.setText(NApplication.IC);
		tvDepartment.setText("教练姓名："+indent.getCName());
		if (NApplication.sex.equals("true")) {
			Sex123.setText("男");
		} else {
			Sex123.setText("女");
		}
		tvTime.setText("教练电话："+indent.getcPhone());//教练电话
		tvCost.setText("订单价格："+indent.getCPrice());
	}

	@Click(R.id.ibtnBack)
	protected void skip(){
		finish();
	}
	
}
