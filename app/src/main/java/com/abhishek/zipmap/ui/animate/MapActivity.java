package com.abhishek.zipmap.ui.animate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abhishek.zipmap.Location;
import com.abhishek.zipmap.R;
import com.abhishek.zipmap.utils.MapAnimator;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback ,
        MapActivityMvpView{

    private ArrayList<Location> locations;
    private ArrayList<LatLng> locationCoordinates = new ArrayList<>();
    private ArrayList<LatLng> updateCoordinates = new ArrayList<>();

    private GoogleMap mMap;

    @Inject
    MapActivityPresenter<MapActivityMvpView> mPresenter;
    private SupportMapFragment mapFragment;
    private boolean isInitialMovement = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        locations = getIntent().getParcelableArrayListExtra("PlaceList");
        setUpLocationCoordinates();

        mPresenter.onAttach(this);

        mPresenter.startGettingMapCoordinates(locationCoordinates);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);



    }

    private void setUpLocationCoordinates() {
        for(Location location: locations) {
            locationCoordinates.add(location.getLatLng());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        isInitialMovement = true;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for(LatLng latLng: updateCoordinates) {
                    builder.include(latLng);
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        startAnim();

                    }

                    @Override
                    public void onCancel() {

                    }
                });

            }
        });
    }

    private void startAnim(){
        if(mMap != null) {
            MapAnimator.getInstance().animateRoute(mMap, updateCoordinates);
        } else {
            Toast.makeText(getApplicationContext(), "Map not ready", Toast.LENGTH_LONG).show();
        }
    }

    public void resetAnimation(View view){
        startAnim();
    }

    @Override
    public void updateCoordinates(ArrayList<LatLng> localLatLngs) {
        updateCoordinates = localLatLngs;
        if(updateCoordinates.size() != 0) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this,
                    "No waypoint for selected location",
                    Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}
