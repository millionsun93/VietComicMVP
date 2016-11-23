package com.quanlt.vietcomicmvp.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.quanlt.vietcomicmvp.R;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.ComicFilter;
import com.quanlt.vietcomicmvp.util.OnFabClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFabClickListener {

    private static final String SELECTED_NAVIGATION_ITEM = "SELECTED_NAVIGATION_ITEM";
    private static final String SELECTED_COMIC = "SELECTED_COMIC";
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @Nullable
    @BindView(R.id.comic_detail_container)
    ScrollView mComicDetailContainer;
    @Nullable
    @BindView(R.id.fab)
    FloatingActionButton mLikeFloatingButton;

    private int mSelectedNavigationItem;
    private boolean isTwoPanel;
    private Comic mSelectedComic;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        setupNavigationDrawer();
        setupNavigationView();
        mainController = MainController.createMainView(this, mSelectedComic);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_NAVIGATION_ITEM, mSelectedNavigationItem);
        outState.putParcelable(SELECTED_COMIC, mSelectedComic);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mSelectedNavigationItem = savedInstanceState.getInt(SELECTED_NAVIGATION_ITEM);
            mSelectedComic = savedInstanceState.getParcelable(SELECTED_COMIC);
            if (mSelectedNavigationItem == 1) {
                mainController.setFilter(ComicFilter.FAVORITE);
            }
            Menu menu = mNavigationView.getMenu();
            menu.getItem(mSelectedNavigationItem).setChecked(true);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void setupNavigationDrawer() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(view -> mDrawerLayout.openDrawer(GravityCompat.START));
    }

    private void setupNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (mSelectedNavigationItem != 0) {
                    mainController.setFilter(ComicFilter.ALL);
                    mSelectedNavigationItem = 0;
                    updateTitle();
                    mainController.reload();
                }
                break;
            case R.id.nav_favorite:
                if (mSelectedNavigationItem != 1) {
                    mainController.setFilter(ComicFilter.FAVORITE);
                    mSelectedNavigationItem = 1;
                    updateTitle();
                    mainController.reload();
                }
                break;
            case R.id.nav_about:
                new LibsBuilder()
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withActivityTitle(getString(R.string.about))
                        .start(this);
                break;
            default:
                return false;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void updateTitle() {
        if (mSelectedNavigationItem == 0) {
            setTitle(getString(R.string.home));
        } else {
            setTitle(getString(R.string.favorite));
        }
    }


    @Optional
    @OnClick(R.id.fab)
    public void onClick(View v){
        mainController.toggleFavorite();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void updateFab(boolean isFavorite) {
        Log.d(TAG,"updatefab");
        if (isFavorite) {
            mLikeFloatingButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            mLikeFloatingButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);

        }
    }
}
