package com.song.androidstudy.testretrofitrxjava.bean;

import com.google.gson.annotations.SerializedName;

public class Blog {

    @SerializedName("id")
    public long id;

    @SerializedName("date")
    public String date;

    @SerializedName("author")
    public String author;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}