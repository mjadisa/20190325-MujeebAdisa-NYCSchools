package com.mujeeb.nycschools.di.module;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.di.scope.AcademicDetailsScope;
import com.mujeeb.nycschools.mvp.academicdetails.AcademicDetailsContract;
import com.mujeeb.nycschools.mvp.academicdetails.AcademicDetailsPresenter;
import com.mujeeb.nycschools.view.academicDetails.AcademicDetailsActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AcademicDetailsActivityModule {


    @AcademicDetailsScope
    @Provides
    static AcademicDetailsContract.Presenter providePresenter(AcademicDetailsContract.View view, NYCSchoolsApiCall apiCall) {
        return new AcademicDetailsPresenter(view, apiCall);
    }

    @AcademicDetailsScope
    @Binds
    public abstract AcademicDetailsContract.View provideView(AcademicDetailsActivity academicDetailsActivity);
}
