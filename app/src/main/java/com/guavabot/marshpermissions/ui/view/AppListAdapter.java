package com.guavabot.marshpermissions.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guavabot.marshpermissions.R;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.injection.ActivityScope;
import com.guavabot.marshpermissions.ui.presenter.AppListPresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>Created by Ivan on 1/2/16.
 */
@ActivityScope
class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.Holder> {

    private final AppListPresenter mAppListPresenter;

    private List<App> mApps = Collections.emptyList();
    private boolean mHideItemButtons = false;

    @Inject
    AppListAdapter(AppListPresenter appListPresenter) {
        mAppListPresenter = appListPresenter;
        setHasStableIds(true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new Holder(view);
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

    void setHideItemButtons(boolean hideItemButtons) {
        if (hideItemButtons != mHideItemButtons) {
            mHideItemButtons = hideItemButtons;
            notifyItemRangeChanged(0, getItemCount());
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

        @Bind(R.id.app_text)
        TextView mText1;
        @Bind(R.id.app_button)
        TextView mHideBtn;
        private App mApp;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(App app) {
            mApp = app;
            mText1.setText(app.getPackage());
            mHideBtn.setVisibility(mHideItemButtons ? View.GONE : View.VISIBLE);
        }

        @OnClick(R.id.app_frame)
        void onItemClick() {
            mAppListPresenter.onItemClicked(mApp);
        }

        @OnClick(R.id.app_button)
        void onButtonClick() {
            mAppListPresenter.onItemButtonClicked(mApp);
        }
    }
}
