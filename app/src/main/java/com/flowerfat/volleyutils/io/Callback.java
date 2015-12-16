package com.flowerfat.volleyutils.io;

/**
 * Created by Bigflower on 2015/12/16.
 */
public interface Callback<T>  {

    void onSuccess(T response);

    void onError(String errorInfo);
}
