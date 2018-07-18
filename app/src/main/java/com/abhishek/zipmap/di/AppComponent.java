package com.abhishek.zipmap.di;

import com.abhishek.zipmap.ZipApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        AppModule.class})
public interface AppComponent extends AndroidInjector<ZipApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(ZipApp app);


        AppComponent build();
    }

    @Override
    void inject(ZipApp instance);
}
