package com.corelambda.tourist;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.provider.CallLog;

import com.corelambda.tourist.datamodel.QueryResponse;
import com.corelambda.tourist.datamodel.WikipediaImage;
import com.corelambda.tourist.datamodel.WikipediaPage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.Calls;

public class PlacesViewModelTest {
    @Rule
    public TestRule rule=new InstantTaskExecutorRule();
    @Test
    public void testGetTouristSite(){
//        WikipediaService wikipediaService=new WikipediaService() {
//            @Override
//            public Call<WikipediaResponse> getPlaces() {
//                WikipediaResponse wikipediaResponse=new WikipediaResponse();
//                wikipediaResponse.setQuery(new QueryResponse());
//                HashMap<Integer,WikipediaPage> pages=new HashMap<Integer, WikipediaPage>();
//                wikipediaResponse.getQuery().setPages(pages);
//
//                WikipediaPage wikipediaPage=new WikipediaPage();
//                pages.put(1234, wikipediaPage);
//                wikipediaPage.setTitle("Statue");
//                wikipediaPage.setPageid(1234);
//                return Calls.response(wikipediaResponse);
//
//            }
//        };

       //PlacesViewModel viewModel =new PlacesViewModel(retrofit);
        WikipediaService MokWikipediaService =new MockWikipediaService();
        PlacesRepository placesRepository=new PlacesRepository(MokWikipediaService);
        PlacesViewModel.PlacesViewModelFactory factory= new PlacesViewModel.PlacesViewModelFactory(placesRepository);
        PlacesViewModel viewModel =factory.create(PlacesViewModel.class);
        LiveData<List<WikipediaPage>> touristSite=viewModel.getPlacesList();
        Assert.assertEquals("Statue",touristSite.getValue().get(0).getTitle());
    }
}
