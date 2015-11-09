package com.huiyuan.networkhospital.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import com.huiyuan.networkhospital.R;

public class PhotoUtil {

	public static void pickPicture(Activity mCtx) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		mCtx.startActivityForResult(intent, Constant.SELECT_BY_PICK_PICTURE);
	}

	/*
	 * MediaStore这个类是android系统提供的一个多媒体数据库 
	 * 其中ContentProvider负责组织应用程序的数据；
	 * 向其他应用程序提供数据；ContentResolver则负责获取ContentProvider提供的数据；修改/添加/删除/更新数据等；
	 */
	public static void takePhoto(Activity mCtx, String guid) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(FileUtil.getTempPath(mCtx, guid))));
		mCtx.startActivityForResult(intent, Constant.SELECT_BY_TACK_PHOTO);
	}

	public static void doTakePhoto(final Context mContext, final Intent data,
			final String name, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String toPath = FileUtil
							.getImageStoragePath(mContext, name);
					String smallPath = FileUtil.getSmallImageStoragePath(
							mContext, name);
					Bitmap image = null;
					if (data != null) {
						if (data.getData() != null) {
							image = MediaStore.Images.Media.getBitmap(
									mContext.getContentResolver(),
									data.getData());
						} else {
							Bundle extras = data.getExtras();
							if (extras != null) {
								// 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
								image = extras.getParcelable("data");
							}
						}
					} else {
						/*
						 * BitmapFactory.decodeFile
						 * 取得data/data中的数据
						 * decodeResourceStream方法根据手机屏幕的密度有一个缩放图片的过程
						 */
						image = BitmapFactory.decodeFile(FileUtil.getTempPath(
								mContext, name));
					}
					// saveToSDcard(image,
					// FileUtil.getImageStoragePath(mContext, "123"));
					createThumbnail(mContext, image, smallPath);
					handler.sendEmptyMessage(1);
					compressBySize(image, toPath);
					handler.sendEmptyMessage(2);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * 生成缩放图，并保存到sd上
	 * @param mcContexts 上下文
	 * @param data 
	 * @param path 路径
	 */
	public static void saveToSDcard(Context mcContexts, byte[] data, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);
			fos.close();
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			createThumbnail(mcContexts, bitmap,
					FileUtil.bigPath2SmallPath(mcContexts, path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存图片，100%比例保存
	 * @param bitmap 源图片
	 * @param path 保存路径
	 */
	public static void saveToSDcard(Bitmap bitmap, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 质量压缩图片
	 * @param image 原图片
	 * @param toPath 保存路劲
	 */
	public static void compressByQuality(Bitmap image, String toPath) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > 200) {
			// 重置baos即清空baos
			baos.reset();
			// 这里压缩options%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			// 每次都减少10
			options -= 10;
		}
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		// ByteArrayInputStream bais = new
		// ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		// Bitmap bitmap = BitmapFactory.decodeStream(bais, null, null);
		// 保存到文件
		try {
			FileOutputStream fos = new FileOutputStream(toPath);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 按尺寸压缩图片
	 * @param fromPath 图片路径
	 * @param toPath 保存路径
	 */
	public static void compressBySize(String fromPath, String toPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(fromPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(fromPath, newOpts);
		compressByQuality(bitmap, toPath);// 压缩好比例大小后再进行质量压缩
	}
	/**
	 * 按尺寸压缩图片
	 * @param image 原图片
	 * @param toPath 保存路径
	 */
	public static void compressBySize(Bitmap image, String toPath) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里压缩100%，把压缩后的数据存放到baos中
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		/*
		 * 在BitmapFactory.Options中提供了另一个成员inJustDecodeBounds。
		 * 设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度
		 */
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		compressByQuality(bitmap, toPath);// 压缩好比例大小后再进行质量压缩
	}
	/**
	 * 生成一个按照比例缩小的缩略图，并保存
	 * @param mcContext 上下文
	 * @param image 原图片
	 * @param toPath 保存图片的路径
	 */
	public static void createThumbnail(Context mcContext, Bitmap image,
			String toPath) {
		/*
		 * getResources().getDimensionPixelSize 
		 * 取出dimens中的值
		 */
		int width = mcContext.getResources().getDimensionPixelSize(
				R.dimen.chat_msg_thumbnail_width);
		int height = mcContext.getResources().getDimensionPixelSize(
				R.dimen.chat_msg_thumbnail_height);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 * @param source 源文件(Bitmap类型)
		 * @param width 压缩成的宽度
		 * @param height 压缩成的高度
		 */
		Bitmap bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		try {
			// 保存图片
			FileOutputStream fos = new FileOutputStream(toPath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到指定大小，压缩后的图片。
	 * @param mcContext 上下文
	 * @param image 原图片
	 * @return 压缩后的图片
	 */
	public static Bitmap getThumbnail(Context mcContext, Bitmap image) {
		/*
		 * getResources().getDimensionPixelSize 
		 * 取出dimens中的值
		 */
		Bitmap bitmap ; 
		int width = mcContext.getResources().getDimensionPixelSize(
				R.dimen.chat_msg_thumbnail_width);
		int height = mcContext.getResources().getDimensionPixelSize(
				R.dimen.chat_msg_thumbnail_height);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 * @param source 源文件(Bitmap类型)
		 * @param width 压缩成的宽度
		 * @param height 压缩成的高度
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width||w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		}else {
			bitmap = image;
		}
		return bitmap;
	}

	/**
	 * 得到指定大小，压缩后的图片。
	 * @param mcContext 上下文
	 * @param image 原图片
	 * @return 压缩后的图片
	 */
	public static Bitmap getThumbnails(Context mcContext, Bitmap image) {
		/*
		 * getResources().getDimensionPixelSize 
		 * 取出dimens中的值
		 */
		Bitmap bitmap ; 
		int width = mcContext.getResources().getDimensionPixelSize(
				R.dimen.Idcard_width);
		int height = mcContext.getResources().getDimensionPixelSize(
				R.dimen.Idcard_height);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 * @param source 源文件(Bitmap类型)
		 * @param width 压缩成的宽度
		 * @param height 压缩成的高度
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width||w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		}else {
			bitmap = image;
		}
		return bitmap;
	}
	/**
	 * 得到指定大小，压缩后的图片。
	 * @param mcContext 上下文
	 * @param image 原图片
	 * @return 压缩后的图片
	 */
	public static Bitmap getHead(Context mcContext, Bitmap image) {
		/*
		 * getResources().getDimensionPixelSize 
		 * 取出dimens中的值
		 */
		Bitmap bitmap ; 
		int width = mcContext.getResources().getDimensionPixelSize(
				R.dimen.head_width);
		int height = mcContext.getResources().getDimensionPixelSize(
				R.dimen.head_height);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 * @param source 源文件(Bitmap类型)
		 * @param width 压缩成的宽度
		 * @param height 压缩成的高度
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width||w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		}else {
			bitmap = image;
		}
		return bitmap;
	}
	public static Bitmap getHeads(Context mcContext, Bitmap image) {
		/*
		 * getResources().getDimensionPixelSize 
		 * 取出dimens中的值
		 */
		Bitmap bitmap ; 
		int width = mcContext.getResources().getDimensionPixelSize(
				R.dimen.heads_width);
		int height = mcContext.getResources().getDimensionPixelSize(
				R.dimen.heads_height);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 * @param source 源文件(Bitmap类型)
		 * @param width 压缩成的宽度
		 * @param height 压缩成的高度
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width||w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		}else {
			bitmap = image;
		}
		return bitmap;
	}
	/**
	 * 得到指定大小，压缩后的图片。
	 * @param mcContext 上下文
	 * @param image 原图片
	 * @return 压缩后的图片
	 */
	public static Bitmap saveHead(Context mcContext, Bitmap image) {
		/*
		 * getResources().getDimensionPixelSize 
		 * 取出dimens中的值
		 */
		Bitmap bitmap ; 
		int width = mcContext.getResources().getDimensionPixelSize(
				R.dimen.save_head_width);
		int height = mcContext.getResources().getDimensionPixelSize(
				R.dimen.save_head_height);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 * @param source 源文件(Bitmap类型)
		 * @param width 压缩成的宽度
		 * @param height 压缩成的高度
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width||w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		}else {
			bitmap = image;
		}
		return bitmap;
	}
	/**
	 * 压缩图片
	 * 
	 * @param bitmap
	 *            源图片
	 * @param width
	 *            想要的宽度
	 * @param height
	 *            想要的高度
	 * @param isAdjust
	 *            是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
	 * @return Bitmap
	 */
	public Bitmap reduce(Bitmap bitmap, int width, int height, boolean isAdjust) {
		// 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
		if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
			return bitmap;
		}
		// 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor,
		// int scale, int roundingMode);
		// scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
		float sx = new BigDecimal(width).divide(
				new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		float sy = new BigDecimal(height).divide(
				new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
			sx = (sx < sy ? sx : sy);
			sy = sx;// 哪个比例小一点，就用哪个比例
		}
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
		/*
		 * surce：用来剪裁的图片源;
		 * x：剪裁x方向的起始位置;
		 * y：剪裁y方向的起始位置;
		 * width：剪裁的宽度;
		 * height：剪裁的高度;
		 * m:可选矩阵被应用到像素
		 * filer：是不是需要过滤。
		 * 必须满足条件：x+width<=bitmap.width()（图片源的原始宽度）否则会抛出IllegalArgumentException异常。
		 */
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}
	/**
	 * 计算样本大小
	 * 动态的计算inSampleSize
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */

	public int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}
	/**
	 * 初始样本大小的计算
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * 
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width,
			int height, int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		if (bitmap == null) {
			return null;
		}
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}
