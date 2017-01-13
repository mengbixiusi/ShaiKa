package com.xhhf.shaika.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/11/9 0009.
 */

public class BaseListView extends ListView {
    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseListView(Context context) {
        super(context);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
