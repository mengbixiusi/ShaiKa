package com.xhhf.shaika.util;

import android.util.Log;

/**
 * 调试log工具类
 *
 * @author jaingbin
 */
public class Share {


    public final static boolean debug = true;

    public static void d(String msg) {
        if (debug)
            Log.d(Share.class.getName(), msg);
    }

    public static void i(String msg) {
        if (debug)
            Log.i(Share.class.getName(), msg);
    }

    public static void e(String msg) {
        if (debug)
            Log.e(Share.class.getName(), msg);
    }

    public static void v(String msg) {
        if (debug)
            Log.v(Share.class.getName(), msg);
    }

    public static void w(String msg) {
        if (debug)
            Log.v(Share.class.getName(), msg);
    }
}
