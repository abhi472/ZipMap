package com.abhishek.zipmap.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {


    private V mvpView;


    @Inject
    public BasePresenter() {

    }


    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;

    }

    @Override
    public void onDetach() {
        this.mvpView = null;
    }

    @Override
    public void onBackClick() {

    }


    public V getMvpView() {
        return mvpView;
    }
}
