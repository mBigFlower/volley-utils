package com.flowerfat.volleyutil.utils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flowerfat.volleyutil.io.Callback;

import org.json.JSONObject;

/**
 * Created by Bigflower on 2015/12/16.
 * if you want to DIY your request,
 * you can do like this class
 */
public class VolleyRequestDIY {

    private StringRequest request;

    public VolleyRequestDIY(VolleyBuilder builder, final Callback listener) {

        L.d("==========================================");
        L.i("method:" + builder.method);
        L.i("url:" + builder.url);
        L.i("params:" + builder.params.toString());
        L.i("headers:" + builder.headers);
        L.d("==========================================");

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
                doVolleyResponse(response, listener);
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("volleyNetwork Error：\n" + error.getMessage());
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

    private void doVolleyResponse(String volleyResponse, Callback<String> listener) {
        try {
            JSONObject response = new JSONObject(volleyResponse);
            if (response.getBoolean("success")) {
                listener.onSuccess("success");
            } else {
                listener.onError(response.getString("errorInfo"));
            }
        } catch (Exception e) {
            listener.onError("catch : " + e.getMessage());
        }
    }
}
