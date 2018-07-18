package com.abhishek.zipmap;

import android.content.Context;

import com.abhishek.zipmap.di.DaggerAppComponent;
import com.facebook.stetho.Stetho;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class ZipApp extends DaggerApplication {

    AndroidInjector<? extends DaggerApplication> androidInjector;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        androidInjector = DaggerAppComponent.builder()
                .application(this)
                .build();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return androidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

    }
}
