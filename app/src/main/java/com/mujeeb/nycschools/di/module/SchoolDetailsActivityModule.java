package com.mujeeb.nycschools.di.module;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.di.scope.SchoolDetailsScope;
import com.mujeeb.nycschools.mvp.schooldetails.SchoolDetailsActivity;
import com.mujeeb.nycschools.mvp.schooldetails.SchoolDetailsContract;
import com.mujeeb.nycschools.mvp.schooldetails.SchoolDetailsPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SchoolDetailsActivityModule {


    @SchoolDetailsScope
    @Provides
    static SchoolDetailsContract.Presenter providePresenter(SchoolDetailsContract.View view, NYCSchoolsApiCall apiCall) {
        return new SchoolDetailsPresenter(view, apiCall);
    }

    @SchoolDetailsScope
    @Binds
    public abstract SchoolDetailsContract.View provideView(SchoolDetailsActivity schoolDetailsActivity);
}
