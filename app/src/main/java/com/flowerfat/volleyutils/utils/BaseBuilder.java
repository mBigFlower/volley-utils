package com.flowerfat.volleyutils.utils;

import com.android.volley.RetryPolicy;

import java.util.Map;

/**
 * Created by Bigflower on 2015/12/16.
 */

public abstract class BaseBuilder {

    protected int method;
    protected String url;
    protected Object tag;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected RetryPolicy mRetryPolicy ;

    public BaseBuilder() {
    }

//    public abstract BaseBuilder method(int var1);

    public abstract BaseBuilder url(String var1);

    public abstract BaseBuilder tag(Object var1);

    public abstract BaseBuilder params(Map<String, String> var1);

    public abstract BaseBuilder addParam(String var1, String var2);

    public abstract BaseBuilder headers(Map<String, String> var1);

    public abstract BaseBuilder addHeader(String var1, String var2);

    public abstract BaseBuilder setTimes(int timeoutMs);

//    public abstract RequestCall build();
}