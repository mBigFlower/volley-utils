package com.flowerfat.volleyutils;

import android.app.Application;

import com.flowerfat.volleyutil.utils.VolleyUtils;

/**
 * Created by 明明大美女 on 2015/12/15.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this; // 单例

        try {
            VolleyUtils.getInstance().init(this, getAssets().open("ca.crt"));
        } catch (Exception e){

        }

//        try {
//            VolleyUtils.getInstance().init(this, getAssets().open("fuck.bks"),
//                    "123456",getAssets().open("ca.crt"));
//        } catch (Exception e){
//
//        }

    }

    public static MyApplication getInstance() {
        return myApplication;
    }

}
