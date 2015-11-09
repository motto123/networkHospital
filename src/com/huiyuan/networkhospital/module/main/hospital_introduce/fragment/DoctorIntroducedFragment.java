package com.huiyuan.networkhospital.module.main.hospital_introduce.fragment;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.Doctor;
import com.huiyuan.networkhospital.module.main.hospital_introduce.adapter.DoctorAdapter;
/**
 * 
 * @ClassName:  DoctorIntroducedFragment   
 * @Description:TODO医院信息展示之医生信息列表
 * @author: 邓肇均
 * @date:   2015年10月21日 下午5:02:38   
 *
 */
@EFragment
public class DoctorIntroducedFragment extends Fragment {

	@ViewById
	Spinner spiSelect1;

	@ViewById
	TextView xlistview_header_time;
	@ViewById
	Spinner spiSelect2;

	@ViewById
	ListView listviewfragment;

	private ArrayList<Doctor> doctors;

	private DoctorAdapter dAdapter;

	private ArrayAdapter<String> adapter;

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_doctor_introduced,
				container, false);
		return view;
	}

	public void read() {
		Doctor d = null;
		for (int i = 0; i < 5; i++) {
			d = new Doctor(i, "张挥武", "治未病中心", "主治医师", "科室主任", "精通外科", "每周星期一");
			doctors.add(d);
			d = new Doctor(i, "张三", "外科", "主治医生", "科室主任", "精通外科", "每周星期一");
		}

	}

	@AfterViews
	protected void init() {
		context = this.getActivity();
		doctors = new ArrayList<Doctor>();
		dAdapter = new DoctorAdapter(doctors, this.getActivity());
		read();
		listviewfragment.setAdapter(dAdapter);
	}
//	
//		@ItemClick(R.id.listviewfragment)
//		protected void skipDoctorDetails(int position) {
//			Tools.startActivity(this.getActivity(), DoctorDetailsActivity_.class);
//		}

}
