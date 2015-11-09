package com.huiyuan.networkhospital.module;

import com.easemob.applib.controller.HXSDKHelper;
import com.huiyuan.networkhospital.NApplication;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT|ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		super.onCreate(savedInstanceState);
		((NApplication) this.getApplication()).addActivity(this);
	}
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // land do nothing is ok
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // port do nothing is ok
            }
    }
	 @Override
	    protected void onResume() {
	        super.onResume();
	        // onresume时，取消notification显示
	        HXSDKHelper.getInstance().getNotifier().reset();
	        
	        // umeng
	        MobclickAgent.onResume(this);
	    }

	    @Override
	    protected void onStart() {
	        super.onStart();
	        // umeng
	        MobclickAgent.onPause(this);
	    }


	    /**
	     * 返回
	     * 
	     * @param view
	     */
	    public void back(View view) {
	        finish();
	    }
}