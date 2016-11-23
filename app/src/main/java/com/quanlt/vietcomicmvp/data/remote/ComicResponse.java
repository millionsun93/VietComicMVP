package com.quanlt.vietcomicmvp.data.remote;


public class ComicResponse<T> {
    private int error;
    private String description;
    private T data;

    public void setError(int error) {
        this.error = error;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public T getData() {
        return data;
    }
}
