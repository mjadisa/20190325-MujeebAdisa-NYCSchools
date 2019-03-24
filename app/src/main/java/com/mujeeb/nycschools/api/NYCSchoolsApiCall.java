package com.mujeeb.nycschools.api;

import com.mujeeb.nycschools.model.Academic;
import com.mujeeb.nycschools.model.AcademicDetails;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NYCSchoolsApiCall {

    @GET("s3k6-pzi2.json")
    Observable<List<Academic>> getAcademicResultsByCity(@Query("$$app_token") String appToken,
                                                        @Query("city") String searchQuery,
                                                        @Query("$offset") int pageNumber,
                                                        @Query("$limit") int limit);

    @GET("f9bf-2cp4.json")
    Observable<List<AcademicDetails>> getAcademicDetails(@Query("$$app_token") String apToken,
                                                         @Query("dbn") String dbn);


    @GET("s3k6-pzi2.json")
    Observable<List<Academic>> getAcademicResults(@Query("$$app_token") String appToken,
                                                  @Query("$offset") int pageNumber,
                                                  @Query("$limit") int limit);


}
