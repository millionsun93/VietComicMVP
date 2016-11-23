package com.quanlt.vietcomicmvp.data.local;

import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.ComicFilter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;

/**
 * Created by Le Thuong Quan on 12/11/2016.
 */
@Singleton
public class DatabaseHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private final Realm realm;

    @Inject
    public DatabaseHelper(Realm realm) {
        this.realm = realm;
    }

    public List<Comic> getComics() {

        List<Comic> comics = realm.where(Comic.class).findAll();
        comics = realm.copyFromRealm(comics);
        return comics;
    }

    public List<Comic> getComics(ComicFilter filter) {
        Realm realm = Realm.getDefaultInstance();
        List<Comic> comics;
        if (filter == ComicFilter.ALL) {
            comics = realm.where(Comic.class).findAll();
            comics = realm.copyFromRealm(comics);
        } else {
            comics = realm.where(Comic.class).equalTo("isFavorite", true).findAll();
            comics = realm.copyFromRealm(comics);
        }
        return comics;
    }

    public void saveComics(List<Comic> comics) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (Comic comic :
                comics) {
            Comic temp = realm.where(Comic.class).equalTo("id", comic.getId()).findFirst();
            if (temp != null) {
                comic.setFavorite(temp.isFavorite());
            }
            realm.copyToRealmOrUpdate(comic);
        }
        realm.commitTransaction();
    }


    public void saveChapter(Comic comic) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Comic realmComic = realm1.where(Comic.class)
                    .equalTo("id", comic.getId())
                    .findFirst();
            realmComic.setChapters(comic.getChapters());
        });

    }

    public Comic getComicDetail(String comicId) {
        Realm realm = Realm.getDefaultInstance();
        Comic comic = realm.where(Comic.class).equalTo("id", comicId).findFirst();
        comic = realm.copyFromRealm(comic);
        return comic;
    }

    public void setFavorite(Comic comic) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Comic realmComic = realm.where(Comic.class)
                .equalTo("id", comic.getId())
                .findFirst();
        realmComic.setFavorite(!comic.isFavorite());
        realm.commitTransaction();
    }
}
