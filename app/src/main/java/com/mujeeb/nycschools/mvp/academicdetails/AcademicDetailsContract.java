package com.mujeeb.nycschools.mvp.academicdetails;

import android.support.annotation.NonNull;

public interface AcademicDetailsContract {
    interface View {
        void showSchoolName(@NonNull String schoolName);

        void showMathsResult(@NonNull String maths);

        void showReadingResult(@NonNull String english);

        void showWritingResult(@NonNull String writing);

        void showNoOfTakersResult(@NonNull String testTakersNumber);

        void showError(@NonNull String errorMessage);

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void getData(String dbn);

        void stop();
    }
}
