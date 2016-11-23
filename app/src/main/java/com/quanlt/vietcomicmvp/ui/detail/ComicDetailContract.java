package com.quanlt.vietcomicmvp.ui.detail;

import android.support.annotation.NonNull;

import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.ui.BaseView;

/**
 * Created by Le Thuong Quan on 14/11/2016.
 */
public interface ComicDetailContract {
    interface View extends BaseView<Presenter> {

        void setComic(Comic comic);

        void showLoading(boolean active);

        void showComicDetail(@NonNull Comic comic);

        void showError(String message);

        void updateFab(boolean isFavorite);
    }

    interface Presenter {
        void loadComicDetail();
        void setDetailView(View view);
        void setComic(Comic comic);
        Comic getComic();
        void toggleFavorite();
    }
}
