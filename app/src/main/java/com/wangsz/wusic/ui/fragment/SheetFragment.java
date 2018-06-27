package com.wangsz.wusic.ui.fragment;

import com.wangsz.wusic.bean.Empty;
import com.wangsz.wusic.ui.fragment.base.BaseListFragment;
import com.wangsz.wusic.viewbinder.EmptyViewBinder;

/**
 * author: wangsz
 * date: On 2018/6/6 0006
 */
public class SheetFragment extends BaseListFragment {

    @Override
    public void initAdapterRegister() {
        mMultiTypeAdapter.register(Empty.class, new EmptyViewBinder());
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void loadData() {
        mItems.add(new Empty());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

}
