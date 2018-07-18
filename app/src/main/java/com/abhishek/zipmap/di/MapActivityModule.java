package com.abhishek.zipmap.di;

import com.abhishek.zipmap.ui.animate.MapActivityMvpPresenter;
import com.abhishek.zipmap.ui.animate.MapActivityMvpView;
import com.abhishek.zipmap.ui.animate.MapActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
class MapActivityModule {


    @Provides
    @PerActivity
    MapActivityMvpPresenter<MapActivityMvpView> provideMapActivityPresenter(
            MapActivityPresenter<MapActivityMvpView> presenter) {
        return presenter;
    }

}
