package com.mujeeb.nycschools.di.module;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.di.scope.HomeScope;
import com.mujeeb.nycschools.mvp.home.HomeActivity;
import com.mujeeb.nycschools.mvp.home.HomeContract;
import com.mujeeb.nycschools.mvp.home.HomePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class HomeActivityModule {

    @HomeScope
    @Provides
    static HomeContract.Presenter providePresenter(HomeContract.View view, NYCSchoolsApiCall apiCall) {
        return new HomePresenter(view, apiCall);
    }

    @HomeScope
    @Binds
    public abstract HomeContract.View provideView(HomeActivity homeActivity);
}
