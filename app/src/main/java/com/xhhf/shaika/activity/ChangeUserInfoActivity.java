package com.xhhf.shaika.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.xhhf.shaika.R;
import com.xhhf.shaika.ShaiKaApplication;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.util.Share;
import com.xhhf.shaika.util.ToastUtil;
import com.xhhf.shaika.util.Util;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZJH on 2016.11.16.
 */
public class ChangeUserInfoActivity extends BaseActivity {
    @InjectView(R.id.ac_change_userinfo_et_info)
    EditText etInfo;

    @InjectView(R.id.ac_change_userinfo_tv_title)
    TextView tvTitle;
    private String title;
    private String mToken;
    private SharedPreferences sp;

    @Override
    protected int addView() {
        return R.layout.ac_change_userinfo;
    }

    @Override
    protected void initView() {

        ButterKnife.inject(this);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        mToken = sp.getString("token", "");

        Share.i("我的token"+mToken);
        Intent intent = getIntent();
        title = intent.getStringExtra("flag");
        Share.i("flag=====" + title);

        if (title.equals("phone")) {
            tvTitle.setText("修改手机号");
        } else if (title.equals("password")) {
            tvTitle.setText("修改密码");
        } else if (title.equals("email")) {
            tvTitle.setText("修改邮箱");
        }else if(title.equals("username")){
            tvTitle.setText("修改用户名");
        }else if (title.equals("sex")){
            tvTitle.setText("修改性别");
        }else{
            tvTitle.setText("修改用户信息");
        }
    }

    @OnClick(R.id.ac_change_userinfo_btn_commit)
    protected void changeInfo() {
        final SharedPreferences.Editor edit = sp.edit();

        switch (title) {
            //修改手机号
            case "phone":
                String etUserInfoPhone = etInfo.getText().toString().trim();
                if (mToken.equals("")) {
                    ToastUtil.show(ChangeUserInfoActivity.this, "服务器数据异常");
                    return;
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", "phone");
                    hashMap.put("value", etUserInfoPhone);
                    hashMap.put("token", mToken);
                    String url = Contants.getUrl();
                    ShaiKaApplication.okHttpHelper.post(Contants.API.MODIFY, "modify",
                            Util.paramsEncoding(hashMap)).execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtil.show(ChangeUserInfoActivity.this, s);
                        }
                    });
                }

                break;
            //修改密码
            case "password":
                String etUserInfoPassword = etInfo.getText().toString().trim();
                if (mToken.equals("")) {
                    ToastUtil.show(ChangeUserInfoActivity.this, "服务器数据异常");
                    return;
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", "password");
                    hashMap.put("value", etUserInfoPassword);
                    hashMap.put("token", mToken);
                    String url = Contants.getUrl();
                    ShaiKaApplication.okHttpHelper.post(Contants.API.MODIFY, "modify",
                            Util.paramsEncoding(hashMap)).execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtil.show(ChangeUserInfoActivity.this, s);
                        }
                    });
                }

                break;
            //修改邮箱
            case "email":
                String etUserInfoEmail = etInfo.getText().toString().trim();
                if (mToken.equals("")) {
                    ToastUtil.show(ChangeUserInfoActivity.this, "服务器数据异常");
                    return;
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", "email");
                    hashMap.put("value", etUserInfoEmail);
                    hashMap.put("token", mToken);
                    String url = Contants.getUrl();
                    ShaiKaApplication.okHttpHelper.post(Contants.API.MODIFY, "modify",
                            Util.paramsEncoding(hashMap)).execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtil.show(ChangeUserInfoActivity.this, s);
                        }
                    });
                }
                break;
            case "username":
                final String etUserInfoUserName = etInfo.getText().toString().trim();
                if (mToken.equals("")) {
                    ToastUtil.show(ChangeUserInfoActivity.this, "服务器数据异常");
                    return;
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", "userName");
                    hashMap.put("value", etUserInfoUserName);
                    hashMap.put("token", mToken);
                    String url = Contants.getUrl();
                    ShaiKaApplication.okHttpHelper.post(Contants.API.MODIFY, "modify",
                            Util.paramsEncoding(hashMap)).execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtil.show(ChangeUserInfoActivity.this, s);
                            edit.putString("username",etUserInfoUserName).commit();
                        }
                    });
                }
                break;
            case "sex":
                String etUserInfoSex = etInfo.getText().toString().trim();
                if (mToken.equals("")) {
                    ToastUtil.show(ChangeUserInfoActivity.this, "服务器数据异常");
                    return;
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", "sex");
                    hashMap.put("value", etUserInfoSex);
                    hashMap.put("token", mToken);
                    String url = Contants.getUrl();
                    ShaiKaApplication.okHttpHelper.post(Contants.API.MODIFY, "modify",
                            Util.paramsEncoding(hashMap)).execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtil.show(ChangeUserInfoActivity.this, s);
                        }
                    });
                }
                break;
        }

    }
}
