package com.huiyuan.networkhospital.module.main.fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.MyToast;

@EFragment
public class FindFragment extends Fragment {
@ViewById
RelativeLayout rl_find;

//@ViewById
//LinearLayout linearLayout1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View view=inflater.inflate(R.layout.fragment_find, container, false);
			return view;
			}

@Click({R.id.linearLayout1})
public void click(View v){
	switch (v.getId()) {
	case R.id.linearLayout1:
		Toast.makeText(this.getActivity(), "此页面的功能正在研发中！", 0).show();
		break;
	}
}

}
