package com.guavabot.marshpermissions.ui.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guavabot.marshpermissions.R;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.injection.AppListComponent;
import com.guavabot.marshpermissions.injection.AppListModule;
import com.guavabot.marshpermissions.injection.DaggerAppListComponent;
import com.guavabot.marshpermissions.ui.presenter.AppListPresenter;
import com.guavabot.marshpermissions.ui.presenter.AppListView;
import com.guavabot.marshpermissions.ui.presenter.Presenter;
import com.guavabot.marshpermissions.ui.widget.DividerItemDecoration;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Displays the screen with the list of apps that target Marshmallow.
 */
public class AppListActivity extends BaseActivity implements AppListView {

    @Inject
    AppListPresenter mAppListPresenter;

    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        mAdapter = new Adapter();
        recyclerView.setAdapter(mAdapter);

        initializeInjector();
    }

    private void initializeInjector() {
        AppListComponent appListComponent = DaggerAppListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .appListModule(new AppListModule(this))
                .build();
        appListComponent.inject(this);
    }

    @Override
    protected Presenter getPresenter() {
        return mAppListPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            startSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSettings() {
        SettingsActivity.start(this);
    }

    public void startAppInfo(String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void setItems(List<App> apps) {
        mAdapter.setItems(apps);
    }

    @Override
    public void setHideItemButtons(boolean hide) {
        mAdapter.setHideItemButtons(hide);
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List<App> mApps = Collections.emptyList();
        private boolean mHideItemButtons = false;

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
        public int getItemCount() {
            return mApps.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            @Bind(R.id.app_text) TextView mText1;
            @Bind(R.id.app_button) TextView mHideBtn;
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

}