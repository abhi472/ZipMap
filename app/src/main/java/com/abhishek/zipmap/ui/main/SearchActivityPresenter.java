package com.abhishek.zipmap.ui.main;

import com.abhishek.zipmap.Location;
import com.abhishek.zipmap.R;
import com.abhishek.zipmap.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchActivityPresenter<V extends SearchActivityMvpView>
        extends BasePresenter<V>
        implements SearchActivityMvpPresenter<V> {

    private LocationAdapter adapter;
    private ArrayList<Location> placeList = new ArrayList<>();


    @Inject
    public SearchActivityPresenter() {


    }


    @Override
    public void updatePlace(Location place) {
        placeList.add(place);
        adapter.setItem(placeList);
    }

    @Override
    public void removeElement(int position) {
        placeList.remove(position);
        adapter.setItem(placeList);
    }

    @Override
    public void setAdapter(LocationAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onClick() {

        if(placeList.size()>1) {
            double distance = addressProximo(placeList.get(0), placeList.get(1));
            int smallestI = 0;
            int smallestJ = 0;

            for (int i = 0; i < placeList.size() - 1; i++) {
                for (int j = i + 1; j < placeList.size(); j++) {
                    double temp = addressProximo(placeList.get(i), placeList.get(j));
                    if (temp < distance) {
                        distance = temp;
                        smallestI = i;
                        smallestJ = j;
                    }
                }
            }

            getMvpView().startShortestDistanceActivity(placeList.get(smallestI).getAddress(), placeList.get(smallestJ).getAddress());
        } else {
            getMvpView().showErrorToast(R.string.min_list_error);
        }


    }

    @Override
    public void onMapClick() {
        if(placeList.size()>1) {
            getMvpView().startMapActivity(placeList);
        } else {
            getMvpView().showErrorToast(R.string.min_list_error);
        }
    }

    private double addressProximo(Location loc1, Location loc2) {

        double lat1 = loc1.getLatLng().latitude;
        double lat2 = loc2.getLatLng().latitude;
        double lon1 = loc1.getLatLng().longitude;
        double lon2 = loc2.getLatLng().longitude;

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
