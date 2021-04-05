package com.dahiapi.mycar.Data.model;

import com.google.gson.annotations.Expose;

public class ResponseData<T> {
    @Expose
    private T data = null;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
