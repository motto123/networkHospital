package com.huiyuan.networkhospital.module.user;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.module.BaseActivity;
@EActivity(R.layout.activity_alter_pw)
public class AlterPWActivity extends BaseActivity {
	@ViewById
	ImageButton ibtnBack;
	
	@ViewById
	Button btConfirmChange;
	
	@ViewById
	EditText etOldPassword;
	
	@ViewById
	EditText etNewPassword;
	
	@ViewById
	EditText etConfirmPassword;
	
	private InputMethodManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((NApplication)this.getApplication()).addActivity(this);
//		setContentView(R.layout.activity_alter_pw);
	}

	
	@AfterViews
	protected void init() {
		manager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//弹出虚拟键盘
		Tools.popupKeyboard(etOldPassword);
	}
	
	@Click({R.id.ibtnBack,R.id.btConfirmChange})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
//			Tools.startActivity(AlterPWActivity.this, MyActivity_.class);
			finish();
			break;
		case R.id.btConfirmChange:
			Tools.startActivity(AlterPWActivity.this, AlterSucceedActivity_.class);
			break;
		
		}
	}
	
	//点此空白的地方,自动收回虚拟键盘
		@Override 
		public boolean onTouchEvent(MotionEvent event) {  
			// TODO Auto-generated method stub   
			if(event.getAction() == MotionEvent.ACTION_DOWN){  
			     if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){  
			       manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
			     }  
			  }  
			return super.onTouchEvent(event);  
		}
	
}
