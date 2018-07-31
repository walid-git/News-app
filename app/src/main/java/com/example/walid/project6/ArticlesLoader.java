package com.example.walid.project6;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;
import java.util.Set;

public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {

    private String url;
    private static final String TAG = "walidTag";
    private Context context;

    public ArticlesLoader(Context context, String url) {
        super(context);
        this.url = url;
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading: ");
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        Log.d(TAG, "loadInBackground: ");
        Uri uri = Uri.parse(url);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> s = preferences.getStringSet(context.getString(R.string.categories_key), null);
        String tags;
        if (s != null && !s.isEmpty()) {
            tags = "";
            for (String str : s) {
                tags += str + "|";
            }
            tags = tags.substring(0, tags.length() - 1);
        } else {
            tags = "technology/technology";
        }
        Uri.Builder builder = uri.buildUpon()
                .appendQueryParameter("page-size", preferences.getString(context.getString(R.string.num_articles_key), context.getString(R.string.num_articles_default)))
                .appendQueryParameter("show-fields", "headline,trailText,thumbnail,byline")
                .appendQueryParameter("api-key", BuildConfig.API_KEY)
                .appendQueryParameter("tag", tags)
                .appendQueryParameter("order-by", preferences.getString(context.getString(R.string.order_by_key), context.getString(R.string.order_by_default)));
        Log.d(TAG, "loadInBackground: " + builder.toString());
        return NetworkUtils.getDataFromUrl(builder.toString());
    }
}
