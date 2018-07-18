package com.abhishek.zipmap.ui.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.abhishek.zipmap.Location;
import com.abhishek.zipmap.R;
import com.abhishek.zipmap.databinding.ActivityMainBinding;
import com.abhishek.zipmap.ui.animate.MapActivity;
import com.abhishek.zipmap.ui.shortestPath.ShortestPathActivity;
import com.abhishek.zipmap.utils.PlacesUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SearchActivity extends AppCompatActivity implements SearchActivityMvpView ,
        GoogleApiClient.OnConnectionFailedListener, PlacesUtil.UpdatePlace{

    @Inject
    SearchActivityPresenter<SearchActivityMvpView> mPresenter;

    @Inject
    LocationAdapter adapter;

    @Inject
    PlacesUtil util;

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mPresenter.onAttach(this);
        binding.setPresenter(mPresenter);

        setupPlacesUtil();
    }

    private void setupPlacesUtil() {
        util.startService( this);
        binding.editText.setAdapter(util.getAdapter());
        binding.editText.setOnItemClickListener(util.getClickListener());
        mPresenter.setAdapter(adapter);
        adapter.setPresenter(mPresenter);
        binding.places.setLayoutManager(new LinearLayoutManager(this));
        binding.places.setAdapter(adapter);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        util.destroyPlaces();

    }

    @Override
    public void updatePlace(Location place) {
        mPresenter.updatePlace(place);
        binding.editText.setText("");
    }

    @Override
    public void startMapActivity(ArrayList<Location> placeList) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putParcelableArrayListExtra("PlaceList", placeList);
        startActivity(intent);
    }

    @Override
    public void startShortestDistanceActivity(String firstLocation, String secondLocation) {
        Intent intent = new Intent(this, ShortestPathActivity.class);
        intent.putExtra("firstLocation", firstLocation);
        intent.putExtra("secondLocation", secondLocation);
        startActivity(intent);
    }

    @Override
    public void showErrorToast(int error) {
        Toast.makeText(this, getString(error), Toast.LENGTH_SHORT).show();
    }
}
