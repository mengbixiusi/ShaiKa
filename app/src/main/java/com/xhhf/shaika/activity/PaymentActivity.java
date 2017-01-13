package com.xhhf.shaika.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.callback.StringCallback;
import com.xhhf.shaika.R;
import com.xhhf.shaika.ShaiKaApplication;
import com.xhhf.shaika.config.Contants;
import com.xhhf.shaika.pay.AuthResult;
import com.xhhf.shaika.pay.PayResult;
import com.xhhf.shaika.util.Share;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 付款界面
 * 作者：Eric on 2016/11/14 11:26
 * 邮箱：845505772@qq.com
 */

public class PaymentActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private Context context;

    @InjectView(R.id.pay_paid)
    protected TextView paid;

    @InjectView(R.id.pay_money)
    protected TextView tvPay;

    @InjectView(R.id.rg_pay)
    protected RadioGroup rgPay;
    @InjectView(R.id.btn_1)
    protected RadioButton rbtWC;
    @InjectView(R.id.btn_2)
    protected RadioButton rbtAli;
    @InjectView(R.id.btn_3)
    protected RadioButton rbtSk;


    @InjectView(R.id.title_tv_name)
    protected TextView tvTitle;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected int addView() {
        return R.layout.ac_payment;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        Intent intent = getIntent();
        String busiName = intent.getStringExtra("item");
        context = PaymentActivity.this;
        tvTitle.setText("商家名字");
        tvPay.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        initListener();
    }

    /**
     *
     */
    private void initListener() {
        rgPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //TODO
            }
        });
    }

    @OnClick(R.id.pay)
    protected void pay() {
        //支付
        ShaiKaApplication.okHttpHelper.get(Contants.API.PAY)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        gotoPay(s);
                    }
                });
    }

    private void gotoPay(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            final String orderInfo = jsonObject.getJSONObject("body").getString("data");
            Share.i("服务器返回参数 >>>" + orderInfo);
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(PaymentActivity.this);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Log.i("msp", result.toString());
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            Thread payThread = new Thread(payRunnable);
            payThread.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
