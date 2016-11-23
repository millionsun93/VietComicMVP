package com.quanlt.vietcomicmvp.ui.main;

import com.quanlt.vietcomicmvp.model.Comic;
import com.quanlt.vietcomicmvp.ui.BaseView;
import com.quanlt.vietcomicmvp.util.ComicFilter;

import java.util.List;

/**
 * Created by Le Thuong Quan on 14/11/2016.
 */
public interface MainContract {
    interface View extends BaseView<Presenter>{
        void showLoading(boolean isActive);
        void showComics(List<Comic> comics);
        void addComics(List<Comic> comics);
        void showNoComic();
        void showError(String message);
        void openComic(Comic comic);
    }
    interface Presenter{
        void loadComic(Integer page);
        void loadMore();
        void openComic(Comic comic);
        void setView(View view);
        void setFilter(ComicFilter filter);
        ComicFilter getFilter();
    }
}
