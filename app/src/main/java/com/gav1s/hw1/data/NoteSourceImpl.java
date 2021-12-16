package com.gav1s.hw1.data;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {

    private final List<NoteData> notes;

    public NoteSourceImpl() {
        notes = new ArrayList<>(7);
    }

    public NoteSourceImpl init() throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        formatter.setLenient(false);
        notes.add(new NoteData("Title 1", "Content 1",
                formatter.parse("12:45 05.01.2021"), 1));
        notes.add(new NoteData("Title 2", "Content 2",
                formatter.parse("08:14 15.01.2021"), 1));
        notes.add(new NoteData("Title 3", "Content 3",
                formatter.parse("22:05 03.02.2021"), 1));
        notes.add(new NoteData("Title 4", "Content 4",
                formatter.parse("11:30 19.02.2021"), 1));
        notes.add(new NoteData("Title 5", "Content 5",
                formatter.parse("16:17 28.02.2021"), 5));
        notes.add(new NoteData("Title 6", "Content 6",
                formatter.parse("16:49 08.03.2021"), 1));
        notes.add(new NoteData("Title 7", "Content 7",
                formatter.parse("18:24 16.03.2021"), 1));
        return this;
    }

    @Override
    public NoteData getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public void updateNoteData(int position, NoteData noteData) {
        notes.set(position, noteData);
    }

    @Override
    public void deleteNoteData(int position) {
        notes.remove(position);
    }
}