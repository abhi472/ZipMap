package com.abhishek.zipmap.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.abhishek.zipmap.Location;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Objects;

import javax.inject.Inject;

public class PlacesUtil {

    private GoogleApiClient client;
    private FragmentActivity activity;
    private UpdatePlace updatePlace;

    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds Bounds_Delhi = new LatLngBounds(
            new LatLng(28.644800, 77.216721), new LatLng(29.644800, 78.216721));

    @Inject
    public PlacesUtil(FragmentActivity context) {
        this.activity = context;
        updatePlace = (UpdatePlace) context;
    }

    public void startService(GoogleApiClient.OnConnectionFailedListener listener) {

        client = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, listener)
                .addApi(Places.GEO_DATA_API)
                .build();

    }

    public PlaceAutocompleteAdapter getAdapter() {
        mAdapter = new PlaceAutocompleteAdapter(activity, client, Bounds_Delhi,
                null);
        return mAdapter;
    }

    public AdapterView.OnItemClickListener getClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
                final AutocompletePrediction item = mAdapter.getItem(position);
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);


            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(client, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

                Toast.makeText(activity, "Clicked: " + primaryText,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }


    private Place place;
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            place = places.get(0);
            Location loc = new Location();
            loc.setLatLng(place.getLatLng());
            loc.setAddress(Objects.requireNonNull(place.getAddress()).toString());
            updatePlace.updatePlace(loc);
            places.release();
        }
    };

    public Place getPlace() {
        return place;
    }


    public void destroyPlaces() {
        client.stopAutoManage((FragmentActivity) activity);
        client.disconnect();
    }

    public interface UpdatePlace {
        void updatePlace(Location place);
    }
}


