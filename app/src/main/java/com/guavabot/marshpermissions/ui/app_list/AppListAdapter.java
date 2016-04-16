package com.guavabot.marshpermissions.ui.app_list;

import android.Manifest;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guavabot.marshpermissions.R;
import com.guavabot.marshpermissions.injection.ComponentScope;
import com.guavabot.marshpermissions.ui.AppIconRequestHandler;
import com.guavabot.marshpermissions.ui.ListAdapter;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Adapter for a list of apps with a button.
 */
@ComponentScope
class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.Holder> implements ListAdapter<AppViewModel> {

    private final AppListPresenter mAppListPresenter;

    private List<AppViewModel> mApps = Collections.emptyList();

    @Inject
    AppListAdapter(AppListPresenter appListPresenter) {
        mAppListPresenter = appListPresenter;
        setHasStableIds(true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AppListItemBinding binding = AppListItemBinding.inflate(inflater, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        AppViewModel app = getItem(position);
        holder.bind(app);
    }

    @Override
    public void setItems(List<AppViewModel> apps) {
        if (apps != null) {
            mApps = apps;
            notifyDataSetChanged();
        }
    }

    AppViewModel getItem(int position) {
        return mApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getPackage().hashCode();
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private final AppListItemBinding mBinding;

        Holder(AppListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setHandler(this);
        }

        void bind(AppViewModel app) {
            mBinding.setApp(app);
            mBinding.executePendingBindings();
        }

        public void onItemClick(@SuppressWarnings("UnusedParameters") View view) {
            mAppListPresenter.onItemClicked(mBinding.getApp());
        }

        public void onButtonClick(@SuppressWarnings("UnusedParameters") View view) {
            mAppListPresenter.onItemButtonClicked(mBinding.getApp());
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
    public static void setPermissions(TextView textView, Set<String> permissions) {
        if (permissions.isEmpty()) {
            textView.setText(R.string.no_permissions);
        } else {
            printPermissions(textView, permissions);
        }
    }

    private static void printPermissions(TextView textView, Set<String> permissions) {
        Context context = textView.getContext();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String permission : permissions) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(getPermissionName(context, permission));
            i++;
        }
        textView.setText(builder);
    }

    private static CharSequence getPermissionName(Context context, String permission) {
        int textId;
        switch (permission) {
            case Manifest.permission_group.CONTACTS:
                textId = R.string.perm_contacts;
                break;
            case Manifest.permission_group.CALENDAR:
                textId = R.string.perm_calendar;
                break;
            case Manifest.permission_group.SMS:
                textId = R.string.perm_sms;
                break;
            case Manifest.permission_group.STORAGE:
                textId = R.string.perm_storage;
                break;
            case Manifest.permission_group.LOCATION:
                textId = R.string.perm_location;
                break;
            case Manifest.permission_group.PHONE:
                textId = R.string.perm_phone;
                break;
            case Manifest.permission_group.MICROPHONE:
                textId = R.string.perm_microphone;
                break;
            case Manifest.permission_group.CAMERA:
                textId = R.string.perm_camera;
                break;
            case Manifest.permission_group.SENSORS:
                textId = R.string.perm_sensors;
                break;
            default:
                throw new AssertionError(permission + " not expected.");
        }
        return context.getText(textId);
    }
}
