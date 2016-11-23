package com.quanlt.vietcomicmvp.ui.main;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.florent37.glidepalette.GlidePalette;
import com.quanlt.vietcomicmvp.R;
import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.util.OnItemClickListener;

import java.util.Collections;
import java.util.List;

public class ComicGridAdapter extends RecyclerView.Adapter<ComicGridItemViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private List<Comic> comics;

    public ComicGridAdapter(Context context) {
        this.context = context;
        comics = Collections.emptyList();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setComics(List<Comic> comics) {
        this.comics.clear();
        notifyDataSetChanged();
        this.comics = comics;
        notifyItemRangeInserted(0, comics.size());
    }

    @Override
    public void onBindViewHolder(ComicGridItemViewHolder viewHolder, int position) {
        final Comic comic = comics.get(position);
        viewHolder.mComicTitle.setText(comic.getTitle());
        viewHolder.mLatestChapterTitle.setText(comic.getLatestChapter());
        Glide.with(context).load(comic.getThumbnail())
                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.colorAccent)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade()
                .listener(GlidePalette.with(comic.getThumbnail())
                        .intoCallBack(palette -> {
                            Palette.Swatch swatch = palette.getVibrantSwatch();
                            if (swatch != null) {
                                viewHolder.mFooterView.setBackgroundColor(swatch.getRgb());
                                viewHolder.mComicTitle.setTextColor(swatch.getBodyTextColor());
                                viewHolder.mLatestChapterTitle.setTextColor(swatch.getBodyTextColor());
                            }
                        }))
                .into(viewHolder.mComicThumbnail);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    @Override
    public ComicGridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_comic, parent, false);
        return new ComicGridItemViewHolder(itemView, onItemClickListener);
    }


    public Comic getItem(int position) {
        return comics.get(position);
    }

    public void addComics(List<Comic> comics) {
        int idx = this.comics.size();
        this.comics.addAll(comics);
        notifyItemRangeInserted(idx, this.comics.size());
    }
}
