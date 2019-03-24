package com.mujeeb.nycschools.view.academicDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mujeeb.nycschools.R;
import com.mujeeb.nycschools.mvp.academicdetails.AcademicDetailsContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.mujeeb.nycschools.common.ConstantString.DBN_KEY;


public class AcademicDetailsActivity extends AppCompatActivity implements AcademicDetailsContract.View {

    @BindView(R.id.tv_academic_name)
    TextView tvAcademicName;
    @BindView(R.id.tv_no_of_test_takers)
    TextView tvTestTakersNumber;
    @BindView(R.id.tv_maths_score)
    TextView tvMaths;
    @BindView(R.id.tv_reading_score)
    TextView tvReading;
    @BindView(R.id.tv_writing_score)
    TextView tvWriting;
    @BindView(R.id.pb_progressBar)
    ProgressBar progressBar;

    @Inject
    AcademicDetailsContract.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_details);
        ButterKnife.bind(this);
        final String dbn = getIntent().getStringExtra(DBN_KEY);
        presenter.getData(dbn);
    }

    @Override
    public void showSchoolName(@NonNull String schoolName) {
        tvAcademicName.setText(getString(R.string.school_name_label, schoolName));
    }

    @Override
    public void showMathsResult(@NonNull String maths) {
        tvMaths.setText(getString(R.string.maths_score_label, maths));
    }

    @Override
    public void showReadingResult(@NonNull String english) {
        tvReading.setText(getString(R.string.reading_score_label, english));
    }

    @Override
    public void showWritingResult(@NonNull String writing) {
        tvWriting.setText(getString(R.string.writing_score_label, writing));
    }

    @Override
    public void showNoOfTakersResult(@NonNull String testTakersNumber) {
        tvTestTakersNumber.setText(getString(R.string.no_of_test_takers_label, testTakersNumber));
    }

    @Override
    public void showError(@NonNull String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }
}
