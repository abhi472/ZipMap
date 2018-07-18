package com.abhishek.zipmap.di;

import com.abhishek.zipmap.ui.main.LocationAdapter;
import com.abhishek.zipmap.ui.main.SearchActivity;
import com.abhishek.zipmap.ui.main.SearchActivityMvpPresenter;
import com.abhishek.zipmap.ui.main.SearchActivityMvpView;
import com.abhishek.zipmap.ui.main.SearchActivityPresenter;
import com.abhishek.zipmap.utils.PlacesUtil;

import dagger.Module;
import dagger.Provides;

@Module
class SearchActivityModule  {

    @Provides
    PlacesUtil provideVisitorPagerAdapter(SearchActivity activity) {
        return new PlacesUtil(activity);
    }

    @Provides
    @PerActivity
    SearchActivityMvpPresenter<SearchActivityMvpView> provideSearchActivityPresenter(
            SearchActivityPresenter<SearchActivityMvpView> presenter) {
        return presenter;
    }

    @Provides
    LocationAdapter provideAdapter(SearchActivity activity) {
        return new LocationAdapter(activity);
    }
}
