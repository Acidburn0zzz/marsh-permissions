package com.guavabot.marshpermissions.ui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, List<T> items) {
        //noinspection unchecked
        ListAdapter<T> adapter = (ListAdapter<T>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItems(items);
        }
    }
}
