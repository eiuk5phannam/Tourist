package com.corelambda.tourist;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

public class PlacesViewModelTest {
    @Rule
    public TestRule rule=new InstantTaskExecutorRule();
    @Test
    public void testGetTouristSite(){
        PlacesViewModel viewModel =new PlacesViewModel();
        LiveData<List<String>> touristSite=viewModel.getPlacesList();
        Assert.assertEquals("Statue",touristSite.getValue().get(0));
    }
}
