package com.guavabot.marshpermissions.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.guavabot.marshpermissions.R;
import com.squareup.picasso.Picasso;

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
        Picasso.with(context)
                .load(AppIconRequestHandler.getUri(packageName))
                .into(imageView);
    }

    @BindingAdapter("permissions")
    public static void setPermissions(TextView textView, List<String> permissions) {
        String text = textView.getContext().getString(R.string.app_num_permissions, permissions.size());
        textView.setText(text);
    }
}
