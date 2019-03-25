package com.mujeeb.nycschools.di.module;

import com.mujeeb.nycschools.di.scope.SchoolDetailsScope;
import com.mujeeb.nycschools.mvp.schooldetails.SchoolDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildSchoolDetailsActivityModule {

    @ContributesAndroidInjector(modules = SchoolDetailsActivityModule.class)
    @SchoolDetailsScope
    abstract SchoolDetailsActivity schoolDetailsActivity();
}
