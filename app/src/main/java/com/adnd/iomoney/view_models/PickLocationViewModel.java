package com.adnd.iomoney.view_models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class PickLocationViewModel extends ViewModel {

    private MutableLiveData<String> locationLabelLiveData = new MutableLiveData<>();
    private MutableLiveData<LatLng> latLngLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getLocationLabelLiveData() {
        return locationLabelLiveData;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabelLiveData.setValue(locationLabel);
    }

    public MutableLiveData<LatLng> getLatLngLiveData() {
        return latLngLiveData;
    }

    public void setLatLng(LatLng latLng) {
        this.latLngLiveData.setValue(latLng);
    }

}
