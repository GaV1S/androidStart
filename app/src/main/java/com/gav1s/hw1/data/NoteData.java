package com.gav1s.hw1.data;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

public class NoteData implements Parcelable {

    private String id;

    private String title;

    private String content;

    private Date createdAt;

    public NoteData(String id, String title, String content, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    protected NoteData(Parcel in) {
        id = in.readString();
        title = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteData noteData = (NoteData) o;
        return Objects.equals(id, noteData.id) && Objects.equals(title, noteData.title) && Objects.equals(content, noteData.content) && Objects.equals(createdAt, noteData.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdAt);
    }
}