package com.guavabot.marshpermissions.ui.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.guavabot.marshpermissions.R;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.injection.AppListComponent;
import com.guavabot.marshpermissions.injection.AppListModule;
import com.guavabot.marshpermissions.injection.DaggerAppListComponent;
import com.guavabot.marshpermissions.ui.presenter.AppListPresenter;
import com.guavabot.marshpermissions.ui.presenter.AppListView;
import com.guavabot.marshpermissions.ui.presenter.Presenter;
import com.guavabot.marshpermissions.ui.widget.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

/**
 * Displays the screen with the list of apps that target Marshmallow.
 */
public class AppListActivity extends BaseActivity implements AppListView {

    @Inject
    AppListPresenter mAppListPresenter;
    @Inject
    AppListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inject();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void inject() {
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

}