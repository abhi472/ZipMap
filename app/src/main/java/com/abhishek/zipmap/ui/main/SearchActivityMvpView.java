package com.abhishek.zipmap.ui.main;

import com.abhishek.zipmap.Location;
import com.abhishek.zipmap.base.MvpView;

import java.util.ArrayList;

public interface SearchActivityMvpView extends MvpView{
    void startMapActivity(ArrayList<Location> placeList);

    void startShortestDistanceActivity(String firstLocation, String secondLocation);

    void showErrorToast(int error);
}
