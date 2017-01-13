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
 * 默认使用的 Callback
 * Created by eric on 2016/11/9.
 */

public abstract class SimpleCallback<T> extends AbsCallback<T> {
    public Type mType;
    private Context context;

    public SimpleCallback(Context context) {
        mType = getSuperclassTypeParameter(getClass());
        this.context = context;
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }



    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        Gson gson = new Gson();
//        Object o = gson.fromJson(s, mType);
        Share.i("缓存数据：success ");
        return gson.fromJson(s, mType);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        ToastUtil.show(context, "没有连接到服务器");
        super.onError(call, response, e);
    }
}
