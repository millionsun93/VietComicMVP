package com.quanlt.vietcomicmvp;

import android.app.Application;

import com.quanlt.vietcomicmvp.di.component.ApplicationComponent;
import com.quanlt.vietcomicmvp.di.component.DaggerApplicationComponent;
import com.quanlt.vietcomicmvp.di.module.ApplicationModule;
import com.quanlt.vietcomicmvp.di.module.NetModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Le Thuong Quan on 10/11/2016.
 */
public class ComicApplication extends Application {
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule())
                .build();
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
