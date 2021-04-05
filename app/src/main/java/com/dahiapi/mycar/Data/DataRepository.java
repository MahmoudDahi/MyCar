package com.dahiapi.mycar.Data;

import android.content.Context;

import com.dahiapi.mycar.Data.Remote.RemoteDataSource;
import com.dahiapi.mycar.Data.model.Car.CarResponseBody;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static final Object LOCK = new Object();

    private static DataRepository INSTANCE;

    public static RemoteDataSource mRemoteDataSource;

    private Context context;

    private DataRepository(Context context) {
        this.context = context;
        changeBaseURL("http://demo1585915.mockable.io/api/");
    }


    public static void changeBaseURL(String url) {
        mRemoteDataSource = RemoteDataSource.getInstance(url);
    }


    public static DataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(context);
                }
            }
        }
        return INSTANCE;
    }








    private String getResponseError(Response response) {
        try {
            return response.errorBody().string();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public void getCars(DataCallBack<CarResponseBody> dataCallback, int pageNumber) {
        mRemoteDataSource.getCars(pageNumber).enqueue(new GeneralResponseCallback<>(dataCallback));
    }


    private class GeneralResponseCallback<T> implements Callback<T> {

        private DataCallBack callback;

        private GeneralResponseCallback(DataCallBack callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                callback.onDataReceived(response.body());
            } else {
                String error = getResponseError(response);
                callback.onFailure(new Exception(error));
                callback.onFailure(error);
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            callback.onFailure(t);
            callback.onFailure(t.getMessage());
        }
    }
}
