package com.flowerfat.volleyutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flowerfat.volleyutils.io.Callback;
import com.flowerfat.volleyutils.utils.VolleyUtils;

/**
 * Created by Bigflower on 2015/12/15.
 * <p>
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
        }
    }

    private void httpGet() {
        VolleyUtils.getInstance()
                .get()
                .url("http://www.baidu.com")
                .tag("MainActivity")
                .Go(new Callback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        contentTV.setText(response);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        Toast.makeText(MainActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void httpPost() {
        VolleyUtils.getInstance()
                .post()
                .url("http://192.168.31.202/goBasic/coach/register/login")
                .tag("MainActivity")
                .addParam("phone", "15828433284")
                .addParam("password", "qqqqqq")
                .addHeader("Charset", "UTF-8")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .Go(new Callback<String>() {
                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onError(String errorInfo) {

                    }
                });
    }

    private void httpCallbackDIY() {
        VolleyUtils.getInstance()
                .post()
                .url("http://192.168.31.202/goBasic/coach/register/login")
                .tag("MainActivity")
                .addParam("phone", "15828433284")
                .addParam("password", "qqqqqq")
                .addHeader("Charset", "UTF-8")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .GoDIY(new Callback<String>() {

                    @Override
                    public void onSuccess(String response) {
                        Log.i("onSuccess", response);
                        contentTV.append(response);
                    }

                    @Override
                    public void onError(String errorInfo) {
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
