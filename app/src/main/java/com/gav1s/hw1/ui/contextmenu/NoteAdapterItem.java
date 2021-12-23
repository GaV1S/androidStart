package com.gav1s.hw1.ui.contextmenu;

import com.gav1s.hw1.data.NoteData;

public class NoteAdapterItem implements AdapterItem {

    private NoteData noteData;
    private String title;
    private String time;

    public NoteAdapterItem(NoteData noteData, String title, String time) {
        this.noteData = noteData;
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public NoteData getNote() {
        return noteData;
    }
}
