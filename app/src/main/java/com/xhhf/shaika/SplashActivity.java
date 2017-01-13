package com.xhhf.shaika;

import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xhhf.shaika.activity.BaseActivity;

/**
 * 启动页
 * 作者：Eric on 2016/11/15 09:03
 * 邮箱：845505772@qq.com
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int addView() {
        return R.layout.ac_splash;
    }

    public static final String TAG = SplashActivity.class.getSimpleName();

    private ImageView mSplashItem_iv = null;

    /*
     * (non-Javadoc)
     *
     * @see com.itau.jingdong.ui.base.BaseActivity#initView()
     */
    @Override
    protected void initView() {
        mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        Handler  mHandler = new Handler(getMainLooper());


        // TODO Auto-generated method stub
        Animation translate = AnimationUtils.loadAnimation(this,
                R.anim.splash_loading);
        translate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                //启动homeactivty，相当于Intent
                openActivity(MainActivity.class);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                SplashActivity.this.finish();
            }
        });
        mSplashItem_iv.setAnimation(translate);
    }
}
