/**
 * 
 */
package com.huiyuan.networkhospital.module.main.doctor_visit.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.R.string;
import com.huiyuan.networkhospital.entity.LookOver;

/**
 * @author Administrator
 *
 */
public class Dv_personAdapter extends BaseAdapter {

	private List<LookOver> list;
	
	private Context context;

	public Dv_personAdapter(List<LookOver> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public LookOver getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=View.inflate(context,R.layout.item_dv_person, null);
			holder=new ViewHolder();
			holder.tvName=(TextView)convertView.findViewById(R.id.tvName);
			holder.tvSex=(TextView)convertView.findViewById(R.id.tvSex);
			holder.tvPhone=(TextView)convertView.findViewById(R.id.tvPhone);
			holder.tvIntroduced=(TextView)convertView.findViewById(R.id.tvIntroduced);
			holder.tvCreateTime=(TextView)convertView.findViewById(R.id.tvCreateTime);
			holder.tvState=(TextView)convertView.findViewById(R.id.tvState);
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		//复用item时 item中的数据也被复用了，初始化item再使用
		holder.tvState.setText("");
		//更新所需的数据
		LookOver person=getItem(position);
		holder.tvName.setText(context.getResources().getString(string.name)+person.getName());
		if (person.getSex().equals("true")) {
			holder.tvSex.setText(context.getResources().getString(string.lr_chose_people_detailes_sex)+"男");
		} else if(person.getSex().equals("false")){
			holder.tvSex.setText(context.getResources().getString(string.lr_chose_people_detailes_sex)+"女");
		}
		
		if("0".equals(person.getIsRefund())){//未退款

		}else if("1".equals(person.getIsRefund())){//已退款
			holder.tvState.setText("退款成功");
		}else if("2".equals(person.getIsRefund())){//退款失败
			holder.tvState.setText("退款失败");
		}else if("3".equals(person.getIsRefund())){//退款中
			holder.tvState.setText("退款中");
		}
		holder.tvCreateTime.setText(context.getResources().getString(string.create_time)+person.getCreateTime());
		if(NApplication.userid.equals(person.getUID())){//是此帐号创建的，显示隐私内容
			holder.tvPhone.setText(context.getResources().getString(string.phone)+person.getPhone());
			holder.tvIntroduced.setText(context.getResources().getString(string.case_introduced)+person.getBrief());
			holder.tvPhone.setVisibility(View.VISIBLE);
			holder.tvIntroduced.setVisibility(View.VISIBLE);
		}else{
			holder.tvPhone.setVisibility(View.GONE);
			holder.tvIntroduced.setVisibility(View.GONE);
		}

		return convertView;}

	class ViewHolder {
		TextView tvName;
		TextView tvSex;
		TextView tvPhone;
		TextView tvIntroduced;
		TextView tvCreateTime;
		TextView tvState;
	}

}
