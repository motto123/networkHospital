package com.huiyuan.networkhospital.module.main.get_medicine.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.module.BaseActivity;

@EActivity(R.layout.activity_gm__submit_succeed)
public class Gm_SubmitSucceedActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_gm__submit_succeed);
	}

	@Click({R.id.imbt_gm_submit_succeed_back,R.id.bt_gm_submit_submit})
	public void back(View v) {
		switch (v.getId()) {
		case R.id.imbt_gm_submit_succeed_back:
			finish();
			break;
		case R.id.bt_gm_submit_submit:
			Intent intent = new Intent(Gm_SubmitSucceedActivity.this,EvaluateActivity_.class);
			startActivity(intent);
			finish();
			break;
		}
	}
}
