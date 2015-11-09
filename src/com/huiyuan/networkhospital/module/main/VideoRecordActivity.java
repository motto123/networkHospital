package com.huiyuan.networkhospital.module.main;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.ExceptionUtil;
import com.huiyuan.networkhospital.common.util.FileUtil;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.common.util.VideoRecord;
import com.huiyuan.networkhospital.common.util.VideoRecord.OnMaxDurationListener;
import com.huiyuan.networkhospital.constant.GlobalConstant;

@EActivity
public class VideoRecordActivity extends Activity implements
SurfaceHolder.Callback, OnCheckedChangeListener,
OnMaxDurationListener{

	/*surfaceView 详解
	 * 特性：可以在主线程之外的线程中向屏幕绘图上。这样可以避免画图任务繁重的时候造成主线程阻塞，从而提高了程序的反应速度。
	 * 
	 * 使用步骤：
	 * 继承SurfaceView并实现SurfaceHolder.Callback接口 ----> 
	 * SurfaceView.getHolder()获得SurfaceHolder对象 ---->
	 * SurfaceHolder.addCallback(callback)添加回调函数---->
	 * SurfaceHolder.lockCanvas()获得Canvas对象并锁定画布----> 
	 * Canvas绘画 ---->
	 * SurfaceHolder.unlockCanvasAndPost(Canvas canvas)结束锁定画图，并提交改变，将图形显示。
	 * 
	 * 1.SurfaceHolder,surface的控制器，用来操纵surface。
	 * 处理它的Canvas上画的效果和动画，控制表面，大小，像素等。
	 * 
	 * 2.
	 * 
	 * 
	 */

	@ViewById
	SurfaceView surfaceView;

	@ViewById(R.id.start_or_stop)
	CheckBox mStartOrStop;

	@ViewById(R.id.video_record_cancle)
	Button cancle;

	@ViewById(R.id.video_record_sure)
	Button sure;

	private VideoRecord mVideoRecord;

	private Camera camera;

	private Parameters parameters;

	/**
	 * 是否正在预览
	 */
	private boolean previewRunning;

	//预览的宽度
	private int previewWidth = 640;
	//预览的高度
	private int previewHeight = 480;
	//帧数
	private int defaultFps = 15;

	private String path;

	private SurfaceHolder mSurfaceHolder;

	private int defaultBitrate = 300 * 1000;
	//视频缩略图地址
	private String smallPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//禁止屏幕休眠
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏

		//		设置竖屏代码：setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏 
		setContentView(R.layout.activity_video_record);


	}

	@SuppressWarnings("deprecation")
	@AfterViews
	protected void init(){
		// 绑定SurfaceView，取得SurfaceHolder对象
		mSurfaceHolder=surfaceView.getHolder();
		mVideoRecord=new VideoRecord(this);
		// SurfaceHolder加入回调接口
		mSurfaceHolder.addCallback(this);
		// mSurfaceHolder.setFixedSize(176, 144); // 预览大小設置
		/*設置顯示器類型，setType必须设置
		 * 这段代码是说在Android 高版本上已经不推荐使用了 ，但是如果你要兼容低版本
		 * （如Android 2.3或以下版本）还是要加上这段代码，不然播放时 只会有声音 没有图像。
		 */
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mStartOrStop.setOnCheckedChangeListener(this);
		mVideoRecord = new VideoRecord(this);//小电影工具类
		mVideoRecord.setOnMaxDurationListener(this);


	}


	@Click({R.id.video_record_sure,R.id.video_record_cancle})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.video_record_sure://将路径传回上一个activity中去
			Intent intent = new Intent();
