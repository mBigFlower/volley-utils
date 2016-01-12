package com.flowerfat.volleyutil.main;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bigflower on 2015/12/15.
 * the difference with StringRequest is the params is Object,
 * so you can add not only string, but also int, float .etc
 */
public class ObjectRequest extends Request<String> {
    private Response.Listener<String> mListener;
    public Map<String, String> requestHeaders = new HashMap<>();
    public Map<String, String> responseHeaders = new HashMap<>();
    public Map<String, Object> params = new HashMap<>();

    public ObjectRequest(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public ObjectRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    public ObjectRequest(int method, String url, Map<String, Object> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        this.params = params;
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    protected void deliverResponse(String response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            responseHeaders = response.headers;
            if (VolleyUtils.Decode == null)
                return Response.success(new String(response.data), HttpHeaderParser.parseCacheHeaders(response));
            else {
                String parsed = new String(response.data, VolleyUtils.Decode);
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return requestHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> realParams = new HashMap<>();
        for (String key : params.keySet()) {
            realParams.put(key, params.get(key).toString());
        }
        return realParams;
    }

    /**
     * set all headers
     *
     * @param headers
     */
    public void setRequestHeaders(Map<String, String> headers) {
        this.requestHeaders = headers;
    }

    /**
     * add one header
     *
     * @param key
     * @param val
     */
    public void addHeader(String key, String val) {
        this.requestHeaders.put(key, val);
    }

    public Map<String, String> getResponseHeader() {
        return responseHeaders;
    }

    public String getCookie() {
        return responseHeaders.get("set-cookie");
    }
}

