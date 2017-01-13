package com.xhhf.shaika.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xhhf.shaika.R;
import com.xhhf.shaika.activity.SearchActivity;

import butterknife.OnClick;

/**
 * Created by eric on 16/11/3.
 */
public abstract class BaseFragment extends Fragment {

//    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        injected = true;
//        initFragment();
        View view = inflater.inflate(addView(), container, false);
        getView(view);
        return view;
    }

    public abstract int addView();

    public abstract void getView(View view);




}
