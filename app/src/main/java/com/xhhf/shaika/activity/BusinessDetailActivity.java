package com.xhhf.shaika.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xhhf.shaika.R;
import com.xhhf.shaika.adapter.BusinessPrizeAdapter;
import com.xhhf.shaika.adapter.ListDetailAdapter;
import com.xhhf.shaika.adapter.NearbyAdapter;
import com.xhhf.shaika.view.FlowLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商户详情页面
 * Created by ZJH on 2016.11.16.
 */
public class BusinessDetailActivity extends BaseActivity {
    private ListView lvDetail1;
    private ListView lvDetail2;
    private FlowLayout mFlowLayout;
    private ListView lvNearby;
    private NearbyAdapter nearbyAdapter;
    private String[] mVals = new String[] { "味道很好666", "服务好666", "很实惠666 ", "店面好找666",
            "口感好666" };
    private LayoutInflater mInflater;

    private ListDetailAdapter mAdapter;
    private ListView lvPrize;
    private BusinessPrizeAdapter prizeAdapter;
    //购卡和使用卡
    private RelativeLayout rlBuy;
    private RelativeLayout rlUse;

    @InjectView(R.id.ac_budiness_tv_buy_card)
    TextView tvBuy;

    @InjectView(R.id.ac_business_tv_use_card)
    TextView tvUse;

    @Override
    protected int addView() {
        return R.layout.ac_business_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

        lvNearby= (ListView) findViewById(R.id.lv_nearby);
        lvPrize= (ListView) findViewById(R.id.business_lv_prize);

        rlBuy= (RelativeLayout) findViewById(R.id.ac_business_rl_buy_card);
        rlUse= (RelativeLayout) findViewById(R.id.ac_business_rl_use_card);

        lvDetail1 = (ListView) findViewById(R.id.ac_business_detail_list1);
        lvDetail1.setFocusable(false);

        tvBuy.setTextColor(getResources().getColor(R.color.theamcolor));
        tvUse.setTextColor(getResources().getColor(R.color.mblack));

        if (mAdapter == null) {
            mAdapter = new ListDetailAdapter(BusinessDetailActivity.this);
            lvDetail1.setAdapter(mAdapter);
            setListViewHeightBasedOnChildren(lvDetail1);
        }

        lvDetail2 = (ListView) findViewById(R.id.ac_business_detail_list2);
        lvDetail2.setFocusable(false);
        lvDetail2.setVisibility(View.GONE);

        //为购卡和使用卡添加点击事件
        rlBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvDetail1.setVisibility(View.VISIBLE);
                lvDetail2.setVisibility(View.GONE);

                tvBuy.setTextColor(getResources().getColor(R.color.theamcolor));
                tvUse.setTextColor(getResources().getColor(R.color.mblack));



                if (mAdapter == null) {
                    mAdapter = new ListDetailAdapter(BusinessDetailActivity.this);
                    lvDetail1.setAdapter(mAdapter);
                    setListViewHeightBasedOnChildren(lvDetail1);
                }
            }
        });

        rlUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvDetail1.setVisibility(View.GONE);
                lvDetail2.setVisibility(View.VISIBLE);

                tvBuy.setTextColor(getResources().getColor(R.color.mblack));
                tvUse.setTextColor(getResources().getColor(R.color.theamcolor));



                if (mAdapter == null) {
                    mAdapter = new ListDetailAdapter(BusinessDetailActivity.this);
                    lvDetail2.setAdapter(mAdapter);
                    setListViewHeightBasedOnChildren(lvDetail2);
                }
            }
        });

        if (nearbyAdapter==null){
            nearbyAdapter=new NearbyAdapter(BusinessDetailActivity.this);
            lvNearby.setAdapter(nearbyAdapter);
            setListViewHeightBasedOnChildren(lvNearby);
        }

        if (prizeAdapter==null){
            prizeAdapter=new BusinessPrizeAdapter(BusinessDetailActivity.this);
            lvPrize.setAdapter(prizeAdapter);
            setListViewHeightBasedOnChildren(lvPrize);
        }

        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.business_detail_flowlayout);

        /**
         * 找到搜索标签的控件
         */
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(
                    R.layout.item_flowlayout_rated_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            final String str = tv.getText().toString();
            //点击事件
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Toast.makeText(BusinessDetailActivity.this,"dianjiwole"+ finalI,Toast.LENGTH_SHORT).show();
                }
            });
            mFlowLayout.addView(tv);//添加到父View
        }
    }

    /**
     *动态设置ListView的高度
     *@paramlistView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView){
        if(listView==null)return;
        ListAdapter listAdapter=listView.getAdapter();
        if(listAdapter==null){
//pre-condition
            return;
        }
        int totalHeight=0;
        for(int i=0;i<listAdapter.getCount();i++){
            View listItem=listAdapter.getView(i,null,listView);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params=listView.getLayoutParams();
        params.height=totalHeight+(listView.getDividerHeight()*(listAdapter.getCount()-1));
        listView.setLayoutParams(params);
    }

    /**
     * 用户反馈
     */
    @OnClick(R.id.ac_business_ib_feedback)
    protected void feedback(){
        Intent intent_feedback=new Intent(BusinessDetailActivity.this,FeedBackActivity.class);
        startActivity(intent_feedback);
    }


}