//			Uri uri = Uri.parse(path);
//			intent.setData(uri);
			intent.putExtra(GlobalConstant.KEY_DATA, new String[]{path,smallPath});
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.video_record_cancle:
			FileUtil.deleteFile(path);//删除录制的视频文件
			finish();
			break;
		}
	}

	/**
	 * 初始相机参数并自动对焦
	 */
	public void autoFocus() {
		if (camera == null)
			return;
		initCamera();// 实现相机的参数 初始化
		camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
	}

	/**
	 *  相机参数的初始化设置
	 */
	public void initCamera() {
		parameters = camera.getParameters();
		Log.i("test", "ver:" + VERSION.SDK_INT);

		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);// 1连续对焦
		// setDispaly(parameters,mCamera);
		camera.setParameters(parameters);
		camera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
	}

	private void handleSurfaceChanged() {
		if (camera == null) {
			finish();//没有开启相机成功销毁当前页面
			return;
		}
		boolean hasSupportRate = false;
		@SuppressWarnings("deprecation")
		//////////////////
		List<Integer> supportedPreviewFrameRates = camera.getParameters()
		.getSupportedPreviewFrameRates();
		if (supportedPreviewFrameRates != null
				&& supportedPreviewFrameRates.size() > 0) {
			Collections.sort(supportedPreviewFrameRates);
			for (int i = 0; i < supportedPreviewFrameRates.size(); i++) {
				int supportRate = supportedPreviewFrameRates.get(i);
				if (supportRate == defaultFps) {
					hasSupportRate = true;
				}
			}
			if (!hasSupportRate) {
				defaultFps = supportedPreviewFrameRates.get(0);
			}
		}
		// 获取摄像头的所有支持的分辨率
		List<Camera.Size> resolutionList = camera.getParameters().getSupportedVideoSizes();
		if (resolutionList != null && resolutionList.size() > 0) {
			Collections.sort(resolutionList, new Comparator<Camera.Size>() {
				@Override
				public int compare(Size arg0, Size arg1) {
					return arg0.width*arg0.height > arg1.width*arg1.height ? 1 :0;
				}
			});
			Camera.Size previewSize = null;
			boolean hasSize = false;
			// 如果摄像头支持640*480，那么强制设为640*480
			for (int i = 0; i < resolutionList.size(); i++) {
				Size size = resolutionList.get(i);
				if (size != null && size.width == previewWidth && size.height == previewHeight) {
					hasSize = true;
					break;
				}
			}
			// 如果不支持设为中间的那个
			if (!hasSize) {
				int mediumResolution = resolutionList.size() / 2;
				if (mediumResolution >= resolutionList.size())
					mediumResolution = resolutionList.size() - 1;
				previewSize = resolutionList.get(mediumResolution);
				previewWidth = previewSize.width;
				previewHeight = previewSize.height;
			}
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			UUID uuid = UUID.randomUUID();
			String guid = uuid.toString().replace("-", "");
			path=FileUtil.getVideoStoragePath(this, guid);
			mVideoRecord=new VideoRecord(this);
			mVideoRecord.startRecord(mSurfaceHolder, camera, path, previewWidth, previewHeight, defaultFps, defaultBitrate);
			mStartOrStop.setText(R.string.stop);
			mStartOrStop.setBackgroundColor(Color.rgb(255, 0, 0));
		}else{
			int result=mVideoRecord.stopRecord();
			if(VideoRecord.STOP_FAIL==result){
				FileUtil.deleteFile(path);
				Toast.makeText(NApplication.context, "录制时间太短请重新录制", 0).show();
				finish();
			}
			//录制成功生成视频缩略图,并保持到sd中
			int width = getResources().getDimensionPixelSize(R.dimen.chat_msg_thumbnail_height);
			int height = getResources().getDimensionPixelSize(R.dimen.chat_msg_thumbnail_width);
			Bitmap bitmap=PhotoUtil.getVideoThumbnail(path, width, height, MediaStore.Images.Thumbnails.MICRO_KIND);
			if(null==bitmap){
				return;
			}
			smallPath = FileUtil.videoPath2SmallPath(this, path);
			PhotoUtil.saveToSDcard(bitmap, smallPath);
			sure.setVisibility(View.VISIBLE);
			cancle.setVisibility(View.VISIBLE);
			mStartOrStop.setVisibility(View.INVISIBLE);
		}

	}

	//在创建时激发，一般在这里调用画图的线程。
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera=Camera.open();
		if(null!=camera){
			autoFocus();
		}else{
			Toast.makeText(NApplication.context, "摄像头打开失败", 0).show();
			finish();
		}
	}

	//在surface的大小发生改变时激发      //当SurfaceView/预览界面的格式和大小发生改变时，该方法被调用
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (previewRunning) {
			camera.stopPreview();
		}
		autoFocus();
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
			handleSurfaceChanged();
			previewRunning = true;
		} catch (IOException e) {
			Log.e("chat", e.getMessage());
			ExceptionUtil.exceptionHandle(e);
		}

	}

	//销毁时激发，一般在这里将画图的线程停止、释放。
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		previewRunning = false;
		if (camera != null) {
			camera.setPreviewCallback(null);//！！这个必须在前，不然退出出错
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}



	//最录制大时长
	@Override
	public void onMaxDuation() {
		mVideoRecord.stopRecord();
		int width = getResources().getDimensionPixelSize(R.dimen.chat_msg_thumbnail_width);
		int height = getResources().getDimensionPixelSize(R.dimen.chat_msg_thumbnail_height);
		Bitmap bitmap = PhotoUtil.getVideoThumbnail(path, width, height, MediaStore.Images.Thumbnails.MICRO_KIND);
		String smallPath = FileUtil.videoPath2SmallPath(this, path);
		PhotoUtil.saveToSDcard(bitmap, smallPath);
		sure.setVisibility(View.VISIBLE);
		cancle.setVisibility(View.VISIBLE);
		mStartOrStop.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onBackPressed() {
		FileUtil.deleteFile(path);//删除视频文件
		previewRunning = false;
		if (camera != null) {
			camera.setPreviewCallback(null);//！！这个必须在前，不然退出出错
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		previewRunning = false;
		if (camera != null) {
			camera.setPreviewCallback(null);//！！这个必须在前，不然退出出错
			camera.stopPreview();
			camera.release();
			camera = null;
		}
		super.onDestroy();
	}
}
