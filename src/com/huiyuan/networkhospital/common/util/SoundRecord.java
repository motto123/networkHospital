package com.huiyuan.networkhospital.common.util;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class SoundRecord {

    private static final String TAG = "SoundRecord";
    private MediaRecorder mRecorder = null;
    private MediaPlayer mMediaPlayer = null;
    private Context mContext = null;
    private boolean isRcoeding = false;
    public static final int STOP_SUCCESS = 0;
    public static final int STOP_FAIL = 1;
    public static final int STOP_NOT_START = 2;

    public SoundRecord(Context ctx) {
        this.mContext = ctx;
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 开始录制
     **/
    public void startRecord(String fileName) {
        String path = FileUtil.getAudioStoragePath(mContext, fileName);
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        } else {
            mRecorder.reset();
        }
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(path);
        try {
            mRecorder.prepare();
            mRecorder.start();
            isRcoeding = true;
        } catch (IllegalStateException e) {
            System.out.print(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * 停止录制
     */
    public int stopRecord() {
        if (mRecorder != null && isRcoeding) {
            try {
                mRecorder.stop();
                //mRecorder.reset();
                mRecorder.release();
                isRcoeding = false;
                mRecorder = null;
                return STOP_SUCCESS;
            } catch (Exception e) {
                //e.printStackTrace();
                Log.e(TAG, "Exception:"+e);
                return STOP_FAIL;
            }
        } else if (!isRcoeding) {
            return STOP_NOT_START;
        }
        return STOP_FAIL;
    }

    /**
     * 播放录音
     */
    public void playAudio(String path) {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取录音时长
     */
    public static int getAudioLength(String path) {
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(path);
            mp.prepare();
            return mp.getDuration() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取声音振幅
     * @return
     */
    public double getAmplitude() {
        if (mRecorder != null) {
            try {
                return (mRecorder.getMaxAmplitude());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } catch (Error e) {
                Log.e(TAG, e.getMessage());
            }
            return 0;
        } else {
            return 0;
        }
    }
}
