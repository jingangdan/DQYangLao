package com.dq.yanglao.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.ui.DistrictActivity;
import com.dq.yanglao.ui.DistrictActivity2;
import com.dq.yanglao.utils.ForceExitReceiver;
import com.dq.yanglao.utils.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jingang on 2018/5/2.
 */

public class Location2Fragment extends MyBaseFragment implements OnCallBackTCP, AMap.OnMarkerClickListener,
        AMap.InfoWindowAdapter,
        AMap.OnMapClickListener {
    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.ivDostrict)
    ImageView ivDostrict;

    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private Marker curShowWindowMarker;

    private double lat = 35.065287, lng = 118.3212733;
    private LatLng latLng = null;
    private MarkerOptions markerOptions = null;
    private BitmapDescriptor bitmapDescriptor = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_mian, null);
        ButterKnife.bind(this, view);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);

        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        setMyLocationStyle();
        getLocation();

        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.setOnMapClickListener(this);

        ForceExitReceiver.setOnClickListenerSOS(this);
        return view;
    }

    public void getLocation() {
        if (MyApplacation.tcpHelper != null) {
            //[DQHB*用户id*LEN*CR,device_id]
            MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(getActivity(), "uid") + "*16*CR," + SPUtils.getPreference(getActivity(), "deviceid") + "]");
        }
    }

    @OnClick(R.id.ivDostrict)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), DistrictActivity2.class));
    }

    /**
     * 实现定位蓝点
     */
    public void setMyLocationStyle() {
        //创建marker并定位到marker
        latLng = new LatLng(lat, lng);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.visible(true);
        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        markerOptions.icon(bitmapDescriptor);
        aMap.addMarker(markerOptions);

        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));//设置地图的缩放级别
        aMap.getUiSettings().setLogoBottomMargin(-50);//取消高德地图logo
        aMap.getUiSettings().setZoomControlsEnabled(false);//控制缩放控件的显示和隐藏。
    }

    @Override
    public void onCallback(String type, String msg) {
        //[DQHB*1*001A*UD,5,35.065287,118.3212733]
        String[] temp = null;
        temp = msg.split(",");//以逗号拆分
        lat = Double.parseDouble(temp[1]);
        lng = Double.parseDouble(temp[2]);

        setMyLocationStyle();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
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

}
