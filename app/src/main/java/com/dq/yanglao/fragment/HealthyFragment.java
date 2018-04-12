package com.dq.yanglao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dq.yanglao.R;
import com.dq.yanglao.adapter.SimpleFragmentPagerAdapter;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 健康
 * Created by jingang on 2018/4/12.
 */

public class HealthyFragment extends MyBaseFragment implements ViewPager.OnPageChangeListener {
    @Bind(R.id.tabLayout1)
    TabLayout tabLayout1;
    @Bind(R.id.tb_noScrollViewPage1)
    NoScrollViewPager noScrollViewPager1;

    private String[] titles = new String[]{"计步", "心率", "睡眠"};
    private List<Fragment> fragments = new ArrayList<>();

    private SimpleFragmentPagerAdapter sfpAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayout1, null);
        ButterKnife.bind(this, view);

        fragments.add(FMStep.newInstance());
        fragments.add(FMStep.newInstance());
        fragments.add(FMStep.newInstance());

        sfpAdapter = new SimpleFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), fragments, titles);
        noScrollViewPager1.setAdapter(sfpAdapter);

        // noScrollViewPager.setCurrentItem(page);
        noScrollViewPager1.setOffscreenPageLimit(titles.length);

        noScrollViewPager1.setOnPageChangeListener(this);
        tabLayout1.setupWithViewPager(noScrollViewPager1);

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}