package com.xhhf.shaika.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.activity.PresentLocationActivity;
import com.xhhf.shaika.activity.SearchActivity;
import com.xhhf.shaika.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商户
 * Created by eric on 16-10-28.
 */
public class BusinessFragment extends BaseFragment {

    private TabLayout mTab = null;
    private ViewPager mViewPager = null;
    private DeliciousFoodFragment mFragment_01 = null;
    private DeliciousFoodFragment mFragment_02 = null;
    private DeliciousFoodFragment mFragment_03 = null;
    private DeliciousFoodFragment mFragment_04 = null;
    /**
     * 标题
     */
    private List<String> mTitle = null;
    /**
     * 页面
     */
    private List<Fragment> mFragments = null;
    private String SUCCESS = "享美食";
    private String NEWS = "休闲时刻";
    private String BUY = "爱玩乐";
    private String CALL = "全部";

    @InjectView(R.id.fragment_busi_tv_location)
    protected TextView tvLocation;

    @Override
    public int addView() {
        return R.layout.fragment_busi;
    }

    private MyFragmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busi, container, false);

        mTab = (TabLayout) view.findViewById(R.id.fragbusi_tab_top);
        mViewPager = (ViewPager) view.findViewById(R.id.fragbusi_lvp);
//        mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());

        mTitle = new ArrayList<>();
        mFragments = new ArrayList<>();
        /**页面标题集合*/
        mTitle.add(SUCCESS);
        mTitle.add(NEWS);
        mTitle.add(BUY);
        mTitle.add(CALL);
        /**切换页面集合*/
        mFragment_01 = new DeliciousFoodFragment();
        mFragments.add(mFragment_01);
        mFragment_02 = new DeliciousFoodFragment();
        mFragments.add(mFragment_02);
        mFragment_03 = new DeliciousFoodFragment();
        mFragments.add(mFragment_03);
        mFragment_04 = new DeliciousFoodFragment();
        mFragments.add(mFragment_04);
        if (mAdapter == null) {
            mAdapter = new MyFragmentAdapter(getActivity().getSupportFragmentManager(), mFragments, mTitle);
            /**设置Tablayout模式*/
            mTab.setTabMode(TabLayout.MODE_FIXED);
            mViewPager.setAdapter(mAdapter);
            /**绑定TabLayout与ViewPager*/
            mTab.setupWithViewPager(mViewPager);
        } else {
            mTab.setTabMode(TabLayout.MODE_FIXED);
            mViewPager.setAdapter(mAdapter);
            /**绑定TabLayout与ViewPager*/
            mTab.setupWithViewPager(mViewPager);
        }


        ButterKnife.inject(this, view);
        System.out.println(getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE).getString("location", ""));
        tvLocation.setText(getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE).getString("location", "当前位置"));
        return view;
    }

    public void getView(View view) {
        ButterKnife.inject(this, view);

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.rl_business_location)
    public void onClick() {
        Intent intent_location = new Intent(getActivity(), PresentLocationActivity.class);
        startActivity(intent_location);
    }
}
