package com.huiyuan.networkhospital.module.user;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.huiyuan.networkhospital.R;
@EActivity(R.layout.activity_hospitals_details)
public class HospitalsDetailsActivity extends Activity {
	@ViewById
	ImageButton ibtnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_hospitals_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hospitals_details, menu);
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
	@Click({R.id.ibtnBack})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
//			Tools.startActivity(AlterSucceedActivity.this, AlterPWActivity_.class);
			finish();
			break;
		
		}
	}
}
