package com.dahiapi.mycar.Data;

public interface DataCallBack <T>{
    /**
     * On data received.
     *
     * @param t the t
     */
    void onDataReceived(T t);

    /**
     * On failure.
     *
     * @param t the t
     */
    default void onFailure(Throwable t) {
    }

    /**
     * On failure.
     *
     * @param s the s
     */
    default void onFailure(String s) {
    }

}
