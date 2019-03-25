package com.mujeeb.nycschools.api;

import com.mujeeb.nycschools.model.School;
import com.mujeeb.nycschools.model.SchoolDetails;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NYCSchoolsApiCall {


    @GET("f9bf-2cp4.json")
    Observable<List<SchoolDetails>> getSchoolDetails(@Query("$$app_token") String apToken,
                                                     @Query("dbn") String dbn);


    @GET("s3k6-pzi2.json")
    Observable<List<School>> getSchoolResults(@Query("$$app_token") String appToken,
                                              @Query("$offset") int offset,
                                              @Query("$limit") int limit);


}
