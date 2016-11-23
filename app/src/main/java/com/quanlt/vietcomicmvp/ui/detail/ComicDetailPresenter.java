package com.quanlt.vietcomicmvp.ui.detail;

import android.util.Log;

import com.quanlt.vietcomicmvp.data.DataManager;
import com.quanlt.vietcomicmvp.model.Comic;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Le Thuong Quan on 15/11/2016.
 */
public class ComicDetailPresenter implements ComicDetailContract.Presenter {

    private ComicDetailContract.View mView;
    private final DataManager dataManager;
    private Comic comic;

    @Inject
    public ComicDetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    @Override
    public void loadComicDetail() {
        mView.setComic(comic);
        dataManager.syncComic(comic.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mComic -> {
                            this.comic = mComic;
                            mView.showComicDetail(mComic);
                            //temporary fix
                            mView.updateFab(mComic.isFavorite());
                        },
                        error -> {
                            error.printStackTrace();
                            mView.showError(error.getMessage());

                        });
    }

    @Override
    public void setDetailView(ComicDetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void toggleFavorite() {
        dataManager.setFavorite(this.comic);
        this.comic.setFavorite(!comic.isFavorite());
        mView.updateFab(this.comic.isFavorite());
    }

    @Override
    public Comic getComic() {
        return comic;
    }

}
