package com.huiyuan.networkhospital.module.main.get_medicine.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.hepeng.GM_Doctor;
import com.huiyuan.networkhospital.entity.zlk.Department;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.DepartmentAdapter;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.DoctorAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


@EActivity(R.layout.activity_select_person)
public class SelectDoctorActivity extends BaseActivity {

	@ViewById
	ImageView ivBack;

	@ViewById
	Spinner spiSelect;

	@ViewById(R.id.lvDoctor)
	ListView lvDoctor;

	@ViewById
	TextView tvTitle1;

	@ViewById
	TextView xlistview_header_time;


	private ArrayList<GM_Doctor> doctors = new ArrayList<GM_Doctor>();
	private GM_Doctor doctor = null;
	private DoctorAdapter dAdapter;

	private DepartmentAdapter adapter;

	private Gson gson = new Gson();

	private AsyncHttpResponseHandler departmentHandler;

	private AsyncHttpResponseHandler doctorlistHandler,addvisitHandler;

	List<Department> list = new ArrayList<Department>();

	private String DepartmentID;

	private String ID;//复诊拿药主ID

	private String DID;//医生id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_select_person);
	}
	/**
	 * 初始化 list，把全部显示放到第一个。
	 */
	public void setdepartment() {
		Department department = new Department();
		department.setID(null);
		department.setName("全部科室");
		department.setIntroduction(null);
		list.add(department);
	}
	private void intihanlder() {
		/**
		 * 显示科室名称
		 */
		departmentHandler=new AsyncHttpResponseHandler(){
			@SuppressWarnings("unchecked")
			@Override

			public void onSuccess(String arg0) {
				try {
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("1")) {
						list.addAll((Collection<? extends Department>) gson.fromJson(jsObject.getString("Data"),
								new TypeToken<ArrayList<Department>>() {
						}.getType()));
						adapter.notifyDataSetChanged();
						Tools.LogE(list.toString());

					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
				super.onSuccess(arg0);
			}
			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(SelectDoctorActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0);
			}

		};
		/**
		 * 显示医生列表
		 */
		doctorlistHandler=new AsyncHttpResponseHandler(){
			@SuppressWarnings("unchecked")
			@Override

			public void onSuccess(String arg0) {
				try {
					Log.e("arg0",arg0 );
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("1")) {
						doctors.addAll((Collection<? extends GM_Doctor>) gson.fromJson(jsObject.getString("Data"),
								new TypeToken<ArrayList<GM_Doctor>>() {
						}.getType()));
						dAdapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
				super.onSuccess(arg0);
			}
			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(SelectDoctorActivity.this, "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0);
			}

		};
		/**
		 * 添加复诊拿药医生,判断是否能够进行聊天，可以就跳到聊天界面。不可以就显示"已有未完成的聊天"
		 */
		addvisitHandler=new AsyncHttpResponseHandler(){
			@Override

			public void onSuccess(String arg0) {
				try {
					Log.e("addvisit",arg0 );
					JSONObject jsObject = new JSONObject(arg0);
					if (jsObject.getString("Status").equals("0")) {
						ID = jsObject.getString("Data");
						GetMedicineActivity.Destroy();
						Tools.comeinHXChat(SelectDoctorActivity.this,"d"+doctor.getPhone(),doctor.getName());
						finish();
					}else if (jsObject.getString("Msg").equals("已有未完成的聊天")) {
						AlertDialog.Builder builder = new Builder(SelectDoctorActivity.this);
						builder.setMessage("与该医生有订单正在进行中");
						builder.setTitle("提示");
						builder.setPositiveButton("确认", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						builder.create().show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
				super.onSuccess(arg0);
			}
			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(getApplicationContext(), "网络连接错误，请检查网络设置后重试。",
						Toast.LENGTH_SHORT).show();
				super.onFailure(arg0);
			}

		};
	}

	@AfterViews
	protected void init(){
		intihanlder();
		tvTitle1.setText("选择医生");
		setdepartment();
		adapter=new DepartmentAdapter(list, this);
		spiSelect.setAdapter(adapter);

		initDepartment();

		dAdapter=new DoctorAdapter(doctors, this);
		lvDoctor.setAdapter(dAdapter);
		//		readAll();
	}

	@Click({R.id.ivBack})
	protected void onClick(View v){
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		}
	}
	@ItemClick(R.id.lvDoctor)
	protected void selectDoctor(int position){
		DID = doctors.get(position).getID();
		doctor = doctors.get(position);
		addvisit();
	}
	@ItemSelect(R.id.spiSelect)
	protected void selectDepartment(boolean selected, int position){
		doctors.clear();
		if(position==0){
			dAdapter=new DoctorAdapter(doctors, this);
			lvDoctor.setAdapter(dAdapter);
			readAll();
		}else {
			DepartmentID = list.get(position).getID();
			dAdapter=new DoctorAdapter(doctors, this);
			lvDoctor.setAdapter(dAdapter);
			read();
		}

	}

	@Override
	protected void onDestroy() {
		HttpClientUtils.cancelRequest(this);
		super.onDestroy();
	}

	/**
	 * 获取科室列表
	 */
	protected void initDepartment() {
		RequestParams parmas=new RequestParams();
		parmas.put("act", "getdepartment");
		HttpClientUtils.post(Url.department, parmas, departmentHandler);
	}

	/**
	 * 按科室ID取得医生列表
	 */
	protected void read() {
		RequestParams parmas=new RequestParams();
		parmas.put("act", "getdoctorlist");
		parmas.put("DepartmentID", DepartmentID);
		HttpClientUtils.post(Url.department, parmas, doctorlistHandler);
	}

	/**
	 * 新增复诊拿药信息
	 */
	protected void addvisit() {
		RequestParams parmas=new RequestParams();
		parmas.put("act", "addvisit");
		parmas.put("UID", NApplication.userid);
		parmas.put("DID", DID);
		System.out.println(Url.department+"?" +parmas);
		HttpClientUtils.post(Url.department, parmas, addvisitHandler);
	}
	/**
	 * 取得全部科室的医生列表
	 */
	protected void readAll() {
		RequestParams parmas=new RequestParams();
		parmas.put("act", "getdoctorlist");
		HttpClientUtils.post(Url.department, parmas, doctorlistHandler);
	}




}
