package com.corelambda.tourist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.corelambda.tourist.datamodel.WikipediaPage;

import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;

public class PlacesViewModel extends ViewModel {
//    private static final List<String> placesList = Arrays.asList("Statue", "Scenic Overlook", "Art Museum",
//            "Market", "Museum of Natural History", "Temple", "Amusement Park", "City Hall", "Big Bridge");
    private LiveData<List<WikipediaPage>> placesListData;
    private PlacesRepository placesRepository;
    public PlacesViewModel(PlacesRepository repo) {

//        final MutableLiveData<List<String>> data = new MutableLiveData<>();
//        data.setValue(placesList);
        placesRepository=repo;
        placesListData= placesRepository.getTouristSites();
    }

    public LiveData<List<WikipediaPage>> getPlacesList() {
        return placesListData;
    }


    public static class PlacesViewModelFactory implements ViewModelProvider.Factory{
        private PlacesRepository placesRepository;
        public PlacesViewModelFactory(PlacesRepository placesRepository) {
            this.placesRepository=placesRepository;
        }

        @NonNull
        @Override
        public PlacesViewModel create(@NonNull Class modelClass) {
            return new PlacesViewModel(placesRepository);
        }
    }
}
