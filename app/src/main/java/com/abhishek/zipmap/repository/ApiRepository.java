package com.abhishek.zipmap.repository;

import com.abhishek.zipmap.api.AppApiHelper;
import com.abhishek.zipmap.data.MapResponse;
import com.abhishek.zipmap.repository.Api;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class ApiRepository implements Api {
    private AppApiHelper apiHelper;

    @Inject
    ApiRepository(AppApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public Observable<MapResponse> getDirectionFromCoordinates(String origin,
                                                               String destination,
                                                               String waypoints,
                                                               String sensor,
                                                               String mode) {
        return apiHelper.getDirectionFromCoordinates(origin,
                destination,
                waypoints,
                sensor,
                mode);
    }
}
