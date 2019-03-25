package com.mujeeb.nycschools.mvp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mujeeb.nycschools.R;
import com.mujeeb.nycschools.common.PaginationListener;
import com.mujeeb.nycschools.model.School;
import com.mujeeb.nycschools.mvp.schooldetails.SchoolDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.mujeeb.nycschools.common.Constants.DBN_KEY;


public class HomeActivity extends AppCompatActivity implements HomeContract.View, SchoolSelectedInterface,
        PaginationListener.PaginationStateListener {

    @BindView(R.id.rv_result)
    RecyclerView recyclerView;
    @BindView(R.id.pb_home)
    ProgressBar progressBar;

    @Inject
    HomeContract.Presenter presenter;

    @Inject
    SchoolListRecyclerViewAdapter schoolAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new PaginationListener(linearLayoutManager, this));
        recyclerView.setAdapter(schoolAdapter);

        presenter.getResults(true);
    }


    @Override
    public void showResults(@NonNull List<School> schoolList) {
        schoolAdapter.setData(schoolList);
    }

    @Override
    public void showError(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void navigateToDetails(@NonNull String dbn) {
        final Intent intent = new Intent(this, SchoolDetailsActivity.class);
        intent.putExtra(DBN_KEY, dbn);
        startActivity(intent);
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
    public void clearResults() {
        schoolAdapter.clearData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }


    @Override
    public boolean isLoading() {
        return presenter.getLoadingState();
    }

    @Override
    public boolean isLastPage() {
        return presenter.isLastPage();
    }

    @Override
    public void loadMoreItems() {
        presenter.getResults(false);

    }

    @Override
    public void onResultSelected(int position) {
        presenter.handleSearchResultSelection(position);
    }
}
