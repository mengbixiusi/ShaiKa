package com.xhhf.shaika.http;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.xhhf.shaika.config.Contants;

import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 网络请求单例模式
 * Created by Eric on 2016/11/8.
 */

public class OkHttpHelper {
    private static OkHttpHelper mInstance;
    private Gson mGson;

    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {
        mGson = new Gson();
//        mHandler = new Handler(Looper.myLooper());
        OkGo.getInstance()
                //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                .debug("OKGO")
                //如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(Contants.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                .setReadTimeOut(Contants.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                .setWriteTimeOut(Contants.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
    }

    public static OkHttpHelper getInstance() {
        return mInstance;
    }

    /**
     * get请求
     *
     * @param url
     */
    public GetRequest get(String url) {
        GetRequest getRequest = OkGo.get(url);
        return getRequest;
    }

    /**
     * post 请求
     *
     * @param url
     * @param params 加密过的参数
     */
    public PostRequest post(String url, String tag, String params) {
        PostRequest postRequest = OkGo.post(url)
                .tag(tag)
                .upJson(params);
        return postRequest;
    }


}
