package com.quanlt.vietcomicmvp.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.quanlt.vietcomicmvp.ComicApplication;
import com.quanlt.vietcomicmvp.R;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.ImageAspect;
import com.quanlt.vietcomicmvp.util.OnFabClickListener;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

public class ComicDetailFragment extends Fragment implements ComicDetailContract.View {

    private static final String ARG_COMIC = "Arg Comic";
    private static final String TAG = ComicDetailFragment.class.getSimpleName();
    @BindView(R.id.iv_comic_thumbnail)
    ImageAspect mComicThumbnail;
    @BindView(R.id.tv_title)
    TextView mComicTitle;
    @BindView(R.id.tg_author)
    TagGroup mAuthorTagGroup;
    @BindView(R.id.tv_update_time)
    TextView mUpdateTime;
    @BindView(R.id.tg_categories)
    TagGroup mCategoriesTagGroup;
    @BindView(R.id.tv_description)
    TextView mDescriptionTextView;
    @BindView(R.id.tv_viewer_count)
    TextView mViewerCount;
    @BindView(R.id.cv_description)
    CardView mDescriptionCardView;
    @BindView(R.id.rv_chapters)
    RecyclerView mChapterRecyclerView;
    @BindView(R.id.cv_chapters)
    CardView mChapterCardView;
    @BindView(R.id.content)
    LinearLayout mContentView;
    private Comic comic;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
    private ChapterAdapter mChapterAdapter;
    private ComicDetailContract.Presenter comicDetailPresenter;
    private OnFabClickListener onFabClickListener;

    public static ComicDetailFragment newInstance(Comic comic) {

        Bundle args = new Bundle();

        ComicDetailFragment fragment = new ComicDetailFragment();
        args.putParcelable(ARG_COMIC, comic);
        fragment.setArguments(args);
        return fragment;
    }

    public ComicDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comic = getArguments().getParcelable(ARG_COMIC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_detail, container, false);
        ButterKnife.bind(this, view);
        ((ComicApplication) getActivity().getApplication()).getApplicationComponent().inject(this);
        if (comic != null) {
            initView();
        }else {
            mContentView.setVisibility(View.GONE);
        }
        initChaptersList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (comicDetailPresenter == null)
            return;
        comicDetailPresenter.setDetailView(this);
        if (mChapterAdapter.getItemCount() == 0 && comic != null) {
            comicDetailPresenter.setComic(comic);
            comicDetailPresenter.loadComicDetail();
        }
        updateChapterCard();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFabClickListener) {
            onFabClickListener = (OnFabClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        onFabClickListener = null;
        super.onDetach();
    }

    private void updateChapterCard() {
        if (mChapterAdapter.getItemCount() == 0) {
            mChapterCardView.setVisibility(View.GONE);
        } else {
            mChapterCardView.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        mContentView.setVisibility(View.VISIBLE);
        mAuthorTagGroup.setTags(comic.getAuthors().split("<\\|>"));
        mCategoriesTagGroup.setTags(comic.getCategories().split("<\\|>"));
        mComicTitle.setText(comic.getTitle());
        mViewerCount.setText(getString(R.string.view_count, comic.getViewers()));
        mUpdateTime.setText(getString(R.string.update_on, format.format(comic.getUpdateTime())));
        Glide.with(this).load(comic.getThumbnail())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mComicThumbnail);
        if (TextUtils.isEmpty(comic.getDescription())) {
            mDescriptionCardView.setVisibility(View.GONE);
        } else {
            mDescriptionCardView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setText(comic.getDescription());
        }
        updateFab(comic.isFavorite());
    }

    private void initChaptersList() {
        mChapterAdapter = new ChapterAdapter();
        mChapterRecyclerView.setAdapter(mChapterAdapter);
        mChapterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mChapterRecyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void setComic(@NonNull Comic comic) {
        this.comic = comic;
        initView();
    }

    @Override
    public void showLoading(boolean active) {

    }

    @Override
    public void showComicDetail(Comic comic) {
        mChapterAdapter.setChapters(comic.getChapters());
        updateChapterCard();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void updateFab(boolean isFavorite) {
        if (onFabClickListener != null)
            onFabClickListener.updateFab(isFavorite);
    }

    @Override
    public void setPresenter(ComicDetailContract.Presenter presenter) {
        comicDetailPresenter = presenter;
    }
}
