package com.wangsz.wusic.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wangsz.wusic.R;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 */
public abstract class BaseListFragment extends BaseFragment {

    /**
     * 初始化MultiTypeAdapter注册
     */
    public abstract void initAdapterRegister();

    public RecyclerView mRecyclerView;
    protected MultiTypeAdapter mMultiTypeAdapter;
    protected Items mItems;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void init() {
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMultiTypeAdapter = new MultiTypeAdapter();
        initAdapterRegister();
        mItems = new Items();
        mMultiTypeAdapter.setItems(mItems);
        mRecyclerView.setAdapter(mMultiTypeAdapter);
    }
}
