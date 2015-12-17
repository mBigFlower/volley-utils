package com.flowerfat.volleyutil.utils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.flowerfat.volleyutil.io.Callback;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bigflower on 2015/12/16.
 */
public class VolleyBuilder extends BaseBuilder {

    public VolleyBuilder() {
    }

    public VolleyBuilder(int method) {
        this.method = method;
    }

    public VolleyBuilder(int method, String url) {
        this.url = url;
        this.method = method;
    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            Iterator i$ = params.keySet().iterator();

            while (i$.hasNext()) {
                String key = (String) i$.next();
                sb.append(key).append("=").append((String) params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public VolleyBuilder url(String url) {
        this.url = url;
        return this;
    }

    public VolleyBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public VolleyBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public VolleyBuilder addParam(String key, String val) {
        if (this.params == null) {
            this.params = new IdentityHashMap();
        }

        this.params.put(key, val);
        return this;
    }

    public VolleyBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public VolleyBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            this.headers = new IdentityHashMap();
        }

        this.headers.put(key, val);
        return this;
    }

    public VolleyBuilder setTimes(int timeoutMs) {
        mRetryPolicy = new DefaultRetryPolicy(timeoutMs, 0, 1f);
        return this;
    }

    public void Go(Callback listener) {
        if (this.params != null && this.method == Request.Method.GET) {
            this.url = this.appendParams(this.url, this.params);
        }
        new VolleyRequest(this, listener);
    }

    public void GoDIY(Callback listener) {
        if (this.params != null && this.method == Request.Method.GET) {
            this.url = this.appendParams(this.url, this.params);
        }
        new VolleyRequestDIY(this, listener);
    }

}
