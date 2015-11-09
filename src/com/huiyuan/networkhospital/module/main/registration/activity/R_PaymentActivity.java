package com.huiyuan.networkhospital.module.main.registration.activity;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Constant;
import com.huiyuan.networkhospital.entity.dengzhaojun.OrderDoctor;
import com.huiyuan.networkhospital.entity.hepeng.R_pay;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.weixinpay.PayActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * 
 * @ClassName:  R_PaymentActivity   
 * @Description:TODO预约挂号发送自己的信息页面
 * @author: 邓肇均
 * @date:   2015年10月21日 下午5:08:34   
 *
 */
@EActivity(R.layout.activity_r__payment)
public class R_PaymentActivity extends BaseActivity {
	@ViewById
	TextView tvName;
	@ViewById
	TextView tvTel;
	@ViewById
	TextView tvId;
	@ViewById
	TextView tvDepartment;
	@ViewById
	TextView tvCost;
	@ViewById
	TextView Sex123;
	@ViewById
	Button btPay;
	OrderDoctor order = new OrderDoctor();
	String time = "";
	ArrayAdapter<String> person1Adapter = null; // 适配器
	@ViewById
	Spinner person1;
	@ViewById
	Spinner spHour;
	String sex = "0";
	static Context context;
	int Position = 0;
	int i = 0;
	String[] times;
	String[] ampm;
	String Age = "";
	String name = "挂号费";
	String total = "1";

	String[] am = new String[]{"9:00","10:00","11:00"};

