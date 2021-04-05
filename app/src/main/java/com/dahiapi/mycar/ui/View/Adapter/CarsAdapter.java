package com.dahiapi.mycar.ui.View.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dahiapi.mycar.Data.model.Car.CarResponse;
import com.dahiapi.mycar.R;
import com.dahiapi.mycar.databinding.ItemCarBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    private List<CarResponse> carGroup;

    public CarsAdapter(List<CarResponse> carGroup) {
        this.carGroup = carGroup;
    }

    public void addList(List<CarResponse> carList) {
        int position = carGroup.size() + 1;
        this.carGroup.addAll(carList);
        notifyItemRangeInserted(position, carList.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCarBinding binding = ItemCarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarResponse model = carGroup.get(position);
        holder.binding.brandTextView.setText(model.getBrand());
        holder.binding.yearTextView.setText(model.getYear());
        if (model.isUsed()) {
            holder.binding.carStatusImage.setImageResource(R.drawable.used_image);
        } else {
            holder.binding.carStatusImage.setImageResource(R.drawable.new_image);
        }
        if (model.getImageUrl()!=null){
            Picasso.get().load(model.getImageUrl()).into(holder.binding.carImageView);
        }else{
            holder.binding.carImageView.setImageResource(R.drawable.defult_car);
        }
    }

    @Override
    public int getItemCount() {
        return carGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemCarBinding binding;

        public ViewHolder(ItemCarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
