package com.example.walid.project6;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {

    private static final String TAG = "walidTag";

    public ArticlesLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading: ");
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        Log.d(TAG, "loadInBackground: ");
        return NetworkUtils.getDataFromUrl(MainActivity.DATA_URL);
    }
}
