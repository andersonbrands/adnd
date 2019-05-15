package com.adnd.iomoney.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.R;
import com.adnd.iomoney.databinding.AccountListItemSimpleBinding;
import com.adnd.iomoney.databinding.AccountListItemSimpleBinding;
import com.adnd.iomoney.models.Account;

import java.util.List;

public class SimpleAccountAdapter extends BaseAdapter<Account, SimpleAccountAdapter.AccountViewHolder> {

    // TODO Reuse code from AccountAdapter
    final private ListItemClickListener<Account> mListItemClickListener;

    public SimpleAccountAdapter(List<Account> objects, ListItemClickListener<Account> mListItemClickListener) {
        super(objects);
        this.mListItemClickListener = mListItemClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AccountListItemSimpleBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.account_list_item_simple, parent, false);
        return new AccountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder accountViewHolder, int position) {
        Account account = getObjects().get(position);
        accountViewHolder.binding.setAccount(account);
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AccountListItemSimpleBinding binding;

        AccountViewHolder(@NonNull AccountListItemSimpleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(getObjects().get(clickedPosition), clickedPosition);
        }
    }
}
