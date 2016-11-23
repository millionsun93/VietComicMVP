package com.quanlt.vietcomicmvp.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.quanlt.vietcomicmvp.R;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.GridItemDecoration;
import com.quanlt.vietcomicmvp.util.OnItemClickListener;
import com.quanlt.vietcomicmvp.util.OnItemSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.view_no_comic)
    RelativeLayout mNoComicView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.comic_grid)
    RecyclerView mRecyclerView;
    private OnItemSelectedListener onItemSelectedListener;
    private ComicGridAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;

    public AbstractFragment() {

    }

    protected abstract void onRefreshAction();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_grid, container, false);
        ButterKnife.bind(this, view);
        initSwipeRefreshLayout();
        initComicsGrid();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateGridLayout();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    protected void initComicsGrid() {
        mAdapter = new ComicGridAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new GridItemDecoration(getActivity(), R.dimen.comic_offset));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int columns = getResources().getInteger(R.integer.movies_columns);
        gridLayoutManager = new GridLayoutManager(getActivity(), columns);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    protected void updateGridLayout() {
        if (mRecyclerView.getAdapter() == null || mRecyclerView.getAdapter().getItemCount() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mNoComicView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoComicView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRefresh() {
        onRefreshAction();
    }

    public ComicGridAdapter getAdapter() {
        return mAdapter;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

}
