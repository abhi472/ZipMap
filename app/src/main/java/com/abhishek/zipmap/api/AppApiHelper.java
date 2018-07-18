package com.abhishek.zipmap.api;


import com.abhishek.zipmap.data.MapResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppApiHelper implements ApiNodes {

    private ApiNodes apiNodes;

    @Inject
    AppApiHelper(ApiNodes apiNodes) {
        this.apiNodes = apiNodes;
    }

    @Override
    public Observable<MapResponse> getDirectionFromCoordinates(String origin,
                                                               String destination,
                                                               String waypoints,
                                                               String sensor,
                                                               String mode) {
        return apiNodes.getDirectionFromCoordinates(origin, destination, waypoints, sensor, mode);
    }
}
