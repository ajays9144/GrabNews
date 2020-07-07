package com.grab.news.repository;

import android.content.Context;

public class BaseRepository {
    /**
     * The Application context.
     */
    Context applicationContext;

    /**
     * Instantiates a new Base repository.
     *
     * @param context the context
     */
    BaseRepository(Context context) {
        this.applicationContext = context;
    }

    /**
     * Gets application context.
     *
     * @return the application context
     */
    public Context getApplicationContext() {
        return this.applicationContext;
    }
}