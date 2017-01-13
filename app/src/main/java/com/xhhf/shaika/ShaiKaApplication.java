package com.xhhf.shaika;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.xhhf.shaika.http.OkHttpHelper;


/**
 * Created by Eric on 2016/11/2.
 */
public class ShaiKaApplication extends Application {
    public static OkHttpHelper okHttpHelper;
    public static SharedPreferences sp;

    //定位
    public LocationClient mLocationClient = null;
    public MyLocationListenner myListener = new MyLocationListenner();
    public static String TAG = "MyApplication";
    private static ShaiKaApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);
        okHttpHelper = OkHttpHelper.getInstance();
        Fresco.initialize(this);
        mInstance = this;
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        sp = getSharedPreferences("config", MODE_PRIVATE);
    }

    public static ShaiKaApplication getInstance() {
        return mInstance;
    }

    /**
     * 停止定位
     */
    public void stopLocationClient() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    /**
     * 发起定位
     */
    public void requestLocationInfo() {
        setLocationOption();
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }

    /**
     * 设置百度地图的相关参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setServiceName("com.baidu.location.service_v2.9");
//        option.setPoiExtraInfo(true);
        option.setAddrType("all");
//        option.setPoiNumber(10);
        option.disableCache(true);
        mLocationClient.setLocOption(option);
    }

    /**
     * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                sendBroadCast("定位失败!");
                return;
            }
            sendBroadCast(location.getAddrStr());
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                sendBroadCast("定位失败!");
                return;
            }
            sendBroadCast(poiLocation.getAddrStr());
        }
    }

    /**
     * 得到发送广播
     *
     * @param address
     */
    public void sendBroadCast(String address) {
        stopLocationClient();
        Intent intent = new Intent(MainActivity.LOCATION_BCR);
        intent.putExtra("address", address);
        sendBroadcast(intent);
    }

}
