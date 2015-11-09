package com.huiyuan.networkhospital.module.main.rehabilitation_tutorial.activity;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiyuan.networkhospital.R;
import com.huiyuan.networkhospital.common.util.HttpClientUtils;
import com.huiyuan.networkhospital.common.util.Tools;
import com.huiyuan.networkhospital.constant.Url;
import com.huiyuan.networkhospital.entity.dengzhaojun.HealthBean;
import com.huiyuan.networkhospital.module.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @ClassName: PlayerActivity
 * @Description:TODO康复教程详情页面
 * @author: 邓肇均
 * @date: 2015年10月21日 下午7:25:02
 *
 */

@SuppressLint("SetJavaScriptEnabled")
@EActivity(R.layout.activity_player)
public class PlayerActivity extends BaseActivity {
	private String isMyCollect;
	// private VParser mVParser;
	@ViewById
	ImageButton ibtnPlayerTitleBack;
	@ViewById
	ImageView imgb_PlayerContextVideoDown;
	@ViewById
	WebView web_PlayerContextVideoDown;

	@ViewById
	RelativeLayout rlytSupport;
	@ViewById
	TextView tvPlayerContextTitle;
	@ViewById
	TextView tvPlayerContextTime;
	@ViewById
	TextView tvPlayerContextIntroduceText;
	@ViewById
	TextView tvPlayerContextVideoDownSupportTimes;

	MediaController mediaco;
	boolean fullscreen = false;
	String aid = "";
	int zan = 0;
	Boolean zankey = true;
	HealthBean healthBean = new HealthBean();
	public static String DEFAULTPATH = "" +
	// +
	// "http://k.youku.com/player/getFlvPath/sid/44454127921571024d3da_00/st/flv/fileid/0300020100562737BA802B0197E534A245C6E3-BC32-04A1-66C7-261F0B6BABF7?K=db83a6c7b45fa730282ae29c&ctype=10&ev=1&oip=1887191773&token=3957&ep=TD0rwD3K5MMBtkdA%2BxcljfQ%2FN6CV8DvGDJaH%2BcpJokHsYtqzPaO%2BVYyLkrQCQjDbD5OXIVX%2BCEtUVursdU2AkiXrRP8TtCmYHGLElFY1iMU8L0l6Rpu48sCoEBja6whMGjWSwK53KFA%3D&ymovie=1"
	// +
//			"#EXTM3Uhttp://k.youku.com/player/getFlvPath/sid/844542987557910f76224_00/st/flv/fileid/0300020400536B7FF7A9AF004674065BAE2DA8-80FD-1639-3F9A-1E67F7B1D9E2?K=107b6abd15ae46292412612c&ctype=10&ev=1&oip=1887191773&token=7771&ep=4cSQiyMm18TOwD3V4hLMeWsky3Rgnis1SfHsL6jOa5uSDmLeLWLfQ32TFlQY2KRIVpIXLNagvZ0SCbM%2BExd9g8lD%2B91MBS%2BgQ2aD7ziYgi%2BLP1ztu%2F%2FWyCZXU0XNqWG7bfd7eKa9NM4%3D&ymovie=1http://k.youku.com/player/getFlvPath/sid/844542987557910f76224_01/st/flv/fileid/0300020401536B7FF7A9AF004674065BAE2DA8-80FD-1639-3F9A-1E67F7B1D9E2?K=3354b18e2b4ce4f1261ea1f5&ctype=10&ev=1&oip=1887191773&token=7771&ep=4cSQiyMm18TOwD3V4hLMeWsky3Rgnis1bUG2gV3R38SSDmLeLWLfQ32TFlQY2KRIVpIXLNagvZ0SCbM%2BExd9g8lD%2B91MBS%2BgQ2aD7ziYgi%2BLP1ztu%2F%2FWyCZXU0XNqWG78AoqguNgGAQ%3D&ymovie=1http://k.youku.com/player/getFlvPath/sid/844542987557910f76224_02/st/flv/fileid/0300020402536B7FF7A9AF004674065BAE2DA8-80FD-1639-3F9A-1E67F7B1D9E2?K=9522fdeb1ffbf35f2412612c&ctype=10&ev=1&oip=1887191773&token=7771&ep=4cSQiyMm18TOwD3V4hLMeWsky3Rgnis1nJWxcM%2FviBaSDmLeLWLfQ32TFlQY2KRIVpIXLNagvZ0SCbM%2BExd9g8lD%2B91MBS%2BgQ2aD7ziYgi%2BLP1ztu%2F%2FWyCZXU0XNqWG7x0XxWcXdfh4%3D&ymovie=1http://k.youku.com/player/getFlvPath/sid/844542987557910f76224_03/st/flv/fileid/0300020403536B7FF7A9AF004674065BAE2DA8-80FD-1639-3F9A-1E67F7B1D9E2?K=18eb2a43ea5dec112412612c&ctype=10&ev=1&oip=1887191773&token=7771&ep=4cSQiyMm18TOwD3V4hLMeWsky3Rgnis1tIdwgYBEWvGSDmLeLWLfQ32TFlQY2KRIVpIXLNagvZ0SCbM%2BExd9g8lD%2B91MBS%2BgQ2aD7ziYgi%2BLP1ztu%2F%2FWyCZXU0XNqWG7xYZlRlQcvsE%3D&ymovie=1"
	 "http://ips.ifeng.com/video19.ifeng.com/video06/2011/11/08/8829bf85-fb8a-4f11-9734-f1e70fecf7ab.mp4"
	;

