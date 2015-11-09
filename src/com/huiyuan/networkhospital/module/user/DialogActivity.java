package com.huiyuan.networkhospital.module.user;

import java.io.File;
import java.io.FileNotFoundException;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.module.BaseActivity;


@EActivity(R.layout.activity_apply_dialog)
public class DialogActivity extends BaseActivity {
	@ViewById
	Button btPicture;
	@ViewById
	Button btPhoto;
	private Bitmap bitmap,bitmap1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_apply_dialog);
	}
	@Click({ R.id.btPicture, R.id.btPhoto })
	protected void skip(View view) {
		switch (view.getId()) {
		case R.id.btPicture:
			getImage();
			break;
		case R.id.btPhoto:
			getPhoto();
			break;
		}
	}
	public void getImage() {
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		/*
		 * 在Activity Action里面有一个“ACTION_GET_CONTENT”字符串常量，
		 * 该常量让用户选择特定类型的数据，并返回该数据的URI.
		 */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		startActivityForResult(intent, 1);
	}

	public void getPhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"workupload.jpg"));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("requestCode", requestCode + "");
		if (resultCode == RESULT_OK && data != null&&requestCode==1) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				Log.v("TestFile",
						"SD card is not avaiable/writeable right now.");
				return;
			}
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			ContentResolver cr = this.getContentResolver();
			try {
				bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
				bitmap1=PhotoUtil.saveHead(DialogActivity.this,bitmap);
//				finish();
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(), e);
			}

		}else if (resultCode == RESULT_OK&&requestCode==2) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				Log.v("TestFile",
						"SD card is not avaiable/writeable right now.");
				return;
			}
			bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/workupload.jpg");
			bitmap1 = PhotoUtil.saveHead(DialogActivity.this,bitmap);
		}
			NApplication.headBitmap=bitmap1;
			finish();
	}

	public static Bitmap getBitmapForFile(String filePath){  
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);  
        return bitmap;  
    }  

}
