package com.huiyuan.networkhospital.module.user.adapter;

/**
 *@author 邓肇均
 *@date 2014年9月4日 09:31:12
 *@describe 助理之我的圈子的适配器
 */
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.entity.Indent;

public class IndentAdapter extends BaseAdapter {
	Context context;
	private List<Indent> indents;
	Indent indent = null;

	// DynamicBean person;
	// ListAdapter adapter = null;
	// protected AsyncHttpClient client = new AsyncHttpClient();
	// ArrayList<String> imgList = new ArrayList<String>();

	public IndentAdapter(Context context, List<Indent> indents) {
		this.indents = indents;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (indents == null) ? 0 : indents.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return indents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		RelativeLayout rlytVisibility, rlytstyle;
		Button btPayment, btDel;
		TextView tvStyle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		indent = indents.get(position);
		ViewHolder viewHolder = new ViewHolder();
//		 if (convertView == null) { // convertview被注释，不被注释的话二层回复会随机被出现
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_indent, null);
		// 先设置为初始头像

		viewHolder.rlytVisibility = (RelativeLayout) convertView
				.findViewById(R.id.rlytVisibility);
		viewHolder.rlytstyle = (RelativeLayout) convertView
				.findViewById(R.id.rlytstyle);
		viewHolder.btPayment = (Button) convertView
				.findViewById(R.id.btPayment);
		viewHolder.btDel = (Button) convertView.findViewById(R.id.btDel);
		viewHolder.tvStyle = (TextView) convertView.findViewById(R.id.tvStyle);
//		convertView.setTag(viewHolder);
//		 }
//		 else {
//		viewHolder = (ViewHolder) convertView.getTag();
//		 }
		viewHolder.tvStyle.setText(indent.getStyle());
		if (viewHolder.tvStyle.getText().equals("已支付")) {
			viewHolder.btPayment.setVisibility(View.INVISIBLE);
		}
		addimglistener(convertView, position, viewHolder);
		return convertView;
	}

	private void addimglistener(final View convertView, final int j,
			final ViewHolder viewHolder) {
		// TODO Auto-generated method stub
		(convertView.findViewById(R.id.rlytstyle))
		// viewHolder.rlytstyle
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
//						Log.e("第一次",
//								viewHolder.rlytVisibility.getVisibility() + "");
						if (viewHolder.rlytVisibility.getVisibility() == View.VISIBLE) {
//							Log.e("1",
//									viewHolder.rlytVisibility.getVisibility() + "");
							viewHolder.rlytVisibility.setVisibility(View.GONE);
//							Log.e("1",
//									viewHolder.rlytVisibility.getVisibility() + "");
						} else {
							viewHolder.rlytVisibility.setVisibility(View.VISIBLE);
//							Log.e("第5次",viewHolder.rlytVisibility.getVisibility() + "");
						}
//						IndentAdapter.this.notifyDataSetChanged();
//						Log.e("viewHolder.rlytCententgetVisibility()",
//								viewHolder.rlytVisibility.getVisibility() + "");
					}
				});
	}

}
