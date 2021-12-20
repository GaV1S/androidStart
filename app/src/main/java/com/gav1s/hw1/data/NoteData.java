package com.gav1s.hw1.data;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

public class NoteData implements Parcelable {

    private String noteTitle;

    private String noteContent;

    @StringRes
    private final int noteCreateDate;

    public NoteData(String noteTitle, String noteContent, int noteCreateDate) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteCreateDate = noteCreateDate;
    }

    protected NoteData(Parcel in) {
        noteTitle = in.readString();
        noteContent = in.readString();
        noteCreateDate= in.readInt();
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

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public int getNoteCreateDate() {
        return noteCreateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteTitle);
        dest.writeString(noteContent);
        dest.writeInt(noteCreateDate);
    }
}