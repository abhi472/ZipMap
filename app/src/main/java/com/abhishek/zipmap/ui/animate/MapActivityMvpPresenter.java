package com.abhishek.zipmap.ui.animate;

import com.abhishek.zipmap.base.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface MapActivityMvpPresenter<V extends MapActivityMvpView> extends MvpPresenter<V> {
    void startGettingMapCoordinates(ArrayList<LatLng> locationCoordinates);
}
