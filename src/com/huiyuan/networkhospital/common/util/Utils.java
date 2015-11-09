package com.huiyuan.networkhospital.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.huiyuan.networkhospital.entity.ChatMsgEntity;

public class Utils {

    public final static boolean DEBUG = false;
    public final static String TAG = "Wangjiaguai";

    public static boolean isTopActivity(Context ctx, String className) {
        boolean isTop = false;
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        if (cn.getClassName().contains(className)) {
            isTop = true;
        }
        return isTop;
    }

    public static void LogI(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    public static void LogD(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void LogE(String msg) {
        if (DEBUG)
            Log.e(TAG, msg);
    }


    public static void LogI(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    public static void LogD(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void LogE(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    /**
     * 根据消息内容和消息类型获取消息内容提示
     * 
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(ChatMsgEntity message, Context context) {
        String digest = "";
        switch (message.type) {
        case Constant.MSG_TYPE_PICTURE: // 图片消息
//            digest = getString(context, R.string.chat_message_picture);
            break;
        case Constant.MSG_TYPE_VOICE:// 语音消息
//            digest = getString(context, R.string.chat_message_voice);
            break;
        case Constant.MSG_TYPE_VIDEO: // 视频消息
//            digest = getString(context, R.string.chat_message_vedio);
            break;
        case Constant.MSG_TYPE_TEXT: // 文本消息
            digest = message.content;
            break;
        default:
            System.err.println("error, unknow type");
            return "";
        }

        return digest;
    }

    public static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }

    public static byte[] string2GBKbyte(String s) {
        byte[] b = null;
        try {
            b = s.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static String byte2GBkString(byte[] b) {
        String s = null;
        try {
            s = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 
     * @param pxValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int px2dip(Context context, float pxValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param dipValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int dip2px(Context context, float dipValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }
    public static byte[] isToString(InputStream is){
        int MaxLen = 1024*8;
        byte cache1[] = new byte[MaxLen];
        byte readArr[] = new byte[1024 * 2];
        int readlen = 0;
        int len = 0;
        try {
            while((readlen = is.read(readArr)) != -1){
                if((len + readlen) >=MaxLen){
                    byte temp[] = new byte[MaxLen];
                    System.arraycopy(cache1, 0, temp, 0, cache1.length);
                    MaxLen = MaxLen*2;
                    cache1 = new byte[MaxLen];
                    System.arraycopy(temp, 0, cache1, 0, temp.length);
                }
                System.arraycopy(readArr, 0, cache1, len, readlen);
                len = len + readlen;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte resultArr[] = new byte[len];
        System.arraycopy(cache1, 0, resultArr, 0, len);
        return resultArr;
        
    }

    /**
     * @param cxt
     * @param pid
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
