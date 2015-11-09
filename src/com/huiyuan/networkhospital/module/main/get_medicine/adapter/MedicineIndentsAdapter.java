package com.huiyuan.networkhospital.module.main.get_medicine.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.Medicine;

public class MedicineIndentsAdapter extends BaseAdapter{

	private List<Medicine> list;
	private Context context;

	public MedicineIndentsAdapter(List<Medicine> list, Context context) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.item_medicines, null);
			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.tvName);
			holder.tvPrice=(TextView)convertView.findViewById(R.id.tvPrice);
			holder.tvNum=(TextView)convertView.findViewById(R.id.tvNum);
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		//更新所需的数据
		Medicine m=list.get(position);
		double price= 0;
		holder.name.setText(m.getName());
		price = Float.parseFloat(m.getPrice())/Float.parseFloat(m.getNum());
		float  b   =  (float)(Math.round(price*100))/100;
		holder.tvPrice.setText(b+"元");
		holder.tvNum.setText("x"+m.getNum());

		return convertView;
	} 

	class ViewHolder {
		TextView name;
		TextView tvPrice;
		TextView tvNum;
	}
}
