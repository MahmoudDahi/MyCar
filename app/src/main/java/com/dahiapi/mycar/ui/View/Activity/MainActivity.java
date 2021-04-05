package com.dahiapi.mycar.ui.View.Activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dahiapi.mycar.Data.model.Car.CarResponse;
import com.dahiapi.mycar.Data.model.Car.CarResponseBody;
import com.dahiapi.mycar.Data.model.ResponseBody;
import com.dahiapi.mycar.Utils.ConnectionReceiver;
import com.dahiapi.mycar.databinding.ActivityMainBinding;
import com.dahiapi.mycar.ui.View.Adapter.CarsAdapter;
import com.dahiapi.mycar.ui.ViewModel.CarViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CarViewModel carViewModel;
    private CarsAdapter carsAdapter;
    private int numberOfColumns = 2;
    private int pageNumber = 1;
    private boolean loading = false;
    private List<CarResponse> carResponsesGroup;
    private ConnectionReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        carViewModel = ViewModelProviders.of(MainActivity.this).get(CarViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getCars(pageNumber);

        binding.recyclerCar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && loading) {
                    loading = false;
                    pageNumber++;
                    getCars(pageNumber);
                }
            }
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNumber=1;
                getCars(pageNumber);
            }
        });
    }

    private void getCars(int page) {
        binding.progressHomeCar.setVisibility(View.VISIBLE);
        carViewModel.getCars(page).observe(MainActivity.this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                binding.progressHomeCar.setVisibility(View.GONE);
                binding.swipeRefresh.setRefreshing(false);
                if (responseBody.isSuccess()) {
                    CarResponseBody carResponseBody = (CarResponseBody) responseBody.getDataResponse();
                    if (carResponseBody.getStatus() == 1) {
                        if (pageNumber==1){
                            intialAdapter();
                        }
                        loading = true;
                        carResponsesGroup.addAll(carResponseBody.getData());
                        carsAdapter.addList(carResponseBody.getData());
                    } else {
                        Toast.makeText(MainActivity.this, "No More Data", Toast.LENGTH_SHORT).show();
                        if (pageNumber > 1) {
                            pageNumber--;
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void intialAdapter() {
        carResponsesGroup = new ArrayList<>();
        carsAdapter = new CarsAdapter(carResponsesGroup);
        binding.recyclerCar.setLayoutManager(new GridLayoutManager
                (MainActivity.this,
                        numberOfColumns,
                        GridLayoutManager.VERTICAL, false));
        binding.recyclerCar.setAdapter(carsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}