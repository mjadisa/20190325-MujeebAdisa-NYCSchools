package com.mujeeb.nycschools.di.module;


import com.mujeeb.nycschools.di.scope.HomeScope;
import com.mujeeb.nycschools.mvp.home.HomeActivity;
import com.mujeeb.nycschools.mvp.home.SchoolListRecyclerViewAdapter;
import com.mujeeb.nycschools.mvp.home.SchoolSelectedInterface;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    @HomeScope
    public SchoolListRecyclerViewAdapter provideSchoolListRecyclerViewAdapter(SchoolSelectedInterface schoolSelectedInterface) {
        return new SchoolListRecyclerViewAdapter(schoolSelectedInterface);
    }


    @Provides
    @HomeScope
    public SchoolSelectedInterface provideSchoolSelectedInterface(HomeActivity homeActivity) {
        return homeActivity;
    }

}