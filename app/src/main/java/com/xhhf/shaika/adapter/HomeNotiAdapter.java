package com.xhhf.shaika.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhhf.shaika.R;

/**
 * Created by Administrator on 2016/11/15 0015.
 * 首页个人消息适配器
 */

public class HomeNotiAdapter extends BaseAdapter {

    private Context context;
    public HomeNotiAdapter(Context context) {
        this.context = context;
    }

    /**
     * 测试数据
     * @return
     */

    private String[] title = {"今日推荐","促销活动"};
    private String[] categray = {"尊敬的用户：已为您定制今天的独享优惠，请勿向其他人泄漏",
            "好想你特推出情侣优惠套餐，单身狗请绕行"};

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
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
            convertView = View.inflate(context, R.layout.item_home_noti,null);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_noti_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_noti_title);
            holder.tvCategray = (TextView) convertView.findViewById(R.id.tv_noti_categray);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_noti_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(title[position]);
        holder.tvCategray.setText(categray[position]);

        return convertView;
    }

    public class ViewHolder{

        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvCategray;
        public TextView tvTime;
    }
}
