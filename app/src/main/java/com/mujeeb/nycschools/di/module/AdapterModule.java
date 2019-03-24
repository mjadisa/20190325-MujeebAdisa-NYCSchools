package com.mujeeb.nycschools.di.module;


import com.mujeeb.nycschools.di.scope.HomeScope;
import com.mujeeb.nycschools.view.home.AcademicListRecyclerViewAdapter;
import com.mujeeb.nycschools.view.home.AcademicSelectedInterface;
import com.mujeeb.nycschools.view.home.HomeActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    @HomeScope
    public AcademicListRecyclerViewAdapter provideAcademicListRecyclerViewAdapter(AcademicSelectedInterface academicSelectedInterface) {
        return new AcademicListRecyclerViewAdapter(academicSelectedInterface);
    }


    @Provides
    @HomeScope
    public AcademicSelectedInterface provideAcademicSelectedInterface(HomeActivity homeActivity) {
        return homeActivity;
    }

}