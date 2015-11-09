package com.huiyuan.networkhospital.module.user;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.RehabilitationBean;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.user.adapter.MyCollectAdapter;
@EActivity(R.layout.activity_my_collect)
public class MyCollectActivity extends BaseActivity {
	public MyCollectAdapter adapter = null;
	public List<RehabilitationBean> collects = new ArrayList<RehabilitationBean>();;
	public boolean isVideo = true;
	@ViewById
	ImageButton ibtnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_my_collect);
	}
	@ViewById
	ListView lvMyColltect;

	@AfterViews
	public void showAdapter(){
		read();
		adapter = new MyCollectAdapter(MyCollectActivity.this, collects,isVideo);
		lvMyColltect.setAdapter(adapter);
	}
	private void read() {

		collects.add(new RehabilitationBean());
		collects.add(new RehabilitationBean());
	}
	@Click({R.id.ibtnBack})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.ibtnBack:
//			Tools.startActivity(DataEditeActivity.this, UserInfoActivity_.class);
			finish();
			break;
		}
	}
}
