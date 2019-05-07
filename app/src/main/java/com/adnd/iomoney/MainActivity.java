package com.adnd.iomoney;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adnd.iomoney.adapters.ListItemClickListener;
import com.adnd.iomoney.models.Account;

public class MainActivity extends AppCompatActivity implements ListItemClickListener<Account> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListItemClick(Account clickedItem, int position) {
        // TODO handle click
    }
}
