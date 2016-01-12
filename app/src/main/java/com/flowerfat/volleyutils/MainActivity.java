package com.flowerfat.volleyutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.flowerfat.volleyutil.callback.StringCallback;
import com.flowerfat.volleyutil.main.VolleyUtils;
import com.flowerfat.volleyutil.utils.L;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bigflower on 2015/12/15.
 * <p/>
 * You should make the VolleyUtils's init at your application
 */


public class MainActivity extends AppCompatActivity {

    private TextView contentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        contentTV = (TextView) findViewById(R.id.main_content);
        contentTV.setMovementMethod(ScrollingMovementMethod.getInstance());//让TextView可滚动
    }


    public void buttonClick(View v) {
        int id = v.getId();
        if (id == R.id.main_getBt) {
            httpGet();
        } else if (id == R.id.main_postBt) {
            httpPost();
        } else if (id == R.id.main_callbackBt) {
            httpCallbackDIY();
        } else if (id == R.id.main_https) {
            startActivity(new Intent(this, HttpsActivity.class));
        }
    }

    private void httpGet() {
        VolleyUtils.getInstance()
                .get()
                .url("http://weibo.com/flowerfat")
                .Go(new StringCallback() {
                    @Override
                    public void onSuccess(String response) {
                        L.i(response);
                        contentTV.setText(response);
                    }

                    @Override
                    public void onError(String e) {
                        L.e(e);
                        contentTV.setText(e);
                    }
                });

    }

    private void httpPost() {
        Map<String, String> data = new HashMap<>();
        data.put("unionid", "12345677888656");
        data.put("country", "多分");
        data.put("city", "阿斯蒂芬采访");
        data.put("nickname", "二万人发");
        data.put("language", "山东肥城v");
        data.put("headimgurl", "http://www.baicu.com");
        data.put("province", "就fid就fid年");
        data.put("sex", "1");
        data.put("openid", "8769806098768790");

//        OkHttpUtils.post()
//                .url("http://192.168.31.202:4000/zjb/coach/register/loginWithWeChat")
//                .addHeader("Charset", "UTF-8")
//                .addHeader("content-type", "application/x-www-form-urlencoded")
//                .params(data)
//                .build()
//                .execute(new com.zhy.http.okhttp.callback.StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        L.e( e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        L.i( response);
//                    }
//                });

        VolleyUtils.getInstance()
                .post()
                .url("http://192.168.31.139:4000/zjb/coach/register/loginWithWeChat")
                .tag("MainActivity")
                .params(data)
                .addHeader("Charset", "UTF-8")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .Go(new StringCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("onSuccess", "right：" + response);
                        contentTV.setText(response);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        Log.e("onError", "错误信息：" + errorInfo);
                        contentTV.setText(errorInfo);
                    }

                });
    }

    private void httpCallbackDIY() {
        VolleyUtils.getInstance()
                .post()
                .url("http://weibo.com/u/1391141497?topnav=1&wvr=6&topsug=1&is_all=1")
                .tag("MainActivity")
                .addParam("phone", "15828433284")
                .addParam("password", "qqqqqq")
                .addHeader("Charset", "UTF-8")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .Go(new BigCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("onSuccess", "right：" + response);
                        contentTV.setText(response);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        Log.e("onError", "错误信息：" + errorInfo);
                        contentTV.setText(errorInfo);
                    }

                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtils.getInstance().cancel("MainActivity");
    }
}
