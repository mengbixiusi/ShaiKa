package com.xhhf.shaika.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 商家界面viewpager适配器
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitle; //页卡标题集合
    private List<Fragment>mFragments;
    public MyFragmentAdapter(FragmentManager mFm , List<Fragment>mFragments, List<String> mTitle) {
        super(mFm);
        this.mFragments = mFragments;
        this.mTitle = mTitle;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
