package com.quanlt.vietcomicmvp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.quanlt.vietcomicmvp.R;
import com.quanlt.vietcomicmvp.util.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ComicGridItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.iv_comic_thumbnail)
    ImageView mComicThumbnail;
    @BindView(R.id.tv_latest_chapter)
    TextView mLatestChapterTitle;
    @BindView(R.id.tv_comic_title)
    TextView mComicTitle;
    @BindView(R.id.rl_footer_view)
    RelativeLayout mFooterView;

    private OnItemClickListener onItemClickListener;

    public ComicGridItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(itemView, getAdapterPosition());
        }
    }
}
