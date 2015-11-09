package com.huiyuan.networkhospital.module.main.get_medicine.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.hepeng.GM_Doctor;
import com.huiyuan.networkhospital.entity.zlk.Department;

public class DoctorAdapter extends BaseAdapter {

	/**
	 * 医生集合
	 */
	private List<GM_Doctor> doctors= new ArrayList<GM_Doctor>();

	private Context context;
	public String path;
	
	public DoctorAdapter(List<GM_Doctor> doctors, Context context) {
		super();
		this.doctors = doctors;
		this.context = context;
	}

	@Override
	public int getCount() {
		return doctors.size();
	}

	@Override
	public Object getItem(int position) {
		return doctors.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		ViewHolder holder=null;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.item_doctor, null);
			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.tvName);
			holder.level=(TextView)convertView.findViewById(R.id.tvLevel);
			
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		//更新所需的数据
		GM_Doctor d=doctors.get(position);
		
		holder.name.setText(d.getName());
		holder.level.setText(d.getLevels());
		if (d.getPhoto()!=null&&!d.getPhoto().equals("")) {
			path = Url.imageupload+d.getPhoto();
			Tools.setimg(((CircularImage) convertView
					.findViewById(R.id.ivMore1)), path, context);
		}
		return convertView;
	}


	class ViewHolder {
		TextView name;
		TextView level;
	}

}
