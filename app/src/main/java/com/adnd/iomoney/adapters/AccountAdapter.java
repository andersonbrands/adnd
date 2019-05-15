package com.adnd.iomoney.adapters;

import android.support.annotation.NonNull;

import com.adnd.iomoney.R;
import com.adnd.iomoney.databinding.AccountListItemBinding;
import com.adnd.iomoney.models.Account;

import java.util.List;

public class AccountAdapter extends BaseAccountAdapter<AccountListItemBinding> {

    public AccountAdapter(List<Account> objects, ListItemClickListener<Account> mListItemClickListener) {
        super(objects, mListItemClickListener, R.layout.account_list_item);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAccountAdapter.AccountViewHolder accountViewHolder, int position) {
        Account account = getObjects().get(position);
        ((AccountListItemBinding) accountViewHolder.binding).setAccount(account);
    }

}
