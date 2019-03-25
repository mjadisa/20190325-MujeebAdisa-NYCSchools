package com.mujeeb.nycschools;

import com.mujeeb.nycschools.api.NYCSchoolsApiCall;
import com.mujeeb.nycschools.model.School;
import com.mujeeb.nycschools.mvp.home.HomeContract;
import com.mujeeb.nycschools.mvp.home.HomePresenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static com.mujeeb.nycschools.common.Constants.APP_TOKEN;
import static com.mujeeb.nycschools.common.Constants.RESULTS_PER_PAGE;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {


    private static final String DBN = "testDBN";
    @Mock
    private School school;
    @Mock
    private HomeContract.View view;
    @Mock
    private NYCSchoolsApiCall apiCall;


    private InOrder inOrder;
    private HomePresenter homePresenter;
    private List<School> schoolList;


    @BeforeClass
    public static void setupRxSchedulers() {
        Scheduler scheduler = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> scheduler);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> scheduler);
    }

    @Before
    public void setup() {
        inOrder = inOrder(view, apiCall);
        homePresenter = new HomePresenter(view, apiCall);
        schoolList = Collections.singletonList(school);


        when(apiCall.getSchoolResults(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(Observable.just(schoolList));

    }

    @Test
    public void getResults_IsNewQuery_ClearsDataAndShowsResult() {
        homePresenter.getResults(true);

        inOrder.verify(view).clearResults();

        inOrder.verify(apiCall).getSchoolResults(APP_TOKEN,
                0, RESULTS_PER_PAGE);
        inOrder.verify(view).showProgress();

        inOrder.verify(view).showResults(schoolList);

        inOrder.verify(view).hideProgress();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void getResults_IsNotNewQuery_IncrementsPageAndShowsResult() {
        homePresenter.getResults(false);

        inOrder.verify(apiCall).getSchoolResults(APP_TOKEN,
                0, RESULTS_PER_PAGE);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).showResults(schoolList);
        inOrder.verify(view).hideProgress();

        homePresenter.getResults(false);

        inOrder.verify(apiCall).getSchoolResults(APP_TOKEN,
                20, RESULTS_PER_PAGE);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).showResults(schoolList);
        inOrder.verify(view).hideProgress();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void handleSearchResultSelection_WithAValidPosition_NavigatesToDetails() {
        when(school.getDbn()).thenReturn(DBN);
        homePresenter.getResults(true);
        homePresenter.handleSearchResultSelection(0);
        verify(view).navigateToDetails(DBN);

    }


}
