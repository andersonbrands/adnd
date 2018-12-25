package com.adnd.popularmovies.adapters;

import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {

    private List<T> objects;
    private final ObservableBoolean listIsEmpty = new ObservableBoolean();

    BaseAdapter(List<T> objects) {
        if (objects == null) {
            this.objects = new ArrayList<>();
        } else {
            this.objects = objects;
        }
        listIsEmpty.set(getItemCount() == 0);
    }

    public ObservableBoolean getListIsEmpty() {
        return listIsEmpty;
    }

    List<T> getObjects() {
        return objects;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
