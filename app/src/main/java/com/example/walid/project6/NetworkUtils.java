package com.example.walid.project6;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class NetworkUtils {

    private static final String TAG = "walidTag";

    public static List<Article> getDataFromUrl(String urlStr) {

        List<Article> articles = null;
        String jsonString = makeHttpRequest(urlStr);
        if(jsonString!=null)
            articles = parseJson(jsonString);
        return articles;
    }

    private static List<Article> parseJson(String jsonString) {
        List<Article> articles = new ArrayList<Article>();
        try {
            JSONObject object = new JSONObject(jsonString).getJSONObject("response");
            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i<results.length(); i++) {
                JSONObject o = results.getJSONObject(i);
                JSONObject fields = o.getJSONObject("fields");
                articles.add(new Article(o.optString("webTitle", "Article title"),
                        o.optString("sectionName"),
                        fields.optString("trailText"),
                        fields.optString("thumbnail"),
                        fields.optString("byline", "Unknown author"),
                        o.optString("webPublicationDate").replace('T', ' ').replace('Z', ' '),
                        o.optString("webUrl", "https://www.theguardian.com/")));
                Log.d(TAG, "parseJson: "+i+" => "+o.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }

    private static String makeHttpRequest(String urlStr) {
        String response = null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line="";
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                response = builder.toString();
            } else {
                Log.e(TAG, "makeHttpRequest: response code = "+connection.getResponseCode() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

}
