package com.quanlt.vietcomicmvp.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.quanlt.vietcomicmvp.ComicApplication;
import com.quanlt.vietcomicmvp.R;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.ui.detail.ComicDetailFragment;
import com.quanlt.vietcomicmvp.ui.detail.ComicDetailPresenter;
import com.quanlt.vietcomicmvp.util.ComicFilter;

import javax.inject.Inject;

/**
 * Created by Le Thuong Quan on 16/11/2016.
 */
public class MainController {
    private final FragmentActivity mFragmentActivity;
    @Nullable
    private final Comic comic;
    @Inject
    MainPhonePresenter mPhonePresenter;
    @Inject
    MainTabletPresenter mTabletPresenter;

    @Inject
    ComicDetailPresenter mComicDetailPresenter;

    private MainController(FragmentActivity mFragmentActivity, Comic comic) {
        this.mFragmentActivity = mFragmentActivity;
        this.comic = comic;
        ((ComicApplication)mFragmentActivity.getApplication()).getApplicationComponent().inject(this);
    }

    public static MainController createMainView(FragmentActivity fragmentActivity, Comic comic) {
        MainController mainController = new MainController(fragmentActivity, comic);
        mainController.initComicView();
        return mainController;
    }

    private void initComicView() {
        if (isTablet()) {
            createTabletView();
        } else {
            createPhoneView();
        }
    }

    private void createPhoneView() {
        MainFragment fragment = createPhoneFragment();
        fragment.setPresenter(mPhonePresenter);
        mPhonePresenter.setView(fragment);

    }

    private void createTabletView() {
        MainFragment mainFragment = createPhoneFragment();
        mainFragment.setPresenter(mTabletPresenter);
        mTabletPresenter.setView(mainFragment);
        ComicDetailFragment detailFragment = createDetailFragment();
        detailFragment.setPresenter(mTabletPresenter);
        mTabletPresenter.setDetailView(detailFragment);
    }

    private MainFragment createPhoneFragment(){
        MainFragment fragment = (MainFragment) getFragmentById(R.id.comic_grid_container);
        if (fragment == null) {
            fragment = MainFragment.newInstance();
            addFragment(R.id.comic_grid_container, fragment);
        }
        return fragment;
    }

    private ComicDetailFragment createDetailFragment(){
        ComicDetailFragment fragment = (ComicDetailFragment) getFragmentById(R.id.comic_detail_container);
        if (fragment == null){
            fragment =ComicDetailFragment.newInstance(comic);
            addFragment(R.id.comic_detail_container, fragment);
        }
        return fragment;
    }

    private void addFragment(int id, Fragment fragment) {
        mFragmentActivity.getSupportFragmentManager().beginTransaction()
                .replace(id, fragment).commit();
    }

    private Fragment getFragmentById(int id) {
        return mFragmentActivity.getSupportFragmentManager().findFragmentById(id);
    }

    private boolean isTablet() {
        return mFragmentActivity.getResources().getBoolean(R.bool.isTablet);
    }
    public void setFilter(ComicFilter filter){
        if (isTablet()){
            mTabletPresenter.setFilter(filter);
        }else {
            mPhonePresenter.setFilter(filter);
        }
    }
    public void reload(){
        if (isTablet()){
            mTabletPresenter.loadComic(null);
        }else {
            mPhonePresenter.loadComic(null);
        }
    }

    public void toggleFavorite() {
        if (isTablet()) {
            mTabletPresenter.toggleFavorite();
        }
    }
}
