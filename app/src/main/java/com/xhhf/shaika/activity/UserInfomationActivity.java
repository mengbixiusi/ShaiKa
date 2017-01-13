package com.xhhf.shaika.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.util.Share;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 用户个人资料
 * Created by ZJH on 2016.11.15.
 */
public class UserInfomationActivity extends  BaseActivity{
    private RelativeLayout rlPassword;
    private SharedPreferences sp;
    private RelativeLayout rlUername;
    private RelativeLayout rlSex;

    @InjectView(R.id.ac_userinfo_tv_username)
    TextView tvUsername;

    @InjectView(R.id.ac_userinfo_tv_sex)
    TextView tvSex;

    @Override
    protected int addView() {
        return R.layout.ac_userinfo;
    }

    @Override
    protected void initView() {

        ButterKnife.inject(this);
        sp=getSharedPreferences("config",MODE_PRIVATE);

        rlUername= (RelativeLayout) findViewById(R.id.ac_userinfo_rl_nickname);
        rlSex= (RelativeLayout) findViewById(R.id.ac_userinfo_rl_sex);

        rlUername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_username=new Intent(UserInfomationActivity.this,ChangeUserInfoActivity.class);
                intent_username.putExtra("flag","username");
                startActivity(intent_username);
            }
        });

        rlSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sex=new Intent(UserInfomationActivity.this,ChangeUserInfoActivity.class);
                intent_sex.putExtra("flag","sex");
                startActivity(intent_sex);
            }
        });

//        rlPassword= (RelativeLayout) findViewById(R.id.ac_userinfo_rl_password);
//
//        rlPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_password=new Intent(UserInfomationActivity.this,ChangeUserInfoActivity.class);
//                startActivity(intent_password);
//            }
//        });
        tvUsername.setText(sp.getString("username",""));
        tvSex.setText(sp.getString("sex",""));
        Share.i("sex==="+sp.getString("sex",""));
    }

}
