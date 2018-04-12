package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.dq.yanglao.R;
import com.dq.yanglao.adapter.SimpleFragmentPagerAdapter;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.fragment.FMInformation;
import com.dq.yanglao.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页-消息
 * Created by jingang on 2018/4/9.
 */

public class InformationActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.tb_noScrollViewPage)
    NoScrollViewPager noScrollViewPager;

    private String[] titles = new String[]{"信息", "通知"};
    private List<Fragment> fragments = new ArrayList<>();

    private SimpleFragmentPagerAdapter sfpAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.tablayout2);
        ButterKnife.bind(this);
        setTvTitle(titles[noScrollViewPager.getChildCount()]);
        setIvBack();

        fragments.add(FMInformation.newInstance());
        fragments.add(FMInformation.newInstance());

        sfpAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, titles);
        noScrollViewPager.setAdapter(sfpAdapter);

        // noScrollViewPager.setCurrentItem(0);
        noScrollViewPager.setOffscreenPageLimit(titles.length);

        noScrollViewPager.setOnPageChangeListener(this);
        tabLayout.setupWithViewPager(noScrollViewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setTvTitle(titles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
