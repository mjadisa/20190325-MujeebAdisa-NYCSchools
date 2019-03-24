package com.mujeeb.nycschools.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Objects;

import static com.mujeeb.nycschools.common.ConstantString.RESULTS_PER_PAGE;

public class PaginationListener extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager linearLayoutManager;
    private final PaginationStateListener listener;

    public PaginationListener(@NonNull LinearLayoutManager linearLayoutManager,
                              @NonNull PaginationStateListener paginationStateListener) {
        Objects.requireNonNull(linearLayoutManager);
        Objects.requireNonNull(paginationStateListener);
        this.linearLayoutManager = linearLayoutManager;
        this.listener = paginationStateListener;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        final int visibleItemCount = linearLayoutManager.getChildCount();
        final int totalCount = linearLayoutManager.getItemCount();
        final int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (!listener.isLastPage() && !listener.isLoading()) {
            if ((visibleItemCount + firstVisiblePosition) >= totalCount &&
                    firstVisiblePosition >= 0 &&
                    totalCount >= RESULTS_PER_PAGE) {
                listener.loadMoreItems();
            }
        }
    }

    public interface PaginationStateListener {
        boolean isLoading();

        boolean isLastPage();

        void loadMoreItems();

    }
}
