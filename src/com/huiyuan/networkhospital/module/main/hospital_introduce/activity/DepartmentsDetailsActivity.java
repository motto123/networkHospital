package com.huiyuan.networkhospital.module.main.hospital_introduce.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.module.BaseActivity;

@EActivity(R.layout.activity_departments_details)
public class DepartmentsDetailsActivity extends BaseActivity {

	@ViewById
	ImageView ivBack;
	
	@ViewById
	TextView tvCourse;
	
	@ViewById
	TextView tvContent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_departments_details);
	}

	@AfterViews
	protected void init(){
		
	}
	
	@Click({R.id.ivBack})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;

		default:
			break;
		}
	}
}
