package com.huiyuan.networkhospital.module.user;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.constant.User;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.usermanager.LoginActivity_;
@EActivity(R.layout.activity_alter_succeed)
public class AlterSucceedActivity extends BaseActivity {
	private User user = new User(AlterSucceedActivity.this);  
	
	@ViewById
	ImageButton ibtnBack;
	@ViewById
	Button ibtnBackLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((NApplication)this.getApplication()).addActivity(this);
//		setContentView(R.layout.activity_alter_succeed);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alter_succeed, menu);
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
	@Click({R.id.ibtnBack,R.id.ibtnBackLogin})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
//			Tools.startActivity(AlterSucceedActivity.this, AlterPWActivity_.class);
			finish();
			break;
		case R.id.ibtnBackLogin:
			Intent intent = new Intent();
			intent.setClass(AlterSucceedActivity.this, LoginActivity_.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			user.removePass();
			intent.putExtra("isLogin", "");
			startActivity(intent);
			finish();
			break;
		
		}
	}
}
