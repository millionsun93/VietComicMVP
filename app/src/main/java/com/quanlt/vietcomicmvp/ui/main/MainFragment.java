package com.quanlt.vietcomicmvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.quanlt.vietcomicmvp.ComicApplication;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.ui.detail.ComicDetailActivity;
import com.quanlt.vietcomicmvp.util.ComicFilter;
import com.quanlt.vietcomicmvp.util.EndlessRecyclerViewOnScrollListener;
import com.quanlt.vietcomicmvp.util.OnItemClickListener;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by Le Thuong Quan on 14/11/2016.
 */
public class MainFragment extends AbstractFragment implements MainContract.View {
    private static final String TAG = MainFragment.class.getSimpleName();
    private MainContract.Presenter presenter;
    EndlessRecyclerViewOnScrollListener endlessRecyclerViewOnScrollListener;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ComicApplication) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        endlessRecyclerViewOnScrollListener = new EndlessRecyclerViewOnScrollListener(getGridLayoutManager()) {
            @Override
            public void onLoadMore() {
                presenter.loadMore();
            }
        };
        mRecyclerView.addOnScrollListener(endlessRecyclerViewOnScrollListener);
        getAdapter().setOnItemClickListener((itemView, position) ->
                presenter.openComic(getAdapter().getItem(position)));

    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
        endlessRecyclerViewOnScrollListener.setLoading(false);
        presenter.setView(this);
        presenter.loadComic(null);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onRefreshAction() {
        if (presenter.getFilter() == ComicFilter.ALL)
            presenter.loadComic(null);
        else
            mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showLoading(boolean isActive) {
        mSwipeRefreshLayout.setRefreshing(isActive);
    }

    @Override
    public void showComics(List<Comic> comics) {
        getAdapter().setComics(comics);
        updateGridLayout();
    }

    @Override
    public void addComics(List<Comic> comics) {
        endlessRecyclerViewOnScrollListener.setLoading(false);
        getAdapter().addComics(comics);
    }

    @Override
    public void showNoComic() {
        updateGridLayout();
    }

    @Override
    public void showError(String message) {
        endlessRecyclerViewOnScrollListener.setLoading(false);
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openComic(Comic comic) {
        Intent intent = new Intent(getActivity(), ComicDetailActivity.class);
        intent.putExtra(ComicDetailActivity.ARG_COMIC, comic);
        startActivity(intent);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter =  presenter;
    }
}
