package com.guavabot.marshpermissions.ui.app_list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.guavabot.marshpermissions.R;
import com.guavabot.marshpermissions.ui.BaseActivity;
import com.guavabot.marshpermissions.ui.Presenter;
import com.guavabot.marshpermissions.ui.settings.SettingsActivity;
import com.guavabot.marshpermissions.ui.widget.DividerItemDecoration;
import com.jakewharton.rxbinding.widget.RxSearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Displays the screen with the list of apps that target Marshmallow.
 */
public class AppListActivity extends BaseActivity implements AppListView {

    @Inject AppListPresenter mAppListPresenter;
    @Inject AppListAdapter mAdapter;

    @Bind(R.id.recycler) RecyclerView mRecyclerView;

    private final PublishSubject<SearchView> mSearchViewSubject = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();

        setContentView(R.layout.app_list);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter);
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
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        initSearchView(searchItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void initSearchView(MenuItem searchItem) {
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search_hint));
        mSearchViewSubject.onNext(searchView);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchView.setQuery("", true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }
        });
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

    @Override
    public void setApps(List<AppViewModel> apps) {
        mAdapter.setItems(apps);
    }

    @Override
    public void startAppInfo(String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public Observable<String> getPackageFilter() {
        return mSearchViewSubject.asObservable()
                .doOnSubscribe(() -> supportInvalidateOptionsMenu())
                .flatMap(searchView -> RxSearchView.queryTextChanges(searchView))
                .map((text) -> text.toString());
    }
}
