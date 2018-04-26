package com.dq.yanglao.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.dq.yanglao.R;
import com.dq.yanglao.adapter.MyExpandableListViewAdapter;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.ui.CurrencyActivity;
import com.dq.yanglao.ui.DistrictActivity;
import com.dq.yanglao.ui.DistrictActivity2;
import com.dq.yanglao.ui.InformationActivity;
import com.dq.yanglao.utils.DensityUtil;
import com.dq.yanglao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 定位
 * Created by jingang on 2018/4/12.
 */

public class LocationFragment extends MyBaseFragment implements
        LocationSource,
        AMapLocationListener,
        AMap.OnMarkerClickListener,
        AMap.InfoWindowAdapter,
        AMap.OnMapClickListener {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.iv_navigation1)
    ImageView ivNavigation1;
    @Bind(R.id.iv_navigation2)
    ImageView ivNavigation2;

    @Bind(R.id.buttom1)
    Button buttom1;
    @Bind(R.id.expendlist)
    ExpandableListView mExpandableListView;

    @Bind(R.id.ivMsg)
    ImageView ivMsg;
    @Bind(R.id.ivFootprint)
    ImageView ivFootprint;
    @Bind(R.id.ivDostrict)
    ImageView ivDostrict;

    @Bind(R.id.mapView)
    MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private Marker curShowWindowMarker;

    //定位需要的数据
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    private Button butToday, butYesterday, butYesterday2, butCustom, butHpCancel;

    // 列表数据
    private List<String> mGroupNameList = null;
    private List<List<String>> mItemNameList = null;
    // 适配器
    private MyExpandableListViewAdapter mAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_location, null);
        ButterKnife.bind(this, view);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        setMyLocationStyle();

