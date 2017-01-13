package com.xhhf.shaika.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhhf.shaika.R;

/**
 * 商户详情购卡的adapter
 * Created by ZJH on 2016.11.16.
 */
public class ListDetailAdapter extends BaseAdapter {
    private Context mContext;

    public ListDetailAdapter(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_business_detail_buy_card, null);
//                holder.tv_history_loan = (TextView) convertView.findViewById(R.id.tv_history_loan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    class ViewHolder {
        TextView tv_history_loan, tv_history_time, tv_history_review_time, tv_history_prediction_review_time, tv_history_repay_status, tv_history_progressing;
    }
}