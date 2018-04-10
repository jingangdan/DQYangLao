package com.dq.yanglao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dq.yanglao.R;
import com.dq.yanglao.adapter.MyExpandableListViewAdapter;
import com.dq.yanglao.base.MyBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页-信息-信息
 * Created by jingang on 2018/4/9.
 */

public class FMInformation extends MyBaseFragment {
    @Bind(R.id.elvInformation)
    ExpandableListView elvInformation;
    private View view;
    // 列表数据
    private List<String> mGroupNameList = null;
    private List<List<String>> mItemNameList = null;
    // 适配器
    private MyExpandableListViewAdapter mAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fm_information, null);
        ButterKnife.bind(this, view);
        elvInformation.setGroupIndicator(null);

        // 初始化数据
        initData();

        // 为ExpandableListView设置Adapter
        mAdapter = new MyExpandableListViewAdapter(getActivity(), mGroupNameList, mItemNameList);
        elvInformation.setAdapter(mAdapter);


        return view;
    }

    public static FMInformation newInstance() {
        Bundle bundle = new Bundle();
//        bundle.putString("phone", phone);
//        bundle.putString("token", token);
        FMInformation fragment = new FMInformation();
        fragment.setArguments(bundle);

        return fragment;
    }

    // 初始化数据
    private void initData() {
        // 组名
        mGroupNameList = new ArrayList<String>();
        mGroupNameList.add("历代帝王");
        mGroupNameList.add("华坛明星");
        mGroupNameList.add("国外明星");
        mGroupNameList.add("政坛人物");

        mItemNameList = new ArrayList<List<String>>();
        // 历代帝王组
        List<String> itemList = new ArrayList<String>();
        itemList.add("唐太宗李世民");
        itemList.add("秦始皇嬴政");
        itemList.add("汉武帝刘彻");
        itemList.add("明太祖朱元璋");
        itemList.add("宋太祖赵匡胤");
        mItemNameList.add(itemList);
        // 华坛明星组
        itemList = new ArrayList<String>();
        itemList.add("范冰冰 ");
        itemList.add("梁朝伟");
        itemList.add("谢霆锋");
        itemList.add("章子怡");
        itemList.add("杨颖");
        itemList.add("张柏芝");
        mItemNameList.add(itemList);
        // 国外明星组
        itemList = new ArrayList<String>();
        itemList.add("安吉丽娜•朱莉");
        itemList.add("艾玛•沃特森");
        itemList.add("朱迪•福斯特");
        mItemNameList.add(itemList);
        // 政坛人物组
        itemList = new ArrayList<String>();
        itemList.add("唐纳德•特朗普");
        itemList.add("金正恩");
        itemList.add("奥巴马");
        itemList.add("普京");
        mItemNameList.add(itemList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
