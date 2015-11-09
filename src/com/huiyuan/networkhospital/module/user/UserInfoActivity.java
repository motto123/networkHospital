package com.huiyuan.networkhospital.module.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.constant.User;
import com.huiyuan.networkhospital.entity.UserInfo;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
@EActivity(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity {
	private String IC,name,Sex,phone,pass,photo;
	private AsyncHttpResponseHandler user_info_handler,change_info_handler,imageup_handler;
	private UserInfo userInfo = null;
	private ProgressDialog dialog;

	@ViewById
	ImageButton ibtnBack;
	@ViewById
	ImageButton ibtnHead;
	@ViewById
	TextView tvNameContext;
	@ViewById
	TextView tvIdContext;
	@ViewById
	TextView tvTelContext;
	@ViewById
	TextView tvSex;
	@ViewById
	CircularImage ivHead;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_user_info);
		//获取xml文件中的pass，phone，获取用户信息
		User user = new User(UserInfoActivity.this);
		pass = user.getPass(); 
		phone = user.getPhone();
		dohandler();
		userlnfo();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//设置头像
		if (NApplication.ImgheadBitmap!=null) {
			ivHead.setImageBitmap(NApplication.ImgheadBitmap);
		}
		//上传头像
		if (NApplication.headBitmap!=null) {
			dialog = ProgressDialog.show(UserInfoActivity.this,"提示", "正在加载中");
			savePicture(NApplication.headBitmap);
		}
		Intent intent = getIntent();
		IC = intent.getStringExtra("IC");
		name = intent.getStringExtra("name");
		Sex = intent.getStringExtra("Sex");
		if (IC != null) {
			tvNameContext.setText(name);
			tvIdContext.setText(IC);
			tvTelContext.setText(phone);
			tvSex.setText(Sex);
			IC = null;
		}
	}
	@Click({R.id.ibtnBack,R.id.ibtnHead,R.id.ivHead})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
			//			Tools.startActivity(UserInfoActivity.this, MyActivity_.class);
			finish();
			break;
		case R.id.ibtnHead:
			//修改信息界面信息显示来源
			Intent intent1 = new Intent(UserInfoActivity.this, DataEditeActivity_.class);
			intent1.putExtra("name", userInfo.getName());
			intent1.putExtra("Sex", isSex(userInfo.getSex()));
			intent1.putExtra("IC", userInfo.getIC());
			startActivity(intent1);
			break;
		case R.id.ivHead:
			Intent intent = new Intent(UserInfoActivity.this,DialogActivity_.class);
			this.startActivity(intent);
			break;

		}
	}
/**
 * 判断男女
 */
	private String isSex(String userSex) {
		// TODO Auto-generated method stub
		String sex = null;
		if (userSex.equals("false")) {
			sex = "女";
		}else if(userSex.equals("true")){
			sex = "男";
		}
		return sex;
	}



	/**
	 * 登录，获取用户信息
	 */
	private void userlnfo() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act","login");
		params.put("phone", phone);
		params.put("pass", pass);
		params.put("equipment", NApplication.device_token);
		client.post(Url.userLogin, params, user_info_handler);
	}
	/**
	 *	更改photo名字
	 */
	private void changelnfo() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("Photo",photo);
		params.put("act","updateinfo");
		params.put("ID", NApplication.userid);
		client.post(Url.userLogin, params, change_info_handler);
	}
	/**
	 * 上传图片
	 */
	private void savePicture(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] byte1=baos.toByteArray();

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act","imageup");
		params.put("soundtrack", new ByteArrayInputStream(byte1), "aa.jpeg");//aa.jpeg 好像是随意
		client.post(Url.imageup, params, imageup_handler);
	}
	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 获取用户信息，并显示到界面上
		 */
		user_info_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(UserInfoActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("登录", arg0);
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("1")) {
						Gson gson = new Gson();
						userInfo = gson.fromJson(jsObject.getString("Data"), UserInfo.class);
						tvNameContext.setText(userInfo.getName());
						tvIdContext.setText(userInfo.getIC());
						tvTelContext.setText(phone);
						tvSex.setText(isSex(userInfo.getSex()));
					}else {
						Toast.makeText(UserInfoActivity.this, "查看失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				super.onSuccess(arg0);
			}

		};
		/**
		 * 修改服务器头像名称，并把要显示格式头像保存
		 */
		change_info_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(UserInfoActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("登录", arg0);
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("0")) {
						if(jsObject.getString("Data").equals("true")){
							NApplication.ImgheadBitmap = PhotoUtil.getHead(UserInfoActivity.this, NApplication.headBitmap);
							NApplication.headBitmap=null;
							ivHead.setImageBitmap(NApplication.ImgheadBitmap);
							NApplication.photo = photo;
							Toast.makeText(UserInfoActivity.this, "头像上传成功",
									Toast.LENGTH_SHORT).show();
						}else {
							Toast.makeText(UserInfoActivity.this, "头像修改失败",
									Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(UserInfoActivity.this, "头像修改失败",
								Toast.LENGTH_SHORT).show();
					}
					if (dialog != null) {
						dialog.dismiss();
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				super.onSuccess(arg0);
			}

		};
		/**
		 * 判断图片上传成功没有
		 */
		imageup_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(UserInfoActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("0")) {
						photo = jsObject.getString("Data");
						changelnfo();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("图片上传",arg0);
				
				super.onSuccess(arg0);
			}

		};

	}
}
