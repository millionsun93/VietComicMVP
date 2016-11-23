package com.quanlt.vietcomicmvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

public class Comic extends RealmObject implements Parcelable {
    @SerializedName("_id")
    @PrimaryKey
    private String id;
    private String title;
    private String status;
    private String source;
    private String description;
    private String thumbnail;
    private String latestChapter;
    private Date updateTime;
    private int viewers;
    private String authors;
    private String categories;
    private RealmList<Chapter> chapters;
    @Expose
    private boolean isFavorite;

    public Comic() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public RealmList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        if (this.chapters == null)
            this.chapters = new RealmList<>();
        this.chapters.clear();
        this.chapters.addAll(chapters);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.status);
        dest.writeString(this.source);
        dest.writeString(this.description);
        dest.writeString(this.thumbnail);
        dest.writeString(this.latestChapter);
        dest.writeLong(this.updateTime != null ? this.updateTime.getTime() : -1);
        dest.writeInt(this.viewers);
        dest.writeString(this.authors);
        dest.writeString(this.categories);
        dest.writeInt(this.isFavorite ? 1 : 0);
    }


    protected Comic(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.status = in.readString();
        this.source = in.readString();
        this.description = in.readString();
        this.thumbnail = in.readString();
        this.latestChapter = in.readString();
        long tmpUpdateTime = in.readLong();
        this.updateTime = tmpUpdateTime == -1 ? null : new Date(tmpUpdateTime);
        this.viewers = in.readInt();
        this.authors = in.readString();
        this.categories = in.readString();
        this.isFavorite = in.readInt() == 1;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel source) {
            return new Comic(source);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    @Override
    public String toString() {
        return "Comic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", isFavorite=" + isFavorite +
                ", latestChapter='" + latestChapter + '\'' +
                '}';
    }
}
