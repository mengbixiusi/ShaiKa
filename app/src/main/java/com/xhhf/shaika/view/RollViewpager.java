package com.xhhf.shaika.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


/**
 * Created by  on 2016/9/14.
 */
public class RollViewpager extends ViewPager {

    private static final String TAG = "RollViewpager";
    //数据源
    private List<String> topImageUrls;

//    private BitmapUtils bitmapUtils;
    private MyRollPagerAdapter adapter;
    private OnItemClickListener onItemClickListener;

    public RollViewpager(Context context) {
        super(context);
//        bitmapUtils = new BitmapUtils(context);
    }

    public RollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 接受图片数据
     * @param topImageUrls
     */
    public void setTopImageUrls(List<String> topImageUrls) {
        this.topImageUrls = topImageUrls;
        //设置适配器
    }

    /**
     * 试验图片
     *
     */
    private int[] imgRes;
    public void setImgRes(int[] imgRes){
        this.imgRes = imgRes;
    }

    private static final  int auto_play_next = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case auto_play_next:
                    //切换下一张图片
                    int nextItem = (getCurrentItem()+1)%imgRes.length;
                    setCurrentItem(nextItem);
                    handler.sendEmptyMessageDelayed(auto_play_next,2000);
                    break;
            }
        }
    };

    /**
     * 开始轮播
     */
    public void startRoll() {
        //设置适配器
        if(adapter==null){
            adapter = new MyRollPagerAdapter();
            setAdapter(adapter);
        }
        //开始轮播
        handler.sendEmptyMessageDelayed(auto_play_next,2000);
        Log.i(TAG, "startRoll: -------startRool---------");
    }



    class MyRollPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgRes.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            //展示真实图片 -- 网络url
            //<1>T View : 展示的控件
            //<2>String uri: 图片的url
//            bitmapUtils.display(imageView,topImageUrls.get(position));
            imageView.setBackgroundResource(imgRes[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 依附到窗体 : 当前控件添加到屏幕上
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 从窗体移除:当前控件从屏幕上移除
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    private float downX = 0;
    private int downTime = 0;
    private float downY = 0;
    /**
     * 滑动优化
     * 1. 如果当前界面=0,向右滑动.应该将事件交给父亲
     * 2. 如果当前界面=topImageUrs.size-1,向左滑动.应该将事件交给父亲
     * 3. 剩余情况,自己处理
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //子Viewpager可以检测到down与move.cancle
        Log.i(TAG, "onTouchEvent: "+ev.getAction());
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                downTime = (int) System.currentTimeMillis();
                getParent().requestDisallowInterceptTouchEvent(true);
                //停止轮播
                handler.removeMessages(auto_play_next);
                break;
            case MotionEvent.ACTION_MOVE:

                float moveX = ev.getX();
                float moveY = ev.getY();

                float disX =Math.abs(moveX - downX);
                float disY = Math.abs(moveY-downY);

                //如果是水平方向滑动,按照原逻辑处理
                if(disX>disY){
                    if(getCurrentItem() == 0 && moveX > downX){
                        //1. 如果当前界面=0,向右滑动.应该将事件交给父亲
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else  if(getCurrentItem() == imgRes.length-1 && moveX < downX){
                        //2. 如果当前界面=topImageUrs.size-1,向左滑动.应该将事件交给父亲
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
                        // 3. 剩余情况,自己处理
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else{
                    //如果是竖直方向滑动,交由父亲
                    getParent().requestDisallowInterceptTouchEvent(false);
                }


                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 回调OnItemClickListener . onItemclick()
                 *      单击事件:  down/up
                 *          单击/长按  : 按下和抬起的时间间隔   :  < 500ms -- 单击
                 *          单击/滑动事件 : 单击:一个点按下并且抬起 (downxY和upxY是一个点)  水平/垂直 距离差  < 5px (防抖动效果)
                 */
                int upTime = (int) System.currentTimeMillis();
                float upX = ev.getX();
                float upY = ev.getY();
                disX = Math.abs(upX-downX);
                disY = Math.abs(upY - downY);
                if(upTime-downTime <=500 && disX < 5 && disY < 5){
                    if(onItemClickListener!=null){
                        //单击事件
                        onItemClickListener.onItemClick(getCurrentItem());//点击的界面,肯定是显示的界面
                    }
                }
            case MotionEvent.ACTION_CANCEL:
                //继续轮播
                startRoll();
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 自定义监听
     *  viewpager的自定义的条目点击监听
     *  1. 自定义监听接口
     *      OnItemClickListener : onItemClick(int position)
     *  2. 对外暴露设置监听接口的方法: setOnItemClickListener(OnItemClickListener onItemClickListener)
     *  3. 回调OnItemClickListener . onItemclick()
     *      单击事件:  down/up
     *          单击/长按  : 按下和抬起的时间间隔   :  < 500ms -- 单击
     *          单击/滑动事件 : 单击:一个点按下并且抬起 (downxY和upxY是一个点)  水平/垂直 距离差  < 5px (防抖动效果)
     */
    public interface  OnItemClickListener{
        public void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
