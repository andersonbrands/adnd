package com.adnd.iomoney.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.models.Account;

import java.util.List;

public abstract class BaseAccountAdapter<T extends ViewDataBinding> extends BaseAdapter<Account, BaseAccountAdapter.AccountViewHolder> {

    final private ListItemClickListener<Account> mListItemClickListener;
    private int layoutResId = -1;

    public BaseAccountAdapter(List<Account> objects, ListItemClickListener<Account> mListItemClickListener, int layoutResId) {
        super(objects);
        this.mListItemClickListener = mListItemClickListener;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        T binding =
                DataBindingUtil.inflate(inflater, layoutResId, parent, false);
        return new AccountViewHolder(binding);
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        T binding;

        AccountViewHolder(@NonNull T binding) {
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
