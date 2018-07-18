package com.abhishek.zipmap.repository;


import com.abhishek.zipmap.data.MapResponse;

import io.reactivex.Observable;

public interface Api {

    Observable<MapResponse> getDirectionFromCoordinates(String origin,
                                                        String destination,
                                                        String waypoints,
                                                        String sensor,
                                                        String mode);
}
