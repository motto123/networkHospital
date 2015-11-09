package com.huiyuan.networkhospital.module.user;

import org.androidannotations.annotations.EActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.module.BaseActivity;
@EActivity(R.layout.activity_system_info_details)
public class SystemInfoDetailsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_system_info_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.system_info_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
