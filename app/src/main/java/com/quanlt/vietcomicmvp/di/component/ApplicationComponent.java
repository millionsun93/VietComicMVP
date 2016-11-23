package com.quanlt.vietcomicmvp.di.component;

import android.app.Application;

import com.quanlt.vietcomicmvp.data.remote.ComicNetworkService;
import com.quanlt.vietcomicmvp.di.module.ApplicationModule;
import com.quanlt.vietcomicmvp.di.module.NetModule;
import com.quanlt.vietcomicmvp.ui.detail.ComicDetailActivity;
import com.quanlt.vietcomicmvp.ui.detail.ComicDetailFragment;
import com.quanlt.vietcomicmvp.ui.main.MainActivity;
import com.quanlt.vietcomicmvp.ui.main.MainController;
import com.quanlt.vietcomicmvp.ui.main.MainFragment;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by Le Thuong Quan on 12/11/2016.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(ComicDetailActivity comicDetailActivity);
    void inject(MainFragment mainFragment);
    void inject(ComicDetailFragment comicDetailFragment);
    void inject(MainController mainController);

    Application application();

    ComicNetworkService comicNetworkService();

    Realm realm();

}
