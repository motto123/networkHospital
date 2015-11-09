package com.huiyuan.networkhospital.module.main.hospital_introduce.fragment;

import org.androidannotations.annotations.EFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huiyuan.networkhospital.R;


@EFragment
public class CourseIntroducedFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_course_introduced, container,
				false);
		return view;
	}
	
	
	
}
