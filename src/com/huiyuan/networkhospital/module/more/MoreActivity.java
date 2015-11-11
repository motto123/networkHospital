package com.huiyuan.networkhospital.module.more;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.User;
import com.huiyuan.networkhospital.module.main.MainActivity_;
import com.huiyuan.networkhospital.module.usermanager.LoginActivity_;
@EActivity(R.layout.activity_more)
public class MoreActivity extends Activity {
	private User user = new User(MoreActivity.this);  

	@ViewById
	ImageButton ibtnBack;

	@ViewById
	RelativeLayout rlytAboutUs;
	@ViewById
	RelativeLayout rlytFeedback;
	@ViewById
	RelativeLayout rlytCellUs;
	@ViewById
	RelativeLayout rlytShare;
	@ViewById
	RelativeLayout rlytUpdate;
	@ViewById
	RelativeLayout rlytRemoveCache;
	@ViewById
	Button btBackLogin;
	@ViewById
	TextView ivRemoveCacheSee;
	//	@ViewsById({R.id.rlytAboutUs,R.id.rlytFeedback,R.id.rlytCellUs,R.id.rlytShare,R.id.rlytUpdate,R.id.rlytRemoveCache})
	//	RelativeLayout rlytAboutUs,rlytFeedback,rlytCellUs,rlytShare,rlytUpdate,rlytRemoveCache;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_more);
	}

	public void RemoveCache(){
		ivRemoveCacheSee.setVisibility(View.INVISIBLE);
	}

	@Click({R.id.ibtnBack,R.id.rlytAboutUs,R.id.rlytFeedback,R.id.rlytCellUs,R.id.rlytShare,R.id.rlytUpdate,R.id.rlytRemoveCache
		,R.id.btBackLogin})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
			Tools.startActivity(MoreActivity.this, MainActivity_.class);//跳转有问题
			finish();
			break;
		case R.id.rlytAboutUs:
			Tools.startActivity(MoreActivity.this, AbautUsActivity_.class);
			break;
		case R.id.rlytFeedback:
			Tools.startActivity(MoreActivity.this, FeedbackActivity_.class);
			break;
		case R.id.rlytCellUs:
			Tools.startActivity(MoreActivity.this, ContactUsActivity_.class);
			break;
		case R.id.rlytShare:
			Intent intent2 = new Intent(Intent.ACTION_SEND);
			intent2.setType("text/plain");
			intent2.putExtra(Intent.EXTRA_SUBJECT, "知了健康");
			intent2.putExtra(
					Intent.EXTRA_TEXT,
					"我正在用《知了健康》"
//							+ "http://118.112.183.197:9111/imgserver/APK/SportAssistant.apk"
							+"http://www.pgyer.com/apiv1/app/install?aId=56e6569bd933ff88b5f864e9aae7861a&_api_key=f7fcbe282a775f6f4301eb037fcf2535"
							+ "");
			startActivity(Intent.createChooser(intent2, "知了健康"));

			//			Tools.startActivity(MoreActivity.this, ShareActivity_.class);
			break;	
		case R.id.rlytUpdate:
			//			Tools.startActivity(MoreActivity.this, UpdateVersionActivity_.class);
			openDialog("当前版本已经是最新版！");
			break;
		case R.id.rlytRemoveCache:
			//			Tools.startActivity(MoreActivity.this, ClearCacheActivity_.class);
			RemoveCache();
			break;
		case R.id.btBackLogin:
			Intent intent  = new Intent(MoreActivity.this, LoginActivity_.class);
			NApplication.ImgheadBitmap = null;
			user.removePass();
			intent.putExtra("isLogin", "");
			EMChatManager.getInstance().logout(new EMCallBack() {

				@Override
				public void onSuccess() {
					Tools.LogI("退出app和环信");
				}

				@Override
				public void onProgress(int arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(int code, String message) {
					Tools.LogI("退出环信 error/"+code+":"+message);
				}
			});//退出环信聊天服务器  
			((NApplication) this.getApplication()).destoryAllActivity();
			startActivity(intent);
			finish();
			break;

		}
	}

	private void openDialog(String msg) {
		Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("温馨提示")
		.setMessage(msg)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
		.create().show();
	}
}
