package com.gav1s.hw1.data;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteData implements Parcelable {
    private final int id;
    private String title;
    private String content;
    private Date createTime;
    private int importance;

    private static int freeId;

    public NoteData(String title, String content, Date createTime, int importance) {
        this.id = freeId++;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.importance = importance;
    }

    protected NoteData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        importance = in.readInt();
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public NoteData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteData setContent(String content) {
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

    public NoteData setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public int getImportance() {
        return importance;
    }

    public NoteData setImportance(int importance) {
        this.importance = importance;
        return this;
    }

    public static int getFreeId() {
        return freeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(importance);
    }
}