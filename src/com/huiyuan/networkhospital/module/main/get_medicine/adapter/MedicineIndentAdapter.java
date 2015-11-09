package com.huiyuan.networkhospital.module.main.get_medicine.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.Medicine;

public class MedicineIndentAdapter extends BaseAdapter{

	private List<Medicine> list;
	private Context context;

	public MedicineIndentAdapter(List<Medicine> list, Context context) {
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
			convertView=View.inflate(context, R.layout.item_medicine, null);
			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.tvName);
			holder.priceCount=(TextView)convertView.findViewById(R.id.tvPriceCount);
			holder.sutotal=(TextView)convertView.findViewById(R.id.tvSubtotal);
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		//更新所需的数据
		Medicine m=list.get(position);
		double price= 0;
		holder.name.setText(m.getName());
		price = Float.parseFloat(m.getPrice())/Float.parseFloat(m.getNum());
		float  b   =  (float)(Math.round(price*100))/100;
		holder.priceCount.setText(b+"元* "+m.getNum()+"件");
		holder.sutotal.setText(m.getPrice()+"元");

		return convertView;
	} 

	class ViewHolder {
		TextView name;
		TextView introduce;
		TextView priceCount;
		TextView sutotal;
	}
}
