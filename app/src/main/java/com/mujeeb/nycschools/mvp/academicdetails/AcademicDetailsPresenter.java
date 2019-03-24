package com.mujeeb.nycschools.mvp.academicdetails;

import android.support.annotation.Nullable;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.model.AcademicDetails;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.mujeeb.nycschools.common.ConstantString.APP_TOKEN;


public class AcademicDetailsPresenter implements AcademicDetailsContract.Presenter {

    private final AcademicDetailsContract.View view;
    private final NYCSchoolsApiCall apiCall;
    private final CompositeDisposable compositeDisposable;


    public AcademicDetailsPresenter(AcademicDetailsContract.View view, NYCSchoolsApiCall apiCall) {
        this.view = view;
        this.apiCall = apiCall;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getData(String dbn) {
        if (dbn != null) {
            compositeDisposable.add(apiCall.getAcademicDetails(APP_TOKEN, dbn)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> view.showProgress())
                    .doOnTerminate(view::hideProgress)
                    .subscribe(this::handleResult, this::handleError));
        }
    }

    private void handleResult(@Nullable List<AcademicDetails> academicDetailsResponse) {

        if (academicDetailsResponse != null) {
            view.showSchoolName(academicDetailsResponse.get(0).getSchoolName());
            view.showNoOfTakersResult(academicDetailsResponse.get(0).getNumOfSatTestTakers());
            view.showMathsResult(academicDetailsResponse.get(0).getSatMathAvgScore());
            view.showReadingResult(academicDetailsResponse.get(0).getSatWritingAvgScore());
            view.showWritingResult(academicDetailsResponse.get(0).getSatCriticalReadingAvgScore());

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
