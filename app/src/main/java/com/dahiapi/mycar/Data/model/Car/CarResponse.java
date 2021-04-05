package com.dahiapi.mycar.Data.model.Car;

import com.google.gson.annotations.SerializedName;

public class CarResponse {
    private String brand;
    @SerializedName("constractionYear")
    private String year;
    private boolean isUsed;
    private String imageUrl;

    public String getBrand() {
        return brand;
    }

    public String getYear() {
        return year;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
