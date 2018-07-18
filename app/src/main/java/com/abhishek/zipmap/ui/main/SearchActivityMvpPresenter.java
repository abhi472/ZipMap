package com.abhishek.zipmap.ui.main;

import com.abhishek.zipmap.Location;
import com.abhishek.zipmap.base.MvpPresenter;

public interface SearchActivityMvpPresenter<V extends SearchActivityMvpView> extends MvpPresenter<V> {


    void updatePlace(Location place);

    void setAdapter(LocationAdapter adapter);

    void removeElement(int position);

    void onClick();

    void onMapClick();

}
