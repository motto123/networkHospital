package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.huiyuan.networkhospital.module.BaseActivity;
/**
 * 创建活动主页面
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_make)
public class MakeActivity extends BaseActivity {

	@ViewById
	ImageButton imbt_make_activity_back;
	@ViewById
	Button bt_make_activity_chose_address;
	@ViewById
	TextView tv_make_activity_address;
	@ViewById
	TextView tv_make_activity_details;
	@ViewById
	Button bt_make_activity_sendorder;
	@ViewById
	Button bt_make_activity_chose_details;
	@ViewById
	CheckBox CB_make_activity;

	public static List<Dv_people> listObj = new ArrayList<Dv_people>();
	private String Area = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}
	/**
	 * 设置监听
	 */
	@Click({ R.id.imbt_make_activity_back, R.id.bt_make_activity_chose_address,
			R.id.bt_make_activity_sendorder,
			R.id.bt_make_activity_chose_details })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_make_activity_sendorder:
			// 还需要判断是否输入了以及是否有3人
			if (Area.equals("")) {
				Tools.showTextToast(MakeActivity.this, "请输入地址");
			} else if (listObj.size() == 0) {
				Tools.showTextToast(MakeActivity.this, "请添加人员信息");
			} else 
//				if (listObj.size() < 3 && !CB_make_activity.isChecked()) 
				{
/*
				new AlertDialog.Builder(MakeActivity.this)
						.setTitle("提示")
						.setMessage("小于三人并且不允许其他人参加 会收取3人的费用")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										Intent intent = new Intent(
												MakeActivity.this,
												MakeDetailsActivity_.class);
										Bundle bundle = new Bundle();
										bundle.putString(
												"address",
												""
														+ tv_make_activity_address
																.getText());
										bundle.putSerializable("persons",
												(Serializable) listObj);

										bundle.putString("Area", "" + Area);
										if (CB_make_activity.isChecked()) {
											bundle.putString("IsParticipate",
													"0");
										} else {
											bundle.putString("IsParticipate",
													"1");

										}
										intent.putExtras(bundle);
										startActivity(intent);
										MakeActivity.this.finish();
									}
								}).setNegativeButton("取消", null).create()
						.show();

			} else {*/

				Intent intent = new Intent(MakeActivity.this,
						MakeDetailsActivity_.class);
				Bundle bundle = new Bundle();
				bundle.putString("address",
						"" + tv_make_activity_address.getText());
				bundle.putSerializable("persons", (Serializable) listObj);
				bundle.putString("Area", "" + Area);
				if (CB_make_activity.isChecked()) {
					bundle.putString("IsParticipate", "0");
				} else {
					bundle.putString("IsParticipate", "1");

				}
				// .clear();
				intent.putExtras(bundle);
				startActivity(intent);
				this.finish();
			}

			break;
		case R.id.imbt_make_activity_back:
			finish();
			break;
		case R.id.bt_make_activity_chose_address:// 选择就诊地点
			Intent intent2 = new Intent(MakeActivity.this,
					Dv_ChoseAddressDetailesActivity_.class);
			startActivityForResult(intent2, 25);
			break;
		case R.id.bt_make_activity_chose_details:// 选择就诊人
			Intent intent3 = new Intent(MakeActivity.this,
					Dv_ChosePeopleActivity_.class);
			startActivityForResult(intent3, 35);
			break;
		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
    	if (listObj.size()<3) {
			CB_make_activity.setClickable(false);
    	}else {
    		CB_make_activity.setClickable(true);
		}
	}
	/**
	 * 根据下个界面返回值做相应处理
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 25://
			switch (resultCode) {// 通过resultCode来辨别activity的相应处理方法
			case 0:
				break;
			case 1:
				tv_make_activity_address.setText(data.getStringExtra("address")
						+ "\n" + data.getStringExtra("ress"));
				Area = data.getStringExtra("Area");// 地区代码
				break;
			default:
				break;
			}
			break;
		case 35:
			tv_make_activity_details.setText("");
			for (Dv_people aobj : listObj) {
				tv_make_activity_details.setText(tv_make_activity_details
						.getText() + aobj.getName() + "\n");
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}
}
