package com.quanlt.vietcomicmvp.ui.main;

import android.util.Log;

import com.quanlt.vietcomicmvp.data.DataManager;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.ComicFilter;
import com.quanlt.vietcomicmvp.util.Utils;

import javax.inject.Inject;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Le Thuong Quan on 12/11/2016.
 */
public class MainPhonePresenter implements MainContract.Presenter {
    private final DataManager mDataManager;
    private final Realm realm;
    private MainContract.View mView;
    private boolean isLoading;
    private ComicFilter filter = ComicFilter.ALL;
    @Inject
    public MainPhonePresenter(DataManager mDataManager, Realm realm) {
        this.mDataManager = mDataManager;
        this.realm = realm;
    }

    @Override
    public void setView(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadComic(Integer page) {
        if (isLoading)
            return;
        isLoading = true;
        mView.showLoading(true);
        Log.d(getClass().getSimpleName(),"loadComic " + (filter==ComicFilter.ALL));
        mDataManager.syncComic(page, filter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comics->{
                    isLoading = false;
                    mView.showLoading(false);
                    mView.showComics(comics);
                },error->{
                    isLoading = false;
                    error.printStackTrace();
                    mView.showLoading(false);
                    mView.showError(error.getMessage());
                });
    }

    @Override
    public void loadMore() {
        if (filter == ComicFilter.FAVORITE)
            return;
        if (isLoading) return;
        isLoading = true;
        mView.showLoading(true);
        mDataManager.getComics(getCurrentPage() + 1, this.filter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comics -> {
                    isLoading = false;
                    mView.showLoading(false);
                    mView.addComics(comics);
                }, error -> {
                    error.printStackTrace();
                    isLoading = false;
                    mView.showLoading(false);
                    mView.showError(error.getMessage());
                });

    }

    @Override
    public void openComic(Comic comic) {
        mView.openComic(comic);
    }

    private int getCurrentPage() {
        return realm.where(Comic.class).findAll().size() / Utils.ITEM_PERPAGE;
    }
    @Override
    public void setFilter(ComicFilter filter) {
        this.filter = filter;
    }
    @Override
    public ComicFilter getFilter() {
        return filter;
    }
}
