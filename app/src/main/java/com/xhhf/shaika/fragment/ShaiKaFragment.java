package com.xhhf.shaika.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.xhhf.shaika.R;
import com.xhhf.shaika.activity.AdActivity;
import com.xhhf.shaika.activity.PaymentActivity;
import com.xhhf.shaika.activity.PresentLocationActivity;
import com.xhhf.shaika.activity.SearchActivity;
import com.xhhf.shaika.adapter.ShaiKaExpandAdapter;
import com.xhhf.shaika.bean.Banner;
import com.xhhf.shaika.bean.ExpandableGroup;
import com.xhhf.shaika.util.Share;
import com.xhhf.shaika.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.xhhf.shaika.R.drawable.expand_group_divider;

/**
 * 晒卡
 * Created by eric on 16-10-28.
 */
public class ShaiKaFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener {

    @InjectView(R.id.elv_showtime)
    protected ExpandableListView mElvShowitem;

    @InjectView(R.id.fragment_shaika_tv_location)
    protected TextView tvLocation;

    private Context context;
    public static final String TAG = "ShaiKaFragment";

    private List<Banner> mBanners;

    @InjectView(R.id.slider_shaika)
    protected SliderLayout mSliderLayout;

    private RelativeLayout rlLocation;
    private Intent mIntent;
    private int requestCode;
    private String mLocation;

    /**
     * 可扩展数据
     * @return
     */
    private List<ExpandableGroup> groups;
    private List<ExpandableGroup> childs;

    @Override
    public int addView() {
        return R.layout.fragment_shaika;
    }

    @Override
    public void getView(View view) {
        ButterKnife.inject(this, view);
        context = getActivity();

        /**
         * 定位的处理
         */
        rlLocation= (RelativeLayout) view.findViewById(R.id.rl_shaika_location);
        getBanner();
        initSlider(mBanners);

        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent();
                mIntent.setClass(getActivity(),
                        PresentLocationActivity.class);
                requestCode = 0;
                startActivityForResult(mIntent, requestCode);
            }
        });
        mElvShowitem.setGroupIndicator(null);


        /**
         * 可扩展listview的处理
         */
        final ShaiKaExpandAdapter adapter = new ShaiKaExpandAdapter(context);
        mElvShowitem.setAdapter(adapter);
        mElvShowitem.expandGroup(0);

        //可扩展listview子条目点击事件
        mElvShowitem.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("item",groupPosition);
                startActivity(intent);
                return true;
            }
        });



        System.out.println(getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE).getString("location", "111"));
        tvLocation.setText(getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE).getString("location", "当前位置"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            mLocation = data.getStringExtra("location");
        }else{
            mLocation = "";
        }
        // 根据上面发送过去的请求码来区别
        switch (requestCode) {
            case 0:
                tvLocation.setText(mLocation);
                break;
            default:
                break;
        }
    }

    /**
     * 添加banner 图
     */
    private void getBanner() {
        Banner banner1 = new Banner();
        banner1.setImgUrl("http://attach.bbs.miui.com/forum/201611/05/010617o77x702xc7c7yaa2.jpg");
        Banner banner2 = new Banner();
        banner2.setImgUrl("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/01/0E/ChMkJ1bKwXSII0EcAAiHkbkXKP4AALGYgH8xm0ACIep698.jpg");

        Banner banner3 = new Banner();
        banner3.setImgUrl("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/03/ChMkJlbKxtqIF93BABJ066MJkLcAALHrQL_qNkAEnUD253.jpg");
        Banner banner = new Banner();
        banner.setImgUrl("");

        mBanners = new ArrayList<>();
        mBanners.add(banner1);
        mBanners.add(banner2);
        mBanners.add(banner3);
    }


    /**
     * 轮播图
     * @param mBanner
     */
    private void initSlider(List<Banner> mBanner) {
        if (mBanner != null) {
            for (Banner banner : mBanner) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(this.getActivity());
                defaultSliderView.image(banner.getImgUrl());
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


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Share.i("轮播图点击事件" + slider.getBundle().getString("url"));
        ToastUtil.show(context, "点击图片>>>>" + slider.getBundle().getString("url"));

        Intent sliderIntent = new Intent(context, AdActivity.class);
        sliderIntent.putExtra("url",slider.getBundle().getString("url"));
        startActivity(sliderIntent);
    }

    /**
     * 搜索
     */
    @OnClick(R.id.rl_search)
    protected void search(){
        Intent searchIntent = new Intent(getActivity(),SearchActivity.class);
        startActivity(searchIntent);
    }

    @Override
    public void onDestroy() {
        //清除
        mSliderLayout.stopAutoCycle();
        super.onDestroy();
    }
}
