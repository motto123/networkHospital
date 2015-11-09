//package com.huiyuan.networkhospital.module.main.hospital_LBS.activity;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.SDKInitializer;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.MapPoi;
//import com.baidu.mapapi.map.MapStatusUpdate;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MyLocationConfiguration;
//import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
//import com.baidu.mapapi.map.MyLocationData;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
//import com.baidu.mapapi.overlayutil.OverlayManager;
//import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
//import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
//import com.baidu.mapapi.search.core.RouteLine;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
//import com.baidu.mapapi.search.route.DrivingRouteResult;
//import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
//import com.baidu.mapapi.search.route.PlanNode;
//import com.baidu.mapapi.search.route.RoutePlanSearch;
//import com.baidu.mapapi.search.route.TransitRoutePlanOption;
//import com.baidu.mapapi.search.route.TransitRouteResult;
//import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
//import com.baidu.mapapi.search.route.WalkingRouteResult;
//import com.huiyuan.networkhospital.R;
//import com.huiyuan.networkhospital.module.BaseActivity;
//import com.huiyuan.networkhospital.module.main.hospital_LBS.activity.MyOrientationListener.OnOrientationListener;
//
//
//public class HospitalLBSActivity extends BaseActivity  implements BaiduMap.OnMapClickListener,
//OnGetRoutePlanResultListener,OnClickListener {
//
//	private MapView mMapView;
//	private BaiduMap mBaiduMap;
//
//	private Context context;
//	private ImageButton iBback;
//
//	// 定位相关
//	private LocationClient mLocationClient;
//	private MyLocationListener mLocationListener;
//	private boolean isFirstIn = true;
//	private double mLatitude;
//	private double mLongtitude;
//	// 自定义定位图标
//	private BitmapDescriptor mIconLocation;
//	private MyOrientationListener myOrientationListener;
//	private float mCurrentX;
//	private LocationMode mLocationMode;
//	//线路规划
//	RouteLine route = null;
//	OverlayManager routeOverlay = null;
//	boolean useDefaultIcon = false;
//	private String address;
//
//	//地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
//	//如果不处理touch事件，则无需继承，直接使用MapView即可
//	//搜索相关
//	RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
//
//	/**
//	 * 省骨科医院
//	 * 纬度:30.657896    精度:104.047107
//	 */
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
//		//注意该方法要再setContentView方法之前实现  
//		SDKInitializer.initialize(getApplicationContext());    
//		setContentView(R.layout.activity_hospital_lbs);  
//		this.context=this;
//		iBback=(ImageButton)findViewById(R.id.imbt_evaluate_back);
//		iBback.setOnClickListener(this);
//		//获取地图控件引用  
//		mMapView = (MapView) findViewById(R.id.mapView);  
//		mBaiduMap = mMapView.getMap();
//		//比例尺500米
//		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
//		mBaiduMap.setMapStatus(msu);
//		// 初始化定位
//		initLocation();
//
//		//地图点击事件处理
//		mBaiduMap.setOnMapClickListener(this);
//		// 初始化搜索模块，注册事件监听
//		mSearch = RoutePlanSearch.newInstance();
//		mSearch.setOnGetRoutePlanResultListener(this);
//
//		mBaiduMap.setOnMapClickListener(new OnMapClickListener()
//		{
//
//			@Override
//			public boolean onMapPoiClick(MapPoi arg0)
//			{
//				return false;
//			}
//
//			@Override
//			public void onMapClick(LatLng arg0)
//			{
//				mBaiduMap.hideInfoWindow();
//			}
//		});
//	}
//
//
//	private void initLocation()
//	{
//
//		mLocationMode = LocationMode.NORMAL;
//		mLocationClient = new LocationClient(this);
//		mLocationListener = new MyLocationListener();
//		mLocationClient.registerLocationListener(mLocationListener);
//
//		LocationClientOption option = new LocationClientOption();
//		option.setCoorType("bd09ll");
//		option.setIsNeedAddress(true);
//		option.setOpenGps(true);
//		option.setScanSpan(1000);
//		mLocationClient.setLocOption(option);
//		// 初始化图标
//		mIconLocation = BitmapDescriptorFactory
//				.fromResource(R.drawable.navi_map_gps_locked);
//		myOrientationListener = new MyOrientationListener(context);
//
//		myOrientationListener
//		.setOnOrientationListener(new OnOrientationListener()
//		{
//			@Override
//			public void onOrientationChanged(float x)
//			{
//				mCurrentX = x;
//			}
//		});
//	}
//
//
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//		mMapView.onResume();
//	}
//
//	@Override
//	protected void onStart()
//	{
//		super.onStart();
//		// 开启定位
//		mBaiduMap.setMyLocationEnabled(true);
//		if (!mLocationClient.isStarted())
//			mLocationClient.start();
//		// 开启方向传感器
//		myOrientationListener.start();
//	}
//
//	@Override
//	protected void onPause()
//	{
//		super.onPause();
//		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//		mMapView.onPause();
//	}
//
//	@Override
//	protected void onStop()
//	{
//		super.onStop();
//
//		// 停止定位
//		mBaiduMap.setMyLocationEnabled(false);
//		mLocationClient.stop();
//		// 停止方向传感器
//		myOrientationListener.stop();
//		mMapView.onPause();
//
//	}
//
//	@Override
//	protected void onDestroy()
//	{
//		super.onDestroy();
//		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//		mMapView.onDestroy();
//	}
//
//	private class MyLocationListener implements BDLocationListener
//	{
//
//		@Override
//		public void onReceiveLocation(BDLocation location)
//		{
//
//			MyLocationData data = new MyLocationData.Builder()//
//			.direction(mCurrentX)//
//			.accuracy(location.getRadius())//
//			.latitude(location.getLatitude())//
//			.longitude(location.getLongitude())//
//			.build();
//			mBaiduMap.setMyLocationData(data);
//			// 设置自定义图标
//			MyLocationConfiguration config = new MyLocationConfiguration(
//					mLocationMode, true, mIconLocation);
//			mBaiduMap.setMyLocationConfigeration(config);
//
//			// 更新经纬度
//			mLatitude = location.getLatitude();
//			mLongtitude = location.getLongitude();
//
//			if (isFirstIn)
//			{
//				LatLng latLng = new LatLng(location.getLatitude(),
//						location.getLongitude());
//				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
//				mBaiduMap.animateMapStatus(msu);
//				isFirstIn = false;
//				address=location.getStreet();
//				
//				
//				
//				
//				Log.i("adress", "location.getAddress();"+location.getAddress().toString()+"||"+"location.getAddress();"
//						+ location.getAddress().toString()
//						+"||"+"location.getBuildingName()"+location.getBuildingName()+
//						"||"+"location.getStreetNumber();"+location.getStreetNumber()+
//						"||"+"location.getCityCode();"+location.getCityCode()+
//						"||"+"location.getCity();"+location.getCity()+"||"+""+"||"+"");
//				Toast.makeText(context, address,
//						Toast.LENGTH_LONG).show();
//			}
//
//		}
//	}
//
//
//
//	//------------以下是线路规划功能------------
//
//	/**
//	 * 发起路线规划搜索示例
//	 *
//	 */
//	public void SearchButtonProcess(View v) {
//		//重置浏览节点的路线数据
//        route = null;
//        mBaiduMap.clear();
//		// 处理搜索按钮响应
//		//		EditText editSt = (EditText) findViewById(R.id.start);
//		//		EditText editEn = (EditText) findViewById(R.id.end);
////中国四川省成都市青羊区家园南街10号
////		String from=address;
//		String from=address+"12号";
//		String to="省骨科医院";
//		//设置起终点信息，对于tranist search 来说，城市名无意义
//		PlanNode stNode = PlanNode.withCityNameAndPlaceName("成都",from );
//		PlanNode enNode = PlanNode.withCityNameAndPlaceName("成都", to);
//
//		// 实际使用中请对起点终点城市进行正确的设定
//		switch (v.getId()) {
//		case R.id.button1:
//			//开车
//			mSearch.drivingSearch((new DrivingRoutePlanOption())
//					.from(stNode)
//					.to(enNode));
//			
//			break;
//		case R.id.button2:
//			//公交
//			mSearch.transitSearch((new TransitRoutePlanOption())
//					.from(stNode)
//					.city("北京")
//					.to(enNode));
//			
//			break;
//		case R.id.button3:
//			//步行
//			mSearch.walkingSearch((new WalkingRoutePlanOption())
//					.from(stNode)
//					.to(enNode));
//			
//			break;
//
//		}
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
//	}
//
//	@Override
//	public void onGetWalkingRouteResult(WalkingRouteResult result) {
//		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//			Toast.makeText(HospitalLBSActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//		}
//		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//			result.getSuggestAddrInfo();
//			return;
//		}
//		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//			route = result.getRouteLines().get(0);
//			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
//			mBaiduMap.setOnMarkerClickListener(overlay);
//			routeOverlay = overlay;
//			overlay.setData(result.getRouteLines().get(0));
//			overlay.addToMap();
//			overlay.zoomToSpan();
//		}
//
//	}
//
//	@Override
//	public void onGetTransitRouteResult(TransitRouteResult result) {
//
//		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//			Toast.makeText(HospitalLBSActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//		}
//		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//			//result.getSuggestAddrInfo()
//			return;
//		}
//		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//			route = result.getRouteLines().get(0);
//			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
//			mBaiduMap.setOnMarkerClickListener(overlay);
//			routeOverlay = overlay;
//			overlay.setData(result.getRouteLines().get(0));
//			overlay.addToMap();
//			overlay.zoomToSpan();
//		}
//	}
//
//	@Override
//	public void onGetDrivingRouteResult(DrivingRouteResult result) {
//		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//			Toast.makeText(HospitalLBSActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//		}
//		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//			//起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//			//result.getSuggestAddrInfo()
//			return;
//		}
//		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//			route = result.getRouteLines().get(0);
//			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
//			routeOverlay = overlay;
//			mBaiduMap.setOnMarkerClickListener(overlay);
//			overlay.setData(result.getRouteLines().get(0));
//			overlay.addToMap();
//			overlay.zoomToSpan();
//		}
//	}
//
//	//定制RouteOverly
//	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {
//
//		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
//			super(baiduMap);
//		}
//
//		@Override
//		public BitmapDescriptor getStartMarker() {
//			if (useDefaultIcon) {
//				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//			}
//			return null;
//		}
//
//		@Override
//		public BitmapDescriptor getTerminalMarker() {
//			if (useDefaultIcon) {
//				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//			}
//			return null;
//		}
//	}
//
//	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {
//
//		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
//			super(baiduMap);
//		}
//
//		@Override
//		public BitmapDescriptor getStartMarker() {
//			if (useDefaultIcon) {
//				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//			}
//			return null;
//		}
//
//		@Override
//		public BitmapDescriptor getTerminalMarker() {
//			if (useDefaultIcon) {
//				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//			}
//			return null;
//		}
//	}
//
//	private class MyTransitRouteOverlay extends TransitRouteOverlay {
//
//		public MyTransitRouteOverlay(BaiduMap baiduMap) {
//			super(baiduMap);
//		}
//
//		@Override
//		public BitmapDescriptor getStartMarker() {
//			if (useDefaultIcon) {
//				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//			}
//			return null;
//		}
//
//		@Override
//		public BitmapDescriptor getTerminalMarker() {
//			if (useDefaultIcon) {
//				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//			}
//			return null;
//		}
//	}
//
//	@Override
//	public void onMapClick(LatLng point) {
//		mBaiduMap.hideInfoWindow();
//	}
//
//	@Override
//	public boolean onMapPoiClick(MapPoi arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//	@Override
//	public void onClick(View v) {
//		finish();		
//	}
//
//}