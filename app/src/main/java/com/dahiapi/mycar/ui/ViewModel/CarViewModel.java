package com.dahiapi.mycar.ui.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dahiapi.mycar.Data.DataCallBack;
import com.dahiapi.mycar.Data.DataRepository;
import com.dahiapi.mycar.Data.model.Car.CarResponseBody;
import com.dahiapi.mycar.Data.model.ResponseBody;

public class CarViewModel extends AndroidViewModel {

    private ResponseBody carResponse;
    private MutableLiveData<ResponseBody> carLiveData;
    protected DataRepository mDataRepository;

    public CarViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(application.getApplicationContext());
        DataRepository.changeBaseURL("https://demo1585915.mockable.io/api/");
    }

    public LiveData<ResponseBody> getCars(int pageNumber) {
        carResponse = new ResponseBody();
        carLiveData = new MutableLiveData<>();
        loadCars(pageNumber);
        return carLiveData;
    }


    private void loadCars(int pageNumber) {
        DataCallBack<CarResponseBody> dataCallback = new DataCallBack<CarResponseBody>() {
            @Override
            public void onDataReceived(CarResponseBody response) {
                carResponse.setDataResponse(response);
                carResponse.setSuccess(true);
                carLiveData.postValue(carResponse);
            }

            @Override
            public void onFailure(String s) {
                Log.d("ttt", "onFailure: " + s);
                carResponse.setSuccess(false);
                carResponse.setMessage(s);
                carLiveData.postValue(carResponse);
            }
        };
        mDataRepository.getCars(dataCallback, pageNumber);
    }
}
