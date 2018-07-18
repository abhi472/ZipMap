package com.abhishek.zipmap.ui.animate;


import com.abhishek.zipmap.base.BasePresenter;
import com.abhishek.zipmap.repository.ApiRepository;
import com.abhishek.zipmap.data.Leg;
import com.abhishek.zipmap.data.MapResponse;
import com.abhishek.zipmap.data.Route;
import com.abhishek.zipmap.data.Step;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MapActivityPresenter<V extends MapActivityMvpView>
        extends BasePresenter<V>
        implements MapActivityMvpPresenter<V> {

    private final ApiRepository repository;

    private CompositeDisposable mCompositeDisposable;
    private PublishSubject<String> subject;
    private ArrayList<LatLng> locationCoordinates = new ArrayList<>();


    @Inject
    public MapActivityPresenter(ApiRepository repository) {
        this.repository = repository;
        mCompositeDisposable = new CompositeDisposable();

    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        subject = PublishSubject.create();
        mCompositeDisposable.add(subject
                .concatMap(i -> {
                    String origin = String.valueOf(locationCoordinates.get(0).latitude)
                            + "," + String.valueOf(locationCoordinates.get(0).longitude);
                    String destination = String.valueOf(locationCoordinates.get(locationCoordinates.size()-1).latitude)
                            + "," + String.valueOf(locationCoordinates.get(locationCoordinates.size() -1 ).longitude);

                    if(i.isEmpty()) {
                        i = null;
                    }

                    return repository.getDirectionFromCoordinates(origin,
                            destination,
                            i,
                            "false",
                            "driving")
                            .subscribeOn(Schedulers.io());
                }).concatMap(this::getEndPointsObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(localLatLngs -> {

                    getMvpView().updateCoordinates(localLatLngs);
                }, throwable -> {

                })

        );
    }

    private Observable<ArrayList<LatLng>> getEndPointsObservable(MapResponse mapResponse) {
        return Observable.fromCallable(() -> getEndPoints(mapResponse) );
    }

    private ArrayList<LatLng> getEndPoints(MapResponse mapResponse) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (Route r : mapResponse.getRoutes()) {
            for (Leg leg : r.getLegs()) {
                for (Step step : leg.getSteps()) {
                    String polyLine = step.getPolyline().getPoints();
                    List decodedPolyline = MapActivityPresenter.this.decodePoly(polyLine);
                    for (int i = 0; i < decodedPolyline.size(); i++) {
                        latLngs.add((LatLng) decodedPolyline.get(i));
                    }
                }
            }
        }
        return latLngs;
    }

    @Override
    public void startGettingMapCoordinates(ArrayList<LatLng> locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
        String waypoints;
        if(locationCoordinates.size() > 2) {
            waypoints = "via:";
            for (int i = 1; i < locationCoordinates.size() - 1; i++) {
                waypoints += String.valueOf(locationCoordinates.get(i).latitude) + "," + String.valueOf(locationCoordinates.get(i).longitude);
                if (i != locationCoordinates.size() - 2)
                    waypoints += "|via:";
            }
        } else
            waypoints = "";

       subject.onNext(waypoints);

    }

    private List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        super.onDetach();
    }
}
