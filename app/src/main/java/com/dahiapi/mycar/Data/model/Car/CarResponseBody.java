package com.dahiapi.mycar.Data.model.Car;

import java.util.List;

public class CarResponseBody {

    private int status;
    private List<CarResponse> data;

    public List<CarResponse> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
