package com.example.walid.project6;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    TextView emptyTv;
    RecyclerView recyclerView;
    ArticlesAdapter adapter;
    ImageView noInternetIV;
    SwipeRefreshLayout refreshLayout;
    List<Article> articles;
    private static final String TAG = "walidTag";
    public static final String DATA_URL = "https://content.guardianapis.com/search?page-size=30&q=technology&show-fields=headline,trailText,thumbnail,byline&from-date=2018-06-01&order-by=newest&api-key="+BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyTv = findViewById(R.id.emptyTV);
        refreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recycler);
        noInternetIV = findViewById(R.id.noConnection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articles = new ArrayList<Article>();
        adapter = new ArticlesAdapter(articles);
        adapter.setEmptyView(emptyTv);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClicked(Article article) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getArticleUrl()));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        if (checkConnection()) {
            noInternetIV.setVisibility(View.GONE);
            final Loader l = getSupportLoaderManager().initLoader(0, null, this);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Log.d(TAG, "onRefresh: ");
                    l.forceLoad();
                }
            });
        } else {
            noInternetIV.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<List<Article>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: ");
        return new ArticlesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<Article>> loader, List<Article> data) {
        Log.d(TAG, "onLoadFinished: ");
        emptyTv.setText("Couldn't load news");
        if (data != null) {
            articles.clear();
            articles = data;
            adapter.setArticles(articles);
            adapter.notifyDataSetChanged();
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<Article>> loader) {

    }
}
