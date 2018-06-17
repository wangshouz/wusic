package com.wangsz.wusic.base;

import android.support.annotation.Nullable;

public interface BaseInterface<T> {

    void success(@Nullable T t);

    void failed(int code);

}
