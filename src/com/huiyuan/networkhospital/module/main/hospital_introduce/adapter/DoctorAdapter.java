package com.huiyuan.networkhospital.module.main.hospital_introduce.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.entity.Doctor;
/**
 * 
 * @ClassName:  DoctorAdapter   
 * @Description:TODO医生信息，全部是死数据+
 * @author: 邓肇均
 * @date:   2015年10月21日 下午5:01:56   
 *
 */
public class DoctorAdapter extends BaseAdapter {

	/**
	 * 医生集合
	 */
	private List<Doctor> list;

	private Context context;

	public DoctorAdapter(List<Doctor> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_doctor, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tvName);
			holder.ivMore1 = (CircularImage) convertView
					.findViewById(R.id.ivMore1);
			holder.张三 = (TextView) convertView.findViewById(R.id.张三);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
			holder.level = (TextView) convertView.findViewById(R.id.tvLevel);
			holder.ivRight = (ImageView) convertView.findViewById(R.id.ivRight);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();
		// 更新所需的数据
		Doctor d = list.get(position);
		holder.张三.setVisibility(View.GONE);
		holder.tv1.setVisibility(View.GONE);
		holder.ivMore1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.head_7190));
		holder.ivRight.setVisibility(View.GONE);
		holder.level.setVisibility(View.GONE);
		try {
			switch (position) {
			case 0:
				holder.name
						.setText("张挥武   主治医师\n科室：治未病中心\n擅长：主要研究方向为中西医结合运动创伤，长期从事膝关节镜外科工作，擅长膝关节伤病诊治");

				break;
			case 1:
				holder.name.setText("何栩   主治医师\n科室：治未病中心\n擅长：");
				break;
			case 2:
				holder.name
						.setText("罗小兵   主任医师\n科室：治未病中心\n擅长：主要擅长：运动创伤康复治疗、运动损伤风险的干预");

				break;
			case 3:
				holder.name.setText("沈海   其他\n科室：治未病中心\n擅长：");

				break;
			case 4:

				holder.name.setText("周睿   其他\n科室：治未病中心\n擅长：");
				break;

			default:
				break;
			}
			// holder.introduce.setText(d.getIntroduce());
			// holder.level.setText(d.getLevel());
			// holder.course.setText(d.getCourse());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView level;
		TextView 张三, tv1;
		ImageView ivRight;
		com.huiyuan.networkhospital.common.roundhead.CircularImage ivMore1;
	}

}
