package com.huiyuan.networkhospital.module.main.hospital_introduce.fragment;

import org.androidannotations.annotations.EFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huiyuan.networkhospital.R;
/**
 * 
 * @ClassName:  CenterIntroducedFragment   
 * @Description:TODO只做界面显示没有任何处理
 * @author: 邓肇均
 * @date:   2015年10月21日 下午5:02:18   
 *
 */
@EFragment 
public class CenterIntroducedFragment extends Fragment {

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_center_introduced, container,
				false);
		return view;
	}
}
