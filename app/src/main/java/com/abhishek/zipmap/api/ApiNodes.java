package com.abhishek.zipmap.api;


import com.abhishek.zipmap.data.MapResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiNodes {

    @GET("api/directions/json")
    Observable<MapResponse> getDirectionFromCoordinates(@Query("origin") String origin,
                                                        @Query("destination") String destination,
                                                        @Query("waypoints") String waypoints,
                                                        @Query("sensor") String sensor,
                                                        @Query("mode") String mode);
}
