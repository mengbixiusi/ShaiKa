package com.xhhf.shaika.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.xhhf.shaika.R;
import com.xhhf.shaika.ShaiKaApplication;
import com.xhhf.shaika.activity.AdActivity;
import com.xhhf.shaika.activity.BusinessDetailActivity;
import com.xhhf.shaika.activity.HomeNotiActivity;
import com.xhhf.shaika.activity.ListActivity;
import com.xhhf.shaika.activity.PresentLocationActivity;
import com.xhhf.shaika.activity.SearchActivity;
import com.xhhf.shaika.adapter.MyListener;
import com.xhhf.shaika.bean.Banner;
import com.xhhf.shaika.bean.HomeUpper;
import com.xhhf.shaika.bean.Key;
import com.xhhf.shaika.bean.NewResponse;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.util.MyResource;
import com.xhhf.shaika.util.Share;
import com.xhhf.shaika.util.ToastUtil;
import com.xhhf.shaika.view.PullToRefreshLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.xhhf.shaika.R.id.slider;

/**
 * 首页
 * Created by eric on 16-10-28.
 */
@RuntimePermissions
public class HomeFragment extends BaseFragment
        implements BaseSliderView.OnSliderClickListener {

    private Context context;
    @InjectView(R.id.lv_show)
    protected ListView mLvShow;

    private static final String TAG = "HomeFragment";
    @InjectView(R.id.gv_icon)
    protected GridView mGvIcon;

    //商家 图标
    private int[] iconImgRes = {R.mipmap.food, R.mipmap.movie, R.mipmap.salo, R.mipmap.wash, R.mipmap.play,
            R.mipmap.missyou, R.mipmap.coffeetea, R.mipmap.yongqi, R.mipmap.holiland, R.mipmap.caking};
    private String[] nameRes = {"美食", "丽人", "娱乐", "休闲", "ktv", "好想你", "荼咖茶", "永琪", "好利来", "卡其诺"};

    //Gradview数据
    private List<HomeUpper.BodyBean.TypeListBean> typeRes;//分类
    private List<HomeUpper.BodyBean.MerchantListBean> iconRes; //商家

    //轮播图数据
    private List<HomeUpper.BodyBean.ImgListBean> imgList;

    @InjectView(R.id.iv_guess1)
    protected ImageView ivGuess1;
    @InjectView(R.id.iv_guess2)
    protected ImageView ivGuess2;

    private List<Banner> mBanners;

    @InjectView(slider)
    protected SliderLayout mSliderLayout;

    //返回值，int类型随意值
    private int REQUEST_CODE = 101;

    private HomeGvAdapter gvAdapter;
    private HomeLvAdapter lvAdapter;
    private HomeUpper homeUpper;

    @Override
    public int addView() {
        return R.layout.activity_scrollview;
    }


    @Override
    public void getView(View view) {
        context = getActivity();
        ButterKnife.inject(this, view);
        ZXingLibrary.initDisplayOpinion(getActivity());
        //初始化数据
        initData();

        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(new MyListener());
        getBanner();
        ShaiKaApplication.okHttpHelper
                .get(Contants.API.BANNER)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Share.i("返回参数 》》》" + s);
                    }
                });
        initSlider(mBanners);
