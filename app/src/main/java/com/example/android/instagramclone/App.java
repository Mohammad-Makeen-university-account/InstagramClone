package com.example.android.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("xAUNPQcJyalRd9fnBNurCH5dcqPQeTAIMgQr5u1X")
                // if defined
                .clientKey("WThHx8xlTKiSDmzcxxG20xCScV5gFy0ksg7JZLkV")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
