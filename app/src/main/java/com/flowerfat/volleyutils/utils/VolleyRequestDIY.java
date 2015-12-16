package com.flowerfat.volleyutils.utils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flowerfat.volleyutils.io.Callback;

import org.json.JSONObject;

/**
 * Created by Bigflower on 2015/12/16.
 *
 * if you want to DIY your request,
 * you can do like this class
 */
public class VolleyRequestDIY {

    public VolleyRequestDIY(VolleyBuilder builder, final Callback listener) {
        final StringRequest request = new StringRequest(builder.method, builder.url, new Response.Listener<String>() {
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

    public void addHeader(StringRequest request, VolleyBuilder builder){
        if(builder.headers != null)
            request.setRequestHeaders(builder.headers);
    }
    public void addTag(StringRequest request, VolleyBuilder builder){
        if(builder.tag != null)
            request.setTag(builder.tag);
    }
    public void setTimes(StringRequest request, VolleyBuilder builder){
        if (builder.mRetryPolicy == null)
            request.setRetryPolicy(new DefaultRetryPolicy(VolleyUtils.DEFAULT_MILLISECONDS, 0, 1f));
        else
            request.setRetryPolicy(builder.mRetryPolicy);
    }

    private void doVolleyResponse(String volleyResponse, Callback<String> listener){
        try {
            JSONObject response = new JSONObject(volleyResponse);
            int responseCode = response.getInt("errorCode");
            switch (responseCode) {
                case 0:
                    listener.onSuccess(response.getString("data"));
                    break;
                case 1:
                    listener.onError("1.手机号格式不对");
                    break;
                case 2:
                    listener.onError("2.系统出错");
                    break;
            }
        } catch (Exception e) {
            listener.onError("catch : "+e.getMessage());
        }
    }
}
