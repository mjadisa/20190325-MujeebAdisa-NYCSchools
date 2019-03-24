package com.mujeeb.nycschools.di.module;

import android.app.Application;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.di.qualifier.ApiInterceptor;
import com.mujeeb.nycschools.di.qualifier.LoggingInterceptor;
import com.mujeeb.nycschools.di.scope.ApplicationScope;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mujeeb.nycschools.common.ConstantString.APP_TOKEN;
import static com.mujeeb.nycschools.common.ConstantString.BASE_URL;
import static com.mujeeb.nycschools.common.ConstantString.CACHE_SIZE;
import static com.mujeeb.nycschools.common.ConstantString.TIMEOUT_REQUEST;

@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideOkHttpClient(Cache cache,
                                            @LoggingInterceptor Interceptor httpLoggingInterceptor,
                                            @ApiInterceptor Interceptor apiInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(apiInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Provides
    @LoggingInterceptor
    @ApplicationScope
    public Interceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @ApiInterceptor
    @ApplicationScope
    public Interceptor provideApiInterceptor() {
        return chain -> {
            HttpUrl originalUrl = chain.request().url();
            HttpUrl newUrl = originalUrl.newBuilder()
                    .addQueryParameter("$$app_token", APP_TOKEN)
                    .build();
            Request request = chain.request().newBuilder()
                    .url(newUrl)
                    .build();
            return chain.proceed(request);
        };
    }


    @Provides
    @ApplicationScope
    public Cache provideCache(Application application) {
        return new Cache(application.getCacheDir(), CACHE_SIZE);
    }


    @Provides
    @ApplicationScope
    public NYCSchoolsApiCall provideService(Retrofit retrofit) {
        return retrofit.create(NYCSchoolsApiCall.class);
    }


}
