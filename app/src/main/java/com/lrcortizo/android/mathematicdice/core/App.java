package com.lrcortizo.android.mathematicdice.core;

import android.app.Application;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@EqualsAndHashCode(callSuper = true)
public class App extends Application {

    @NonFinal
    SQLite db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db = new SQLite(this.getApplicationContext());
    }
}
