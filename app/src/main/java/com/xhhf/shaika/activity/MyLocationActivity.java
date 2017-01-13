package com.xhhf.shaika.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.xhhf.shaika.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 定位我的位置及搜索位置
 * Created by ZJH on 2016.11.02.
 */
@RuntimePermissions
public class MyLocationActivity extends BaseActivity {

    //定位相关的
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private SDKReceiver mReceiver;
    public LocationClient mLocationClient = null;
    private String mText;
    private Button button;

    @Override
    protected int addView() {
        return R.layout.ac_mylocation;
    }

    @Override
    protected void initView() {
        SDKInitializer.initialize(getApplicationContext());
        MyLocationActivityPermissionsDispatcher.showLocationWithCheck(this);
    }

    //关于6.0以上手机动态申请权限
    /**
     * 告知用户具体需要权限的原因
     * @param messageResId
     * @param request
     */
    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();//请求权限
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    /**
     * 被拒绝并且不再提醒,提示用户去设置界面重新打开权限
     */
    private void AskForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("当前应用缺少拍照权限,请去设置界面打开\n打开之后按两次返回键可回到该应用哦");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyLocationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showLocation() {
        //定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setAddrType("all");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        //点击显示当前位置；
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDLocation bd = new BDLocation();
                if (bd != null) {

                    Toast.makeText(MyLocationActivity.this, mText, Toast.LENGTH_SHORT).show();

                }
            }
        });

        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
    }


    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationalForLocation(PermissionRequest request) {
        showRationaleDialog("使用此功能需要打开定位的权限", request);
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForLocation() {
        Toast.makeText(MyLocationActivity.this,"你拒绝了权限，该功能不可用",Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForLocation() {
        AskForPermission();
    }

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
//				text.setText("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
                Toast.makeText(getApplicationContext(), "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置", Toast.LENGTH_SHORT).show();
            } else if (s
                    .equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
                Toast.makeText(getApplicationContext(), "key 验证成功! 功能可以正常使用", Toast.LENGTH_SHORT).show();
            } else if (s
                    .equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(getApplicationContext(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            mText = location.getAddrStr();

        }

    }

    @Override
    protected void onDestroy() {
        //退出时销毁定位
        if (mLocClient != null) {
            mLocClient.stop();
        }
        super.onDestroy();
        // 取消监听 SDK 广播
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
