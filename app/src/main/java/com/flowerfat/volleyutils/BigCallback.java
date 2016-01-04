package com.flowerfat.volleyutils;

import com.flowerfat.volleyutil.callback.Callback;

import java.io.IOException;

/**
 * Created by bigflower on 2016/1/4.
 */
public abstract class BigCallback extends Callback<String> {
    @Override
    public Decide dataBeautifulPlus(String response) throws IOException {
        return doResponse(response);
    }

    private Decide doResponse(String response) {
        return new Decide(true, "\nI am user design\n\n" + response);
    }
}
