package com.abhishek.zipmap.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhishek.zipmap.Location;
import com.abhishek.zipmap.R;
import com.abhishek.zipmap.databinding.LocationCardBinding;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {


    private final Context context;
    SearchActivityPresenter<SearchActivityMvpView> presenter;
    private ArrayList<Location> place = new ArrayList<>();

    public LocationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LocationCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.location_card,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {

      holder.updateViews(place.get(position), position);

    }

    @Override
    public int getItemCount() {
        return place.size();
    }

    public void setPresenter(SearchActivityPresenter<SearchActivityMvpView> presenter) {
        this.presenter = presenter;
    }

    public void setItem(ArrayList<Location> place) {
        this.place = place;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LocationCardBinding binding;
        public ViewHolder(LocationCardBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void updateViews(Location place, final int position) {
            binding.name.setText(place.getAddress());
            binding.clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.removeElement(position);
                }
            });

        }
    }
}
