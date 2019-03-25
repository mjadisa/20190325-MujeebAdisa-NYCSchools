package com.mujeeb.nycschools.di.module;

import com.mujeeb.nycschools.di.scope.HomeScope;
import com.mujeeb.nycschools.mvp.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildHomeActivityModule {
    @ContributesAndroidInjector(modules = {HomeActivityModule.class, AdapterModule.class})
    @HomeScope
    abstract HomeActivity homeActivity();
}
