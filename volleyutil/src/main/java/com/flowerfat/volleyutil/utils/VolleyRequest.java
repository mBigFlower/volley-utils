package com.flowerfat.volleyutil.utils;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flowerfat.volleyutil.io.Callback;

import java.util.IdentityHashMap;

/**
 * Created by bigflower on 2015/12/16.
 */
public class VolleyRequest {

    private StringRequest request;

    public VolleyRequest(VolleyBuilder builder, final Callback listener) {

        setCookie(builder);

        Log.d("VolleyRequestDIY", "==========================================");
        Log.i("VolleyRequestDIY", "method:" + builder.method);
        Log.i("VolleyRequestDIY", "url:" + builder.url);
        Log.i("VolleyRequestDIY", "params:" + builder.params);
        Log.i("VolleyRequestDIY", "headers:" + builder.headers);
        Log.d("VolleyRequestDIY", "==========================================");

        if (builder.method == Request.Method.GET || builder.method == Request.Method.DELETE) {
            getAndDelete(builder, listener);
        } else {
            postAndPut(builder, listener);
        }
    }

    public void postAndPut(VolleyBuilder builder, final Callback listener) {
        request = new StringRequest(builder.method, builder.url, builder.params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", response);
                saveCookie();
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("网络请求出错：\n" + error.getMessage());
            }
        });

        // header
        addHeader(request, builder);
        // tag
        addTag(request, builder);
        // times
        setTimes(request, builder);
        // Go
        VolleyUtils.getInstance().build(request);
    }

    public void getAndDelete(VolleyBuilder builder, final Callback listener) {
        request = new StringRequest(builder.method, builder.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                saveCookie();
                listener.onSuccess(response);
                Log.i("response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("网络请求出错：\n" + error.getMessage());
            }
        });

        // header
        addHeader(request, builder);
        // tag
        addTag(request, builder);
        // times
        setTimes(request, builder);
        // Go
        VolleyUtils.getInstance().build(request);
    }


    public void addHeader(StringRequest request, VolleyBuilder builder) {
        if (builder.headers != null)
            request.setRequestHeaders(builder.headers);
    }

    public void addTag(StringRequest request, VolleyBuilder builder) {
        if (builder.tag != null)
            request.setTag(builder.tag);
    }

    public void setTimes(StringRequest request, VolleyBuilder builder) {
        if (builder.mRetryPolicy == null)
            request.setRetryPolicy(new DefaultRetryPolicy(VolleyUtils.DEFAULT_MILLISECONDS, 0, 1f));
        else
            request.setRetryPolicy(builder.mRetryPolicy);
    }

    //////////////////////////////////////////////////////////////////////////
    private void saveCookie() {
        String cookie = request.getCookie();
        Log.i("saveCookie", "saveCookie" + cookie);
        if (cookie != null) {
            VolleyUtils.getInstance().setCookie(cookie);
        }
    }

    private void setCookie(VolleyBuilder builder) {
        String cookie = VolleyUtils.getInstance().getCookie();
        Log.i("setCookie", "setCookie:" + cookie);
        if (cookie != null) {
            if(builder.headers == null) {
                builder.headers = new IdentityHashMap();
            }
            builder.headers.put("set-cookie", cookie);
        }
    }

}
