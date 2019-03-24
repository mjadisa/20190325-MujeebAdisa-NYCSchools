package com.mujeeb.nycschools.view.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mujeeb.nycschools.common.PaginationListener;
import com.mujeeb.nycschools.R;
import com.mujeeb.nycschools.model.Academic;
import com.mujeeb.nycschools.mvp.home.HomeContract;
import com.mujeeb.nycschools.view.academicDetails.AcademicDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.mujeeb.nycschools.common.ConstantString.DBN_KEY;


public class HomeActivity extends AppCompatActivity implements HomeContract.View, AcademicSelectedInterface,
        PaginationListener.PaginationStateListener {

    @BindView(R.id.rv_result)
    RecyclerView recyclerView;
    @BindView(R.id.pb_home)
    ProgressBar progressBar;

    @Inject
    HomeContract.Presenter presenter;

    @Inject
    AcademicListRecyclerViewAdapter academicAdapter;

    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        processIntent(getIntent());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new PaginationListener(linearLayoutManager, this));
        recyclerView.setAdapter(academicAdapter);

        presenter.getResults(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchTerm = intent.getStringExtra(SearchManager.QUERY);
            presenter.getResultsByCity(searchTerm, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    @Override
    public void showResults(@NonNull List<Academic> academicList) {
        academicAdapter.setData(academicList);
    }

    @Override
    public void showError(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void navigateToDetails(@NonNull String dbn) {
        final Intent intent = new Intent(this, AcademicDetailsActivity.class);
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
        academicAdapter.clearData();
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
        if (searchTerm == null || searchTerm.isEmpty()) {
            presenter.getResults(false);
        } else {
            presenter.getResultsByCity(searchTerm, false);
        }
    }

    @Override
    public void onResultSelected(int position) {
        presenter.handleSearchResultSelection(position);
    }
}
