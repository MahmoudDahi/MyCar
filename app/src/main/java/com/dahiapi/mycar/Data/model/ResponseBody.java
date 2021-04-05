package com.dahiapi.mycar.Data.model;

public class ResponseBody <T>{
    private T dataResponse;
    private boolean isSuccess;
    private String message;

    public T getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(T dataResponse) {
        this.dataResponse = dataResponse;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
