package com.quanlt.vietcomicmvp.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Le Thuong Quan on 10/11/2016.
 */
@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application getApplication() {
        return application;
    }


    @Provides
    Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(Context context){
        Realm.init(context);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        builder.name("comic.realm");
        return builder.build();
    }

    @Singleton
    @Provides
    Realm provideRealm(Context context) {
        Realm.init(context);
        return Realm.getDefaultInstance();
    }
}
