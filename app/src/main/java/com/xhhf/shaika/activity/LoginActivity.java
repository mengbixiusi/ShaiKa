package com.xhhf.shaika.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.xhhf.shaika.R;
import com.xhhf.shaika.ShaiKaApplication;
import com.xhhf.shaika.bean.NewResponse;
import com.xhhf.shaika.bean.User;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.http.SimpleCallback;
import com.xhhf.shaika.util.ClassPathResource;
import com.xhhf.shaika.util.Share;
import com.xhhf.shaika.util.ToastUtil;
import com.xhhf.shaika.util.Util;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 登录
 * Created by ZJH on 2016.11.02.
 */
public class LoginActivity extends BaseActivity {
    private SharedPreferences sp;
    @InjectView(R.id.et_user_account)
    protected EditText etUserName;

    @InjectView(R.id.et_login_user_psw)
    protected EditText etPassWord;

    @InjectView(R.id.ac_login_iv_edit_hide)
    protected ImageView ivHide;

    private Context context;
    //用于判断密码是否显示的标记
    private boolean flag = false;
    private int requestCode = 1;


    @Override
    protected int addView() {
        return R.layout.ac_login;
    }

    @Override
    protected void initView() {
        sp = getSharedPreferences("config", MODE_PRIVATE);
        context = LoginActivity.this;
        ButterKnife.inject(this);
        etUserName.setText(sp.getString("userName", ""));
        etUserName.setSelection(etUserName.getText().length());
    }

    @OnClick(R.id.ac_login_tv_shaika_agreement)
    protected void gotoAgreement() {
        Intent intent_agreement = new Intent(LoginActivity.this, ShaikaAgreementActivity.class);
        startActivity(intent_agreement);
    }

    @OnClick(R.id.tv_forget_psw)
    protected void forgetOnClick() {
        //忘记密码
        Intent intent_forget = new Intent(LoginActivity.this, ForgetPasswordActivity1.class);
        startActivity(intent_forget);
    }

    @OnClick(R.id.tv_onkey_regist)
    protected void reisetOnClick() {
        Intent intent_regist = new Intent(LoginActivity.this, VerificationCodeLoginActivity.class);
        startActivity(intent_regist);
    }

    @OnClick(R.id.btn_login_in)
    protected void loginOnClick() {
        final String userName = etUserName.getText().toString();
        String passWord = etPassWord.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.show(context, getString(R.string.error_username_toast));
            return;
        }
        if (!ClassPathResource.isMobileNO(userName)) {
            ToastUtil.show(context, getString(R.string.error_mobile2_toast));
            return;
        }

        if (TextUtils.isEmpty(passWord)) {
            ToastUtil.show(context, getString(R.string.error_psw_toast));
            return;
        }
        final SpotsDialog dialog=new SpotsDialog(LoginActivity.this,"拼命加载中……");
        dialog.show();

        final SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", userName);
        edit.commit();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", userName);
        hashMap.put("password", passWord);
//        String url = Contants.getUrl();
        ShaiKaApplication.okHttpHelper.post(Contants.API.ULOGIN, "login",
                Util.paramsEncoding(hashMap))
                .execute(new SimpleCallback<NewResponse<User>>(context) {
                    @Override
                    public void onSuccess(NewResponse<User> userNewResponse, Call call, Response response) {
                        dialog.dismiss();
                        //将用户信息全部保存

//                        Share.i("token>>>" + user.getToken() + "appUser userName>>>" + user.getAppUser().getUserName());
                        if (userNewResponse.success) {
                            edit.putString("userresponse", new Gson().toJson(userNewResponse));
                            User user = userNewResponse.body;
                            //保存token
                            edit.putString("token", user.getToken()).commit();

                            //用户名
                            String username = user.getAppUser().getUserName();
                            edit.putString("username", username).commit();
                            //手机号
                            String phone = user.getAppUser().getPhone();
                            edit.putString("phone", phone).commit();
                            //用户头像
                            String imageUrl = user.getAppUser().getPhoto();
                            edit.putString("imageurl", imageUrl).commit();
                            //性别
                            String sex=user.getAppUser().getSex();
                            Share.i("性别=="+sex);
                            edit.putString("sex",sex).commit();

                            Intent mIntent = new Intent();
                            mIntent.putExtra("username", username);
                            mIntent.putExtra("phone", phone);
                            mIntent.putExtra("photo", imageUrl);
                            // 设置结果，并进行传送
                            LoginActivity.this.setResult(requestCode, mIntent);

                            ToastUtil.show(context, userNewResponse.msg);
                            //登录成功关闭页面
                            finish();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(userNewResponse.msg);
                            builder.setTitle("提示");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ToastUtil.show(context, userNewResponse.msg);
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                    }
                });
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
////                ToastUtil.show(context, s);
//                        Share.i("返回值：  " + s);
//                        try {
//                            JSONObject jsonObject = new JSONObject(s);
//                            Boolean loginSuccess = jsonObject.getBoolean("success");
//                            Share.i("返回值：  " + loginSuccess);
//                            String msg = jsonObject.getString("msg");
//                            if (loginSuccess) {
//                                JSONObject jsonBody = jsonObject.getJSONObject("body");
//                                String token = jsonBody.getString("token");
//                                //将token保存下来
//                                sp.edit().putString("token", token).commit();
//                                JSONObject jsonData = jsonBody.getJSONObject("data");
//                                //用户名
//                                String username = jsonData.getString("userName");
//                                //手机号
//                                String phone = jsonData.getString("phone");
//                                //用户头像
//                                String imageUrl = jsonData.getString("photo");
//
//                                Intent mIntent = new Intent();
//                                mIntent.putExtra("username", username);
//                                mIntent.putExtra("phone", phone);
//                                mIntent.putExtra("photo", imageUrl);
//                                // 设置结果，并进行传送
//                                LoginActivity.this.setResult(requestCode, mIntent);
//
//                                ToastUtil.show(context, msg);
//                                //登录成功关闭页面
//                                finish();
//                            } else {
//                                ToastUtil.show(context, msg);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
    }

    //显示与隐藏密码的方法；
    @OnClick(R.id.ac_login_iv_edit_hide)
    protected void showPassword() {
        etPassWord.setSelection(etPassWord.getText().length());
        flag = !flag;
        if (flag) {
            ivHide.setImageResource(R.mipmap.hide);
            etPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            ivHide.setImageResource(R.mipmap.show);
            etPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
