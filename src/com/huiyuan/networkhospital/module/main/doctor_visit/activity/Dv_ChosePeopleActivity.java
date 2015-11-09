package com.huiyuan.networkhospital.module.main.doctor_visit.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.RollDelete;
import com.huiyuan.networkhospital.entity.Gm_endBean;
import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.huiyuan.networkhospital.module.main.doctor_visit.adapter.Dv_peopleAdapter;
import com.huiyuan.networkhospital.module.main.get_medicine.adapter.Gm_endAdapter;
/**
 * 发起活动设置人员list
 * @author lenovo
 *
 */
@EActivity(R.layout.activity_dv_chose_people)
public class Dv_ChosePeopleActivity extends BaseActivity {
	@ViewById
	ImageButton imbt_chose_people_back;
	@ViewById
	Button bt_chose_people;
	@ViewById(R.id.lv_chose_people)
	ListView listView;
	private Dv_peopleAdapter adapter = null;
	private List<Dv_people> persons = new ArrayList<Dv_people>();
	private ArrayList<Dv_people> items = new ArrayList<Dv_people>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "滑动可删除就诊人", 0).show();
	}

	@AfterViews
	void initBookmarkList() {
		persons = MakeActivity.listObj;
		showByMyBaseAdapter();
		/**
		 * 滑动删除
		 */
		RollDelete rl = new RollDelete(Dv_ChosePeopleActivity.this, persons,
				adapter, listView) {
			@Override
			public void deleteItem(int position) {
				// TODO Auto-generated method stub
				persons.remove(position);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onDismiss(AbsListView listView,
					int[] reverseSortedPositions) {
				// TODO Auto-generated method stub
				for (int position : reverseSortedPositions) {
					persons.remove(position);
				}
				Toast.makeText(Dv_ChosePeopleActivity.this,
						"Removed positions: ", Toast.LENGTH_SHORT).show();
			}
		};
		adapter.notifyDataSetChanged();
	}

	/**
	 * 初始化adapter
	 */
	private void showByMyBaseAdapter() {
		// TODO Auto-generated method stub
		adapter = new Dv_peopleAdapter(Dv_ChosePeopleActivity.this, persons);
		listView.setAdapter(adapter);

	}

	/**
	 * 设置监听
	 */
	@Click(R.id.imbt_chose_people_back)
	public void back() {
		final Intent intent = getIntent();
		MakeActivity.listObj = persons;
		setResult(1, intent);
		finish();
	}

	@Click(R.id.bt_chose_people)
	public void okback() {
		final Intent intent = getIntent();
		MakeActivity.listObj = persons;
		setResult(1, intent);
		finish();
	}

	@Click(R.id.imbt_chose_people_add)
	public void subadd() {
		Intent intent = new Intent(Dv_ChosePeopleActivity.this,
				Dv_ChosePeopleDetailesActivity_.class);
		startActivityForResult(intent, 1);
	}

	/**
	 * 根据下个界面返回值做相应处理
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1://
			if (resultCode == 1) {
				Dv_people a = new Dv_people();
				a.setName(data.getStringExtra("name"));
				a.setSex(data.getStringExtra("sex"));
				System.out.println(data.getStringExtra("sex"));
				a.setPhone(data.getStringExtra("phone"));
				a.setBrief(data.getStringExtra("deta"));
				a.setAge(data.getStringExtra("age"));
				persons.add(a);
				adapter.notifyDataSetChanged();
			}
			// data.getStringExtra("sex")
			// data.getStringExtra("age")
			// data.getStringExtra("phone")
			// data.getStringExtra("deta")

			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);

	}
	// private void read() {
	// // TODO Auto-generated method stub
	// // 从网络获取数据并且调用Handler来添加到persons里面
	// items = new ArrayList<Dv_people>();
	// persons.addAll(items);
	// }
}
