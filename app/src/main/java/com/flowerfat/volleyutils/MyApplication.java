package com.flowerfat.volleyutils;

import android.app.Application;

import com.flowerfat.volleyutil.main.VolleyUtils;

/**
 * Created by 明明大美女 on 2015/12/15.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this; // 单例

//        try {
//            VolleyUtils.getInstance().init(this, getAssets().open("ca.crt"));
//        } catch (Exception e){
//
//        }

        try {
            VolleyUtils.getInstance().init(this, getAssets().open("girl_client.bks"),
                    "123456",getAssets().open("boy_server.cer"));
            VolleyUtils.getInstance().setAutoCookie(true);
//            VolleyUtils.Decode = "GBK";
        } catch (Exception e){

        }

    }

    public static MyApplication getInstance() {
        return myApplication;
    }

}
