package com.xhhf.shaika.util;

import android.content.Context;
import android.util.Log;

import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 上传图片工具类
 * 作者：Eric on 2016/11/16 13:59
 * 邮箱：845505772@qq.com
 */

public class UploadImg {

    //    private UpLoadImgBack upLoadImgBack;
    private Context context;
    private String url;
    private ArrayList<ImageItem> images;

    /**
     * 多张图片
     *
     * @param context 上下文
     * @param url     服务器地址
     * @param images  图片集合
     */
    public UploadImg(Context context, String url, ArrayList<ImageItem> images) {
//        this.upLoadImgBack = upLoadImgBack;
        this.context = context;
        this.url = url;
        this.images = images;
//        uploadImg();
    }

    public void uploadImg(final UpLoadImgBack upLoadImgBack) {
        ArrayList<File> files = new ArrayList<>();
        if (images != null && images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                files.add(new File(images.get(i).path));
            }
        }
        OkGo.post(url)
                .tag(this)
                .addFileParams("appImg", files)           // 这种方式为同一个key，上传多个文件
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        handleResponse(responseData.data, call, response);
                        Log.i("图片地址", s);
                        upLoadImgBack.uploadImgSuccessResult(s);
                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtil.show(context, "上传出错");
                    }


                });
    }


    public interface UpLoadImgBack {
        //上传成功返回的图片路径
        public void uploadImgSuccessResult(String url);
    }
}
