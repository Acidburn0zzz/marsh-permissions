package com.guavabot.marshpermissions.ui.app_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guavabot.marshpermissions.injection.ActivityScope;
import com.guavabot.marshpermissions.ui.ListAdapter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Adapter for a list of apps with a button.
 */
@ActivityScope
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
}
