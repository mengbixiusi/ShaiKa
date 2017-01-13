package com.xhhf.shaika.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.xhhf.shaika.R;
import com.xhhf.shaika.activity.AboutUsActivity;
import com.xhhf.shaika.activity.LoginActivity;
import com.xhhf.shaika.activity.MyCollentionActivity;
import com.xhhf.shaika.activity.MyCommentActivity;
import com.xhhf.shaika.activity.MyMemberShipCardActivity;
import com.xhhf.shaika.activity.MyOrderActivity;
import com.xhhf.shaika.activity.MyPurseActivity;
import com.xhhf.shaika.activity.PaymentActivity;
import com.xhhf.shaika.activity.SetUpActivity;
import com.xhhf.shaika.activity.UserInfomationActivity;
import com.xhhf.shaika.util.ToastUtil;
import com.xhhf.shaika.view.CircularImage;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * 我的界面
 * Created by eric on 16-10-28.
 */
@RuntimePermissions
public class MineFragment extends BaseFragment {
    private SharedPreferences sp;

    @InjectView(R.id.fragment_mine_cover_user_photo)
    CircularImage coverUserPhoto;

    @InjectView(R.id.fragment_mine_tv_username)
    TextView tvUsername;

    private int REQUEST_CODE;
    private String token;

    @Override
    public int addView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void getView(View view) {
        ButterKnife.inject(this, view);
        ZXingLibrary.initDisplayOpinion(getActivity());
        //设置默认图像
        coverUserPhoto.setImageResource(R.mipmap.memberphoto);

        sp = getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE);
        token = sp.getString("token", "");
        if (!token.equals("")) {
            tvUsername.setText(sp.getString("username", ""));
        }
    }

    @OnClick(R.id.fragment_mine_tv_username)
    protected void gotoLogin() {
        if (sp.getString("token", "").equals("")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
            REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent_login = new Intent(getActivity(), UserInfomationActivity.class);
            startActivity(intent_login);
        }
    }

    @OnClick(R.id.fragment_mine_cover_user_photo)
    protected void clickUserPhoto() {
        if (sp.getString("token", "").equals("")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
            REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent_login = new Intent(getActivity(), UserInfomationActivity.class);
            startActivity(intent_login);
        }
    }

    /**
     * 我的收藏
     */
    @OnClick(R.id.fragment_mine_rl_collection)
    protected void gotoMyCollection() {
        if (sp.getString("token", "").equals("")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent = new Intent(getActivity(), MyCollentionActivity.class);
            startActivity(intent);
        }
    }

    //付款
    @OnClick(R.id.fragment_mine_ll_scanpay)
    protected void pay() {
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        startActivity(intent);
    }

    //测试界面，暂时跳转到用户反馈。（这里是加盟合作的点击事件）
    @OnClick(R.id.fragment_mine_rl_cooperation)
    protected void gotoFeedback() {
//        Intent intent_feedback = new Intent(getActivity(), FeedBackActivity.class);
//        startActivity(intent_feedback);
    }

    //我的钱包
    @OnClick(R.id.fragment_mine_rl_purse)
    protected void gotoPurse() {
        Intent intent_purse = new Intent(getActivity(), MyPurseActivity.class);
        startActivity(intent_purse);
    }

    //我的订单
    @OnClick(R.id.fragment_mine_rl_order)
    protected void gotoOrder() {
        if (sp.getString("token", "").equals("")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent_order = new Intent(getActivity(), MyOrderActivity.class);
            startActivity(intent_order);
        }
    }

    /**
     * 关于我们
     */
    @OnClick(R.id.fragment_mine_rl_about_us)
    protected void gotoAboutUs() {
        Intent intent_aboutus = new Intent(getActivity(), AboutUsActivity.class);
        startActivity(intent_aboutus);
    }

    //联系客服
    @OnClick(R.id.fragment_mine_rl_service)
    protected void gotoService() {
//        ToastUtil.show(getActivity(), "联系客服");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String tel = getString(R.string.service_phone);
        intent.setData(Uri.parse(tel));
        startActivity(intent);
    }

    //我的点评
    @OnClick(R.id.fragment_mine_rl_comment)
    protected void gotoMyComment() {
        if (sp.getString("token", "").equals("")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent_comment = new Intent(getActivity(), MyCommentActivity.class);
            startActivity(intent_comment);
        }
    }

    //设置
    @OnClick(R.id.frament_mine_iv_setting)
    protected void gotoSetting() {
        if (sp.getString("token", "").equals("")) {
//            new android.support.v7.app.AlertDialog.Builder(SetUpActivity.this)
//                    .setTitle("提示")
//                    .setMessage("请先登录")
//                    .setPositiveButton("确定", null)
//                    .show();
            //进行是否登录判断
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent_setting = new Intent(getActivity(), SetUpActivity.class);
            startActivity(intent_setting);
        }

    }

    //会员卡
    @OnClick(R.id.fragment_mine_rl_membership_card)
    protected void gotoMembershipCard() {

        if (sp.getString("token", "").equals("")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            REQUEST_CODE = 1;
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent_card = new Intent(getActivity(), MyMemberShipCardActivity.class);
            startActivity(intent_card);
        }
    }

    //扫一扫
    @OnClick(R.id.fragment_mine_ll_scan)
    protected void mfScan() {
//        Intent intent_scan=new Intent(getActivity(), CaptureActivity.class);
//        startActivityForResult(intent_scan,REQUEST_CODE);
        MineFragmentPermissionsDispatcher.needCameraWithCheck(this);
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
        if (requestCode == 0) {
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

        if (requestCode == 1) {
            //接收返回的数据
            if (data != null) {
                String username = data.getStringExtra("username");
                String phone = data.getStringExtra("phone");
                tvUsername.setText(username);
                //保存到本地
                sp.edit().putString("username", username).commit();
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

    @Override
    public void onStart() {
        super.onStart();
        String newName=sp.getString("username","");
        if (!newName.equals("")){
            tvUsername.setText(newName);
        }else{
            tvUsername.setText("您的用户名");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void needCamera() {
        Intent intent_scan = new Intent(getActivity(), CaptureActivity.class);
        REQUEST_CODE = 0;
        startActivityForResult(intent_scan, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MineFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onShowRationaleCamera(PermissionRequest request) {
        showRationaleDialog("使用此功能需要打开照相机的权限", request);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraPermissionDenied() {
        Toast.makeText(getActivity(), "你拒绝了权限，该功能不可用", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onNeverAskAgain() {
        AskForPermission();
    }
}
