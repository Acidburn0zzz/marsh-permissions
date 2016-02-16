package com.guavabot.marshpermissions.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

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

    @BindingAdapter("icon")
    public static void setIcon(ImageView imageView, String packageName) {
        Context context = imageView.getContext();
        PackageManager packageManager = context.getPackageManager();
        try {
            Drawable drawable = packageManager.getApplicationIcon(packageName);
            imageView.setImageDrawable(drawable);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("icon BindingAdapter", "app not found", e);
        }
    }
}