	/**
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_player);
	}

	@AfterViews
	public void get() {
		Intent intent = getIntent();
		isMyCollect = intent.getStringExtra("isMyCollect");
		healthBean = (HealthBean) intent.getSerializableExtra("bean");
		tvPlayerContextTitle.setText(healthBean.getTitle());
		tvPlayerContextTime.setText(healthBean.getCreateTime());
		tvPlayerContextIntroduceText.setText(healthBean.getContents());
		tvPlayerContextVideoDownSupportTimes.setText("("
				+ healthBean.getPraise() + ")");
//		 DEFAULTPATH = healthBean.getURL();
		
//		 DEFAULTPATH = healthBean.getURL();
		
		
		aid = healthBean.getId();
		zan = Integer.parseInt(healthBean.getPraise());
		// Uri uri = Uri.parse(DEFAULTPATH);
		// mediaco = new MediaController(this);
		// vvPlayerContextVideo.setVideoURI(uri);
		// vvPlayerContextVideo.setMediaController(mediaco);
		// mediaco.setMediaPlayer(vvPlayerContextVideo);
		// // 让VideiView获取焦点
		// vvPlayerContextVideo.requestFocus();
		// vvPlayerContextVideo.start();
		// vvPlayerContextVideo.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stu
		// System.out.println(":点击了一下视频");
		// if (!fullscreen) {// 设置RelativeLayout的全屏模式
		// RelativeLayout.LayoutParams layoutParams = new
		// RelativeLayout.LayoutParams(
		// RelativeLayout.LayoutParams.MATCH_PARENT,
		// RelativeLayout.LayoutParams.MATCH_PARENT);
		// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// vvPlayerContextVideo.setLayoutParams(layoutParams);
		//
		// fullscreen = true;// 改变全屏/窗口的标记
		// } else {// 设置RelativeLayout的窗口模式
		// RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		// 320, 240);
		// lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		// vvPlayerContextVideo.setLayoutParams(lp);
		// fullscreen = false;// 改变全屏/窗口的标记
		// }
		// }
		// });

	}

	// @Click(R.id.ibtnPlayerTitleBack)
	// public void Back(){
	// if(isMyCollect.equals("false")){
	// Intent intents = new
	// Intent(PlayerActivity.this,RehabilitationTutorialActivity_.class);
	// startActivity(intents);
	// finish();
	// }else if (isMyCollect.equals("true")) {
	// Intent intents = new
	// Intent(PlayerActivity.this,MyCollectActivity_.class);
	// startActivity(intents);
	// finish();
	// }
	// }
	@SuppressWarnings("deprecation")
	@Click({ R.id.ibtnPlayerTitleBack, R.id.rlytShare,
			R.id.imgb_PlayerContextVideoDown, R.id.rlytSupport,
			R.id.ibtnPlayerContextVideoDownSupport })
	protected void skip(View v) {
		switch (v.getId()) {
		case R.id.ibtnPlayerTitleBack:
			// Tools.startActivity(AlterSucceedActivity.this,
			// AlterPWActivity_.class);
			finish();
			break;
		case R.id.imgb_PlayerContextVideoDown:
			System.out.println(11111);
			// DEFAULTPATH = "http://v.17173.com/v_102_610/MTc0Mzc5MjE.html";
			System.out.println(DEFAULTPATH.toString().substring(
					DEFAULTPATH.toString().lastIndexOf(".") + 1,
					DEFAULTPATH.toString().length()));
			// if (DEFAULTPATH
			// .toString()
			// .substring(DEFAULTPATH.toString().lastIndexOf(".") + 1,
			// DEFAULTPATH.toString().length()).equals("html")) {
			// Intent browserIntent = new Intent(Intent.ACTION_VIEW,
			// Uri.parse(DEFAULTPATH));
			// startActivity(browserIntent);
			WebSettings settings = web_PlayerContextVideoDown.getSettings();
			settings.setPluginState(WebSettings.PluginState.ON);
			settings.setJavaScriptEnabled(true);
			settings.setAllowFileAccess(true);
			settings.setDefaultTextEncodingName("GBK");
			web_PlayerContextVideoDown.setBackgroundColor(0);
			web_PlayerContextVideoDown.loadUrl(DEFAULTPATH);

			// web_PlayerContextVideoDown.loadDataWithBaseURL(null, DEFAULTPATH,
			// "text/html", "UTF-8", null);

			// } else {
			// Uri uri = Uri.parse(DEFAULTPATH);
			// Intent intent = new Intent(Intent.ACTION_VIEW);
			// intent.setDataAndType(
			// uri,
			// "video/"
			// + uri.toString().substring(
			// uri.toString().lastIndexOf(".") + 1,
			// uri.toString().length())
			// );
			// startActivity(intent);
			//
			// }
			break;
		case R.id.rlytShare:
			// Uri uri = Uri
			// .parse("http://static.zqgame.com/html/playvideo.html?name=http://lom.zqgame.com/v1/video/LOM_Promo~2.flv");
			// Uri uri = Uri.parse(DEFAULTPATH);
			// Intent intent = new Intent(Intent.ACTION_VIEW);
			// intent.setDataAndType(uri, "video/mp4");
			// startActivity(intent);
			// /**
			// * 换算成u3m8
			// */
			// String website = DEFAULTPATH;
			// if (TextUtils.isEmpty(website)) {
			// return;
			// }
			// new AsyncTask<Object, Void, Video>() {
			// @Override
			// protected Video doInBackground(Object... params) {
			// return mVParser.parse(String.valueOf(params[0]));
			// }
			//
			// @Override
			// protected void onPostExecute(Video result) {
			// super.onPostExecute(result);
			// String title = result.title;
			// DEFAULTPATH = result.videoUri;
			// String website = result.videoSiteUri;
			// // mTitleView.setText(title);
			// // mUriView.setText(uri);
			// }
			// }.execute(website);
			//
			//
			// Intent intent = new Intent();
			// intent.setClass(PlayerActivity.this, Play.class);
			// String path = DEFAULTPATH;
			// if (path == null) {
			// path = DEFAULTPATH;
			// }
			// intent.putExtra("path", path);
			// startActivity(intent);
			break;
		case R.id.rlytSupport:
			/**
			 * 点赞
			 */
			if (zankey) {
				zankey = false;
				System.out.println("点赞");
				RequestParams parmas = new RequestParams();
				parmas.put("act", "update");
				parmas.put("id", aid);
				HttpClientUtils.post(Url.kangfujiaocheng, parmas,
						new AsyncHttpResponseHandler() {
							@Override
							public void onFailure(Throwable arg0, String arg1) {
								// TODO Auto-generated method stub
								Tools.showTextToast(PlayerActivity.this, "赞失败");
								super.onFailure(arg0, arg1);
							}

							@Override
							public void onSuccess(String arg0) {
								// TODO Auto-generated method stub
								Tools.showTextToast(PlayerActivity.this, "赞成功");
								zan = zan + 1;
								tvPlayerContextVideoDownSupportTimes
										.setText("(" + zan + ")");
								super.onSuccess(arg0);
							}

						});
			}
			break;
		case R.id.ibtnPlayerContextVideoDownSupport:
			/**
			 * 点赞
			 */
			if (zankey) {
				zankey = false;
				System.out.println("点赞");
				RequestParams parmas2 = new RequestParams();
				parmas2.put("act", "update");
				parmas2.put("id", aid);
				HttpClientUtils.post(Url.kangfujiaocheng, parmas2,
						new AsyncHttpResponseHandler() {
							@Override
							public void onFailure(Throwable arg0, String arg1) {
								// TODO Auto-generated method stub
								Tools.showTextToast(PlayerActivity.this, "赞失败");
								super.onFailure(arg0, arg1);
							}

							@Override
							public void onSuccess(String arg0) {
								// TODO Auto-generated method stub
								Tools.showTextToast(PlayerActivity.this, "赞成功");
								zan = zan + 1;
								tvPlayerContextVideoDownSupportTimes
										.setText("(" + zan + ")");
								super.onSuccess(arg0);
							}

						});
			}
			break;
		}
	}

	// public void da(){
	// Uri uri =
	// Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Test_Movie.m4v");
	// VideoView videoView = (VideoView)this.findViewById(R.id.video_view);
	// videoView.setMediaController(new MediaController(this));
	// videoView.setVideoURI(uri);
	// videoView.start();
	// videoView.requestFocus();
	// }
	// 检查浏览器是否支持swf
	private boolean check() {
		PackageManager pm = getPackageManager();
		List<PackageInfo> infoList = pm
				.getInstalledPackages(PackageManager.GET_SERVICES);
		for (PackageInfo info : infoList) {
			if ("com.adobe.flashplayer".equals(info.packageName)) {
				return true;
			}
		}
		return false;
	}

}
