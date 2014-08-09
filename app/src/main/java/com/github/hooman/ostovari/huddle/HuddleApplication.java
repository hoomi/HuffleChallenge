package com.github.hooman.ostovari.huddle;

import android.app.Application;

import com.google.inject.Stage;

import roboguice.RoboGuice;

/**
 * Created by hostova1 on 08/08/2014.
 */
public class HuddleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stage stage = BuildConfig.DEBUG ? Stage.DEVELOPMENT : Stage.PRODUCTION;
        RoboGuice.setBaseApplicationInjector(this, stage,
                RoboGuice.newDefaultRoboModule(this), new HuddleModule());
    }

}
