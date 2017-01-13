package com.xhhf.shaika.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xhhf.shaika.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class SearchLvHisAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;

    public SearchLvHisAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_search_lv,null);
            holder.tvHis = (TextView) convertView.findViewById(R.id.tv_history);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvHis.setText(data.get(position));

        return convertView;
    }

    public class ViewHolder{
        public TextView tvHis;
    }
}
