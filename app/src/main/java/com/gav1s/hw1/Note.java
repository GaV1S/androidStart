package com.gav1s.hw1;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private final int id;
    private String title;
    private String content;
    private Date createTime;
    private int importance;

    private static int freeId;

    public Note(String title, String content, Date createTime, int importance) {
        this.id = freeId++;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.importance = importance;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Note setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getStringCreateTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm  dd MMMM yyyy");
        return dateFormat.format(createTime);
    }

    public Note setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public int getImportance() {
        return importance;
    }

    public Note setImportance(int importance) {
        this.importance = importance;
        return this;
    }

    public static int getFreeId() {
        return freeId;
    }
}