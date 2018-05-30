package com.corelambda.tourist;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;


import com.corelambda.tourist.datamodel.QueryResponse;
import com.corelambda.tourist.datamodel.WikipediaPage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlacesRepository {
    private Activity mainSceen;

    public LiveData<List<WikipediaPage>> getTouristSites() {

        final MutableLiveData<List<WikipediaPage>> liveData = new MutableLiveData<>();


        AsyncTask<Void, Void, List<WikipediaPage>> task = new AsyncTask<Void, Void, List<WikipediaPage>>() {
            @Override
            protected List<WikipediaPage> doInBackground(Void[] objects) {
                List<WikipediaPage> places = null;
                try {
                    String response = doRequest();
                    places = handleResponse(response);
                    liveData.postValue(places);

                } catch (Exception ex) {
                    Log.e("PlacesRepository", "FAILED!!!" + ex);
                }
                return places;
            }

            @Override
            protected void onPostExecute(List<WikipediaPage> places) {
                liveData.setValue(places);
            }
        };
        task.execute();
        return liveData;
    }

    private String doRequest() throws IOException {
        String urlString = "https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages&generator=geosearch&pithumbsize=250&ggscoord=10.7712404%7C106.6978887&ggsradius=10000";
        StringBuilder reponse = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.getInputStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                reponse.append(line);
            }
        } finally {
            urlConnection.disconnect();
        }
        return reponse.toString();
    }

        private List<WikipediaPage> handleResponse(String response) {
        Gson gson = new Gson();
        WikipediaResponse wikipediaResponse = gson.fromJson(response, WikipediaResponse.class);
        QueryResponse queryResponse = wikipediaResponse.getQuery();
        Map<Integer, WikipediaPage> pagesMap = queryResponse.getPages();
        List<WikipediaPage> pages = new ArrayList<>(pagesMap.values());
        return pages;
    }
//    private  List<String>handleResponse(String response) throws JSONException {
//        List<String> placesName = new ArrayList<>();
//        JSONObject responseObject = new JSONObject(response);
//        JSONObject queuryObject = responseObject.getJSONObject("query");
//        JSONObject pageObject = queuryObject.getJSONObject("pages");
//        Iterator<String> Keys = pageObject.keys();
//        while (Keys.hasNext()) {
//            JSONObject page = pageObject.getJSONObject(Keys.next());
//            String title = page.getString("title");
//            placesName.add(title);
//        }
//        return placesName;
//    }

}
