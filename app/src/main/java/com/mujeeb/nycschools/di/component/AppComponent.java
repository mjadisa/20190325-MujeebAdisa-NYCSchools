package com.mujeeb.nycschools.di.component;

import android.app.Application;

import com.mujeeb.nycschools.common.SchoolApp;
import com.mujeeb.nycschools.di.module.BuildHomeActivityModule;
import com.mujeeb.nycschools.di.module.BuildSchoolDetailsActivityModule;
import com.mujeeb.nycschools.di.module.NetworkModule;
import com.mujeeb.nycschools.di.scope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {AndroidInjectionModule.class, BuildSchoolDetailsActivityModule.class,
        BuildHomeActivityModule.class, NetworkModule.class})
@ApplicationScope
public interface AppComponent {
    void inject(SchoolApp schoolApp);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
