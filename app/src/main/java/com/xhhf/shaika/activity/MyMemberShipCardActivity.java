package com.xhhf.shaika.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xhhf.shaika.R;
import com.xhhf.shaika.adapter.MembershipCardRecylerAdapter;
import com.xhhf.shaika.view.DividerItemDecoration;

import butterknife.ButterKnife;

/**
 * 我的会员卡
 * Created by ZJH on 2016.11.09.
 */
public class MyMemberShipCardActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private MembershipCardRecylerAdapter mAdapter;

    @Override
    protected int addView() {
        return R.layout.ac_membership_card;
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);

        recyclerView = (RecyclerView) findViewById(R.id.ac_recycler_member_card);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        layoutManager = new LinearLayoutManager(this);

        mAdapter = new MembershipCardRecylerAdapter();//自定义的适配器
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

//    @OnClick(R.id.ac_member_card_ib_back)
//    protected void memberCardback(){
//        finish();
//    }
}
