package com.mujeeb.nycschools.di.module;

import com.mujeeb.nycschools.di.scope.AcademicDetailsScope;
import com.mujeeb.nycschools.view.academicDetails.AcademicDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildAcademicDetailsActivityModule {

    @ContributesAndroidInjector(modules = AcademicDetailsActivityModule.class)
    @AcademicDetailsScope
    abstract AcademicDetailsActivity academicDetailsActivity();
}
