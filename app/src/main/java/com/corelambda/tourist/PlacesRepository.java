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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesRepository {
    private Activity mainSceen;
    private WikipediaService wikipediaService;
    private Retrofit retrofit;
    public PlacesRepository(WikipediaService wikipediaService){
        this.wikipediaService=wikipediaService;
    }
    public LiveData<List<WikipediaPage>> getTouristSites() {

        final MutableLiveData<List<WikipediaPage>> liveData = new MutableLiveData<>();
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://en.wikipedia.org")
//                .build();
        //wikipediaService = retrofit.create(WikipediaService.class);
        wikipediaService.getPlaces().enqueue(new Callback<WikipediaResponse>() {
            @Override
            public void onResponse(Call<WikipediaResponse> call, Response<WikipediaResponse> response) {
                WikipediaResponse wikipediaResponse = response.body();
                QueryResponse queryResponse = wikipediaResponse.getQuery();
                Map<Integer, WikipediaPage> pagesMap = queryResponse.getPages();
                List<WikipediaPage> pages = new ArrayList<>(pagesMap.values());
                Collections.sort(pages, new Comparator<WikipediaPage>() {
                    @Override
                    public int compare(WikipediaPage lhs, WikipediaPage rhs) {
                        return lhs.getIndex()-rhs.getIndex();
                    }
                });
                liveData.postValue(pages);
            }

            @Override
            public void onFailure(Call<WikipediaResponse> call, Throwable t) {
                Log.e("PlacesRepository","Wikipedia request failed",t);
            }
        });
//        AsyncTask<Void, Void, List<WikipediaPage>> task = new AsyncTask<Void, Void, List<WikipediaPage>>() {
//            @Override
//            protected List<WikipediaPage> doInBackground(Void[] objects) {
//                List<WikipediaPage> places = null;
//                try {
//                    InputStream response = doRequest();
//                    places = handleResponse(response);
//                    liveData.postValue(places);
//
//                } catch (Exception ex) {
//                    Log.e("PlacesRepository", "FAILED!!!" + ex);
//                }
//                return places;
//            }
//            @Override
//            protected void onPostExecute(List<WikipediaPage> places) {
//                liveData.setValue(places);
//            }
//        };
//        task.execute();
        return liveData;
    }

    private InputStream doRequest() throws IOException {

        String urlString = "https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages&generator=geosearch&pithumbsize=250&ggscoord=10.7712404%7C106.6978887&ggsradius=10000";
        //StringBuilder reponse = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream stream = urlConnection.getInputStream();
        return stream;
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                reponse.append(line);
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//        return reponse.toString();
    }

    private List<WikipediaPage> handleResponse(InputStream response) {
        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        WikipediaResponse wikipediaResponse = gson.fromJson(reader, WikipediaResponse.class);
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
