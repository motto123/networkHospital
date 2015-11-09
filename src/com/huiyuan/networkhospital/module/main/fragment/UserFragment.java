package com.huiyuan.networkhospital.module.main.fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huiyuan.networkhospital.NApplication;
import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.roundhead.CircularImage;
import com.huiyuan.networkhospital.common.util.PhotoUtil;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.constant.User;
import com.huiyuan.networkhospital.module.main.case_history.activity.CaseHistoryActivity_;
import com.huiyuan.networkhospital.module.main.registration.activity.R_Myorder_;
import com.huiyuan.networkhospital.module.user.ChoseAddress_;
import com.huiyuan.networkhospital.module.user.IndentListActivity_;
import com.huiyuan.networkhospital.module.user.MyCollectActivity_;
import com.huiyuan.networkhospital.module.user.UserInfoActivity_;
import com.huiyuan.networkhospital.module.usermanager.ForgetActivity_;

@EFragment
public class UserFragment extends Fragment {

	public String path;
	private ProgressDialog dialog;

	@ViewById
	RelativeLayout rlytMyInfo;
	@ViewById
	RelativeLayout rlytChangepassword;
	@ViewById
	RelativeLayout rlytMyNews;
	@ViewById
	RelativeLayout rlytOrderDetails;
	@ViewById
	RelativeLayout rlytMyCase;
	@ViewById
	RelativeLayout rlytMyCollect;
	@ViewById
	RelativeLayout rlytMyAddress;
	@ViewById
	CircularImage ivHead;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_user,container,false);
		return view;
	}

	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (NApplication.ImgheadBitmap!=null) {
			ivHead.setImageBitmap(NApplication.ImgheadBitmap);
		}else {
			getImage();
		}
	}	

	@Click({R.id.rlytMyInfo,R.id.rlytChangepassword,R.id.rlytMyNews,R.id.rlytOrderDetails,
		R.id.rlytMyCase,R.id.rlytMyCollect,
		R.id.rlytMyAddress})
	protected void skip(View v){
		switch (v.getId()) {
		case R.id.rlytMyInfo:
			//个人信息
			Tools.startActivity(this.getActivity(), UserInfoActivity_.class);
			break;
		case R.id.rlytChangepassword:
			//修改密码
			User user = new User(getActivity()); 
			Intent intent = new Intent(this.getActivity(), ForgetActivity_.class);
			intent.putExtra("phone",user.getPhone());
			getActivity().startActivity(intent);
			break;
		case R.id.rlytMyNews:
			//我的信息
//			Tools.startActivity(this.getActivity(), MessageListActivity_.class);
			Tools.startActivity(this.getActivity(), R_Myorder_.class);
			break;
		case R.id.rlytOrderDetails:
			//我的订单
			Tools.startActivity(this.getActivity(), IndentListActivity_.class);
			break;
		case R.id.rlytMyCase:
			//我的病例
			Tools.startActivity(this.getActivity(), CaseHistoryActivity_.class);
			break;
		case R.id.rlytMyCollect:
			Tools.startActivity(this.getActivity(), MyCollectActivity_.class);
			break;
		case R.id.rlytMyAddress:
			Tools.startActivity(this.getActivity(), ChoseAddress_.class);
			break;

		}
	}

	/**
	 * 获取头像
	 */
	public void getImage(){
		if (NApplication.photo!=null&&!NApplication.photo.equals("")) {
			path = Url.imageupload+NApplication.photo;
			Log.e("path",path );
			dialog = ProgressDialog.show(getActivity(),"提示", "正在加载中",true,true);
			MYTask myTask = new MYTask(getActivity());
			myTask.execute(path);
		}
	}

	public class MYTask extends AsyncTask<String, Void, Bitmap> {

		private Context context;

		public MYTask(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
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
			if (result!=null) {
				NApplication.ImgheadBitmap = PhotoUtil.getHead(context, result);
//				NApplication.photo = null;
				ivHead.setImageBitmap(NApplication.ImgheadBitmap);
			}
			if (dialog != null) {
				dialog.dismiss();
			}
		}

	}
}
