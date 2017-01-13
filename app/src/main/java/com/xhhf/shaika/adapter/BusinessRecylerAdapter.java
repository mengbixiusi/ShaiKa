package com.xhhf.shaika.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.xhhf.shaika.R;
import com.xhhf.shaika.activity.BusinessDetailActivity;
import com.xhhf.shaika.bean.DeliciousFoodBean;

import java.util.ArrayList;


/**
 * 商家recycle adapter
 */
public class BusinessRecylerAdapter extends RecyclerView.Adapter {
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;

    private ArrayList<DeliciousFoodBean> dataList;

    private ProgressBar pbLoading;
    private TextView tvLoadMore;
    private Context mContext;

    public BusinessRecylerAdapter(Context ctx,ArrayList<DeliciousFoodBean> dataList2) {
//        dataList = new ArrayList<>();
        mContext=ctx;
        dataList=dataList2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate

                    (R.layout.item_business_list_layout, parent, false));
        } else if (viewType == TYPE_FOOTER) {//加载进度条的布局
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate

                    (R.layout.load_more_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            DeliciousFoodBean bean = dataList.get(position);
//            ((ContentViewHolder) holder).tvId.setText("" + bean.getId());
//            ((ContentViewHolder) holder).tvName.setText(bean.getName());
            ((ContentViewHolder) holder).srb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleRatingBar.AnimationBuilder builder = ((ContentViewHolder) holder).srb.getAnimationBuilder()
                            .setRatingTarget(3)
                            .setDuration(2000)
                            .setInterpolator(new BounceInterpolator());
                    builder.start();
                }
            });
            //设置评论星级不可点击
            ((ContentViewHolder) holder).srb.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            //条目点击
            ((ContentViewHolder) holder).llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("条目点击=========="+position);
                    Intent intent =new Intent(mContext, BusinessDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else if (type == TYPE_FOOTER) {
            pbLoading = ((FooterViewHolder) holder).pbLoading;
            tvLoadMore = ((FooterViewHolder) holder).tvLoadMore;
        }
    }

    /**
     * 获取数据集加上一个footer的数量
     */
    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    /**
     * 获取数据集的大小
     */
    public int getListSize() {
        return dataList.size();
    }



    /**
     * 内容的ViewHolder
     */
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvName;
        private SimpleRatingBar srb;
        private LinearLayout llItem;

        public ContentViewHolder(View itemView) {
            super(itemView);
//            tvId = (TextView) itemView.findViewById(R.id.title);
//            tvName = (TextView) itemView.findViewById(R.id.content);
            srb= (SimpleRatingBar) itemView.findViewById(R.id.item_recylerview_star);
            llItem= (LinearLayout) itemView.findViewById(R.id.delicious_frag_ll_item);
        }
    }

    /**
     * footer的ViewHolder
     */
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLoadMore;
        private ProgressBar pbLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvLoadMore = (TextView) itemView.findViewById(R.id.tv_item_footer_load_more);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_item_footer_loading);
        }
    }

    /**
     * 显示正在加载的进度条，滑动到底部时，调用该方法，上拉就显示进度条，隐藏"上拉加载更多"
     */
    public void showLoading() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.VISIBLE);
            tvLoadMore.setVisibility(View.GONE);
        }
    }

    /**
     * 显示上拉加载的文字，当数据加载完毕，调用该方法，隐藏进度条，显示“上拉加载更多”
     */
    public void showLoadMore() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.GONE);
            tvLoadMore.setVisibility(View.VISIBLE);
        }
    }

}