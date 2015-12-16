package com.flowerfat.volleyutils.utils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flowerfat.volleyutils.io.Callback;

/**
 * Created by bigflower on 2015/12/16.
 */
public class VolleyRequest {

    public VolleyRequest(VolleyBuilder builder, final Callback listener) {
        final StringRequest request = new StringRequest(builder.method, builder.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("错误提示：" + error.getMessage());
            }
        });

        // header
        if(builder.headers != null)
            request.setRequestHeaders(builder.headers);
        // tag
        if(builder.tag != null)
            request.setTag(builder.tag);
        // times
        if (builder.mRetryPolicy == null)
            request.setRetryPolicy(new DefaultRetryPolicy(VolleyUtils.DEFAULT_MILLISECONDS, 0, 1f));
        else
            request.setRetryPolicy(builder.mRetryPolicy);
        // Go
        VolleyUtils.getInstance().build(request);
    }


}
