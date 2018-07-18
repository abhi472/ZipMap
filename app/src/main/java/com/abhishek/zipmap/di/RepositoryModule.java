package com.abhishek.zipmap.di;

import com.abhishek.zipmap.repository.Api;
import com.abhishek.zipmap.repository.ApiRepository;

import dagger.Binds;
import dagger.Module;

@Module
public interface RepositoryModule {

    @Binds
    Api bindApiServiceRepository(ApiRepository apiRepository );
}
