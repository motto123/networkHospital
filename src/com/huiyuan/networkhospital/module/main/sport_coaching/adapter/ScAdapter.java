package com.huiyuan.networkhospital.module.main.sport_coaching.adapter;

/**
 * 此处用的是复诊拿药里面选择医生界面的listview布局
 * 
 */
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.common.widget.BadgeView;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.ScBean;
import com.huiyuan.networkhospital.entity.zlk.ReminderCount;
import com.huiyuan.networkhospital.module.main.fragment.PreventionFragment;
/**
 * 
 * @ClassName:  ScAdapter   
 * @Description:TODO运动指导主界面的适配器
 * @author: 邓肇均
 * @date:   2015年10月22日 下午5:06:01   
 *
 */
public class ScAdapter extends BaseAdapter {
	Map<String, String> map = new HashMap<String, String>();
	Context context;
	ListAdapter adapter = null;
	private List<ScBean> persons;
	ScBean person;
	ArrayList<String> imgList = new ArrayList<String>();
	public String path;

	public ScAdapter(Context context, List<ScBean> persons) {
		this.persons = persons;
		this.context = context;
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
		TextView name, address;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		person = persons.get(position);
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_gm_ing, null);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.tv_gm_ing_item1);
			viewHolder.address = (TextView) convertView
					.findViewById(R.id.tv_gm_ing_item2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(person.getCName());
		if (person.getCPhoto()!=null&&!person.getCPhoto().equals("")) {
			path = Url.imageupload+person.getCPhoto();
			Tools.setimg(((CircularImage) convertView
					.findViewById(R.id.img_gm_ing_item)), path, context);
//			Log.e("path",path );
//			MYTask myTask = new MYTask(context,convertView);
//			myTask.execute(path);
		}else{
			((CircularImage) convertView
					.findViewById(R.id.img_gm_ing_item)).setImageResource(R.drawable.ic_head_base);
		}
		viewHolder.address.setTextColor(Color.RED);
		switch (person.getState()) {
		case "0":
			viewHolder.address.setText("进行中");
			break;
		case "1":
			viewHolder.address.setText("教练确认完成");
			break;
		case "2":
			viewHolder.address.setText("用户确认完成");
			break;
		case "3":
			viewHolder.address.setText("聊天进行中");
			ArrayList<ReminderCount> rcs = PreventionFragment.rcs;
			String c ="c"+person.getcPhone();
			for(int i = 0;i<PreventionFragment.rcs.size();i++){
				if(c.equals(rcs.get(i).getCoach())){
				BadgeView bd = new BadgeView(context);
				bd.setBadgeCount(rcs.get(i).getcCount());
				bd.setTargetView((CircularImage) convertView
						.findViewById(R.id.img_gm_ing_item));
				}
			}
			break;
		default:
			break;
		}
		return convertView;
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
