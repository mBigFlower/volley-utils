package com.flowerfat.volleyutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flowerfat.volleyutil.io.Callback;
import com.flowerfat.volleyutil.utils.VolleyUtils;

public class HttpsActivity extends AppCompatActivity {

    TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https);

        resultTv = (TextView) findViewById(R.id.https_resultShow);
    }


    public void httpClick(View view) {
        String url = "http://192.168.56.1:8080/";
        VolleyUtils.getInstance()
                .get(url)
                .tag("MainActivity")
                .Go(new Callback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        Log.w("httpClick", response);
                        resultTv.setText(response);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        Log.e("httpsClick", errorInfo);
                        resultTv.setText(errorInfo);
                    }
                });

    }

    public void httpsClick(View view) {
        String url = "https://192.168.56.1:8443/";
        VolleyUtils.getInstance()
                .get(url)
                .tag("MainActivity")
                .Go(new Callback<String>() {
                    @Override
                    public void onSuccess(String response) {

                        Log.w("httpsClick", response);
                        resultTv.setText(response);
                    }

                    @Override
                    public void onError(String errorInfo) {
                        Log.e("httpsClick", errorInfo);
                        resultTv.setText(errorInfo);
                    }
                });

    }

}
