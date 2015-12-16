package com.flowerfat.volleyutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.flowerfat.volleyutils.io.Callback;
import com.flowerfat.volleyutils.utils.VolleyUtils;

/**
 * Created by Bigflower on 2015/12/15.
 *
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
        }
    }

    private void httpGet() {
        VolleyUtils.getInstance()
                .get()
                .url("http://www.baidu.com")
                .Go(new Callback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        contentTV.setText(response);
                    }

                    @Override
                    public void onError(String errorInfo) {

                    }
                });
    }

}
