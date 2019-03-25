package com.mujeeb.nycschools.mvp.home;

import android.support.annotation.NonNull;

import com.mujeeb.nycschools.model.School;

import java.util.List;

public interface HomeContract {
    interface View {
        void showResults(@NonNull List<School> schools);

        void showError(@NonNull String message);

        void navigateToDetails(@NonNull String dbn);

        void showProgress();

        void hideProgress();

        void clearResults();
    }

    interface Presenter {

        void getResults(boolean isNewPage);

        void handleSearchResultSelection(int position);

        void stop();

        boolean getLoadingState();

        boolean isLastPage();


    }
}
