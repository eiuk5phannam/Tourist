package com.corelambda.tourist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.corelambda.tourist.datamodel.WikipediaPage;

import java.util.Arrays;
import java.util.List;

public class PlacesViewModel extends ViewModel {
//    private static final List<String> placesList = Arrays.asList("Statue", "Scenic Overlook", "Art Museum",
//            "Market", "Museum of Natural History", "Temple", "Amusement Park", "City Hall", "Big Bridge");
    private LiveData<List<WikipediaPage>> placesListData;
    private PlacesRepository placesRepository;
    public PlacesViewModel() {
        placesRepository =new PlacesRepository();

//        final MutableLiveData<List<String>> data = new MutableLiveData<>();
//        data.setValue(placesList);
        placesRepository=new PlacesRepository();
        placesListData= placesRepository.getTouristSites();
    }

    public LiveData<List<WikipediaPage>> getPlacesList() {
        return placesListData;
    }

}