//        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//                //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取
//            }
//        });

        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.setOnMapClickListener(this);

        // 初始化数据
        initData();
        setExpandableListView();
        mExpandableListView.setGroupIndicator(null);

        return view;
    }

    /**/
    public void setExpandableListView() {
        // 为ExpandableListView设置Adapter
        mAdapter = new MyExpandableListViewAdapter(getActivity(), mGroupNameList, mItemNameList);
        mExpandableListView.setAdapter(mAdapter);

        // 监听组点击
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (mGroupNameList.get(groupPosition).isEmpty()) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Toast.makeText(getActivity(),
                        mAdapter.getGroup(groupPosition) + ":"
                                + mAdapter.getChild(groupPosition, childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @OnClick({R.id.iv_navigation1, R.id.iv_navigation2, R.id.ivMsg, R.id.ivDostrict, R.id.ivFootprint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navigation1:
                toggleLeftSliding();
                break;

            case R.id.iv_navigation2:
                toggleRightSliding();
                break;
            case R.id.ivMsg:
                //消息
                startActivity(new Intent(getActivity(), InformationActivity.class));
                break;
            case R.id.ivDostrict:
                startActivity(new Intent(getActivity(), DistrictActivity2.class));
                break;
            case R.id.ivFootprint:
                final Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hp_footprint, null);
                bottomDialog.setContentView(contentView);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
                params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getActivity(), 16f);
                params.bottomMargin = DensityUtil.dp2px(getActivity(), 8f);
                contentView.setLayoutParams(params);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();

                butToday = (Button) bottomDialog.findViewById(R.id.butToday);
                butYesterday = (Button) bottomDialog.findViewById(R.id.butYesterday);
                butYesterday2 = (Button) bottomDialog.findViewById(R.id.butYesterday2);
                butCustom = (Button) bottomDialog.findViewById(R.id.butCustom);
                butHpCancel = (Button) bottomDialog.findViewById(R.id.butHpCancel);

                butToday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomDialog.dismiss();
                    }
                });

                butYesterday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomDialog.dismiss();
                    }
                });

                butYesterday2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomDialog.dismiss();
                    }
                });

                butCustom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //自定义
                        // startActivity(new Intent());
                        bottomDialog.dismiss();
                    }
                });

                butHpCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomDialog.dismiss();
                    }
                });
                break;
        }
    }

    /*左侧侧边栏*/

    private void toggleLeftSliding() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            drawerLayout.openDrawer(Gravity.START);
        }
    }

    /*右侧侧边栏*/
    private void toggleRightSliding() {
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.closeDrawer(Gravity.END);
        } else {
            drawerLayout.openDrawer(Gravity.END);
        }
    }

    // 初始化数据
    private void initData() {
        // 组名
        mGroupNameList = new ArrayList<String>();
        mGroupNameList.add("历代帝王");
        mGroupNameList.add("华坛明星");
        mGroupNameList.add("国外明星");
        mGroupNameList.add("政坛人物");

        mItemNameList = new ArrayList<List<String>>();
        // 历代帝王组
        List<String> itemList = new ArrayList<String>();
        itemList.add("唐太宗李世民");
        itemList.add("秦始皇嬴政");
        itemList.add("汉武帝刘彻");
        itemList.add("明太祖朱元璋");
        itemList.add("宋太祖赵匡胤");
        mItemNameList.add(itemList);
        // 华坛明星组
        itemList = new ArrayList<String>();
        itemList.add("范冰冰 ");
        itemList.add("梁朝伟");
        itemList.add("谢霆锋");
        itemList.add("章子怡");
        itemList.add("杨颖");
        itemList.add("张柏芝");
        mItemNameList.add(itemList);
        // 国外明星组
        itemList = new ArrayList<String>();
        itemList.add("安吉丽娜•朱莉");
        itemList.add("艾玛•沃特森");
        itemList.add("朱迪•福斯特");
        mItemNameList.add(itemList);
        // 政坛人物组
        itemList = new ArrayList<String>();
        itemList.add("唐纳德•特朗普");
        itemList.add("金正恩");
        itemList.add("奥巴马");
        itemList.add("普京");
        mItemNameList.add(itemList);
    }

    @OnClick({R.id.buttom1, R.id.butManage, R.id.butCurrency, R.id.butOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttom1:
                ToastUtils.getInstance(getActivity()).showMessage("aaaaa");
                break;
            case R.id.butManage:
                //管理
                break;
            case R.id.butCurrency:
                //通用
                startActivity(new Intent(getActivity(), CurrencyActivity.class));
                break;
            case R.id.butOut:
                //退出
                break;
        }
    }

    /**
     * 实现定位蓝点
     */
    public void setMyLocationStyle() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        // 设置定位监听
        aMap.setLocationSource(this);
        //设置地图的放缩级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        aMap.getUiSettings().setLogoBottomMargin(-50);//取消高德地图logo
        aMap.getUiSettings().setZoomControlsEnabled(false);//控制缩放控件的显示和隐藏。

        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔*****************************，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。

        myLocationStyle.showMyLocation(true);

    }

    /**
     * 定位蓝点展现模式
     */
    public void setmyLocationType() {
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

    }

    /**
     * 自定义定位蓝点图标
     */
    public void setmyLocationIcon() {
        //方法自5.1.0版本后支持
        //  myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。

        //myLocationStyle.myLocationIcon(BitmapDescriptor myLocationIcon);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。

        //MyLocationStyle anchor(float u, float v);//设置定位蓝点图标的锚点方法。

//        MyLocationStyle strokeColor(int color);//设置定位蓝点精度圆圈的边框颜色的方法。
//        MyLocationStyle radiusFillColor(int color);//设置定位蓝点精度圆圈的填充颜色的方法。

        // MyLocationStyle strokeWidth(float width);//设置定位蓝点精度圈的边框宽度的方法。

//        MyLocationStyle.LOCATION_TYPE_FOLLOW ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（默认1秒1次定位）
//        MyLocationStyle.LOCATION_TYPE_MAP_ROTATE;//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（默认1秒1次定位）
//        MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE;//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（默认1秒1次定位）默认执行此种模式。

        //MyLocationStyle interval(long interval);//设置定位频次方法，单位：毫秒，默认值：1000毫秒，如果传小于1000的任何值将按照1000计算。该方法只会作用在会执行连续定位的工作模式上。
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //点击其它地方隐藏infoWindow
        if (curShowWindowMarker != null) {
            curShowWindowMarker.hideInfoWindow();
        }
    }

    /* marker点击事件 */
    @Override
    public boolean onMarkerClick(Marker marker) {
        curShowWindowMarker = marker;
        if (curShowWindowMarker.isInfoWindowShown()) {
            curShowWindowMarker.hideInfoWindow();
        } else {
            curShowWindowMarker.showInfoWindow();
        }
        return true;
    }

    /* 自定义窗体 */
    @Override
    public View getInfoWindow(final Marker marker) {
        View infoWindow = getActivity().getLayoutInflater().inflate(R.layout.infowindow, null);//display为自定义layout文件
        TextView name = (TextView) infoWindow.findViewById(R.id.name);
        name.setText("设备名称:" + marker.getTitle());
        LatLng l = marker.getPosition();// 获取标签的位置
//        TextView dis = (TextView) infoWindow.findViewById(R.id.dis);
//        float distance = AMapUtils.calculateLineDistance(l, la) / 1000;// 调用函数计算距离
//        description = "距您所在位置：" + distance + "KM" + "\n";
//        dis.setText(description);
        TextView des = (TextView) infoWindow.findViewById(R.id.des);
        des.setText("简介：" + marker.getSnippet());
        // 此处省去长篇代码

        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //定位回调  在回调方法中调用“mListener.onLocationChanged(amapLocation);”可以在地图上显示系统小蓝点。
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                System.out.println("11111 = " + aMapLocation.getLongitude());
                System.out.println("22222 = " + aMapLocation.getLatitude());

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("定位AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        //激活定位
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(getActivity());
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);

            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        //停止定位
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
