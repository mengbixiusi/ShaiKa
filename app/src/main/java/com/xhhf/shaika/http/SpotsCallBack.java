package com.xhhf.shaika.http;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.BaseRequest;
import com.xhhf.shaika.util.Share;
import com.xhhf.shaika.util.ToastUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 带有dialog的CallBack
 * @param <T>
 */
public abstract class SpotsCallBack<T> extends AbsCallback<T> {

    public Type mType;
    private Context mContext;

    private SpotsDialog mDialog;

    public SpotsCallBack(Context context) {
        mType = getSuperclassTypeParameter(getClass());
        mContext = context;
        initSpotsDialog();
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    private void initSpotsDialog() {
        mDialog = new SpotsDialog(mContext, "拼命加载中...");

    }

    @Override
    public void onBefore(BaseRequest request) {
        showDialog();
        super.onBefore(request);
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        dismissDialog();
        super.onAfter(t, e);
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        Gson gson = new Gson();
        Share.i("缓存数据：success ");
        return gson.fromJson(s, mType);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        ToastUtil.show(mContext, "没有连接到服务器");
        super.onError(call, response, e);
    }


    public void showDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }


    public void setLoadMessage(int resId) {
        mDialog.setMessage(mContext.getString(resId));
    }

}
