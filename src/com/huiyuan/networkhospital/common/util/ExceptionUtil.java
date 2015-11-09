package com.huiyuan.networkhospital.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.huiyuan.networkhospital.NApplication;

/**
 * 处理项目中所有的异常
 * 
 * @author tarena
 * 
 */
public class ExceptionUtil {
	public static void exceptionHandle(Exception e) {
		if (NApplication.isRelease) {
			// 把异常信息变成字符串，发给开发人员
			String str = "";

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			str = stringWriter.toString();
			// Log.i("test",str);
			// 联网发送
		} else {
			e.printStackTrace();
		}
	}
}
