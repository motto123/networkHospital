package com.huiyuan.networkhospital.module.main.fragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.huiyuan.networkhospital.R;
/**
 * 
 * @ClassName:  testFragment   
 * @Description:TODO防止小屏手机监听被覆盖
 * @author: 邓肇均
 * @date:   2015年10月21日 下午4:57:03   
 *
 */
@EFragment
public class testFragment extends Fragment {
@ViewById
RelativeLayout rl_find;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View view=inflater.inflate(R.layout.fragment_test, container, false);
			return view;
			}

}
