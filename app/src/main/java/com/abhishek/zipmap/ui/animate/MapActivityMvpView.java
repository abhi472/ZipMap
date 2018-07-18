package com.abhishek.zipmap.ui.animate;

import com.abhishek.zipmap.base.MvpView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public interface MapActivityMvpView extends MvpView{
    void updateCoordinates(ArrayList<LatLng> localLatLngs);
}