	String[] pm = new String[]{"14:00","15:00","16:00"};
	private ArrayAdapter<String> ampmAdapter;
	/**
	 * 小时spinner被点击的位置
	 */
	private int position1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	protected void init() {
		dohandler();
		context = R_PaymentActivity.this;
		Intent a = getIntent();
		order = (OrderDoctor) a.getSerializableExtra("person");
		tvDepartment.setText(order.getName());
		tvCost.setText((Integer.parseInt(order.getPrice())+5) + "元");
		try {
			tvName.setText(NApplication.username);
			tvTel.setText(NApplication.phone);
			tvId.setText(NApplication.IC);
			Time time12 = new Time();
			time12.setToNow();
			try {
				Age = time12.year
						- Integer.parseInt(tvId.getText().toString()
								.substring(6, 10)) + "";
			} catch (Exception e) {
				Tools.LogE("身份证位数不正确！");
				e.printStackTrace();
			}

			if (NApplication.sex.equals("true")) {
				Sex123.setText("男");
				sex = "0";
			} else {
				Sex123.setText("女");
				sex = "1";
			}

			Gson gson = new Gson();
			ArrayList<ordertime> items = new ArrayList<ordertime>();
			JSONObject jsonObject = new JSONObject("{\"Data\":"
					+ order.getOutpatientTime() + "}");
			items = gson.fromJson(jsonObject.getString("Data"),
					new TypeToken<ArrayList<ordertime>>() {
			}.getType());
			times = new String[items.size()];
			ampm = new String[items.size()];
			for (ordertime t : items) {
				time = getday(Integer.parseInt(t.getDay()));

				switch (t.getDay().toString()) {
				case "1":
					t.setDay("星期一");
					break;
				case "2":
					t.setDay("星期二");
					break;
				case "3":
					t.setDay("星期三");
					break;
				case "4":
					t.setDay("星期四");
					break;
				case "5":
					t.setDay("星期五");
					break;
				case "6":
					t.setDay("星期六");
					break;
				case "7":
					t.setDay("星期天");
					break;
				default:
					break;
				}
				switch (t.getTime()) {
				case "am":
					ampm[i] = "09:00:00";
					t.setTime("上午");
					break;
				case "pm":
					ampm[i] = "14:00:00";
					t.setTime("下午");
					break;
				default:
					break;
				}
				i++;
				time = time + " " + t.getDay() + " " + t.getTime();
				times[i - 1] = time;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int j =0;
		//获取数组长度
		for(int i = 0;i<times.length;i++){
			if(filterTime(times[i].substring(3, 5))){
				j++;
			}
		}
		String[] timeCopy = new String[j];
		for(int i = 0;i<times.length;i++){
			if(filterTime(times[i].substring(3, 5))){
				timeCopy[j-1] = times[i];
				j--;
			}
		}
		try {
			if(null == timeCopy[0]){
			}
		} catch (Exception e) {
			Toast.makeText(R_PaymentActivity.this, "预约时间不能超过7天", 1).show();
			btPay.setEnabled(false);
			return;
		}
		times = timeCopy;
		person1Adapter = new ArrayAdapter<String>(R_PaymentActivity.this,
				android.R.layout.simple_spinner_item, times);
		person1.setAdapter(person1Adapter);
		person1.setSelection(0, true);
		if(getTime(times[0])){
			ampm = am;
		}else{
			ampm = pm;
		}
		ampmAdapter = new ArrayAdapter<String>(R_PaymentActivity.this,
				android.R.layout.simple_spinner_item, ampm);
		spHour.setAdapter(ampmAdapter);
		spHour.setSelection(0, true);
	}

	private void dohandler() {
		new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Toast.makeText(R_PaymentActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				try {
					new AlertDialog.Builder(R_PaymentActivity.this)
					.setTitle("提示")
					.setMessage("支付成功")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
						public void onClick(
								DialogInterface dialog,
								int which) {
							R_PaymentActivity.this.finish();
						}
					})
					.show();
				} catch (Exception e) {
					e.printStackTrace();
				}

				super.onSuccess(arg0);
			}
		};

	}

	/**
	 * 根据星期得到号数
	 * 
	 * @param a
	 * @return
	 */
	// 本周的周a为t.monthDay+(a-day+1)日
	public String getday(int a) {
		Time t = new Time();
		t.setToNow();
		int day = t.weekDay;

		if (day + 3 > 7) {
			day = day - 7;
			if (a > day + 3) {
				System.out.println(1);
				return getmonth(t.monthDay + (a - day));
			} else {
				System.out.println(2);
				return getmonth(t.monthDay + (a - day) + 7);
			}
		} else {
			if (a > day + 3) {
				System.out.println(3);
				return getmonth(t.monthDay + (a - day));
			} else {
				System.out.println(4);
				return getmonth(t.monthDay + (a - day) + 7);
			}
		}

	}

	/**
	 * 输入号数，得出年月日
	 * 
	 * @param a
	 * @return
	 */
	public String getmonth(int a) {
		Time t = new Time();
		t.setToNow();
		int month = t.month + 1;
		if ((t.year % 4 == 0 && t.year % 100 != 0) || t.year % 400 == 0) {
			if (month == 2) {
				if (a > 29) {
					month = month + 1;
					a = a - 28;
				}
			} else if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				if (a > 31) {
					month = month + 1;
					a = a - 31;
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (a > 30) {
					month = month + 1;
					a = a - 30;
				}
			}
		} else {
			if (month == 2) {
				if (a > 28) {
					month = month + 1;
					a = a - 28;
				}
			} else if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				if (a > 31) {
					month = month + 1;
					a = a - 31;
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (a > 30) {
					month = month + 1;
					a = a - 30;
				}
			}
		}
		if (month > 12) {
			return 1 + "月" + a + "日";
		}
		return month + "月" + a + "日";

	}

	//小时选择器
	@ItemSelect(R.id.person1)
	public void spinner(boolean selected, int position) {
		Position = position; // 记录当前序号，留给下面适配器时用
		if(getTime(times[0])){
			ampm = am;
			ampmAdapter.notifyDataSetChanged();
		}else{
			ampm = pm;
			ampmAdapter.notifyDataSetChanged();
		}
		spHour.setSelection(0, true);
	}
	
	@ItemSelect(R.id.spHour)
	public void spinner1(boolean selected, int position) {
		position1 = position; // 记录当前序号，留给下面适配器时用
		
	}

	@Click({ R.id.ibtnBack, R.id.btPay })
	protected void skip(View v) {
		switch (v.getId()) {
		case R.id.ibtnBack:
			finish();
			break;
		case R.id.btPay:
			/**
			 * 付款成功后又订单号添加数据,订单号在最后一个
			 */
			// tvName.setText(NApplication.name);
			// tvName.setText(NApplication.name);
			if (tvName.getText().toString().equals("")) {
				Tools.showTextToast(R_PaymentActivity.this, "请输入姓名");
			} else if (tvTel.getText().toString().equals("")
					|| tvTel.getText().toString().length() < 11) {
				Tools.showTextToast(R_PaymentActivity.this, "请输入11位电话号码");
			} else if (tvId.getText().toString().equals("")
					|| tvId.getText().length() < 18) {
				Tools.showTextToast(R_PaymentActivity.this, "请输入18位身份证号码");
			} else {
				NApplication.pay_style = Constant.R_PAY;
				Time t = new Time();
				t.setToNow();
				int year = t.year;
				int month = Integer.parseInt(times[Position].substring(0,
						times[Position].indexOf("月")));
				int monthday = Integer.parseInt(times[Position].substring(
						times[Position].indexOf("月") + 1,
						times[Position].indexOf("日")));
				if (Integer.parseInt(times[Position].substring(0,
						times[Position].indexOf("月"))) > 12) {
					year = t.year + 1;
					month = month - 12;
				}
				R_pay r_pay = new R_pay();
				r_pay.setName(tvName.getText().toString());
				r_pay.setSex(sex);
				r_pay.setPhone(tvTel.getText().toString());
				r_pay.setIC(tvId.getText().toString());
				r_pay.setDname(tvDepartment.getText().toString());
//				r_pay.setRTime(year + "-" + month + "-" + monthday + " "
//						+ ampm[Position]);
				r_pay.setRTime(year + "-" + month + "-" + monthday + " "
						+ ampm[position1]);
				r_pay.setPrice((Integer
						.parseInt(tvCost
								.getText()
								.toString()
								.substring(
										0,
										tvCost.getText().toString()
										.indexOf("元"))))
										+ "");
				r_pay.setAge(Age);
				NApplication.r_pay = r_pay;
				ConnectivityManager manager = (ConnectivityManager) (R_PaymentActivity.this)
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = manager.getActiveNetworkInfo();
				if (networkInfo == null) {
					Toast.makeText(R_PaymentActivity.this,
							"网络连接错误，请检查网络设置后重试。", Toast.LENGTH_SHORT).show();
				} else {

					PayActivity payActivity = new PayActivity(this, name, total);
					payActivity.name();
				}

				DoctorList.Destroy();
				DoctorDetail.Destroy();

				break;
			}
		}
	}

	/**
	 * 显示上午或下午的时间
	 * @param time
	 * @return
	 */
	private boolean getTime(String time){
		String s = time.substring(11, 13);
		if("上午".equals(s)){
			return true;
		}
		return false;
		
	}

	public boolean filterTime(String day){
		String nowTime=Tools.getTime();
		int dayNow = Integer.parseInt(nowTime.substring(8, 10));
		if((dayNow+7) >= Integer.parseInt(day)){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @ClassName:  ordertime   
	 * @Description:TODO时间类方便计算出预约日期
	 * @author: 邓肇均
	 * @date:   2015年10月21日 下午5:11:40   
	 *
	 */
	class ordertime {
		String day, time;

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}

	public static void Destroy() {
		((Activity) context).finish();
	}

}
