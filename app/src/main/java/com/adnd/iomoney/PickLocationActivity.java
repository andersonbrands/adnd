package com.adnd.iomoney;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.adnd.iomoney.view_models.PickLocationViewModel;
import com.google.android.gms.maps.model.LatLng;

public class PickLocationActivity extends AppCompatActivity {

    public static final int PICK_LOCATION_REQUEST_CODE = 1;
    public static final String EXTRA_LOCATION_LABEL = "extra_location_label";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.choose_location);
        }

        Intent intentThatStartedThisActivity = getIntent();

        PickLocationViewModel model =
                ViewModelProviders.of(this).get(PickLocationViewModel.class);

        if (intentThatStartedThisActivity.hasExtra(EXTRA_LOCATION_LABEL)) {
            model.setLocationLabel(
                    intentThatStartedThisActivity.getStringExtra(EXTRA_LOCATION_LABEL)
            );
        }
        if (intentThatStartedThisActivity.hasExtra(EXTRA_LATITUDE) && intentThatStartedThisActivity.hasExtra(EXTRA_LONGITUDE)) {
            LatLng latLng = new LatLng(
                    intentThatStartedThisActivity.getDoubleExtra(EXTRA_LATITUDE, 0),
                    intentThatStartedThisActivity.getDoubleExtra(EXTRA_LONGITUDE, 0)
            );
            model.setLatLng(latLng);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}
