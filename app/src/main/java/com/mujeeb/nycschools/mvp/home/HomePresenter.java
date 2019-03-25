package com.mujeeb.nycschools.mvp.home;

import android.support.annotation.Nullable;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.model.School;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.mujeeb.nycschools.common.Constants.APP_TOKEN;
import static com.mujeeb.nycschools.common.Constants.RESULTS_PER_PAGE;
import static com.mujeeb.nycschools.common.Constants.START_RESULT_INDEX;
import static com.mujeeb.nycschools.common.Constants.TOTAL_RESULTS;


public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    private final NYCSchoolsApiCall apiCall;
    private final CompositeDisposable compositeDisposable;
    private final List<School> schools;

    private boolean isLoading;
    private int offset = START_RESULT_INDEX;
    private boolean isLastPage;


    public HomePresenter(HomeContract.View view, NYCSchoolsApiCall apiCall) {
        this.view = view;
        this.apiCall = apiCall;
        compositeDisposable = new CompositeDisposable();
        schools = new ArrayList<>();
        isLoading = false;
        isLastPage = false;
    }


    @Override
    public void getResults(boolean isNewPage) {
        if (isNewPage) {
            view.clearResults();
            schools.clear();
            isLastPage = isLoading = false;
        }
        if (!isLoading) {
            compositeDisposable.add(apiCall.getSchoolResults(APP_TOKEN, offset, RESULTS_PER_PAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> handleSubscriptionChange(true))
                    .doOnTerminate(() -> handleSubscriptionChange(false))
                    .subscribe(this::handleResult, this::handleError));
        } else {
            view.showError("Please try again");
        }

    }

    @Override
    public void handleSearchResultSelection(int position) {
        if (position >= 0 && position < schools.size()) {
            view.navigateToDetails(schools.get(position).getDbn());
        }
    }

    private void handleSubscriptionChange(boolean isSubscribed) {
        isLoading = isSubscribed;
        if (isSubscribed) {
            view.showProgress();
        } else {
            view.hideProgress();
        }
    }

    private void handleResult(@Nullable List<School> schoolResultsResponse) {
        if (schoolResultsResponse != null) {
            isLastPage = offset >= TOTAL_RESULTS;
            offset = +RESULTS_PER_PAGE;
            this.schools.addAll(schoolResultsResponse);
            view.showResults(schoolResultsResponse);
        } else {
            view.showError("An unexpected error happened while getting your search results, please try again");
        }
    }

    private void handleError(Throwable throwable) {
        if (throwable.getMessage() != null) {
            view.showError(throwable.getMessage());
        }
    }

    @Override
    public void stop() {
        if (isLoading) {
            handleSubscriptionChange(false);
        }
        compositeDisposable.clear();
    }


    @Override
    public boolean isLastPage() {
        return isLastPage;
    }

    @Override
    public boolean getLoadingState() {
        return isLoading;
    }

}
