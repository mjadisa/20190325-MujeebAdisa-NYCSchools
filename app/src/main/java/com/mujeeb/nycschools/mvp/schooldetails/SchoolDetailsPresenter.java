package com.mujeeb.nycschools.mvp.schooldetails;

import android.support.annotation.Nullable;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.model.SchoolDetails;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.mujeeb.nycschools.common.Constants.APP_TOKEN;


public class SchoolDetailsPresenter implements SchoolDetailsContract.Presenter {

    private final SchoolDetailsContract.View view;
    private final NYCSchoolsApiCall apiCall;
    private final CompositeDisposable compositeDisposable;


    public SchoolDetailsPresenter(SchoolDetailsContract.View view, NYCSchoolsApiCall apiCall) {
        this.view = view;
        this.apiCall = apiCall;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getData(String dbn) {
        if (dbn != null) {
            compositeDisposable.add(apiCall.getSchoolDetails(APP_TOKEN, dbn)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> view.showProgress())
                    .doOnTerminate(view::hideProgress)
                    .subscribe(this::handleResult, this::handleError));
        }
    }

    private void handleResult(@Nullable List<SchoolDetails> schoolDetailsResponse) {

        if (schoolDetailsResponse != null) {
            view.showSchoolName(schoolDetailsResponse.get(0).getSchoolName());
            view.showNoOfTakersResult(schoolDetailsResponse.get(0).getNumOfSatTestTakers());
            view.showMathsResult(schoolDetailsResponse.get(0).getSatMathAvgScore());
            view.showReadingResult(schoolDetailsResponse.get(0).getSatWritingAvgScore());
            view.showWritingResult(schoolDetailsResponse.get(0).getSatCriticalReadingAvgScore());

        }
    }


    private void handleError(Throwable throwable) {
        if (throwable.getMessage() != null) {
            view.showError(throwable.getMessage());
        }
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }
}
