package com.adnd.iomoney.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adnd.iomoney.R;
import com.adnd.iomoney.databinding.TransactionListItemBinding;
import com.adnd.iomoney.models.Transaction;

import java.util.List;

public class TransactionAdapter extends BaseAdapter<Transaction, TransactionAdapter.TransactionViewHolder> {

    final private ListItemClickListener<Transaction> mListItemClickListener;

    public TransactionAdapter(List<Transaction> objects, ListItemClickListener<Transaction> mListItemClickListener) {
        super(objects);
        this.mListItemClickListener = mListItemClickListener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TransactionListItemBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.transaction_list_item, parent, false);
        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder accountViewHolder, int position) {
        Transaction transaction = getObjects().get(position);
        accountViewHolder.binding.setTransaction(transaction);
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TransactionListItemBinding binding;

        TransactionViewHolder(@NonNull TransactionListItemBinding binding) {
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
