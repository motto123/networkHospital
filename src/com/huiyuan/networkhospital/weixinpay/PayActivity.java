package com.huiyuan.networkhospital.weixinpay;

import java.io.StringReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Constant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class PayActivity {
	private Context context;
	private AsyncHttpResponseHandler vupdate_handler, R_PHandler, DV_P_handler,
	aadd_handler, madd_handler, mupdate_handler;
	private ProgressDialog dialog;
	ArrayList<Dv_people> listObj;
	public String id;

	private static final String TAG = "MicroMsg.SDKSample.PayActivity";

	PayReq req;
	private IWXAPI msgApi;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	public String ip;
	public String pay_num;
	public String nameString = "药品";
	public String transaction_id = null;

	public PayActivity(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		msgApi = WXAPIFactory.createWXAPI(context, null);
		hanlder();
	}

	public PayActivity(Context context, String nameString, String pay_num) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.nameString = nameString;
		this.pay_num = pay_num;
		msgApi = WXAPIFactory.createWXAPI(context, null);
		hanlder();
	}

	public void name() {
		req = new PayReq();
		sb = new StringBuffer();

		msgApi.registerApp(Constants.APP_ID);
		if (msgApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT) {
			getIp();
			Log.e("ip", ip);
			String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
					.toUpperCase();
			GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
			getPrepayId.execute();
		} else {
			Toast.makeText(context, "未安装微信，您的手机不能完成支付，", Toast.LENGTH_SHORT)
			.show();
		}

	}

	/**
	 * wifi 获取ip
	 */
	public void getIp() {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (wifiManager.isWifiEnabled()) {
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			ip = intToIp(ipAddress);
		} else {
			ip = getLocalIpAddress();
		}
	}

	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	/**
	 * 使用GPR情况下，获取ip
	 * 
	 * @return
	 */
	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	/**
	 * 生成签名
	 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", packageSign);
		return packageSign;
	}

	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");

			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		return sb.toString();
	}

	private class GetPrepayIdTask extends
	AsyncTask<Void, Void, Map<String, String>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// dialog = ProgressDialog.show(PayActivity.this,
			// getString(R.string.app_tip),
			// getString(R.string.getting_prepayid));
			dialog = ProgressDialog.show(context,
					context.getString(R.string.app_tip),
					context.getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			if (dialog != null) {
				dialog.dismiss();
			}
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
			// show.setText(sb.toString());

			resultunifiedorder = result;
			genPayReq();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {

			String url = String
					.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			Log.e("orion", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}
	}

	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	//
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
			.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", nameString));
			packageParams
			.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url",
					"http://183.221.242.75:8089/APPInterface/user/"));
			Constants.out_trade_no = genOutTradNo();
			packageParams.add(new BasicNameValuePair("out_trade_no",
					Constants.out_trade_no));
			packageParams.add(new BasicNameValuePair("spbill_create_ip", ip));
			packageParams.add(new BasicNameValuePair("total_fee", pay_num));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);

			return new String(xmlstring.toString().getBytes(), "ISO8859-1");

		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}

	}

	private void genPayReq() {

		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");

		// show.setText(sb.toString());

		Log.e("orion", signParams.toString());
		sendPayReq();
	}

	private void sendPayReq() {

		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);
	}

	/**
	 * 获取订单号
	 * @author hepeng
	 *
	 */
	private class GetTransactionIdTask extends
	AsyncTask<Void, Void, Map<String, String>> {

		@Override
		protected void onPreExecute() {
			// dialog = ProgressDialog.show(PayActivity.this,
			// getString(R.string.app_tip),
			// getString(R.string.getting_prepayid));
			dialog = ProgressDialog.show(context,
					context.getString(R.string.app_tip),
					context.getString(R.string.getting_prepayid));
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {

			transaction_id = result.get("transaction_id");
			Tools.LogE("支付订单号："+transaction_id);
			switch (NApplication.pay_style) {
			case Constant.GM_PAY:
				vupdate();
				break;
			case Constant.R_PAY:
				add();
				break;
			case Constant.DV_PAY:
				aiadd();
				break;
			case Constant.MD_PAY:
				aadd();
				break;
			case Constant.SC_PAY:
				madd();
				break;

			}

		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {

			String url = String
					.format("https://api.mch.weixin.qq.com/pay/orderquery");
			String entity = genProductArg();

			Log.e("orions", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orions", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}
	}

	/**
	 * 获取订单号
	 */
	public void startTransactionId() {
		new GetTransactionIdTask().execute();
	}

	private String genProductArg() {
		StringBuffer xml = new StringBuffer();

		try {
			String nonceStr = genNonceStr();

			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams
			.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams
			.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("out_trade_no",
					Constants.out_trade_no));

			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));

			String xmlstring = toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}
	}

	/**
	 * 修改状态，同时上传订单号，收货地址。
	 */
	/**
	 * 用户付款后状态码改变
	 */
	public void vupdate() {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "vupdate");
		params.put("ID", NApplication.Vid);
		params.put("state", "2");
		params.put("title", transaction_id);
		params.put("Type",  NApplication.gm_pay.getType());
		params.put("Name",  NApplication.gm_pay.getName());
		params.put("Phone",  NApplication.gm_pay.getPhone());
		params.put("Receiving", NApplication.gm_pay.getAddress());
		// NApplication.address = null;
		client.post(Url.department, params, vupdate_handler);
	}

	/**
	 * 新增挂号信息列表
	 */
	public void add() {
		RequestParams parmas = new RequestParams();
		parmas.put("act", "add");
		parmas.put("UID", NApplication.userid);
		parmas.put("Name", NApplication.r_pay.getName());
		parmas.put("Sex", NApplication.r_pay.getSex());
		parmas.put("Phone", NApplication.r_pay.getPhone());
		parmas.put("IC", NApplication.r_pay.getIC());
		parmas.put("Dname", NApplication.r_pay.getDname());
		parmas.put("RTime", NApplication.r_pay.getRTime());
		parmas.put("Price", NApplication.r_pay.getPrice());
		parmas.put("Number", transaction_id);
		parmas.put("Age", NApplication.r_pay.getAge());
		// NApplication.r_pay = null;
		System.out.println(Url.Registers + "?" + parmas);
		HttpClientUtils.post(Url.Registers, parmas, R_PHandler);
	}

	/**
	 * 用户发布医生上门信息
	 */
	public void aiadd() {

		AsyncHttpClient client2 = new AsyncHttpClient();
		RequestParams params2 = new RequestParams();
		Gson gson = new Gson();
		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Dv_people>>() {
		}.getType();

		if (NApplication.dv_people != null) {
			listObj = new ArrayList<Dv_people>();
			listObj.add(NApplication.dv_people);
			// NApplication.dv_people = null;
		}
		for (Dv_people a : listObj) {
			a.setNumber(transaction_id);
		}
		params2.put("act", "aiadd");
		params2.put("list", gson.toJson(listObj, type));
		System.out.println(("Make1" + Url.Operation + "?" + params2));
		client2.post(Url.Operation, params2, DV_P_handler);
	}

	/**
	 * 用户新增医生上门人员信息
	 */
	public void aadd() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "aadd");
		params.put("uid", NApplication.userid);
		params.put("Province", NApplication.md_pay.getProvince());
		params.put("City", NApplication.md_pay.getCity());
		params.put("Area", NApplication.md_pay.getArea());
		params.put("Explain", NApplication.md_pay.getExplain());
		params.put("Title", NApplication.md_pay.getTitle());
		params.put("IsParticipate", NApplication.md_pay.getIsParticipate());
		params.put("Address", NApplication.md_pay.getAddress());
		System.out.println(("Make1" + Url.Operation + "?" + params));
		client.post(Url.Operation, params, aadd_handler);
	}

	/**
	 * 用户新增运动指导信息
	 */
	public void madd() {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "madd");
		params.put("UID", NApplication.userid);
		params.put("CID", NApplication.CID);
		params.put("title",transaction_id);
		System.out.println(("Make2" + (Url.userMovementGuidance + "?" + params)));
		client.post(Url.userMovementGuidance, params, madd_handler);
	}

	/**
	 * 用户运动指导付款后添加订单号
	 */
	public void mupdate() {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("act", "mupdate");
		params.put("ID", id);
		params.put("title", transaction_id);
		System.out.println(("Make1" + Url.Operation + "?" + params));
		client.post(Url.userMovementGuidance, params, mupdate_handler);
	}

	private void hanlder() {
		/**
		 * 用户付款后状态码改变,判断是否改变
		 */
		vupdate_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// Toast.makeText(context, "网络连接错误，请检查网络设置后重试。",
				// Toast.LENGTH_SHORT).show();
				vupdate();
				// if (dialog != null) {
				// dialog.dismiss();
				// }
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("vupdate", arg0);
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					if (jsonObject.getString("Data").equals("true")) {
						NApplication.gm_pay = null;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onSuccess(arg0);
			}
		};
		/**
		 * 新增挂号信息列表，判断是否新增
		 */
		R_PHandler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// Toast.makeText(context, "网络连接错误，请检查网络设置后重试。",
				// Toast.LENGTH_SHORT).show();
				// if (dialog != null) {
				// dialog.dismiss();
				// }
				add();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("add", arg0);
					JSONObject jsonObject = new JSONObject(arg0);
					if (jsonObject.getString("State").equals("0")) {
						NApplication.r_pay = null;
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// Tools.showTextToast(getActivity(), "没有数据可以显示了");
					e.printStackTrace();
				}
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onSuccess(arg0);
			}
		};
		/**
		 * 用户发布医生上门信息，判断是否添加成功
		 */
		DV_P_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// Toast.makeText(context, "网络连接错误，请检查网络设置后重试。",
				// Toast.LENGTH_SHORT).show();
				// if (dialog != null) {
				// dialog.dismiss();
				// }
				aiadd();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("DV_P_handler", arg0);
				try {
					NApplication.dv_people = null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Tools.showTextToast(context, "没有数据可以显示了");
					e.printStackTrace();
				}
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onSuccess(arg0);
			}
		};
		/**
		 * 用户新增医生上门人员信息，判断新增是否成功，成功在发布医生上门信息
		 */
		aadd_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// Toast.makeText(context, "网络连接错误，请检查网络设置后重试。",
				// Toast.LENGTH_SHORT).show();
				// if (dialog != null) {
				// dialog.dismiss();
				// }
				aadd();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					String Aid = "";
					String CreateTime = "";
					Log.e("aadd", arg0);
					JSONObject jsonObject = new JSONObject(arg0);
					Aid = jsonObject.getString("Data");

					Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time
					// Zone资料。
					t.setToNow(); // 取得系统时间。
					CreateTime = t.year + "-" + t.month + "-" + t.monthDay;
					Log.e("CreateTime", CreateTime);
					listObj = NApplication.md_pay.getListObj();
					Log.e("listObj", listObj.size() + "");
					for (Dv_people a : listObj) {
						a.setAID(Aid);
						a.setCreateTime(CreateTime);
					}
					aiadd();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					Tools.showTextToast(context, "没有数据可以显示了");
					e.printStackTrace();
				}
				super.onSuccess(arg0);
			}
		};
		/**
		 * 用户新增运动指导信息，判断是否新增成功，成功后添加订单号
		 */
		madd_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// Toast.makeText(context, "网络连接错误，请检查网络设置后重试。",
				// Toast.LENGTH_SHORT).show();
				// if (dialog != null) {
				// dialog.dismiss();
				// }
				madd();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("madd", arg0);
					JSONObject jsonObject = new JSONObject(arg0);
					if (jsonObject.getInt("Data") > 0) {
						id = jsonObject.getString("Data");
						mupdate();
					}
					// Aid = jsonObject.getString("Data");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					Tools.showTextToast(context, "数据错误了");
					e.printStackTrace();
				}
				super.onSuccess(arg0);
			}
		};
		/**
		 * 用户运动指导付款后添加订单号，判断是否添加成功
		 */
		mupdate_handler = new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// Toast.makeText(context, "网络连接错误，请检查网络设置后重试。",
				// Toast.LENGTH_SHORT).show();
				// if (dialog != null) {
				// dialog.dismiss();
				// }
				mupdate();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					Log.e("mupdate", arg0);
					JSONObject jsonObject = new JSONObject(arg0);
					if (jsonObject.getString("Data").equals("true")) {

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Tools.showTextToast(context, "数据错误了");
					e.printStackTrace();
				}
				if (dialog != null) {
					dialog.dismiss();
				}
				super.onSuccess(arg0);
			}
		};
	}

}
