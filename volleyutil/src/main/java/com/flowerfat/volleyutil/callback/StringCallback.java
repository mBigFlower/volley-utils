package com.flowerfat.volleyutil.callback;

import java.io.IOException;

/**
 * Created by bigflower on 2016/1/4.
 */
public class StringCallback extends Callback<String> {
    public StringCallback() {
    }

    @Override
    public Decide dataBeautifulPlus(String response) throws IOException {
        return null;
    }

    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onError(String e) {

    }
}
