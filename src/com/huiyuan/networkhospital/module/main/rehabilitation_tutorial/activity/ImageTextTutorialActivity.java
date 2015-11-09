package com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.module.BaseActivity;
@EActivity(R.layout.activity_image_text_tutorial)
public class ImageTextTutorialActivity extends BaseActivity {
	private String isMyCollect ;

	@ViewById
	ImageButton ibtnImageTextTutorialTitleBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_image_text_tutorial);
		get();
	}

	public void get(){
		Intent intent = getIntent();
		isMyCollect = intent.getStringExtra("isMyCollect");
		Log.e("isMyCollect",isMyCollect+"");
	}
	
//	@Click(R.id.ibtnImageTextTutorialTitleBack)
//	public void Back(){
//
//		if(isMyCollect.equals("false")){
//			Intent intents = new Intent(ImageTextTutorialActivity.this,RehabilitationTutorialActivity_.class);
//			startActivity(intents);
//			finish();
//		}else if (isMyCollect.equals("true")) {
//			Intent intents = new Intent(ImageTextTutorialActivity.this,MyCollectActivity_.class);
//			startActivity(intents);
//			finish();
//		}
//	}
	@Click({R.id.ibtnImageTextTutorialTitleBack})
	protected void skip(View v){
		int i=v.getId();
		switch (v.getId()) {
		case R.id.ibtnImageTextTutorialTitleBack:
//			Tools.startActivity(AlterSucceedActivity.this, AlterPWActivity_.class);
			finish();
			break;
		
		}
	}

}
