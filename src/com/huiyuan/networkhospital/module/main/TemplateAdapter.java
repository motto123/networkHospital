/**
 * 
 */
package com.huiyuan.networkhospital.module.main;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.huiyuan.networkhospital.entity.zlk.Department;

/**
 * @author Administrator
 *
 */
public class TemplateAdapter extends BaseAdapter {

	private List<Department> list;
	
	private Context context;
	

	public TemplateAdapter(List<Department> list, Context context) {
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
	public Object getItem(int position) {
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
//			convertView=View.inflate(context,android.R.layout.simple_list_item_1, null);
//			holder=new ViewHolder();
//			holder.course=(TextView)convertView.findViewById(android.R.id.text1);
//			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		//更新所需的数据
		Object list=getItem(position);

		return convertView;}

	class ViewHolder {
//		TextView course;
	}

}
