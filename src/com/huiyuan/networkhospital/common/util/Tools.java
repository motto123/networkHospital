package com.huiyuan.networkhospital.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.module.huanxin.activity.ChatMainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class Tools {

	public static int count;

	private static boolean DEBUG = NApplication.isRelease;

	private static String TAG = "networkhospital_user";

	/**
	 * 将文件写到指定的sdCard路径
	 * 
	 * @param context
	 * @param path
	 *            文件路径
	 * @param fileName
	 *            文件名字
	 * @param fileData
	 *            文件数据
	 */
	public static void writeToSdcard(Context context, String path,
			String fileName, byte[] fileData) {
		FileOutputStream fileOutputStream = null;
		try {

			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				File filePath = new File(path);
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				File file = new File(path, fileName);
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(fileData);
				fileOutputStream.flush();
			}
		} catch (Exception e) {
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 通过文件路径从sdCard中读出文件数据
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件数据byte[]
	 */
	public static byte[] readFileFromSdcard(String path) {
		byte[] data = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(path);
			int size = fileInputStream.available();
			data = new byte[size];
			fileInputStream.read(data);
		} catch (Exception e) {
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception e2) {
			}
		}
		return data;
	}

	private static ProgressDialog progressDialog = null;
	private static Toast toast = null;

	/**
	 * 显示方形进度框
	 * 
	 * @param activity
	 * @param msg
	 */
	public static ProgressDialog showProgressDialog(Activity activity,
			String msg) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(activity);
			// 在屏幕上单击dialog不会消失
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage(msg);
			progressDialog.show();
		}
		return progressDialog;
	}

	/**
	 * 关闭方形进度框
	 */
	public static void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.cancel();
			progressDialog = null;
			System.gc();
		}
	}

	/**
	 * 获得应用的当前版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getCurrentVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					packageName, 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得系统当前时间
	 * 
	 * @return
	 */
	public static String getTime() {
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(currentTime));
	}

	/**
	 * 判断当前时间是否大于7天
	 * @param time  聊天记录的时间
	 * @return
	 */
	public static boolean matching(String time){
		boolean b = true;
		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(5, 7));
		int day = Integer.parseInt(time.substring(8, 10));
		String nowTime=Tools.getTime();
		int yearNow = Integer.parseInt(nowTime.substring(0, 4));
		int monthNow = Integer.parseInt(nowTime.substring(5, 7));
		int dayNow = Integer.parseInt(nowTime.substring(8, 10));
		if(year != yearNow){
			return false;
		}
		if(month != monthNow){
			if(monthNow == month+1){
				if((dayNow + 30-13) >day){
					return false;
				}
			}
			return b;
		}
		if((dayNow-13) > day){
			return false;
		}
		return b;
	}
	
	/**
	 * 启动闹钟
	 * 
	 * @param context
	 */
	public static void startClock(Context context) {
		Intent intent1 = new Intent();
		intent1.setAction(AlarmClock.ACTION_SET_ALARM);
		context.startActivity(intent1);
	}

	/**
	 * activity不带参数跳转
	 * 
	 * @param packageContext
	 * @param cls
	 */
	public static void startActivity(Context packageContext, Class cls) {
		packageContext.startActivity(new Intent(packageContext, cls));
	}

	/**
	 * 
	 * @param context
	 * @param msg
	 */
	@SuppressWarnings("unused")
	public static void showTextToast(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	/**
	 * 弹出虚拟键盘
	 * 
	 * @param editText
	 */
	public static void popupKeyboard(final EditText editText) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				InputMethodManager inputManager = (InputMethodManager) editText
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(editText, 0);
			}
		}, 398);
	}

	/**
	 * 判断时间是否相等
	 * 
	 * @param str
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Boolean dealString(String string, String year, String month,
			String day, String hour, String minute) {
		Boolean b = false;
		String s = string.substring(0, 4);
		String s1 = string.substring(5, 7);
		String s2 = string.substring(8, 10);
		String s3 = string.substring(11, 13);
		String s4 = string.substring(14, 16);
		b = year.equals(s);
		if (!b) {
			return b;
		}
		b = month.equals(deal(s1));
		if (!b) {
			return b;
		}
		b = day.equals(deal(s2));
		if (!b) {
			return b;
		}
		b = hour.equals(deal(s3));
		if (!b) {
			return b;
		}
		b = minute.equals(deal(s4));
		return b;
	}

	/**
	 * 从系统获得的时间,数字小于10,格式为 1 ,服务器的时间数据格式为 01,把服务器的时间格式改为 1
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String deal(String str) {
		String s = str.substring(1, 2);
		Boolean b = str.substring(0, 1).equals("0");
		if (b) {
			str = s;
		}
		return str;
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
	 * 进入环信聊天
	 * 
	 * @param packageContext
	 * @param username
	 *            对方的手机号，请在手机号前加标识符
	 * @param toChatName
	 *            对方的名字
	 */
	public static void comeinHXChat(Context packageContext, String tousername,
			String toChatName) {
		packageContext.startActivity(new Intent(packageContext,
				ChatMainActivity.class).putExtra("userId", tousername)
				.putExtra("toChatName", toChatName)
				.putExtra("self", NApplication.getInstance().getUserName()));
	}

	/**
	 * 显示图片
	 * 
	 * @param imageView
	 * @param url
	 */

	public static void setimg(ImageView imageView, String url, Context context) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(url, imageView,
				getDisplayImageOptions(context));
	}

	@SuppressWarnings("deprecation")
	protected static DisplayImageOptions getDisplayImageOptions(Context context) {
		// TODO Auto-generated method stub
		DisplayImageOptions options;
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCache(new WeakMemoryCache())
				.writeDebugLogs()
				// 打印log信息
				.threadPoolSize(3)
				// 线程池内加载的数量
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100) // 缓存的文件数量
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
				// connectTimeout (5 s), readTimeout (30 s)超时时间
				.build();

		options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnLoading(R.drawable.ic_head_base) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_head_base)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_head_base) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(false)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(5))// 是否设置为圆角，弧度为多少
				.build();// 构建完成
		ImageLoader.getInstance().init(configuration);
		return options;
	}

	/**
	 * 发给医生发送聊天信息到服务器
	 * 
	 * @param Vid
	 *            复诊拿药ID
	 * @param DID
	 *            医生ID
	 * @param UID
	 *            患者ID
	 * @param SendType
	 *            发送方类型0患者1医生
	 * @param Contents
	 *            发送内容
	 */
	public static void postDoctor(final String Vid, final String DID,
			final String UID, final String SendType, final String Contents) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams parmas = new RequestParams();
		parmas.put("act", "vadd");
		parmas.put("Vid", Vid);
		parmas.put("DID", DID);
		parmas.put("UID", UID);
		parmas.put("SendType", SendType);
		parmas.put("Contents", Contents);
		client.post(Url.Chat, parmas, new AsyncHttpResponseHandler());
	}

	/**
	 * 发往教练发送聊天信息到服务器
	 * 
	 * @param Mid
	 *            运动指导ID
	 * @param CID
	 *            教练ID
	 * @param UID
	 *            患者ID
	 * @param SendType
	 *            发送方类型0患者1教练
	 * @param Contents
	 *            发送内容
	 */
	public static void postsend(final String Mid, final String CID, final String UID,
			final String SendType, final String Contents) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams parmas = new RequestParams();
		parmas.put("act", "madd");
		parmas.put("Mid", Mid);
		parmas.put("CID", CID);
		parmas.put("UID", UID);
		parmas.put("SendType", SendType);
		parmas.put("Contents", Contents);
		client.post(Url.Chat, parmas, new AsyncHttpResponseHandler());
	}

	/**
	 * 创建一个对话框
	 * @param context
	 * @param msg
	 */
	public static void createDialog(Context context,String msg) {
		Builder dialog=new AlertDialog.Builder(context);
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
		});
		dialog.create().show();
	}
		
}
