package com.flowerfat.volleyutil.callback;

import java.io.IOException;

/**
 * Created by bigflower on 2016/1/4.
 */
public abstract class Callback<T> {

    public abstract Decide dataBeautifulPlus(String response) throws IOException;

    public abstract void onSuccess(T response);
    public abstract void onError(String e);

    public class Decide {
        boolean isSuccess;
        T result;

        public Decide(boolean isSuccess, T result) {
            this.isSuccess = isSuccess;
            this.result = result;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public T getResult() {
            return result;
        }

        public void setResult(T result) {
            this.result = result;
        }
    }

}