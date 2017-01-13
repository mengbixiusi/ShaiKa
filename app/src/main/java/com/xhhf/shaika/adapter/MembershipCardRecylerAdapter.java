package com.xhhf.shaika.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.xhhf.shaika.R;


public class MembershipCardRecylerAdapter extends RecyclerView.Adapter {
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_HEADER = 1;

//    private ArrayList<DataBean> dataList;

    private String[] arr=new String[]{"好想你枣业","好想你枣业","好想你枣业","上海永琪美容美发","上海永琪美容美发","上海永琪美容美发","北京先花后付","北京先花后付","北京先花后付","如家宾馆","北京先花后付","北京先花后付"};

    public MembershipCardRecylerAdapter() {
//        dataList = new ArrayList<>();
//        dataList = dataList2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate

                    (R.layout.item_member_card_layout, parent, false));
        } else if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate

                    (R.layout.member_card_header_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
//            DataBean bean = dataList.get(position);
//            ((ContentViewHolder) holder).tvId.setText("" + bean.getId());
//            ((ContentViewHolder) holder).tvName.setText(bean.getName());
        } else if (type == TYPE_HEADER) {
            ((HeaderViewHolder)holder).text.setText(arr[position]);
//            pbLoading = ((HeaderViewHolder) holder).pbLoading;
//            tvLoadMore = ((HeaderViewHolder) holder).tvLoadMore;
        }
    }

    /**
     * 获取数据集加上一个footer的数量
     */
    @Override
    public int getItemCount() {
        return 12;
    }


    @Override
    public int getItemViewType(int position) {
        if ((position + 3) % 3 == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_CONTENT;
        }
    }

//    /**
//     * 获取数据集的大小
//     */
//    public int getListSize() {
//        return dataList.size();
//    }


    /**
     * 内容的ViewHolder
     */
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvName;
        private SimpleRatingBar srb;

        public ContentViewHolder(View itemView) {
            super(itemView);
//            tvId = (TextView) itemView.findViewById(R.id.title);
//            tvName = (TextView) itemView.findViewById(R.id.content);
        }
    }

    /**
     * header的ViewHolder
     */
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ProgressBar pbLoading;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text1);
        }
    }

}