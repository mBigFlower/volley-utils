package com.flowerfat.volleyutils.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Bigflower on 2015/12/15.
 */
public class VolleyUtils {

    public static final int DEFAULT_MILLISECONDS = 10 * 1000;
    private static VolleyUtils mInstance;
    private RequestQueue mRequestQueue;

    public static VolleyUtils getInstance() {
        if (mInstance == null) {
            Class var0 = VolleyUtils.class;
            synchronized (VolleyUtils.class) {
                if (mInstance == null) {
                    mInstance = new VolleyUtils();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    public void cancelAll() {
        mRequestQueue.cancelAll(this);
    }


    /////////////////////////////////////

    public VolleyBuilder get() {
        return new VolleyBuilder();
    }

    public VolleyBuilder get(String url) {
        return new VolleyBuilder(Request.Method.GET, url);
    }

    public VolleyBuilder post() {
        return new VolleyBuilder(Request.Method.POST);
    }

    public VolleyBuilder post(String url) {
        return new VolleyBuilder(Request.Method.POST, url);
    }

    public VolleyBuilder delete() {
        return new VolleyBuilder(Request.Method.DELETE);
    }

    public VolleyBuilder delete(String url) {
        return new VolleyBuilder(Request.Method.DELETE, url);
    }

    public VolleyBuilder put() {
        return new VolleyBuilder(Request.Method.PUT);
    }

    public VolleyBuilder put(String url) {
        return new VolleyBuilder(Request.Method.PUT, url);
    }

    /////////////////////////////////////

    public void build(Request request) {
        mRequestQueue.add(request);
    }

}
