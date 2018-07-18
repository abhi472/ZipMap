package com.abhishek.zipmap.di;

import com.abhishek.zipmap.ui.animate.MapActivity;
import com.abhishek.zipmap.ui.main.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity contributesSearchInjector();

    @ContributesAndroidInjector(modules = MapActivityModule.class)
    abstract MapActivity contributesMapInjector();


}