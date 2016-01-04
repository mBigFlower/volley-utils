package com.flowerfat.volleyutil.main;

import com.android.volley.RetryPolicy;

import java.util.Map;

/**
 * Created by Bigflower on 2015/12/16.
 */

public abstract class BaseBuilder {

    public int method;
    public String url;
    public Object tag;
    public Map<String, String> headers;
    public Map<String, String> params;
    public RetryPolicy mRetryPolicy ;

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