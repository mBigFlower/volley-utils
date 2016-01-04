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
                .url("http://www.baicu.com/")
                .addHeader("Charset", "UTF-8")
                .addHeader("content-type", "application/x-www-form-urlencoded")
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
        VolleyUtils.getInstance()
                .post()
                .url("http://www.baicu.com/")
                .tag("MainActivity")
                .addParam("phone", "15828433284")
                .addParam("password", "qqqqqq")
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
                .url("http://www.baicu.com/")
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
