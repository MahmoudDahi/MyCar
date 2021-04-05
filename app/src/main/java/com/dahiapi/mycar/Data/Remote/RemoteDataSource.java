package com.dahiapi.mycar.Data.Remote;

import com.dahiapi.mycar.Data.model.Car.CarResponseBody;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {
    private static ApiServices apiServices;

    private static final Object LOCK = new Object();

    private static RemoteDataSource INSTANCE;

    public static Retrofit retrofit;

    private RemoteDataSource(String base_url) {
        buildApiService(base_url);
    }


    public static void buildApiService(String base_url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiServices = retrofit.create(ApiServices.class);
    }

    public static RemoteDataSource getInstance(String base_url) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteDataSource(base_url);
                }
            }
        } else {
            buildApiService(base_url);
        }
        return INSTANCE;
    }

    public Call<CarResponseBody> getCars(int pageNumber) {
       return apiServices.getGroupOfCars(pageNumber);
    }
}
