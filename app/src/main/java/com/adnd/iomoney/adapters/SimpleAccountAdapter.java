package com.adnd.iomoney.adapters;

import android.support.annotation.NonNull;

import com.adnd.iomoney.R;
import com.adnd.iomoney.databinding.AccountListItemSimpleBinding;
import com.adnd.iomoney.models.Account;

import java.util.List;

public class SimpleAccountAdapter extends BaseAccountAdapter<AccountListItemSimpleBinding> {

    public SimpleAccountAdapter(List<Account> objects, ListItemClickListener<Account> mListItemClickListener) {
        super(objects, mListItemClickListener, R.layout.account_list_item_simple);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAccountAdapter.AccountViewHolder accountViewHolder, int position) {
        Account account = getObjects().get(position);
        ((AccountListItemSimpleBinding)accountViewHolder.binding).setAccount(account);
    }

}
