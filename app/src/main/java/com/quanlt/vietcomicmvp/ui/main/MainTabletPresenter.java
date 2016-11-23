package com.quanlt.vietcomicmvp.ui.main;

import com.quanlt.vietcomicmvp.data.DataManager;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.ui.detail.ComicDetailContract;
import com.quanlt.vietcomicmvp.ui.detail.ComicDetailPresenter;
import com.quanlt.vietcomicmvp.util.ComicFilter;

import javax.inject.Inject;

/**
 * Created by Le Thuong Quan on 16/11/2016.
 */
public class MainTabletPresenter implements MainContract.Presenter, ComicDetailContract.Presenter {

    private final DataManager mDataManager;
    private final MainPhonePresenter mPhonePresenter;
    private final ComicDetailPresenter mComicDetailPresenter;

    @Inject
    public MainTabletPresenter(DataManager mDataManager, MainPhonePresenter mPhonePresenter, ComicDetailPresenter mComicDetailPresenter) {
        this.mDataManager = mDataManager;
        this.mPhonePresenter = mPhonePresenter;
        this.mComicDetailPresenter = mComicDetailPresenter;
    }

    @Override
    public void loadComicDetail() {
        mComicDetailPresenter.loadComicDetail();
    }

    @Override
    public void setDetailView(ComicDetailContract.View view) {
        mComicDetailPresenter.setDetailView(view);
    }

    @Override
    public void setComic(Comic comic) {
        mComicDetailPresenter.setComic(comic);
    }

    @Override
    public void toggleFavorite() {
        mComicDetailPresenter.toggleFavorite();
    }

    @Override
    public Comic getComic() {
        return mComicDetailPresenter.getComic();
    }

    @Override
    public void loadComic(Integer page) {
        mPhonePresenter.loadComic(page);
    }

    @Override
    public void loadMore() {
        mPhonePresenter.loadMore();
    }

    @Override
    public void openComic(Comic comic) {
        mComicDetailPresenter.setComic(comic);
        mComicDetailPresenter.loadComicDetail();
    }

    @Override
    public void setView(MainContract.View view) {
        mPhonePresenter.setView(view);
    }

    @Override
    public void setFilter(ComicFilter filter) {
        mPhonePresenter.setFilter(filter);
    }

    @Override
    public ComicFilter getFilter() {
        return mPhonePresenter.getFilter();
    }
}
