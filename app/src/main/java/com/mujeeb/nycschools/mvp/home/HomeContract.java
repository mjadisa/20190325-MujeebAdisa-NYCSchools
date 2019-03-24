package com.mujeeb.nycschools.mvp.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mujeeb.nycschools.model.Academic;

import java.util.List;

public interface HomeContract {
    interface View {
        void showResults(@NonNull List<Academic> academics);

        void showError(@NonNull String message);

        void navigateToDetails(@NonNull String dbn);

        void showProgress();

        void hideProgress();

        void clearResults();
    }

    interface Presenter {
        void getResultsByCity(@Nullable String searchTerm, boolean isNewQuery);

        void getResults(boolean isNewQuery);

        void handleSearchResultSelection(int position);

        void stop();

        boolean getLoadingState();

        boolean isLastPage();


    }
}
