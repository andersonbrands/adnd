package com.adnd.iomoney.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.adapters.AccountAdapter;
import com.adnd.iomoney.adapters.ListItemClickListener;
import com.adnd.iomoney.databinding.FragmentAccountsListBinding;
import com.adnd.iomoney.models.Account;
import com.adnd.iomoney.view_models.AccountViewModel;
import com.google.android.gms.ads.AdRequest;

import java.util.List;

public class AccountsListFragment extends Fragment {

    private ListItemClickListener<Account> accountListItemClickListener;
    private FragmentAccountsListBinding binding;

    public AccountsListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            accountListItemClickListener = (ListItemClickListener<Account>) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ListItemClickListener<Account>");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountsListBinding.inflate(inflater);

        if (getActivity() != null) {
            AccountViewModel model = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);

            model.getAccountsLiveData().observe(this, new Observer<List<Account>>() {
                @Override
                public void onChanged(@Nullable List<Account> accounts) {
                    setRecyclerView(accounts);
                }
            });

            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            binding.adView.loadAd(adRequest);
        }

        return binding.getRoot();
    }

    private void setRecyclerView(@Nullable List<Account> accounts) {
        binding.rvAccounts.setLayoutManager(new LinearLayoutManager(getActivity()));
        AccountAdapter accountAdapter = new AccountAdapter(accounts, accountListItemClickListener);
        binding.rvAccounts.setAdapter(accountAdapter);

        float totalBalance = 0.0f;
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        binding.setTotalBalance(totalBalance);
    }
}
