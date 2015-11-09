package com.huiyuan.networkhospital.common.util;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.util.Log;
import android.view.SurfaceHolder;

public class VideoRecord implements OnErrorListener, OnInfoListener{

	private static final String TAG = "VideoRecord";
	private Context mContext = null;
	private Camera mCamera = null;

	private MediaRecorder mediaRecorder;
	/**
	 * 视频录制的最大时长
	 */
	private final int maxDurationInMs = 60000;
	private final long maxFileSizeInBytes = 500000;
	private final int videoFramesPerSecond = 15;
	private OnMaxDurationListener mOnMaxDurationListener = null;
	/**
	 * 停止失败
	 * 原因:录制时间太短
	 */
	public static final int STOP_FAIL = 0;
	/**
	 * 停止成功
	 */
	public static final int STOP_SUCCESS = 1;
	/**
	 * 没有启动
	 */
	public static final int STOP_NOT_START = 2;

	public VideoRecord(Context mContext) {
		this.mContext = mContext;
	}

	/**
	 * 开启录制
	 * @param mSurfaceHolder surfaceView
	 * @param mCamera 摄像头
	 * @param outPath 视频输出路径
	 * @param width 视频分辨率
	 * @param heigth 视频分辨率
	 * @param fps 帧数
	 * @param bitRate 比特率
	 * 
	 * @return
	 */
	public boolean startRecord(SurfaceHolder mSurfaceHolder, Camera mCamera, String outPath, int width, int heigth, int fps, int bitRate) {
		try {
			this.mCamera = mCamera;
			mCamera.unlock();//开启摄像机
			if (mediaRecorder == null) {
				mediaRecorder = new MediaRecorder();
			} else {
				mediaRecorder.reset();//重置媒体记录机
			}

			mediaRecorder.setCamera(mCamera);
			//mediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制  
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//给媒体记录机设置麦克风
			mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//给媒体记录机设置摄像头
			// 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4/* DEFAULT */);
			//视频录制最大时长
			mediaRecorder.setMaxDuration(maxDurationInMs);
			// 设置视频文件输出的路径
			mediaRecorder.setOutputFile(outPath);
			//设置声音解码器
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC/*DEFAULT*/);
			// 设置录制的视频编码h263 h264
			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264/*DEFAULT*/);
			// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
			mediaRecorder.setVideoFrameRate(videoFramesPerSecond);
			Tools.LogD("startRecord width="+width+", heigth="+heigth);
			// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
			mediaRecorder.setVideoSize(width, heigth);
			//通过surface预览视频
			mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

			//mediaRecorder.setMaxFileSize(maxFileSizeInBytes);
			mediaRecorder.setVideoEncodingBitRate(bitRate);

			mediaRecorder.prepare();//准备录制
			mediaRecorder.start();//准备播放
			mediaRecorder.setOnErrorListener(this);
			mediaRecorder.setOnInfoListener(this);

			return true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			release();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			release();
			return false;
		}
	}

	/**
	 * 关闭媒体
	 * @return
	 */
	public int stopRecord() {
		int result = STOP_FAIL;
		if (mediaRecorder != null) {
			try {
				mediaRecorder.stop();
				result = STOP_SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				result = STOP_FAIL;
			}
		}
		if (mCamera != null) {
			mCamera.lock();
			mCamera.stopPreview();
		}
		//release();
		return result;
	}

	/**
	 * 释放录制视频所需的资源
	 */
	public void release() {
		if (mediaRecorder != null) {
			mediaRecorder.setOnErrorListener(null);
			mediaRecorder.setOnInfoListener(null);
		}
		// 如果camera不为null ,释放摄像头
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		switch (what) {
		case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
			Log.d(TAG, "MEDIA_RECORDER_INFO_UNKNOWN");
			break;
		case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
			Log.d(TAG, "MEDIA_RECORDER_INFO_MAX_DURATION_REACHED");
			if (mOnMaxDurationListener != null) {
				mOnMaxDurationListener.onMaxDuation();
			}
			break;
		case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
			Log.d(TAG, "MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED");
			break;
		}
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		if (what == MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN) {
			Log.d(TAG, "MEDIA_RECORDER_ERROR_UNKNOWN");
		}
	}

	public void setOnMaxDurationListener(OnMaxDurationListener listener) {
		this.mOnMaxDurationListener = listener;
	}

	public interface OnMaxDurationListener {
		void onMaxDuation();
	}
}
