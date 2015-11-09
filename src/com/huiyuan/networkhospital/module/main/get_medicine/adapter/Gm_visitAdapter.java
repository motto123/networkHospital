package com.huiyuan.networkhospital.module.main.get_medicine.adapter;

/**
 *@author 邓肇均
 *@date 2015年3月10日 18:08:21
 *@describe main1的适配器
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.BadgeView;
import com.huiyuan.networkhospital.constant.GlobalConstant;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.hepeng.GM_visitlist;
import com.huiyuan.networkhospital.entity.zlk.ReminderCount;
import com.huiyuan.networkhospital.module.huanxin.activity.ChatMainActivity;
import com.huiyuan.networkhospital.module.main.check.Check_;
import com.huiyuan.networkhospital.module.main.fragment.PreventionFragment;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.Gm_PaymentActivity_;
import com.huiyuan.networkhospital.module.main.get_medicine.activity.Gm_checkprescriptionActivity_;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Gm_visitAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<GM_visitlist> persons;
	GM_visitlist person;
	ArrayList<String> imgList = new ArrayList<String>();

	AsyncHttpResponseHandler Handler;

	private String isPay = "0";
	public String path;

	private String vid;
	
	private String activity;


	public Gm_visitAdapter(Context context, List<GM_visitlist> persons) {
		this.persons = persons;
		this.context = context;
	}
	
	public Gm_visitAdapter(Context context, List<GM_visitlist> persons,String activity) {
		this.persons = persons;
		this.context = context;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (persons == null) ? 0 : persons.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return persons.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public class ViewHolder {
		TextView name,state;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		//		 if(convertView==null){ 
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_gm_ing, null);
		viewHolder.name = (TextView) convertView
				.findViewById(R.id.tv_gm_ing_item1);
		viewHolder.state = (TextView) convertView
				.findViewById(R.id.tv_gm_ing_item2);
		convertView.setTag(viewHolder);
		//				 }else{
		viewHolder = (ViewHolder)convertView.getTag();
		//				 }
		//			viewHolder.address.setText(person.getAddress());
		viewHolder.name.setText(person.getName());
		viewHolder.state.setText(getState(person.getState()));
		if (person.getPhoto()!=null&&!person.getPhoto().equals("")) {
			path = Url.imageupload+person.getPhoto();
			Log.e("path",path );
			Tools.setimg(((CircularImage) convertView
					.findViewById(R.id.img_gm_ing_item)), path, context);
			//				MYTask myTask = new MYTask(context,convertView);
			//				myTask.execute(path);
		}else{
			((CircularImage) convertView
					.findViewById(R.id.img_gm_ing_item)).setImageResource(R.drawable.ic_head_base);
		}
		ArrayList<ReminderCount> rcs = PreventionFragment.rcs;
		String d ="d"+person.getdPhone();
		if(person.getState().equals("3")){
			for(int i = 0;i<PreventionFragment.rcs.size();i++){
				if(d.equals(rcs.get(i).getDoctor())){
					Tools.LogE("未读");
					BadgeView bd = new BadgeView(context);
					bd.setBadgeCount(rcs.get(i).getdCount());
					bd.setTargetView((CircularImage) convertView
							.findViewById(R.id.img_gm_ing_item));
				}
			}
		}
		if("CaseHistoryActivity".equals(activity)){
			return convertView;
		}
		/*
		 * 参与监听
		 */
		addListener(convertView, position, persons);

		return convertView;
	}
	private void addListener(final View convertView, final int position,
			final List<GM_visitlist> persons2) {
		convertView
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if( convertView.findViewById(R.id.bt_gm_chat).getVisibility()==View.VISIBLE||
						convertView.findViewById(R.id.bt_gm_prescription).getVisibility()==View.VISIBLE){
					convertView.findViewById(R.id.bt_gm_chat).setVisibility(View.GONE);
					convertView.findViewById(R.id.bt_gm_prescription).setVisibility(View.GONE);
				}else {
					//					if (persons2.get(position).getState().equals("0")) {
					//						AlertDialog.Builder builder = new Builder(context);
					//						builder.setMessage("您的订单医生尚未受理，请等待");
					//						builder.setTitle("提示");
					//						builder.setPositiveButton("确认", new OnClickListener() {
					//							@Override
					//							public void onClick(DialogInterface dialog, int which) {
					//								dialog.dismiss();
					//							}
					//						});
					//						builder.create().show();}else 
					if(persons2.get(position).getState().equals("0")||persons2.get(position).getState().equals("3")){
						((Button)convertView.findViewById(R.id.bt_gm_chat)).setText("聊天");
						convertView.findViewById(R.id.bt_gm_chat).setVisibility(View.VISIBLE);
						convertView.findViewById(R.id.bt_gm_prescription).setVisibility(View.GONE);
					}else if (persons2.get(position).getState().equals("1")) {
						((Button)convertView.findViewById(R.id.bt_gm_chat)).setText("记录");
						isPay = "0";
						convertView.findViewById(R.id.bt_gm_chat).setVisibility(View.VISIBLE);
						convertView.findViewById(R.id.bt_gm_prescription).setVisibility(View.VISIBLE);
					}else if (persons2.get(position).getState().equals("2")||persons2.get(position).getState().equals("4")) {
						((Button)convertView.findViewById(R.id.bt_gm_chat)).setText("记录");
						isPay = "1";
						convertView.findViewById(R.id.bt_gm_chat).setVisibility(View.VISIBLE);
						convertView.findViewById(R.id.bt_gm_prescription).setVisibility(View.VISIBLE);
					}
				}

			}
		});
		/**
		 * 聊天按钮的监听，如果text 为记录，则表示为查看聊天记录，为聊天表示进去为聊天。
		 */
		convertView.findViewById(R.id.bt_gm_chat)
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Button)convertView.findViewById(R.id.bt_gm_chat)).getText().equals("记录")) {
					//判断环信的聊天记录是否
					vid = persons.get(position).getId();
					if(Tools.matching(persons.get(position).getCreateTime())){//环信聊天服务器的历史记录
						GM_visitlist p = persons.get(position);
						context.startActivity(new Intent(context,
								ChatMainActivity.class).putExtra("userId", "d"+persons.get(position).getdPhone())
								.putExtra("toChatName", persons.get(position).getName())
								.putExtra("self", NApplication.getInstance().getUserName())
								.putExtra("record", "record"));
					}else{//自己服务器的聊天历史记录
						Intent intent = new Intent(context,Check_.class);
						intent.putExtra(GlobalConstant.KEY_DATA, persons.get(position).getId());
						intent.putExtra("flag", "d");
						context.startActivity(intent);
					}
				}else if (((Button)convertView.findViewById(R.id.bt_gm_chat)).getText().equals("聊天")) {
					Tools.comeinHXChat(context, "d"+persons.get(position).getdPhone(),persons.get(position).getName());
				}

			}
		});

		convertView.findViewById(R.id.bt_gm_prescription)
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//医生已经开药0，用户确认付款的1.
				if (isPay.equals("0") ) {
					Intent intent = new Intent(context,Gm_PaymentActivity_.class);
					NApplication.doctorname = persons2.get(position).getName();
					intent.putExtra(GlobalConstant.KEY_DATA, persons2.get(position).getId());
					context.startActivity(intent);
				}else {
					Intent intent = new Intent(context,Gm_checkprescriptionActivity_.class);
					intent.putExtra("Vid", persons2.get(position).getId());
					context.startActivity(intent);
				}

			}
		});
	}
	/**
	 * 
	 * @return
	 */
	public String getState(String state){
		String type = null;
		if (state.equals("0")||state.equals("3")) {
			type = "聊天中";
		}else if(state.equals("1")){
			type = "已开药";
		}else if (state.equals("2")) {
			type = "已付款";
		}else if (state.equals("4")) {
			type = "已发货";
		}

		return type;
	}


	/**
	 * 加载图片
	 * @author hepeng
	 *
	 */
	public class MYTask extends AsyncTask<String, Void, Bitmap> {

		private Context context;
		private View convertView;

		public MYTask(Context context,View convertView) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.convertView = convertView;
		}
		/**
		 * 表示任务执行之前的操作
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		/**
		 * 主要是完成耗时的操作
		 */
		@Override
		protected Bitmap doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			// 使用网络连接类HttpClient类王城对网络数据的提取
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(arg0[0]);
			Bitmap bitmap = null;
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					byte[] data = EntityUtils.toByteArray(httpEntity);
					bitmap = BitmapFactory
							.decodeByteArray(data, 0, data.length);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			return bitmap;
		}

		/**
		 * 主要是更新UI的操作
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			((CircularImage) convertView
					.findViewById(R.id.img_gm_ing_item)).setImageBitmap(PhotoUtil.getHeads(context, result));
		}

	}

}
