package com.huiyuan.networkhospital.module.main.registration.activity;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.huiyuan.networkhospital.entity.dengzhaojun.OrderDoctor;
/**
 * 
 * @ClassName:  DoctorDetail   
 * @Description:TODO医生信息详情
 * @author: 邓肇均
 * @date:   2015年10月21日 下午5:06:17   
 *
 */
@EActivity(R.layout.activity_doctor_detail)
public class DoctorDetail extends Activity {
	@ViewById
	TextView tvName2;
	@ViewById
	TextView tvDuty;
	@ViewById
	TextView tvIntroduce2;
	@ViewById
	Button btPay;
	@ViewById
	ImageView ivBack;
	OrderDoctor order = new OrderDoctor();
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	protected void init() {
		Intent a = getIntent();
		order = (OrderDoctor) a.getSerializableExtra("person");
		tvName2.setText(order.getName());
		tvDuty.setText(order.getOccupation());
		tvIntroduce2.setText(tvIntroduce2.getText() + order.getIntroduction());
		context = DoctorDetail.this;
	}

	@Click({ R.id.btPay, R.id.ivBack })
	protected void back(View v) {
		switch (v.getId()) {
		case R.id.btPay:
			if("1".equals(order.getType())){
				Toast.makeText(this, "预约已满，请选择其他的医生", 0).show();
				return;
			}
			Intent intent = new Intent(this, R_PaymentActivity_.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("person", order);
			intent.putExtras(bundle);
			startActivity(intent);
			
			break;
		case R.id.ivBack:
			finish();
			break;

		default:
			break;
		}
	}

	public static void Destroy() {
		((Activity) context).finish();
	}
}
