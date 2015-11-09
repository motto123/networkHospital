package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.sport_coaching.activity.SelectCoach;
/**
 * 发起活动设置地址页面
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_dv__chose_address_detailes)
public class Dv_ChoseAddressDetailesActivity extends BaseActivity {
	@ViewById(R.id.sp_chose_address_deta1)
	Spinner provinceSpinner;
	@ViewById(R.id.sp_chose_address_deta2)
	Spinner citySpinner;
	@ViewById(R.id.sp_chose_address_deta3)
	Spinner countySpinner;
	@ViewById
	ImageButton imbt_chose_address_back;
	@ViewById
	Button bt_chose_address_deta_sum;
	@ViewById
	EditText et_chose_address_deta;
	// private Spinner provinceSpinner = null; //省级（省、直辖市）
	// private Spinner citySpinner = null; //地级市
	// private Spinner countySpinner = null; //县级（区、县、县级市）
	ArrayAdapter<String> provinceAdapter = null; // 省级适配器
	ArrayAdapter<String> cityAdapter = null; // 地级适配器
	ArrayAdapter<String> countyAdapter = null; // 县级适配器
	static int provincePosition = 3;
	static String Area = "510104";
	static String address = "锦江区";
	private InputMethodManager manager;
	private String[] province = new String[] { "四川" };
	private String[][] city = new String[][] { { "成都市" } };
	private String[][][] county = new String[][][] { { // 北京
	{ "锦江区", "青羊区", "金牛区", "武侯区", "成华区", "龙泉驿区", "青白江区", "新都区", "温江县", "金堂县",
			"双流县", "郫　县", "大邑县", "蒲江县", "新津县", "都江堰市", "彭州市", "邛崃市", "崇州市" } }, };
	private String countyid[] = new String[] { "510104", "510105", "510106",
			"510107", "510108", "510112", "510113", "510114", "510115",
			"510121", "510122", "510124", "510129", "510131", "510132",
			"510181", "510182", "510183", "510184" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@AfterViews
	protected void init() {
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	/**
	 * 设置下拉框
	 */
	@AfterViews
	void setSpinner() {
		// 绑定适配器和值
		provinceAdapter = new ArrayAdapter<String>(
				Dv_ChoseAddressDetailesActivity.this,
				android.R.layout.simple_spinner_item, province);
		provinceSpinner.setAdapter(provinceAdapter);
		// provinceSpinner.setSelection(0, true); // 设置默认选中项，此处为默认选中第4个值

		cityAdapter = new ArrayAdapter<String>(
				Dv_ChoseAddressDetailesActivity.this,
				android.R.layout.simple_spinner_item, city[0]);
		citySpinner.setAdapter(cityAdapter);
		// citySpinner.setSelection(0, true); // 默认选中第0个
		countyAdapter = new ArrayAdapter<String>(
				Dv_ChoseAddressDetailesActivity.this,
				android.R.layout.simple_spinner_item, county[0][0]);
		countySpinner.setAdapter(countyAdapter);
	}

	@ItemSelect(R.id.sp_chose_address_deta1)
	public void spinner(boolean selected, int position) {
		// 省级下拉框监听
		cityAdapter = new ArrayAdapter<String>(
				Dv_ChoseAddressDetailesActivity.this,
				android.R.layout.simple_spinner_item, city[position]);
		citySpinner.setAdapter(cityAdapter);
		provincePosition = position; // 记录当前省级序号，留给下面修改县级适配器时用
	}

	@ItemSelect(R.id.sp_chose_address_deta2)
	public void spinnerWithArgument(boolean selected, int position) {
		countyAdapter = new ArrayAdapter<String>(
				Dv_ChoseAddressDetailesActivity.this,
				android.R.layout.simple_spinner_item,
				county[provincePosition][position]);
		countySpinner.setAdapter(countyAdapter);
	}

	@ItemSelect(R.id.sp_chose_address_deta3)
	public void fat(boolean selected, int position) {
		System.out.println(position);
		System.out.println(county[0][0][position].toString());
		Area = countyid[position];
		address = county[0][0][position].toString();
	}

	@Click({ R.id.imbt_chose_address_back, R.id.bt_chose_address_deta_sum })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_chose_address_deta_sum:
			// Intent intent = new Intent(Dv_ChoseAddressDetailesActivity.this,
			// Dv_ChoseAddressDetailesActivity_.class);
			// startActivity(intent);
			// 会传送一些数据到上个页面
			if (et_chose_address_deta.getText().toString().equals("")) {
				Tools.showTextToast(Dv_ChoseAddressDetailesActivity.this,
						"请输入详细地址");
			} else {
				final Intent intent = getIntent();
				intent.putExtra("address", "四川省成都市" + address);
				intent.putExtra("Area", Area);
				intent.putExtra("ress", et_chose_address_deta.getText()
						.toString());
				setResult(1, intent);
				finish();
			}
			break;
		case R.id.imbt_chose_address_back:
			finish();
			break;
		default:
			break;
		}
	}

	// 点此空白的地方,自动收回虚拟键盘
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

}
