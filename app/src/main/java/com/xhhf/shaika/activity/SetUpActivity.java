package com.xhhf.shaika.activity;

import android.content.Intent;
import android.content.SharedPreferences;

import com.xhhf.shaika.MainActivity;
import com.xhhf.shaika.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心的设置界面
 * Created by ZJH on 2016.11.15.
 */
public class SetUpActivity extends BaseActivity {
    private SharedPreferences sp;

    @Override
    protected int addView() {
        return R.layout.ac_setup;
    }

    @Override
    protected void initView() {

        ButterKnife.inject(this);
        sp = getSharedPreferences("config", MODE_PRIVATE);
    }

    /**
     * 个人资料
     */
    @OnClick(R.id.ac_setup_rl_userinfo)
    protected void gotoUserInfo() {
        Intent intent_userinfo = new Intent(SetUpActivity.this, UserInfomationActivity.class);
        startActivity(intent_userinfo);
    }

    /**
     * 账户安全
     */
    @OnClick(R.id.ac_setup_rl_account_safe)
    protected void safeInfo() {
        Intent intent_change = new Intent(SetUpActivity.this, AccountSafeActivity.class);
        startActivity(intent_change);
    }

    /**
     * 退出当前账号
     */
    protected void exit(){
        sp.edit().clear().commit();
        Intent intent_exit=new Intent(SetUpActivity.this, MainActivity.class);
        intent_exit.putExtra("isexitlogin",true);
        startActivity(intent_exit);
    }
}
