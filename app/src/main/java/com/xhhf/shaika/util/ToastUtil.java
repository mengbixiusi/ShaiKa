package com.xhhf.shaika.util;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtil工具类
 *
 * @author jiangbin
 */
public class ToastUtil {

    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }
}
