package com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.RehabilitationBean;
import com.huiyuan.networkhospital.entity.dengzhaojun.HealthBean;
import com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity.ImageTextTutorialActivity_;
import com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity.PlayerActivity_;

/**
 * 
 * @ClassName:  RehabilitationAdapter   
 * @Description:TODO康复教程listview的适配器
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:03:39   
 *
 */
public class RehabilitationAdapter extends BaseAdapter{
	private Context context;
	private List<HealthBean> tutorials;
	HealthBean tutorial = null;
	boolean isVideo = true;
	
	public RehabilitationAdapter(Context context,List<HealthBean> tutorials,boolean isVideo ) {
		this.context = context;
		this.tutorials = tutorials;
		this.isVideo = isVideo;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tutorials.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tutorials.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public class ViewHolder {
		TextView title,contents,time;
	} 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		tutorial = tutorials.get(position);
		ViewHolder viewHolder = new ViewHolder();
		if(convertView==null){ 
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_rehabilitation_tutorial, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.tvTutorialTextTitle);
			viewHolder.contents = (TextView) convertView
					.findViewById(R.id.tvTutorialTextContents);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.tvTutorialTextTime);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.title.setText(tutorial.getTitle());
		viewHolder.contents.setText(tutorial.getContents());
		viewHolder.time.setText(tutorial.getCreateTime());
		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Log.e("dfadfadg","fasdfasdfa");
			}
		});
		addListener(convertView, position, tutorials);
		return convertView;
	}
	private void addListener(View convertView, final int position,
			final List<HealthBean> tutorials) {
		(convertView)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(isVideo){
							Intent intent = new Intent(context,PlayerActivity_.class);

							Bundle bundle = new Bundle();
							bundle.putSerializable("bean",
									tutorials.get(position));
							intent.putExtras(bundle);
							context.startActivity(intent);
						}else {
							Intent intent = new Intent(context,ImageTextTutorialActivity_.class);
							context.startActivity(intent);
						}
						
					}
				});
	}

}
