package com.abhishek.zipmap.di;

import com.abhishek.zipmap.api.NetworkModule;

import dagger.Module;

@Module(includes = {NetworkModule.class, RepositoryModule.class})
class AppModule {

}
