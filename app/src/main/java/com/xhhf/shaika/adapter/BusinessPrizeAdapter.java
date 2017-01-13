package com.xhhf.shaika.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhhf.shaika.R;

/**
 * 这是商家荣誉的adapter
 * Created by ZJH on 2016.11.15.
 */
public class BusinessPrizeAdapter extends BaseAdapter {

    private Context ctx;

    public BusinessPrizeAdapter(Context context){
        this.ctx=context;
    }
    @Override
    public int getCount() {
        return 2;
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
            convertView = View.inflate(ctx, R.layout.item_business_prize, null);
//                holder.tv_history_loan = (TextView) convertView.findViewById(R.id.tv_history_loan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_history_loan, tv_history_time, tv_history_review_time, tv_history_prediction_review_time, tv_history_repay_status, tv_history_progressing;
    }
}