//        initView();
        onItemClick();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Log.e(TAG, "initData: initData", null);
        ShaiKaApplication.okHttpHelper
                .get(MyResource.HOME_TOP_URL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e(TAG, "onSuccess: s*******" + s, null);
                        Gson gson = new Gson();
                        homeUpper = gson.fromJson(s, HomeUpper.class);
                        Log.i(TAG, "*********initData: " + s);
                        typeRes = homeUpper.body.typeList;
                        iconRes = homeUpper.body.merchantList;
                        imgList = homeUpper.body.imgList;
                        initView();
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        onSuccess(s,call,null);
                    }
                });
    }

    private void onItemClick() {
        //分类点击事件
        mGvIcon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ListActivity.class);
                if (position > 4){
                    intent.putExtra("item",iconRes.get(position - 3));
                }else{
                    intent.putExtra("item",iconRes.get(position));
                }
                startActivity(intent);
            }
        });

        //特惠专区点击事件
        ivGuess1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guessIntent = new Intent(context, AdActivity.class);
                startActivity(guessIntent);
            }
        });
        ivGuess2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guessIntent = new Intent(context, AdActivity.class);
                startActivity(guessIntent);
            }
        });

        //猜你喜欢点击事件
        mLvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(context, BusinessDetailActivity.class);
                startActivity(detailIntent);
            }
        });

    }

    /**
     * 个人消息
     */
    @OnClick(R.id.ib_home_noti)
    protected void noti() {
        Intent notiIntent = new Intent(context, HomeNotiActivity.class);
        startActivity(notiIntent);
    }

    /**
     * 搜索
     */
    @OnClick(R.id.rl_search)
    protected void search() {
        Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
        startActivity(searchIntent);
    }


    /**
     * 首页扫码
     */
    @OnClick(R.id.ib_home_scanner)
    protected void scan() {
        HomeFragmentPermissionsDispatcher.needsPermissonWithCheck(this);
    }

    /**
     * 首页定位
     */
    @OnClick(R.id.fragment_home_location)
    protected void myLocation() {
        Intent intent_location = new Intent(context, PresentLocationActivity.class);
        startActivity(intent_location);
    }

    /**
     * 处理二维码返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.show(getActivity(), "解析结果" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(getActivity(), "解析失败");
                }
            }
        }
    }

    /**
     * 告知用户具体需要权限的原因
     *
     * @param messageResId
     * @param request
     */
    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                intent.setData(Uri.parse("package:" + getActivity().getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
        builder.create().show();
    }


    /**
     * 添加banner 图
     */
    private void getBanner() {
        Banner banner1 = new Banner();
        banner1.setImgUrl("http://attach.bbs.miui.com/forum/201611/05/010617o77x702xc7c7yaa2.jpg");
//        banner1.setImgUrl(imgList.get(0).src);
        Banner banner2 = new Banner();
        banner2.setImgUrl("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/01/0E/ChMkJ1bKwXSII0EcAAiHkbkXKP4AALGYgH8xm0ACIep698.jpg");
//        banner2.setImgUrl(imgList.get(1).src);

        Banner banner3 = new Banner();
        banner3.setImgUrl("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/03/ChMkJlbKxtqIF93BABJ066MJkLcAALHrQL_qNkAEnUD253.jpg");
//        banner3.setImgUrl(imgList.get(2).src);
        Banner banner = new Banner();
        banner.setImgUrl("");

        mBanners = new ArrayList<>();
        mBanners.add(banner1);
        mBanners.add(banner2);
        mBanners.add(banner3);
    }


    /**
     * 轮播图
     *
     * @param mBanner
     */
    private void initSlider(List<Banner> mBanner) {
        if (mBanner != null) {
            for (Banner banner : mBanner) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(this.getActivity());
                defaultSliderView.image(banner.getImgUrl());
//                textSliderView.description(banner.getName());//描述信息
                defaultSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                defaultSliderView.bundle(new Bundle());
                defaultSliderView.getBundle().putString("url", banner.getImgUrl());
                defaultSliderView.setOnSliderClickListener(this);
                mSliderLayout.addSlider(defaultSliderView);
            }
        }

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
    }

    /**
     * 绑定数据
     */
    private void initView() {
        gvAdapter = new HomeGvAdapter();
        mGvIcon.setAdapter(gvAdapter);

        lvAdapter = new HomeLvAdapter();
        mLvShow.setAdapter(lvAdapter);

        //猜你喜欢测试用图
        ivGuess1.setBackgroundResource(R.mipmap.danmai_shiren);
        ivGuess2.setBackgroundResource(R.mipmap.danmai_shiren_001);
        Log.i(TAG, "initView: ---------viewfinish--------");


    }

    @Override
    public void onDestroy() {
        //清除
        mSliderLayout.stopAutoCycle();
        super.onDestroy();
    }

    /**
     * 轮播图点击事件
     *
     * @param slider
     */
    @Override
    public void onSliderClick(BaseSliderView slider) {
//        slider.setOnSliderClickListener()
        Share.i("轮播图点击事件" + slider.getBundle().getString("url"));
        ToastUtil.show(context, "点击图片>>>>" + slider.getBundle().getString("url"));

        Intent sliderIntent = new Intent(context, AdActivity.class);
        sliderIntent.putExtra("url",slider.getBundle().getString("url"));
        startActivity(sliderIntent);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void needsPermisson() {
        Intent intent_scan = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent_scan, REQUEST_CODE);
    }


    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationale(PermissionRequest request) {
        showRationaleDialog("使用此功能需要打开照相机的权限", request);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void permissionDenied() {
        Toast.makeText(getActivity(), "你拒绝了权限，该功能不可用", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void neverAskAgain() {
        AskForPermission();
    }

    /**
     * 猜你喜欢适配器
     */
    public class HomeLvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LvViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_home_listview, null);
                holder = new LvViewHolder();
                holder.ivBrand = (ImageView) convertView.findViewById(R.id.iv_brand);
                holder.tvCategary = (TextView) convertView.findViewById(R.id.tv_categary);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.tvSaledPrice = (TextView) convertView.findViewById(R.id.tv_sale_price);
                holder.tvSaled = (TextView) convertView.findViewById(R.id.tv_saled);
                convertView.setTag(holder);
            } else {
                holder = (LvViewHolder) convertView.getTag();
            }

            holder.ivBrand.setBackgroundResource(R.mipmap.missyou);
            return convertView;
        }
    }


    /**
     * 分类适配
     */
    public class HomeGvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return iconRes.size() + typeRes.size();
        }

        @Override
        public Object getItem(int position) {
            return position>4?iconRes.get(position):typeRes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GvViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_home_gridview, null);
                holder = new GvViewHolder();
                holder.ivIocn = (SimpleDraweeView) convertView.findViewById(R.id.iv_icon);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (GvViewHolder) convertView.getTag();
            }

            if (position > 3){
                HomeUpper.BodyBean.MerchantListBean bean = iconRes.get(position - 4);
                    holder.ivIocn.setImageURI(Uri.parse(MyResource.BASE_URL + "/" + bean.icon));
                    holder.tvName.setText(bean.name);

            }else{
                HomeUpper.BodyBean.TypeListBean bean = typeRes.get(position);
                    holder.ivIocn.setImageURI(Uri.parse(MyResource.BASE_URL + "/" + bean.icon));
                    holder.tvName.setText(bean.name);

            }

//            holder.ivIocn.setImageResource(iconImgRes[position]);
//            holder.tvName.setText(nameRes[position]);
            return convertView;
        }
    }

    private class GvViewHolder {
        public SimpleDraweeView ivIocn;
        public TextView tvName;
    }

    private class LvViewHolder {
        public ImageView ivBrand;
        public TextView tvSaled;
        public TextView tvSaledPrice;
        public TextView tvPrice;
        public TextView tvCategary;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
