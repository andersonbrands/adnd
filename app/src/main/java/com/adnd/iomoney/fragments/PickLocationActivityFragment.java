package com.adnd.iomoney.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.PickLocationActivity;
import com.adnd.iomoney.R;
import com.adnd.iomoney.databinding.FragmentPickLocationBinding;
import com.adnd.iomoney.view_models.PickLocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


public class PickLocationActivityFragment extends Fragment implements
        OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap googleMap;

    private FragmentPickLocationBinding binding;


    public PickLocationActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPickLocationBinding.inflate(inflater);

        binding.mapView.getMapAsync(this);
        binding.mapView.onCreate(savedInstanceState);

        binding.btSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocation();
            }
        });

        return binding.getRoot();
    }

    private void selectLocation() {
        if (googleMap != null) {

            String locationLabel = binding.etLocationLabel.getText().toString();

            if (TextUtils.isEmpty(locationLabel)) {
                binding.etLocationLabel.setError(getString(R.string.msg_provide_location_label));
                return;
            }

            LatLng latLng = googleMap.getCameraPosition().target;

            Intent intent = new Intent();
            intent.putExtra(PickLocationActivity.EXTRA_LOCATION_LABEL, locationLabel);
            intent.putExtra(PickLocationActivity.EXTRA_LATITUDE, latLng.latitude);
            intent.putExtra(PickLocationActivity.EXTRA_LONGITUDE, latLng.longitude);

            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        binding.mapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        binding.mapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        binding.mapView.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        binding.mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        binding.mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        onMapReady();
        PickLocationViewModel model = ViewModelProviders.of(getActivity()).get(PickLocationViewModel.class);

        model.getLocationLabelLiveData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.etLocationLabel.setText(s);
            }
        });

        model.getLatLngLiveData().observe(getActivity(), new Observer<LatLng>() {
            @Override
            public void onChanged(@Nullable LatLng latLng) {
                onLatLngReceived(latLng);
            }
        });
    }

    private void onLatLngReceived(LatLng latLng) {
        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
        }
    }

    private void onMapReady() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else if (googleMap != null) {
            // Location access has been granted
            googleMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation == null)
                    lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (lastKnownLocation != null) {
                    LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                }
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            onMapReady();
        } else {
            new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.location_permission_denied)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
        }
    }
}
