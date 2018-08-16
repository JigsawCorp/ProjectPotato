package com.jigsawcorp.android.jigsaw;

import android.app.Application;
import android.content.Context;

public class ProjectPotato extends Application {
    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        ProjectPotato.sContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return ProjectPotato.sContext;
    }
}
