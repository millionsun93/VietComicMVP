package com.quanlt.vietcomicmvp.ui.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.quanlt.vietcomicmvp.ComicApplication;
import com.quanlt.vietcomicmvp.R;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.OnFabClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComicDetailActivity extends AppCompatActivity implements OnFabClickListener {
    public static final String ARG_COMIC = "Comic";
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.iv_backdrop)
    ImageView mBackdropImageView;
    @BindView(R.id.fab)
    FloatingActionButton mFavoriteButton;
    private Comic comic;
    @Inject
    ComicDetailPresenter comicDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        ButterKnife.bind(this);
        ((ComicApplication) getApplication()).getApplicationComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
        comic = getIntent().getParcelableExtra(ARG_COMIC);
        mCollapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        mCollapsingToolbar.setTitle(comic.getTitle());
        setTitle("");
        Glide.with(this).load(comic.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(mBackdropImageView);
        ComicDetailFragment comicDetailFragment = (ComicDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        if (comicDetailFragment == null) {
            Log.d(getClass().getSimpleName(),"comic " + comic.isFavorite());
            comicDetailFragment = comicDetailFragment.newInstance(comic);
                    getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, comicDetailFragment)
                    .commit();
        }
        comicDetailFragment.setPresenter(comicDetailPresenter);
    }

    @OnClick(R.id.fab)
    void onClick() {
        comicDetailPresenter.toggleFavorite();
    }

    @Override
    public void updateFab(boolean isFavorite) {
        if (isFavorite) {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);

        }
    }

}
