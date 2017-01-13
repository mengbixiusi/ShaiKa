package com.xhhf.shaika;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.xhhf.shaika.activity.BaseActivity;
import com.xhhf.shaika.bean.Key;
import com.xhhf.shaika.bean.NewResponse;
import com.xhhf.shaika.bean.Tab;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.fragment.BusinessFragment;
import com.xhhf.shaika.fragment.HomeFragment;
import com.xhhf.shaika.fragment.MineFragment;
import com.xhhf.shaika.fragment.ShaiKaFragment;
import com.xhhf.shaika.http.SimpleCallback;
import com.xhhf.shaika.util.Share;
import com.xhhf.shaika.util.ToastUtil;
import com.xhhf.shaika.weiget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity {

    private LayoutInflater mInflater;

    private FragmentTabHost mTabhost;
    private List<Tab> mTabs = new ArrayList<>();
    //定位相关
    private BroadcastReceiver broadcastReceiver;
    public static String LOCATION_BCR = "location_bcr";
    private Button locBtn;
    private TextView locInfo;
    private ShaiKaApplication application;
    private boolean isExitLogin;

    @Override
    protected int addView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        application = (ShaiKaApplication) super.getApplication();
        Intent intent=getIntent();

        //判断是从哪里登录
        isExitLogin=intent.getBooleanExtra("isexitlogin",false);

        //检查权限
        MainActivityPermissionsDispatcher.needsLocationPermissionWithCheck(this);
        boolean first = ShaiKaApplication.sp.getBoolean("first", false);
        if (!first) {
            getKey();
        }
        initTab();
    }

    private void getKey() {
        ShaiKaApplication.okHttpHelper.get(Contants.API.key)
                .execute(new SimpleCallback<NewResponse<Key>>(MainActivity.this) {
                    @Override
                    public void onSuccess(NewResponse<Key> keyNewResponse, Call call, Response response) {
                        Key body = keyNewResponse.body;
                        SharedPreferences.Editor edit = ShaiKaApplication.sp.edit();
                        edit.putBoolean("first", true);
                        edit.putString("publicKey", body.getPublicKey());
                        edit.commit();
                    }

                    @Override
                    public void onCacheSuccess(NewResponse<Key> keyNewResponse, Call call) {
                        onSuccess(keyNewResponse, call, null);
                    }
                });
    }

    private void initTab() {

        Tab tab_home = new Tab(R.string.home, R.drawable.selector_icon_home, HomeFragment.class);
        Tab tab_hot = new Tab(R.string.business, R.drawable.selector_icon_hot, BusinessFragment.class);
        Tab tab_category = new Tab(R.string.shaika, R.drawable.selector_icon_category, ShaiKaFragment.class);
        Tab tab_mine = new Tab(R.string.mine, R.drawable.selector_icon_mine, MineFragment.class);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_mine);


        mInflater = LayoutInflater.from(this);
        mTabhost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (Tab tab : mTabs) {

            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));

            tabSpec.setIndicator(buildIndicator(tab));

            mTabhost.addTab(tabSpec, tab.getFragment(), null);

        }

        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

//                if (tabId == getString(R.string.cart)) {
//
//                    refData();
//                }

            }
        });

        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        if (isExitLogin) {
            mTabhost.setCurrentTab(3);
        }else{
            mTabhost.setCurrentTab(0);
        }


    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return view;
    }

    private void initialize() {
        registerBroadCastReceiver();//注册广播
    }

    private void initializeViews() {
    }

    private void initializeListeners() {
        application.requestLocationInfo();
    }

    /**
     * 注册一个广播，监听定位结果，接受广播获得地址信息
     */
    private void registerBroadCastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String address = intent.getStringExtra("address");
                Share.i("定位》》》" + address);
                String mAddress = "";
                if (!TextUtils.isEmpty(address)) {
                    mAddress = address.replace("中国", "");
                }
//                String mAddress = address.replace("中国", "");
                getSharedPreferences("config", MODE_PRIVATE).edit().putString("location", mAddress).commit();
            }
        };
        IntentFilter intentToReceiveFilter = new IntentFilter();
        intentToReceiveFilter.addAction(LOCATION_BCR);
        registerReceiver(broadcastReceiver, intentToReceiveFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * 告知用户具体需要权限的原因
     *
     * @param messageResId
     * @param request
     */
    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(MainActivity.this)
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("当前应用缺少拍照权限,请去设置界面打开\n打开之后按两次返回键可回到该应用");
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

    //关于安卓6.0以上手机需要动态申请权限
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsLocationPermission() {
        initialize();
        initializeViews();
        initializeListeners();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowLocationRationale(PermissionRequest request) {
        showRationaleDialog("部分功能需要使用相关的权限", request);
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
        ToastUtil.show(MainActivity.this, "您拒绝了该权限，功能暂不可使用");
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onNeverAskAgain() {
        AskForPermission();
    }
}
