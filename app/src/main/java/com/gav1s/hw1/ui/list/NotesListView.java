package com.gav1s.hw1.ui.list;

import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.ui.contextmenu.AdapterItem;
import com.gav1s.hw1.ui.contextmenu.NoteAdapterItem;

import java.util.List;

public interface NotesListView {

    void showNotes(List<AdapterItem> notes);

    void showProgress();

    void hideProgress();

    void showEmpty();

    void hideEmpty();

    void onNoteAdded(NoteAdapterItem adapterItem);

    void onNoteRemoved(NoteData selectedNote);
}
