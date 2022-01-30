package com.lrcortizo.android.mathematicdice.core;

import android.app.Application;

public class App extends Application {

    private SQLite db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db = new SQLite( this.getApplicationContext() );
    }

    public SQLite getDb() {
        return this.db;
    }
}
