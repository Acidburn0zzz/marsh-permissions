package com.guavabot.marshpermissions.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.injection.ActivityScope;
import com.guavabot.marshpermissions.ui.presenter.AppListPresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Adapter for a list of apps with a button.
 */
@ActivityScope
class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.Holder> {

    private final AppListPresenter mAppListPresenter;

    private List<App> mApps = Collections.emptyList();

    @Inject
    AppListAdapter(AppListPresenter appListPresenter) {
        mAppListPresenter = appListPresenter;
        setHasStableIds(true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding = ListItemBinding.inflate(inflater, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        App app = getItem(position);
        holder.bind(app);
    }

    void setItems(List<App> apps) {
        if (!apps.equals(mApps)) {
            mApps = apps;
            notifyDataSetChanged();
        }
    }

    App getItem(int position) {
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

        private final ListItemBinding mBinding;

        Holder(ListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setHandler(this);
        }

        void bind(App app) {
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
