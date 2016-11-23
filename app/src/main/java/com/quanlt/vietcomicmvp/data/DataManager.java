package com.quanlt.vietcomicmvp.data;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.quanlt.vietcomicmvp.data.local.DatabaseHelper;
import com.quanlt.vietcomicmvp.data.remote.ComicNetworkService;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.ComicFilter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Le Thuong Quan on 12/11/2016.
 */
@Singleton
public class DataManager {
    private final ComicNetworkService comicNetworkService;
    private final DatabaseHelper databaseHelper;

    @Inject
    public DataManager(ComicNetworkService comicNetworkService, DatabaseHelper databaseHelper) {
        this.comicNetworkService = comicNetworkService;
        this.databaseHelper = databaseHelper;
    }

    @RxLogObservable
    public Observable<List<Comic>> syncComic(Integer page, ComicFilter filter) {
        if (filter == ComicFilter.ALL)
            return Observable.concat(getComicsFromDatabase(filter),
                    getComics(page, filter))
                    .onErrorReturn(throwable -> null)
                    .filter(comics -> comics != null);
        return getComicsFromDatabase(filter);
    }

    @RxLogObservable
    public Observable<List<Comic>> getComics(Integer page, ComicFilter filter) {
        return comicNetworkService
                .getComic(page)
                .subscribeOn(Schedulers.io())
                .map(listComicResponse -> listComicResponse.getData())
                .doOnNext(result -> databaseHelper.saveComics(result))
                .map(comics -> databaseHelper.getComics(filter));
    }

    public Observable<Comic> getComicDetail(String comicId) {
        return comicNetworkService.getComicDetail(comicId)
                .subscribeOn(Schedulers.io())
                .map(comicComicResponse -> comicComicResponse.getData())
                .doOnNext(result -> databaseHelper.saveChapter(result))
                .map(comic -> databaseHelper.getComicDetail(comicId));
    }

    @RxLogObservable
    public Observable<List<Comic>> getComicsFromDatabase(ComicFilter filter) {
        return Observable.just(databaseHelper.getComics(filter));
    }

    public Observable<Comic> getComicDetailFromDatabase(String comicId) {
        return Observable.just(databaseHelper.getComicDetail(comicId));
    }

    @RxLogObservable
    public Observable<Comic> syncComic(String comicId) {
        return Observable.concat(getComicDetailFromDatabase(comicId),
                getComicDetail(comicId).onErrorReturn(throwable -> null))
                .filter(comic -> comic != null);
    }

    public void setFavorite(Comic comic) {
        databaseHelper.setFavorite(comic);

    }
}
