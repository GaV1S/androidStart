package com.gav1s.hw1.ui.list;

import android.content.Context;

import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.data.NoteSource;

import java.util.List;

public class NotesListPresenter {

    private NotesListView view;

    private NoteSource repository;

    public NotesListPresenter(NotesListView view, NoteSource repository) {
        this.view = view;
        this.repository = repository;
    }

    public void updateNotesList (Context context) {
        List<NoteData> updatedNotesList = repository.getNotes(context);

        view.showNotes(updatedNotesList);
    }
}
