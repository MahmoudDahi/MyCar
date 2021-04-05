package com.dahiapi.mycar.Data.Remote;

import com.dahiapi.mycar.Data.model.Car.CarResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("v1/cars")
    Call<CarResponseBody> getGroupOfCars(@Query("page") int pageNumber);
}
