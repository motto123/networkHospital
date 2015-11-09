package com.huiyuan.networkhospital.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

/**
 * 文件工具类
 * @author Administrator
 *
 */
public class FileUtil {
	/**
	 * sd卡的根目录
	 */
	private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
	/**
	 * 手机的缓存根目录
	 */
	private static String mDataRootPath = null;
	/**
	 * 保存Image的目录名
	 */
	private final static String FOLDER_NAME = "/AndroidImage";


	public FileUtil(Context context){
		mDataRootPath = context.getCacheDir().getPath();
	}


	/**
	 * 获取储存Image的目录
	 * @return
	 */
	private String getStorageDirectory(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
				mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
	}

	/**
	 * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	 * @param fileName 
	 * @param bitmap   
	 * @throws IOException
	 */
	public void savaBitmap(String fileName, Bitmap bitmap) throws IOException{
		if(bitmap == null){
			return;
		}
		String path = getStorageDirectory();
		File folderFile = new File(path);
		if(!folderFile.exists()){
			folderFile.mkdir();
		}
		File file = new File(path + File.separator + fileName);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
	}

	/**
	 * 从手机或者sd卡获取Bitmap
	 * @param fileName
	 * @return
	 */
	public Bitmap getBitmap(String fileName){
		return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + fileName);
	}

	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public boolean isFileExists(String fileName){
		return new File(getStorageDirectory() + File.separator + fileName).exists();
	}

	/**
	 * 获取文件的大小
	 * @param fileName
	 * @return
	 */
	public long getFileSize(String fileName) {
		return new File(getStorageDirectory() + File.separator + fileName).length();
	}


	/**
	 * 删除SD卡或者手机的缓存图片和目录
	 */
	public void deleteFile() {
		File dirFile = new File(getStorageDirectory());
		if(! dirFile.exists()){
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}

		dirFile.delete();
	}
	/**
	 * 获得保存录音的文件路径
	 * @param ctx
	 * @param name
	 * @return
	 */
	public static String getAudioStoragePath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/audio");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".mp4";
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/audio");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".mp4";
		}
		return path;
	}
	public static String getSlideImage(Context ctx,int type, String name) {
		String path = null;
		String sdcard = getSDPath();
		String folderPath = null;
		if(type == 1){
			folderPath = "/networkhospital_user/slideimage/images";
		}else{
			folderPath = "/networkhospital_user/slideimage/contents";
		}
		if (sdcard != null) {
			File dir = new File(sdcard + folderPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name;
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + folderPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name;
		}
		return path;
	}

	public static String getImageStoragePath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/image");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".jpg";
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/image");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".jpg";
		}
		return path;
	}

	public static String getPngStoragePath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/image");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".png";
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/image");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".png";
		}
		return path;
	}

	public static String getSmallImageStoragePath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/small");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".jpg";
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/small");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".jpg";
		}
		return path;
	}

	public static String getTempPath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File root = new File(sdcard + "/networkhospital_userTemp");
			if (!root.exists()) {
				root.mkdir();
			}
			path = root.getAbsolutePath() + "/" + name + ".jpg";
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File root = new File(filePath + "/networkhospital_user");
			if (!root.exists()) {
				root.mkdir();
			}
			path = root.getAbsolutePath() + "/" + name + ".jpg";
		}
		return path;
	}

	/**
	 * 创建mp4文件
	 * @param ctx
	 * @param name
	 * @return 视频文件的路径
	 */
	public static String getVideoStoragePath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/video");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".mp4";
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/video");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".mp4";
		}
		return path;
	}

	public static String getLogPath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/Log");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".txt";
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/Log");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name + ".txt";
		}
		return path;
	}

	public static String getFilePath(Context ctx, String name) {
		String path = null;
		String sdcard = getSDPath();
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/File");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name;
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/File");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path = dir.getAbsolutePath() + "/" + name;
		}
		return path;
	}

	/**
	 * 获得sdCard路径
	 * @return sdCard路径
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			return null;
		}
		return sdDir.toString();
	}

	public static boolean deleteFile(String path) {
		if (path == null) {
			Utils.LogE("delete path is null");
			return false;
		}
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param ctx
	 * @param big
	 * @return 视频缩略图的存储路径
	 */
	public static String bigPath2SmallPath(Context ctx, String big) {
		checkSmallPath(ctx);
		return big.replace("image", "small");
	}

	/**
	 * 
	 * @param ctx 
	 * @param big mp4文件路径
	 * @return 视频缩略图路径
	 */
	public static String videoPath2SmallPath(Context ctx, String big) {
		checkSmallPath(ctx);
		return big.replace("video", "small").replace("mp4", "jpg");
	}

	/**
	 * 创建视频缩略图的存储路径
	 * @param ctx
	 */
	public static void checkSmallPath(Context ctx) {
		String sdcard = getSDPath();//sdCard根目录
		if (sdcard != null) {
			File dir = new File(sdcard + "/networkhospital_user/small");
			if (!dir.exists()) {
				dir.mkdirs();
			}
		} else {
			String filePath = ctx.getFilesDir().getPath();
			File dir = new File(filePath + "/networkhospital_user/small");
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}

	public static void saveToSDcard(byte[] data, String path) {
		try {
			Utils.LogD("decodeMessage write file start!");
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);
			fos.close();
			Utils.LogD("decodeMessage write file end!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	/**
	 * 打开文件
	 * @param file
	 */ 
	public static int openFile(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// 跳转
		// 这里最好try一下，有可能会报错。
		// 比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
		try {
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * @param file
	 */ 
	private static String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		// MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	// MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组：
	private static final String[][] MIME_MapTable = {
		// {后缀名，MIME类型}
		{ ".3gp", "video/3gpp" },
		{ ".apk", "application/vnd.android.package-archive" },
		{ ".asf", "video/x-ms-asf" },
		{ ".avi", "video/x-msvideo" },
		{ ".bin", "application/octet-stream" },
		{ ".bmp", "image/bmp" },
		{ ".c", "text/plain" },
		{ ".class", "application/octet-stream" },
		{ ".conf", "text/plain" },
		{ ".cpp", "text/plain" },
		{ ".doc", "application/msword" },
		{ ".docx",
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
		{ ".xls", "application/vnd.ms-excel" },
		{ ".xlsx",
		"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
		{ ".exe", "application/octet-stream" },
		{ ".gif", "image/gif" },
		{ ".gtar", "application/x-gtar" },
		{ ".gz", "application/x-gzip" },
		{ ".h", "text/plain" },
		{ ".htm", "text/html" },
		{ ".html", "text/html" },
		{ ".jar", "application/java-archive" },
		{ ".java", "text/plain" },
		{ ".jpeg", "image/jpeg" },
		{ ".jpg", "image/jpeg" },
		{ ".js", "application/x-javascript" },
		{ ".log", "text/plain" },
		{ ".m3u", "audio/x-mpegurl" },
		{ ".m4a", "audio/mp4a-latm" },
		{ ".m4b", "audio/mp4a-latm" },
		{ ".m4p", "audio/mp4a-latm" },
		{ ".m4u", "video/vnd.mpegurl" },
		{ ".m4v", "video/x-m4v" },
		{ ".mov", "video/quicktime" },
		{ ".mp2", "audio/x-mpeg" },
		{ ".mp3", "audio/x-mpeg" },
		{ ".mp4", "video/mp4" },
		{ ".mpc", "application/vnd.mpohun.certificate" },
		{ ".mpe", "video/mpeg" },
		{ ".mpeg", "video/mpeg" },
		{ ".mpg", "video/mpeg" },
		{ ".mpg4", "video/mp4" },
		{ ".mpga", "audio/mpeg" },
		{ ".msg", "application/vnd.ms-outlook" },
		{ ".ogg", "audio/ogg" },
		{ ".pdf", "application/pdf" },
		{ ".png", "image/png" },
		{ ".pps", "application/vnd.ms-powerpoint" },
		{ ".ppt", "application/vnd.ms-powerpoint" },
		{ ".pptx",
		"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
		{ ".prop", "text/plain" },
		{ ".rc", "text/plain" },
		{ ".rmvb", "audio/x-pn-realaudio" },
		{ ".rtf", "application/rtf" },
		{ ".sh", "text/plain" },
		{ ".tar", "application/x-tar" },
		{ ".tgz", "application/x-compressed" },
		{ ".txt", "text/plain" },
		{ ".wav", "audio/x-wav" },
		{ ".wma", "audio/x-ms-wma" },
		{ ".wmv", "audio/x-ms-wmv" },
		{ ".wps", "application/vnd.ms-works" },
		{ ".xml", "text/plain" },
		{ ".z", "application/x-compress" },
		{ ".zip", "application/x-zip-compressed" },
		{ "", "*/*" }
	};
}
