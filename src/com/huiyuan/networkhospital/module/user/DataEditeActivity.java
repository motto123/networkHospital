package com.huiyuan.networkhospital.module.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_data_edite)
public class DataEditeActivity extends BaseActivity {
	private AsyncHttpResponseHandler change_info_handler, imageup_handler;
	private String IC, name, Sex="0", photo;
	private ProgressDialog dialog;

	@ViewById
	ImageButton ibtnBack;
	@ViewById
	EditText etNameContext;
	@ViewById
	EditText etIdContext;
	@ViewById
	Button btConfirm;
	@ViewById
	CircularImage ivHead;
	@ViewById
	RadioGroup rbtnGroup;
	@ViewById
	RadioButton radioMale;
	@ViewById
	RadioButton radioFemale;
	private InputMethodManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_data_edite);
		Toast.makeText(this.getApplicationContext(), 
				"请填写真实姓名和身份证号码，便于您资料与医院信息一致", 1).show();
		dohandler();
	}

	@AfterViews
	protected void init() {
		// this.getResources().getDrawable(R.id.ic_head_base);
		// ivHead.setImageBitmap();
		if (getIntent() != null) {
			IC = getIntent().getStringExtra("IC");
			name = getIntent().getStringExtra("name");
//			if (getIntent().getStringExtra("Sex").equals("女")) {
//			}
//			Sex = getIntent().getStringExtra("Sex");
			etNameContext.setText(name);
			etIdContext.setText(IC);

		}
		//设置头像
		if (NApplication.ImgheadBitmap != null) {
			ivHead.setImageBitmap(NApplication.ImgheadBitmap);
		}
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 弹出虚拟键盘
		// Tools.popupKeyboard(etTel);
		rbtnGroup.setOnCheckedChangeListener(mChangeRadio);
	}

	private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if (checkedId == radioMale.getId()) {
				// 把mRadio1的内容传到mTextView1
				Sex = "0";
			} else if (checkedId == radioFemale.getId()) {
				// 把mRadio2的内容传到mTextView1
				Sex = "1";
			}
		}
	};

	@Click({ R.id.ibtnBack, R.id.btConfirm, R.id.ivHead })
	protected void skip(View v) {
		switch (v.getId()) {
		case R.id.ibtnBack:
			// Tools.startActivity(DataEditeActivity.this,
			// UserInfoActivity_.class);
			NApplication.headBitmap = null;
			finish();
			break;
		case R.id.btConfirm:
			btConfirmclick();
			break;
		case R.id.ivHead:
			Intent intent = new Intent(DataEditeActivity.this,
					DialogActivity_.class);
			this.startActivity(intent);
			break;
		}
	}

	// 点此空白的地方,自动收回虚拟键盘
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		NApplication.headBitmap = null;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//设置头像
		if (NApplication.headBitmap != null) {
			ivHead.setImageBitmap(PhotoUtil.getHead(DataEditeActivity.this,
					NApplication.headBitmap));
		}
	}

	/**
	 * btConfirm 的点击处理事件，headBitmap有值，上传图片同时修改用户基本信息
	 * headBitmap无值，只是修改用户基本信息
	 */
	private void btConfirmclick() {
		// TODO Auto-generated method stub
		name = etNameContext.getText() + "";
		IC = etIdContext.getText() + "";
		if (etNameContext.getText().length() == 0) {
			Toast.makeText(DataEditeActivity.this, "姓名为空，请填写姓名",
					Toast.LENGTH_SHORT).show();
		} else if ((etIdContext.getText() + "").length() == 0) {
			Toast.makeText(DataEditeActivity.this, "身份证为空，请输入身份证号码",
					Toast.LENGTH_SHORT).show();
		} else if ((etIdContext.getText() + "").length() != 18) {
			Toast.makeText(DataEditeActivity.this, "身份证号码位数不对",
					Toast.LENGTH_SHORT).show();
		} else {
			dialog = ProgressDialog.show(DataEditeActivity.this, "提示", "正在加载中");
			if (NApplication.headBitmap != null) {
				savePicture(NApplication.headBitmap);
			} else {
				changelnfo();
			}
		}
	}

	/**
	 * 登录连接服务器，修改用户基础信息，根据photo是否有值，判断是否需要修改photo
	 */
	private void changelnfo() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (photo != null) {
			params.put("Photo", photo);
			photo = null;
		}
		params.put("act", "updateinfo");
		params.put("id", NApplication.userid);
		params.put("Sex", Sex);
		params.put("name", name);
		params.put("IC", IC);
		client.post(Url.userLogin, params, change_info_handler);
	}

	/**
	 * 上传图片
	 */
	private void savePicture(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] byte1 = baos.toByteArray();

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "imageup");
		params.put("soundtrack", new ByteArrayInputStream(byte1), "aa.jpeg");// aa.jpeg
																				// 好像是随意
		client.post(Url.imageup, params, imageup_handler);
	}

	/**
	 * 连接网络后跳转的handler
	 */
	private void dohandler() {
		/**
		 * 修改信息网络完成后处理事件，判断时候修改完成
		 */
		change_info_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(DataEditeActivity.this, "网络连接错误，请检查网络设置后重试。",
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
						if (jsObject.getString("Data").equals("true")) {
							//headBitmap 上传的图片，设置界面显示的头像图片
							if (NApplication.headBitmap != null) {
								NApplication.ImgheadBitmap = PhotoUtil.getHead(
										DataEditeActivity.this,
										NApplication.headBitmap);
								NApplication.headBitmap = null;
								NApplication.photo = photo;
							}
							NApplication.IC = IC;
							NApplication.username = name;
							
							Intent intent = new Intent(DataEditeActivity.this,
									UserInfoActivity_.class);
							intent.putExtra("IC", IC);
							intent.putExtra("name", name);
							if (Sex.equals("1")) {
								NApplication.sex = "false";
								intent.putExtra("Sex", "女");
							}else {
								NApplication.sex = "true";
								intent.putExtra("Sex", "男");
							}
							startActivity(intent);
							finish();
						} else {
							Toast.makeText(DataEditeActivity.this, "修改失败",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(DataEditeActivity.this, "修改失败",
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
		 * 判断头像是否上传成功，取得头像名字
		 */
		imageup_handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(DataEditeActivity.this, "网络连接错误，请检查网络设置后重试。",
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
				Log.e("图片上传", arg0);

				super.onSuccess(arg0);
			}

		};

	}
}
