package com.mujeeb.nycschools.di.component;

import android.app.Application;

import com.mujeeb.nycschools.common.AcademicApp;
import com.mujeeb.nycschools.di.module.BuildAcademicDetailsActivityModule;
import com.mujeeb.nycschools.di.module.BuildHomeActivityModule;
import com.mujeeb.nycschools.di.module.NetworkModule;
import com.mujeeb.nycschools.di.scope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {AndroidInjectionModule.class, BuildAcademicDetailsActivityModule.class,
        BuildHomeActivityModule.class, NetworkModule.class})
@ApplicationScope
public interface AppComponent {
    void inject(AcademicApp academicApp);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
