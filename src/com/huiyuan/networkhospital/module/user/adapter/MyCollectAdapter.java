package com.huiyuan.networkhospital.module.user.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.RehabilitationBean;
import com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity.ImageTextTutorialActivity_;
import com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity.PlayerActivity_;


public class MyCollectAdapter extends BaseAdapter{
	private Context context;
	private List<RehabilitationBean> collects;
	RehabilitationBean collect = null;
	boolean isVideo = true;
	
	public MyCollectAdapter(Context context,List<RehabilitationBean> collects,boolean isVideo ) {
		this.context = context;
		this.collects = collects;
		this.isVideo = isVideo;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return collects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return collects.get(position);
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
		collect = collects.get(position);
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
		viewHolder.title.setText(collect.getTitle());
		viewHolder.contents.setText(collect.getContents());
		viewHolder.time.setText(collect.getTime());
		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Log.e("dfadfadg","fasdfasdfa");
			}
		});
		addListener(convertView, position, collects);
		return convertView;
	}
	private void addListener(View convertView, final int position,
			final List<RehabilitationBean> collects) {
		(convertView)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(isVideo){
							Intent intent = new Intent(context,PlayerActivity_.class);
							intent.putExtra("isMyCollect","true");
							context.startActivity(intent);
						}else {
							Intent intent = new Intent(context,ImageTextTutorialActivity_.class);
							intent.putExtra("isMyCollect", "true");
							context.startActivity(intent);
						}
						
					}
				});
	}

}
