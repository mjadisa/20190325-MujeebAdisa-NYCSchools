package com.mujeeb.nycschools.mvp.home;

import android.support.annotation.Nullable;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.model.Academic;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.mujeeb.nycschools.common.ConstantString.APP_TOKEN;
import static com.mujeeb.nycschools.common.ConstantString.PAGE_START;
import static com.mujeeb.nycschools.common.ConstantString.RESULTS_PER_PAGE;
import static com.mujeeb.nycschools.common.ConstantString.TOTAL_PAGES;


public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    private final NYCSchoolsApiCall apiCall;
    private final CompositeDisposable compositeDisposable;
    private final List<Academic> academics;

    private boolean isLoading;
    private int currentPage = PAGE_START;
    private boolean isLastPage;


    public HomePresenter(HomeContract.View view, NYCSchoolsApiCall apiCall) {
        this.view = view;
        this.apiCall = apiCall;
        compositeDisposable = new CompositeDisposable();
        academics = new ArrayList<>();
        isLoading = false;
        isLastPage = false;
    }

    @Override
    public void getResultsByCity(@Nullable String searchTerm, boolean isNewQuery) {
        if (isNewQuery) {
            view.clearResults();
            academics.clear();
            currentPage = PAGE_START;
            isLastPage = isLoading = false;
        }
        if (searchTerm != null && !isLoading) {
            compositeDisposable.add(apiCall.getAcademicResultsByCity(APP_TOKEN, searchTerm, currentPage, RESULTS_PER_PAGE)
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
    public void getResults(boolean isNewQuery) {
        if (isNewQuery) {
            view.clearResults();
            academics.clear();
            currentPage = PAGE_START;
            isLastPage = isLoading = false;
        }
        if (!isLoading) {
            compositeDisposable.add(apiCall.getAcademicResults(APP_TOKEN, currentPage, RESULTS_PER_PAGE)
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
        if (position >= 0 && position < academics.size()) {
            view.navigateToDetails(academics.get(position).getDbn());
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

    private void handleResult(@Nullable List<Academic> academicResultsResponse) {
        if (academicResultsResponse != null) {
            isLastPage = currentPage >= TOTAL_PAGES;
            currentPage++;
            this.academics.addAll(academicResultsResponse);
            view.showResults(academicResultsResponse);
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
